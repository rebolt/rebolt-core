package io.rebolt.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 자바 오브젝트를 활용해 해시코드를 생성한다
 */
public final class HashUtil {

  private static final long NO_PARAM_KEY = Long.MIN_VALUE;
  private static final long NULL_PARAM_KEY = 53L;

  /**
   * DJB2 해시코드
   *
   * @param value 변환할 {@link String}
   * @return Djb2 해시코드 (음수포함)
   */
  public static long djb2Hash(String value) {
    long hash = 5381L;
    if (!StringUtil.isNullOrEmpty(value)) {
      for (char entity : value.trim().toCharArray()) {
        hash = ((hash << 5) + hash) + entity;
      }
    }
    return hash;
  }

  /**
   * Deep 해시코드
   *
   * @param objects 해시코드를 생성하고자 하는 {@link Object} 배열
   *                {@link Collection} 또는 {@link Map}도 배열 내 {@link Object}로 사용가능하다
   * @return 해시코드 (음수포함)
   */
  public static long deepHash(Object... objects) {
    if (objects.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (Object object : objects) {
      if (object instanceof List) {
        long sum = 0;
        for (Object entry : (Collection) object) {
          sum ^= entry.hashCode();
        }
        hashCode = 31L * hashCode + sum;
      } else if (object instanceof Map) {
        long sum = 0;
        for (Object entry : ((Map) object).values()) {
          sum ^= entry.hashCode();
        }
        hashCode = 31L * hashCode + sum;
      } else {
        hashCode = 31L * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
      }
    }
    return hashCode;
  }

}
