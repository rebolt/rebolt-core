package io.rebolt.core.utils;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.NotInitializedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_DOUBLE_COLON;
import static io.rebolt.core.constants.Constants.STRING_EQUAL;

public final class ReflectionUtil {

  /**
   * 런타임시 조회된 {@link Method}들을 반영구적으로 보관한다.
   */
  private static final Map<Long, Method> methodMap = Maps.newHashMap();

  /**
   * Generic Class의 Generic Type 조회
   * Runtime 용도로는 사용하지 말 것.
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
   * {@link Method} 추출
   *
   * @param clazz 클래스 타입
   * @param methodName 메소드명
   * @param parameterTypes 메소드 파라미터 타입
   * @return {@link Method}
   * @since 0.1.0
   */
  public static Method extractMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      long key = HashUtil.djb2Hash(clazz.getName() + STRING_DOUBLE_COLON + methodName + STRING_EQUAL + HashUtil.deepHash((Object[]) parameterTypes));
      Method method = methodMap.get(key);
      if (method == null) { // thread-unsafe, 하지만 영향은 없다
        method = clazz.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        methodMap.put(key, method);
      }
      return method;
    } catch (NoSuchMethodException e) {
      throw new NotInitializedException(e);
    }
  }

  /**
   * {@link Method} 호출
   *
   * @param method {@link Method}
   * @param object 클래스 인스턴스는 this, 전역 메소드는 null
   * @param args 메소드 파라미터
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <R> R invoke(Method method, Object object, Object... args) {
    try {
      return (R) method.invoke(object, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new NotInitializedException(e);
    }
  }
}
