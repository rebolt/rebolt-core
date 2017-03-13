package io.rebolt.core.utils;

import io.rebolt.core.exceptions.NullPointerException;
import io.rebolt.core.models.IModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtil {

  // region isNull

  /**
   * 오브젝트의 Null 검사
   *
   * @param object {@link Object}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isNull(Object object) {
    return object == null;
  }

  /**
   * 오브젝트 배열의 Null 검사
   *
   * @param objects {@link Object} 배열
   * @return 입력값 중 1개라도 null이라면 true
   * @since 0.1.0
   */
  public static boolean isOrNull(Object... objects) {
    if (objects == null) {
      return true;
    }
    for (Object object : objects) {
      if (isNull(object)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 입력값이 null이라면 {@link NullPointerException}
   *
   * @param object {@link Object}
   * @since 0.1.0
   */
  public static void requireNonNull(Object object) {
    if (isNull(object)) {
      throw new NullPointerException();
    }
  }

  /**
   * 입력값 중 1개라도 null이라면 {@link NullPointerException}
   *
   * @param objects {@link Object} 배열
   * @since 0.1.0
   */
  public static void requireNonNull(Object... objects) {
    if (isOrNull(objects)) {
      throw new NullPointerException();
    }
  }

  /**
   * 오브젝트 배열의 Null 검사
   *
   * @param objects {@link Object} 배열
   * @return 입력값이 모두 null일때 true
   * @since 0.1.0
   */
  public static boolean isAndNull(Object... objects) {
    if (objects == null) {
      return true;
    }
    for (Object object : objects) {
      if (!isNull(object)) {
        return false;
      }
    }
    return true;
  }

  // endregion

  // region isEmpty

  /**
   * isEmtpy
   *
   * @param model {@link IModel}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(IModel model) {
    return model == null || model.isEmpty();
  }

  /**
   * isNotEmpty
   *
   * @param model {@link IModel}
   * @return true/false
   * @since 0.1.9
   */
  public static boolean isNotEmpty(IModel model) {
    return !isEmpty(model);
  }

  /**
   * isEmpty
   *
   * @param chars {@link CharSequence}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(CharSequence chars) {
    return chars == null || chars.length() == 0;
  }

  /**
   * isEmpty
   *
   * @param string {@link String}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(String string) {
    return StringUtil.isNullOrEmpty(string);
  }

  /**
   * isEmpty
   *
   * @param list {@link List}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(List list) {
    return list == null || list.isEmpty();
  }

  /**
   * isEmpty
   *
   * @param map {@link Map}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  /**
   * isEmpty
   *
   * @param objects {@link Object} 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(Object[] objects) {
    return objects == null || objects.length == 0;
  }

  /**
   * isEmpty
   *
   * @param bytes byte 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(byte[] bytes) {
    return bytes == null || bytes.length == 0;
  }

  /**
   * isEmpty
   *
   * @param ints int 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(int[] ints) {
    return ints == null || ints.length == 0;
  }

  /**
   * isEmpty
   *
   * @param longs long 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(long[] longs) {
    return longs == null || longs.length == 0;
  }

  /**
   * isEmpty
   *
   * @param doubles double 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(double[] doubles) {
    return doubles == null || doubles.length == 0;
  }

  /**
   * isEmpty
   *
   * @param shorts short 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(short[] shorts) {
    return shorts == null || shorts.length == 0;
  }

  /**
   * isEmpty
   *
   * @param floats float 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(float[] floats) {
    return floats == null || floats.length == 0;
  }

  /**
   * isEmpty
   *
   * @param chars char 배열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isEmpty(char[] chars) {
    return chars == null || chars.length == 0;
  }

  /**
   * thenNonEmpty
   *
   * @param model {@link IModel}을 상속받은 제네릭 인스턴스
   * @param consumer {@link Consumer}
   * @param <T> {@link IModel}을 상속받은 제네릭 타입
   * @since 0.1.0
   */
  public static <T extends IModel> void thenNonEmpty(T model, Consumer<? super T> consumer) {
    if (!isEmpty(model)) {
      consumer.accept(model);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param string {@link String}
   * @param consumer {@link Consumer}
   * @since 0.1.0
   */
  public static void thenNonEmpty(String string, Consumer<String> consumer) {
    if (!isEmpty(string)) {
      consumer.accept(string);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param list {@link List}
   * @param consumer {@link Consumer}
   * @since 0.1.0
   */
  public static void thenNonEmpty(List list, Consumer<? super List> consumer) {
    if (!isEmpty(list)) {
      consumer.accept(list);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param map {@link Map}
   * @param consumer {@link Consumer}
   * @since 0.1.0
   */
  public static void thenNonEmpty(Map map, Consumer<? super Map> consumer) {
    if (!isEmpty(map)) {
      consumer.accept(map);
    }
  }

  // endregion

  // region nullGuard

  /**
   * nullGuard
   *
   * @param map {@link Map}
   * @param <K> 키 제네릭 타입
   * @param <V> 값 제네릭 타입
   * @return 만약 null이라면, Empty {@link Map}이 반환된다.
   * @since 0.1.0
   */
  public static <K, V> Map<K, V> nullGuard(Map<K, V> map) {
    return map == null ? Collections.emptyMap() : map;
  }

  /**
   * nullGuard
   *
   * @param list {@link List}
   * @param <T> {@link List} 제네릭 타입
   * @return 만약 null이라면, Empty {@link List}가 반환된다.
   * @since 0.1.0
   */
  public static <T> List<T> nullGuard(List<T> list) {
    return list == null ? Collections.emptyList() : list;
  }

  /**
   * nullGuard
   *
   * @param set {@link Set}
   * @param <T> {@link Set} 제네릭 타입
   * @return 만약 null이라면, Emtpy {@link Set}이 반환된다.
   * @since 0.1.0
   */
  public static <T> Set<T> nullGuard(Set<T> set) {
    return set == null ? Collections.emptySet() : set;
  }

  // endregion
}
