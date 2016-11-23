package io.rebolt.core.utils;

import io.rebolt.core.exceptions.NullPointerException;
import io.rebolt.core.models.IModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtil {

  // region isNull

  /**
   * 입력값이 null이라면 true
   *
   * @since 0.1.0
   */
  public static boolean isNull(Object object) {
    return object == null;
  }

  /**
   * 입력값이 null이라면 {@link NullPointerException}
   *
   * @since 0.1.0
   */
  public static void requireNull(Object object) {
    if (isNull(object)) {
      throw new NullPointerException();
    }
  }

  /**
   * 입력값 중 1개라도 null이라면 true
   *
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
   * 입력값 중 1개라도 null이라면 {@link NullPointerException}
   *
   * @since 0.1.0
   */
  public static void requireOrNull(Object... objects) {
    if (isOrNull(objects)) {
      throw new NullPointerException();
    }
  }

  /**
   * 입력값이 모두 null일때 true
   *
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

  /**
   * 입력값이 모두 null이면 {@link NullPointerException} 발생
   *
   * @since 0.1.0
   */
  public static void requireAndNull(Object... objects) {
    if (isAndNull(objects)) {
      throw new NullPointerException();
    }
  }

  // endregion

  // region isEmpty

  /**
   * isEmtpy
   *
   * @param model {@link IModel}
   * @since 0.1.0
   */
  public static boolean isEmpty(IModel model) {
    return model == null || model.isEmpty();
  }

  /**
   * isEmpty
   *
   * @param chars {@link CharSequence}
   * @since 0.1.0
   */
  public static boolean isEmpty(CharSequence chars) {
    return chars == null || chars.length() == 0;
  }

  /**
   * isEmpty
   *
   * @param string {@link String}
   * @since 0.1.0
   */
  public static boolean isEmpty(String string) {
    return StringUtil.isNullOrEmpty(string);
  }

  /**
   * isEmpty
   *
   * @param list {@link List}
   * @since 0.1.0
   */
  public static boolean isEmpty(List list) {
    return list == null || list.isEmpty();
  }

  /**
   * isEmpty
   *
   * @param map {@link Map}
   * @since 0.1.0
   */
  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  // endregion

  // region nullGuard

  /**
   * nullGuard
   *
   * @param map 만약 null이라면, Empty {@link Map}이 반환된다.
   * @since 0.1.0
   */
  public static <K, V> Map<K, V> nullGuard(Map<K, V> map) {
    return map == null ? Collections.emptyMap() : map;
  }

  /**
   * nullGuard
   *
   * @param list 만약 null이라면, Empty {@link List}가 반환된다.
   * @since 0.1.0
   */
  public static <T> List<T> nullGuard(List<T> list) {
    return list == null ? Collections.emptyList() : list;
  }

  /**
   * nullGuard
   *
   * @param set 만약 null이라면, Emtpy {@link Set}이 반환된다.
   * @since 0.1.0
   */
  public static <T> Set<T> nullGuard(Set<T> set) {
    return set == null ? Collections.emptySet() : set;
  }

  // endregion
}
