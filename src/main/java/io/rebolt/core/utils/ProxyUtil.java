/*
 * Copyright 2017 The Rebolt Framework
 *
 * The Rebolt Framework licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package io.rebolt.core.utils;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.any;

/**
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProxyUtil {
  private static final String _className = "io.rebolt.core.ProxyInterceptor";
  private static final ByteBuddy _buddy = new ByteBuddy(ClassFileVersion.JAVA_V8);
  private static final Map<Class, Class> _proxyMap = Maps.newHashMap();
  private static final Object _lock = new Object();

  /**
   * Proxy 대상클래스내 Method 호출시 interceptor 메소드의 제어를 받는다.
   * Proxy 대상클래스를 내부적으로 캐싱하여 사용한다. (싱글턴)
   *
   * @param interceptor {@link Class} extends {@link AbstractIterceptor} Interceptor 클래스
   * @param targetClass {@link Class} Proxy 대상 클래스 (유저 클래스)
   * @param <T> 첫번째 제네릭 타입, {@link AbstractIterceptor}를 상속받아야 한다.
   * @param <R> 두번째 제네릭 타입
   * @return Proxy 클래스
   * @since 0.1.0
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static <T extends AbstractIterceptor, R> R getInterceptorClass(Class<T> interceptor, Class<R> targetClass) {
    Class proxyClass = _proxyMap.get(targetClass);
    if (proxyClass == null) {
      synchronized (_lock) {
        if (proxyClass == null) {
          DynamicType.Unloaded<R> dynamicType =
              _buddy.subclass(targetClass)
                  .name(_className)
                  .method(any()).intercept(MethodDelegation.to(ClassUtil.newInstance(interceptor)))
                  .make();
          proxyClass = dynamicType.load(targetClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
          _proxyMap.put(targetClass, proxyClass);
        }
      }
    }
    return (R) ClassUtil.getSingleton(proxyClass);
  }

  /**
   * Proxy 대상클래스내 Method 호출시 interceptor 메소드의 제어를 받는다.
   * Proxy 대상클래스를 매번 생성한다.
   *
   * @param interceptor {@link Class} extends {@link AbstractIterceptor} Interceptor 클래스
   * @param targetClass {@link Class} Proxy 대상 클래스 (유저 클래스)
   * @param <T> 첫번째 제네릭 타입, {@link AbstractIterceptor}를 상속받아야 한다.
   * @param <R> 두번째 제네릭 타입
   * @return Proxy 클래스
   * @since 0.1.0
   */
  @SuppressWarnings("ConstantConditions")
  public static <T extends AbstractIterceptor, R> R newInterceptorClass(Class<T> interceptor, Class<R> targetClass) {
    return ClassUtil.newInstance(
        _buddy.subclass(targetClass)
            .name(_className)
            .method(any()).intercept(MethodDelegation.to(ClassUtil.newInstance(interceptor)))
            .make()
            .load(targetClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded());
  }

  /**
   * AbstractIterceptor
   * <p>
   * ProxyUtil에서 사용하는 Interceptor 클래스는 아래를 상속받아 사용한다.
   * RuntimeType 어노테이션은 상속된 클래스에서 반드시 수동으로 추가해야 한다.
   */
  public static abstract class AbstractIterceptor {
    @RuntimeType
    public abstract Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method, @AllArguments Object[] args) throws Exception;
  }
}
