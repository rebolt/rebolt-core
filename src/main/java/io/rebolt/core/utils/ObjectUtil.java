/*
 * Copyright 2017 The Rebolt Framework
 *
 * The Rebolt Framework licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package io.rebolt.core.utils;

import io.rebolt.core.exceptions.NullPointerException;
import io.rebolt.core.models.IModel;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

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
   * @param set {@link Set}
   * @return true 또는 false
   * @since 0.2.25
   */
  public static boolean isEmpty(Set set) {
    return set == null || set.isEmpty();
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

  /**
   * thenNonEmpty
   *
   * @param set {@link Set}
   * @param consumer {@link Consumer}
   * @since 0.2.25
   */
  public static void thenNonEmpty(Set set, Consumer<? super Set> consumer) {
    if (!isEmpty(set)) {
      consumer.accept(set);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param dateTime {@link ZonedDateTime}
   * @param consumer {@link Consumer}
   * @since 0.1.14
   */
  public static void thenNonEmpty(ZonedDateTime dateTime, Consumer<? super ZonedDateTime> consumer) {
    if (!isNull(dateTime)) {
      consumer.accept(dateTime);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param date {@link Date}
   * @param consumer {@link Consumer}
   * @since 0.2.4
   */
  public static void thenNonEmpty(Date date, Consumer<? super Date> consumer) {
    if (!isNull(date)) {
      consumer.accept(date);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param bool {@link Boolean}
   * @param consumer {@link Consumer}
   * @since 0.1.14
   */
  public static void thenNonEmpty(Boolean bool, Consumer<? super Boolean> consumer) {
    if (!isNull(bool)) {
      consumer.accept(bool);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param number {@link Long}
   * @param consumer {@link Consumer}
   * @since 0.2.25
   */
  public static void thenNonEmpty(Long number, Consumer<? super Long> consumer) {
    if (!isNull(number)) {
      consumer.accept(number);
    }
  }

  /**
   * thenNonEmpty
   *
   * @param integer {@link Integer}
   * @param consumer {@link Consumer}
   * @since 0.2.25
   */
  public static void thenNonEmpty(Integer integer, Consumer<? super Integer> consumer) {
    if (!isNull(integer)) {
      consumer.accept(integer);
    }
  }

  /**
   * thenConatiains
   * <p>
   * 전달받은 Map내에 Key를 포함하고 있다면 조회된 Value를 이용해 Consumer를 실행한다
   *
   * @param map 전달 받은 Map 인스턴스
   * @param key Map 내 포함되어야 할 key
   * @param consumer 조건 만족 후 실행될 구문
   * @param <T> first generic
   * @param <V> second generic
   * @since 0.1.13
   */
  public static <T, V> void thenContains(Map<T, V> map, T key, Consumer<? super V> consumer) {
    if (!isEmpty(map) && map.containsKey(key)) {
      consumer.accept(map.get(key));
    }
  }

  /**
   * thenExists
   *
   * @param map 전달 받은 Map 인스턴스
   * @param key Map 내 포함되어야 할 key
   * @param consumer 조건 만족 후 실행될 구문
   * @param <T> first generic
   * @param <V> second generic
   * @since 0.2.6
   */
  public static <T, V> void thenExists(Map<T, V> map, T key, Consumer<? super V> consumer) {
    if (!isEmpty(map) && map.containsKey(key)) {
      V value = map.get(key);
      if (value != null) {
        consumer.accept(value);
      }
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

  // region converter

  /**
   * 리스트 컨버터
   *
   * @param from 원본 리스트
   * @param func 변경식
   * @return 변경식에 의해 변경된 리스트
   * @since 0.2.20
   */
  public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
    return from.stream().map(func).collect(Collectors.toList());
  }

  /**
   * 배열 컨버터
   *
   * @param from 원본 배열
   * @param func 변경식
   * @param generator 배열 생성식 (테스트코드 참조)
   * @return 변경식에 의해 변경된 배열
   * @since 0.2.20
   */
  public static <T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator) {
    return Arrays.stream(from).map(func).toArray(generator);
  }

  // endregion
}
