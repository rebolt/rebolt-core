package io.rebolt.core.utils;

import com.google.common.collect.Maps;

import java.lang.reflect.Constructor;
import java.util.Map;

public final class ClassUtil {

  /**
   * 싱글턴 맵
   * <p>
   * 생성자 파라미터가 없는 싱글턴의 경우, Class.getName()을 맵의 키로 사용한다
   * 생성자 파라미터가 있을 경우, {@link HashUtil}의 deepHash 메소드를 이용해 키를 생성한다
   * <p>
   * 키: 클래스명 (Class.getName())
   * 값: 클래스 인스턴스
   */
  private static final Map<String, Object> singletonMap = Maps.newHashMap();

  private static final String KEY_DELIMETER = "#";

  /**
   * 싱글턴 생성기
   *
   * @param clazz 싱글턴 클래스 타입
   * @return 싱글턴 인스턴스
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static <T> T getSingleton(Class<T> clazz) {
    String key = clazz.getName();
    T instance = (T) singletonMap.get(key);
    if (instance == null) {
      synchronized (clazz.getClassLoader()) {
        if (instance == null) {
          try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            instance = declaredConstructor.newInstance();
            singletonMap.put(key, instance);
          } catch (Exception ex) {
            LogUtil.error(ex);
            instance = null;
          }
        }
      }
    }
    return instance;
  }

  /**
   * 싱글턴 생성기 (파라미터 포함)
   *
   * @param clazz         싱글턴 클래스 타입
   * @param arguments     파라미터 목록
   * @param argumentTypes 파라미터 클래스 타입 목록 (파라미터 목록과 길이가 같아야 한다)
   * @return 싱글턴 인스턴스
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static <T> T getSingleton(Class<T> clazz, Object[] arguments, Class<?>[] argumentTypes) {
    String key = clazz.getName() + KEY_DELIMETER + HashUtil.deepHash(arguments);
    T instance = (T) singletonMap.get(key);
    if (instance == null) {
      synchronized (clazz.getClassLoader()) {
        if (instance == null) {
          try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(argumentTypes);
            declaredConstructor.setAccessible(true);
            instance = declaredConstructor.newInstance(arguments);
            singletonMap.put(key, instance);
          } catch (Exception ex) {
            LogUtil.error(ex);
            instance = null;
          }
        }
      }
    }
    return instance;
  }

  /**
   * 인스턴스 생성기
   *
   * @param clazz 클래스 타입
   * @return 클래스 인스턴스
   */
  public static <T> T newInstance(Class<T> clazz) {
    try {
      Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
      declaredConstructor.setAccessible(true);
      return declaredConstructor.newInstance();
    } catch (Exception ex) {
      LogUtil.error(ex);
      return null;
    }
  }

  /**
   * {@link String} 타입 변환
   *
   * @param clazz 변환하고자 하는 클래스 타입
   * @param value 데이터 값
   * @return 변환된 클래스 인스턴스
   */
  public static <T> T cast(Class<T> clazz, String value) {
    switch (clazz.getSimpleName()) {
      case "Integer":
        return clazz.cast(Integer.valueOf(value));
      case "Long":
        return clazz.cast(Long.valueOf(value));
      case "Boolean":
        return clazz.cast(Boolean.valueOf(value));
      case "Double":
        return clazz.cast(Double.valueOf(value));
      case "Float":
        return clazz.cast(Float.valueOf(value));
      case "Short":
        return clazz.cast(Short.valueOf(value));
      case "String":
        return clazz.cast(value);
      default:
        return clazz.cast(String.valueOf(value));
    }
  }

}