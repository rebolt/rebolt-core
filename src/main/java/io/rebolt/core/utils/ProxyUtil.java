package io.rebolt.core.utils;

import com.google.common.collect.Maps;
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
 * Proxy 클래스 생성
 */
public final class ProxyUtil {

  private static final ByteBuddy buddy = new ByteBuddy(ClassFileVersion.JAVA_V8);
  private static final Map<Class, Class> proxyMap = Maps.newHashMap();

  /**
   * Proxy 대상클래스내 Method 호출시 interceptor 메소드의 제어를 받는다.
   * Proxy 대상클래스를 내부적으로 캐싱하여 사용한다. (싱글턴)
   *
   * @param interceptor {@link Class} extends {@link AbstractIterceptor} Interceptor 클래스
   * @param targetClass {@link Class<R>} Proxy 대상 클래스 (유저 클래스)
   * @return Proxy 클래스
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static <T extends AbstractIterceptor, R> Class<? extends R> getInterceptorClass(Class<T> interceptor, Class<R> targetClass) {
    Class<?> proxyClass = proxyMap.get(targetClass);
    if (proxyClass == null) {
      synchronized (proxyMap) {
        if (proxyClass == null) {
          DynamicType.Unloaded<R> dynamicType = buddy.subclass(targetClass).method(any()).intercept(MethodDelegation.to(ClassUtil.getSingleton(interceptor))).make();
          proxyClass = dynamicType.load(targetClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
          proxyMap.put(targetClass, proxyClass);
        }
      }
    }
    return (Class<? extends R>) proxyClass;
  }

  /**
   * Proxy 대상클래스내 Method 호출시 interceptor 메소드의 제어를 받는다.
   * Proxy 대상클래스를 매번 생성한다.
   *
   * @param interceptor {@link Class} extends {@link AbstractIterceptor} Interceptor 클래스
   * @param targetClass {@link Class<R>} Proxy 대상 클래스 (유저 클래스)
   * @return Proxy 클래스
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static <T extends AbstractIterceptor, R> Class<? extends R> newInterceptorClass(Class<T> interceptor, Class<R> targetClass) {
    DynamicType.Unloaded<R> dynamicType = buddy.subclass(targetClass).method(any()).intercept(MethodDelegation.to(ClassUtil.newInstance(interceptor))).make();
    return dynamicType.load(targetClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
  }

  /**
   * AbstractIterceptor
   *
   * ProxyUtil에서 사용하는 Interceptor 클래스는 아래를 상속받아 사용한다.
   * {@link RuntimeType} 어노테이션은 상속된 클래스에서 반드시 수동으로 추가해야 한다.
   */
  public static abstract class AbstractIterceptor {
    @RuntimeType
    public abstract Object intercept(@SuperCall Callable<?> superMethod, @Origin Method method, @AllArguments Object[] args) throws Exception;
  }

}
