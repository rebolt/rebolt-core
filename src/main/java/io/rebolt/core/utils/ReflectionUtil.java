package io.rebolt.core.utils;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.NotInitializedException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_AND;
import static io.rebolt.core.constants.Constants.STRING_DOUBLE_COLON;

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
   * <p>
   * 가능한 런타임시에 사용하지 않는다.
   * 런타임시 사용할 경우 x8의 오버헤드가 발생한다.
   *
   * @param clazz 클래스 타입
   * @param context 클래스 인스턴스
   * @param methodName 메소드명
   * @param parameterTypes 메소드 파라미터 타입
   * @return {@link MethodHandle}
   * @since 0.1.0
   */
  public static MethodHandle extractMethodHandle(Class<?> clazz, Object context, String methodName, Class<?>... parameterTypes) {
    ObjectUtil.requireNonNull(clazz, context, methodName);
    try {
      long key = makeMethodKey(clazz, methodName, parameterTypes);
      MethodHandle methodHandle = methodHandleMap.get(key);
      if (methodHandle == null) { // thread-unsafe, 하지만 영향은 없다.
        Method method = extractMethod(clazz, methodName, parameterTypes);
        methodHandle = MethodHandles.lookup().unreflect(method).bindTo(context);
        methodHandleMap.put(key, methodHandle);
      }
      return methodHandle;
    } catch (IllegalAccessException e) {
      throw new NotInitializedException(e);
    }
  }

  /**
   * {@link Method} 추출
   * <p>
   * 가능한 런타임시에 사용하지 않는다.
   * 런타임시 사용할 경우 x8의 오버헤드가 발생한다.
   * {@link MethodHandle} 방식에 비해 x3이상 느리다.
   *
   * @param clazz 클래스 타입
   * @param methodName 메소드명
   * @param parameterTypes 메소드 파라미터 타입
   * @return {@link Method}
   * @since 0.1.0
   */
  public static Method extractMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    ObjectUtil.requireNonNull(clazz, methodName);
    try {
      long key = makeMethodKey(clazz, methodName, parameterTypes);
      Method method = methodMap.get(key);
      if (method == null) {
        method = clazz.getMethod(methodName, parameterTypes);
      }
      return method;
    } catch (NoSuchMethodException e) {
      throw new NotInitializedException(e);
    }
  }

  private static long makeMethodKey(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    if (parameterTypes == null || parameterTypes.length == 0) {
      return HashUtil.djb2Hash(clazz.getName() + STRING_DOUBLE_COLON + methodName);
    } else {
      return HashUtil.djb2Hash(clazz.getName() + STRING_DOUBLE_COLON + methodName + STRING_AND + HashUtil.deepHash((Object[]) parameterTypes));
    }
  }

  /**
   * {@link Method} 호출
   * <p>
   * {@link MethodHandle} 호출 방식에 비해 x3이상 느리다.
   *
   * @param method {@link Method}
   * @param context 클래스 메소드는 this, 전역 메소드인 경우 null
   * @param args 메소드 파라미터
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <R> R invokeMethod(Method method, Object context, Object... args) {
    ObjectUtil.requireNonNull(method);
    try {
      return (R) method.invoke(context, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new NotInitializedException(e);
    }
  }

}

