package io.rebolt.core.utils;

import io.rebolt.core.models.IModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtil {

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
   * @sice 0.1.0
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
}
