package io.rebolt.core.utils;

import com.google.common.collect.Lists;
import io.rebolt.core.exceptions.IllegalParameterException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.StringJoiner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {
  private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private static final byte[] hexFilters = {0, 10, 11, 12, 13, 14, 15};
  private static final char[] alphaDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  // region isNullOrEmpty

  /**
   * {@link String}에 대한 null 또는 empty 반복 검사
   *
   * @param value {@link String}
   * @since 0.1.0
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * {@link String}에 대한 null 또는 empty 반복 검사
   *
   * @param values {@link String} 배열
   * @since 0.1.0
   */
  public static boolean isNullOrEmpty(String... values) {
    for (String value : values) {
      if (isNullOrEmpty(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@link StringBuilder}에 대한 null 또는 empty 검사
   *
   * @param value {@link StringBuilder}
   * @since 0.1.0
   */
  public static boolean isNullOrEmpty(StringBuilder value) {
    return value == null || value.length() == 0;
  }

  /**
   * Trim된 {@link String}에 대한 null 또는 empty 검사
   *
   * @param value Trim {@link String}
   * @since 0.1.0
   */
  public static boolean isNullOrEmptyWithTrim(String value) {
    return value == null || isNullOrEmpty(value.trim());
  }

  /**
   * {@link String}에 대한 숫자검사
   *
   * @param value 숫자로된 문자열
   * @since 0.1.0
   */
  public static boolean isNumeric(String value) {
    return !isNullOrEmpty(value) && !value.chars().anyMatch(entity -> entity < 48 || entity > 57);
  }
  // endregion

  // region hexString

  /**
   * HexString to ByteArray
   *
   * @param hexString Hex string
   * @return Byte array
   * @since 0.1.0
   */
  public static byte[] hexToByteArray(String hexString) {
    if (hexString == null || hexString.length() % 2 != 0) {
      return new byte[0];
    }
    byte[] charBuffer = new byte[hexString.length() / 2];
    int index;
    int head, tail;
    for (int i = 0; i < charBuffer.length; i++) {
      index = i * 2;
      head = hexString.charAt(index);
      tail = hexString.charAt(index + 1);
      charBuffer[i] = (byte) (((head & 0x40) == 0 ? head & 0x0f : hexFilters[head & 0x0f]) * 16 | ((tail & 0x40) == 0 ? tail & 0x0f : hexFilters[tail & 0x0f]));
    }
    return charBuffer;
  }

  /**
   * ByteArray to HexString
   *
   * @param bytes Byte array
   * @return Hex string
   * @since 0.1.0
   */
  public static String byteArrayToHex(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return "";
    }
    int length = bytes.length;
    char[] charBuffer = new char[length * 2];
    int index;
    for (int i = 0; i < length; i++) {
      index = i * 2;
      charBuffer[index] = hexDigits[(bytes[i] & 0xf0) >> 4];
      charBuffer[index + 1] = hexDigits[bytes[i] & 0x0f];
    }
    return new String(charBuffer);
  }

  // endregion

  // region randomString

  /**
   * A generator of uniform pseudorandom values applicable for use in (among other contexts) isolated parallel computations that may generate subtasks. Class SplittableRandom
   * supports methods for producing pseudorandom numbers of type int, long, and double with similar usages as for class Random but differs in the following ways:
   * <p>
   * Series of generated values pass the DieHarder suite testing independence and uniformity properties of random number generators. (Most recently validated with version 3.31.1.)
   * These tests validate only the methods for certain types and ranges, but similar properties are expected to hold, at least approximately, for others as well. The period (length
   * of any series of generated values before it repeats) is at least 264.
   * <p>
   * Method split() constructs and returns a new SplittableRandom instance that shares no mutable state with the current instance. However, with very high probability, the values
   * collectively generated by the two objects have the same statistical properties as if the same quantity of values were generated by a single thread using a single
   * SplittableRandom object.
   * <p>
   * Instances of SplittableRandom are not thread-safe. They are designed to be split, not shared, across threads. For example, a fork/join-style computation using random numbers
   * might include a construction of the form new Subtask(aSplittableRandom.split()).fork().
   * <p>
   * This class provides additional methods for generating random streams, that employ the above techniques when used in stream.parallel() mode. Instances of SplittableRandom are
   * not cryptographically secure. Consider instead using SecureRandom in security-sensitive applications. Additionally, default-constructed instances do not use a
   * cryptographically random seed unless the system property java.util.secureRandomSeed is set to true.
   * <p>
   * Since: 1.8
   */
  private final static SplittableRandom random = new SplittableRandom();

  /**
   * 고정된 길이의 랜덤문자열 생성
   *
   * @param length 길이
   * @since 0.1.0
   */
  public static String randomAlpha(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("A random string's length cannot be zero or negative");
    }
    char[] buffer = new char[length];
    for (int i = 0; i < length; i++) {
      buffer[i] = alphaDigits[random.nextInt(alphaDigits.length)];
    }
    return new String(buffer);
  }

  /**
   * 가변 길이의 랜덤문자열 생성
   *
   * @param min 최소길이
   * @param max 최대길이
   * @since 0.1.0
   */
  public static String randomAlpha(int min, int max) {
    if (min <= 0 || max <= 0 || min > max) {
      throw new IllegalArgumentException("A random string's length cannot be zero or negative or min > max");
    }
    return randomAlpha(random.nextInt(max - min) + min);
  }

  // endregion

  // region cast

  /**
   * {@link String} 타입 변환
   *
   * @param type 변환하고자 하는 클래스 타입
   * @param value 데이터 값
   * @return 변환된 클래스 인스턴스
   * @since 0.1.0
   */
  public static <T> T cast(String value, Class<T> type) {
    switch (type.getSimpleName()) {
      case "Integer":
        return type.cast(Integer.valueOf(value));
      case "Long":
        return type.cast(Long.valueOf(value));
      case "Boolean":
        return type.cast(Boolean.valueOf(value));
      case "Double":
        return type.cast(Double.valueOf(value));
      case "Float":
        return type.cast(Float.valueOf(value));
      case "Short":
        return type.cast(Short.valueOf(value));
      case "String":
        return type.cast(value);
      default:
        return type.cast(String.valueOf(value));
    }
  }
  // endregion

  // region join

  /**
   * 문자열 합치기 (skip nulls)
   *
   * @param separator 구분자
   * @param strings 대상 인스턴스 목록
   * @since 0.1.0
   */
  public static String join(String separator, String... strings) {
    return join(separator, (CharSequence[]) strings);
  }

  /**
   * 문자열 합치기 (skip nulls)
   *
   * @param separator 구분자
   * @param iterables 대상 인스턴스 목록 {@link Iterable}
   * @since 0.1.0
   */
  public static String join(String separator, Iterable<? extends CharSequence> iterables) {
    Objects.requireNonNull(separator);
    Objects.requireNonNull(iterables);
    StringJoiner joiner = new StringJoiner(separator);
    iterables.forEach(entry -> {
      if (!Objects.isNull(entry)) {
        joiner.add(entry);
      }
    });
    return joiner.toString();
  }

  /**
   * 문자열 합치기 (skip nulls)
   *
   * @param separator 구분자
   * @param iterators 대상 인스턴스 목록 {@link Iterator}
   * @since 0.1.0
   */
  public static String join(String separator, Iterator<? extends CharSequence> iterators) {
    Objects.requireNonNull(separator);
    Objects.requireNonNull(iterators);
    StringJoiner joiner = new StringJoiner(separator);
    while (iterators.hasNext()) {
      CharSequence value = iterators.next();
      if (!Objects.isNull(value)) {
        joiner.add(value);
      }
    }
    return joiner.toString();
  }

  /**
   * 문자열 합치기 (skip nulls)
   *
   * @param separator 구분자
   * @param values 대상 인스턴스 목록
   * @since 0.1.0
   */
  public static String join(String separator, CharSequence... values) {
    Objects.requireNonNull(separator);
    Objects.requireNonNull(values);
    StringJoiner joiner = new StringJoiner(separator);
    for (CharSequence value : values) {
      if (!Objects.isNull(value)) {
        joiner.add(value);
      }
    }
    return joiner.toString();
  }

  // endregion

  // region split

  /**
   * 문자열 나누기 (skip nulls, trim)
   *
   * @param separator 구분자
   * @param string 문자열
   * @param limit 결과물 목록수 (0: unlimit)
   * @since 0.1.0
   */
  public static List<String> split(char separator, String string, int limit) {
    if (limit < 0) {
      throw new IllegalParameterException("limit >= 0");
    }
    int length = string.length();
    int off = 0;
    int next;
    boolean limited = limit > 0;
    List<String> list = Lists.newLinkedList();
    String buffer;
    while ((next = string.indexOf(separator, off)) != -1) {
      if (!limited || list.size() < limit - 1) {
        buffer = trim(string.substring(off, next));
        if (!buffer.isEmpty()) {
          list.add(buffer);
        }
        off = next + 1;
      } else {
        buffer = trim(string.substring(off, next));
        if (!buffer.isEmpty()) {
          list.add(buffer);
        }
        off = length;
        break;
      }
    }
    if (off == 0) {
      return Lists.newArrayList(string);
    }
    if (!limited || list.size() < limit) {
      list.add(string.substring(off, length));
    }
    int resultSize = list.size();
    if (limit == 0) {
      while (resultSize > 0 && list.get(resultSize - 1).length() == 0) {
        resultSize--;
      }
    }
    return list.subList(0, resultSize);
  }

  /**
   * 문자열 나누기
   *
   * @param separator 구분자
   * @param string 문자열
   * @since 0.1.0
   */
  public static List<String> split(char separator, String string) {
    return split(separator, string, 0);
  }

  // endregion

  /**
   * 문자열 Trim
   *
   * @param value {@link CharSequence}
   * @return {@link CharSequence}
   * @since 0.1.0
   */
  public static CharSequence trim(CharSequence value) {
    Objects.requireNonNull(value);
    final int length = value.length();
    int far = length;
    int cursor = 0;
    while (cursor < far && value.charAt(cursor) <= 32) {
      ++cursor;
    }
    while (cursor < far && value.charAt(far - 1) <= 32) {
      --far;
    }
    return cursor <= 0 && length <= far ? value : value.subSequence(cursor, far);
  }

  /**
   * 문자열 Trim
   *
   * @param value {@link String}
   * @return {@link String}
   * @since 0.1.0
   */
  public static String trim(String value) {
    return (String) trim((CharSequence) value);
  }
}