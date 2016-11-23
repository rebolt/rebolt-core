package io.rebolt.core.utils;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.NotInitializedException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_DOUBLE_COLON;
import static io.rebolt.core.constants.Constants.STRING_EQUAL;

public final class ReflectionUtil {

  /**
   * 런타임시 조회된 {@link MethodHandle}들을 반영구적으로 보관한다.
   */
  private static final Map<Long, MethodHandle> methodHandleMap = Maps.newHashMap();

  /**
   * 런타임시 조회된 {@link Method}들을 반영구적으로 보관한다.
   */
  private static final Map<Long, Method> methodMap = Maps.newHashMap();

  /**
   * Generic Class의 Generic Type 조회
   * <p>
   * 일반적인 클래스에서는 Type조회가 되지 않는다. 상위 클래스로부터 Generic을 상속받아
   * 자료형을 명시적으로 선언한 클래스부터 typeFinder를 사용할 수 있다.
   * <p>
   * 예)
   * {@code public class OkHttp3Template extends AbstractTemplate<Request, Response>}
   * 위와 같이 OkHttp3Template 클래스에서 정의된 Request, Response Type을 찾는데 사용된다.
   * <p>
   * ** 런타임용으로는 사용하지 말 것
   *
   * @param clazz Generic 클래스 타입
   * @param index Generic 인덱스 (첫번째 파라미터라면 0, 두번째 파라미터는 1, ...)
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <R> Class<R> typeFinder(Class<?> clazz, int index) {
    return (Class<R>) ReflectionUtil.recursiveTypeArgumentFinder(clazz.getGenericSuperclass(), index);
  }

  private static Type recursiveTypeArgumentFinder(Type type, int index) {
    if (type instanceof Class) {
      return recursiveTypeArgumentFinder(((Class) type).getGenericSuperclass(), index);
    } else if (type instanceof ParameterizedType) {
      return ((ParameterizedType) type).getActualTypeArguments()[index];
    } else {
      return null;
    }
  }

  /**
   * {@link MethodHandle} 추출
   *
   * @param clazz 클래스 타입
   * @param methodName 메소드명
   * @param returnType 메소드 리턴 타입
   * @param parameterTypes 메소드 파라미터 타입
   * @return {@link MethodHandle}
   * @since 0.1.0
   */
  public static MethodHandle extractMethodHandle(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... parameterTypes) {
    ObjectUtil.requireOrNull(clazz, methodName, returnType);
    try {
      long key = HashUtil.djb2Hash(clazz.getName() + STRING_DOUBLE_COLON + methodName + STRING_EQUAL + HashUtil.deepHash((Object[]) parameterTypes));
      MethodHandle methodHandle = methodHandleMap.get(key);
      if (methodHandle == null) { // thread-unsafe, 하지만 영향은 없다.
        methodHandle = MethodHandles.lookup().findVirtual(clazz, methodName, MethodType.methodType(returnType, parameterTypes));
        methodHandleMap.put(key, methodHandle);
      }
      return methodHandle;
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new NotInitializedException(e);
    }
  }

  /**
   * {@link MethodHandle} 호출
   *
   * @param method {@link MethodHandle}
   * @param context 클래스 인스턴스
   * @param args 메소드 파라미터
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <R> R invoke(MethodHandle method, Object context, Object... args) {
    ObjectUtil.requireOrNull(method);
    try {
      MethodType invocationType = MethodType.genericMethodType(args.length);
      return (R) method.bindTo(context).invokeExact(method.asType(invocationType), args);
    } catch (Throwable t) {
      throw new NotInitializedException(t);
    }
  }
}

/*
MethodType invocationType = MethodType.genericMethodType(arguments == null ? 0 : arguments.length);
        return invocationType.invokers().spreadInvoker(0).invokeExact(asType(invocationType), arguments);
 */
