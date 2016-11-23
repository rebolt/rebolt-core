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

public final class ReflectionUtil {

  /**
   * 런타임시 조회된 {@link MethodHandle}들을 반영구적으로 보관한다.
   *
   * Table 형태로 구성된 이중맵.
   * Class : 클래스 타입
   * String : 메소드명
   * MethodHandle : {@link MethodHandle}
   */
  private static final Map<Class, Map<String, MethodHandle>> methodFactory = Maps.newHashMap();

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
   * 메소드명이 동일한 2개이상의 메소드가 클래스내에 있다면 파라미터 타입에 무관하게 최초 1개만 추출 가능
   * 만약, 2개이상의 메소드를 파라미터 타입을 구분해 추출하고 싶다면 extractMethod 사용 (성능저하)
   *
   * @param clazz 클래스 타입
   * @param context 클래스 인스턴스
   * @param methodName 메소드명
   * @param parameterTypes 메소드 파라미터 타입
   * @return {@link MethodHandle}
   * @since 0.1.0
   */
  public static MethodHandle extractMethodHandle(Class clazz, Object context, String methodName, Class... parameterTypes) {
    ObjectUtil.requireNonNull(clazz, context, methodName);
    try {
      Map<String, MethodHandle> methodMap = getMethodMap(clazz);
      MethodHandle methodHandle = methodMap.get(methodName);
      if (methodHandle == null) {
        Method method = extractMethod(clazz, methodName, parameterTypes);
        methodHandle = MethodHandles.lookup().unreflect(method).bindTo(context);
        methodMap.put(methodName, methodHandle);
      }
      return methodHandle;
    } catch (IllegalAccessException e) {
      throw new NotInitializedException(e);
    }
  }

  @SuppressWarnings("ConstantConditions")
  private static Map<String, MethodHandle> getMethodMap(Class clazz) {
    Map<String, MethodHandle> methodMap = methodFactory.get(clazz);
    if (methodMap == null) {
      synchronized (methodFactory) {
        if (methodMap == null) {
          methodMap = Maps.newHashMap();
          methodFactory.put(clazz, methodMap);
        }
      }
    }
    return methodMap;
  }

  /**
   * {@link Method} 추출
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
      return clazz.getMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
      throw new NotInitializedException(e);
    }
  }

  /**
   * {@link Method} 호출
   * <p>
   * {@link MethodHandle} 직접 호출에 비해 3배 이상 느리다.
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

