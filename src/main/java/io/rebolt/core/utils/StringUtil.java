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

import io.rebolt.core.exceptions.IllegalParameterException;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.StringJoiner;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;
import static javafx.fxml.FXMLLoader.DEFAULT_CHARSET_NAME;

public final class StringUtil {
  private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private static final byte[] hexFilters = {0, 10, 11, 12, 13, 14, 15};
  private static final char[] alphaDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  /**
   * 기본값 반환
   *
   * @param value empty 검사할 입력 문자열
   * @param defaultValue value가 empty일 때 대신 반환된 문자열
   * @return value 또는 defaultValue
   */
  public static String getDefault(final String value, final String defaultValue) {
    return isNullOrEmpty(value) ? defaultValue : value;
  }

  // region isNullOrEmpty

  /**
   * {@link String}에 대한 null 또는 empty 검사
   *
   * @param value {@link String}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * {@link String}에 대한 null 또는 empty 반복 검사
   *
   * @param values {@link String} 배열
   * @return true 또는 false
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
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isNullOrEmpty(StringBuilder value) {
    return value == null || value.length() == 0;
  }

  /**
   * Trim된 {@link String}에 대한 null 또는 empty 검사
   *
   * @param value Trim {@link String}
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isNullOrEmptyWithTrim(String value) {
    return value == null || isNullOrEmpty(value.trim());
  }

  /**
   * {@link String}에 대한 숫자검사
   *
   * @param value 숫자로된 문자열
   * @return true 또는 false
   * @since 0.1.0
   */
  public static boolean isNumeric(String value) {
    return !isNullOrEmpty(value) && value.chars().noneMatch(entity -> entity < 48 || entity > 57);
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
   * 고정된 길이의 랜덤문자열 생성. 알파벳과 숫자의 조합.
   *
   * @param length 길이
   * @return 고정된 길이의 랜덤 문자열
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
   * 가변 길이의 랜덤문자열 생성. 알파벳과 숫자의 조합.
   *
   * @param min 최소길이
   * @param max 최대길이
   * @return 가변 길이의 랜덤 문자열
   * @since 0.1.0
   */
  public static String randomAlpha(int min, int max) {
    if (min <= 0 || max <= 0 || min > max) {
      throw new IllegalArgumentException("A random string's length cannot be zero or negative or min > max");
    }
    return randomAlpha(random.nextInt(max - min) + min);
  }

  /**
   * 랜덤 long
   *
   * @return 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong() {
    return random.nextLong();
  }

  /**
   * 랜덤 long
   *
   * @param max 최대수
   * @return 최대수를 넘지 않는 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong(long max) {
    return random.nextLong(++max);
  }

  /**
   * 랜덤 long
   *
   * @param min 최소수
   * @param max 최대수
   * @return 최소 ~ 최대사이의 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong(long min, long max) {
    return random.nextLong(min, ++max);
  }

  /**
   * 랜덤 int
   *
   * @return 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt() {
    return random.nextInt();
  }

  /**
   * 랜덤 int
   *
   * @param max 최대수
   * @return 최대수를 넘지 않는 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt(int max) {
    return random.nextInt(++max);
  }

  /**
   * 랜덤 int
   *
   * @param min 최소수
   * @param max 최대수
   * @return 최소 ~ 최대사이의 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt(int min, int max) {
    return random.nextInt(min, ++max);
  }

  // endregion

  // region toString
  public static String toString(Map<?, ?> map) {
    StringBuilder builder = new StringBuilder();
    ObjectUtil.nullGuard(map).forEach((key, value) ->
        builder.append(key).append("=").append(value.getClass().isArray() ? join(",", (Object[]) value) : value).append(";"));
    return builder.toString();
  }
  // endregion

  // region cast

  /**
   * {@link String} 타입 변환
   *
   * @param value 데이터 값
   * @param type 변환하고자 하는 클래스 타입
   * @param <T> 반환하고자 하는 클래스의 제네릭 타입
   * @return 변환된 클래스 인스턴스
   * @since 0.1.0
   */
  public static <T> T cast(String value, Class<T> type) {
    if (type.isPrimitive()) {
      throw new IllegalParameterException("doesn't use primitive type: " + type);
    }
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
   * @return 합쳐진 최종 문자열
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
   * @return 합쳐진 최종 문자열
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
   * @return 합쳐진 최종 문자열
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
   * @return 합쳐진 최종문자열
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

  /**
   * 문자열 합치기 (skip nulls)
   *
   * @param separator 구분자
   * @param values 대상 인스턴스 목록 (Object)
   * @return 0.2.13
   */
  public static String join(String separator, Object[] values) {
    Objects.requireNonNull(separator);
    Objects.requireNonNull(values);
    StringJoiner joiner = new StringJoiner(separator);
    for (Object value : values) {
      if (!Objects.isNull(value)) {
        joiner.add(value.toString());
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
   * @return 나누어진 문자열 목록
   * @since 0.1.0
   */
  public static List<String> split(char separator, String string, int limit) {
    return split(separator, string, limit, false);
  }

  /**
   * 문자열 나누기
   *
   * @param separator 구분자
   * @param string 문자열
   * @return 나누어진 문자열 목록
   * @since 0.1.0
   */
  public static List<String> split(char separator, String string) {
    return split(separator, string, 0);
  }

  /**
   * 문자열 나누기
   *
   * @param separator 구분자
   * @param string 문자열
   * @param limit 결과물 목록수 (0: unlimit)
   * @param remain 마지막 결과에 나머지 문자열 추가여부, 입력값 ('/', "a/b/c/d/e/f", 2) - 출력값 ["a", "b/c/d/e/f"]
   * @return 나누어진 문자열 목록
   * @since 0.1.5
   */
  public static List<String> split(char separator, String string, int limit, boolean remain) {
    if (limit < 0) {
      throw new IllegalParameterException("limit >= 0");
    }
    int length = string.length();
    int off = 0;
    int next;
    boolean limited = limit > 0;
    List<String> list = new LinkedList<>();
    String buffer;
    while ((next = string.indexOf(separator, off)) != -1) {
      if (!limited || list.size() < limit - 1) {
        buffer = trim(string.substring(off, next));
        if (!buffer.isEmpty()) {
          list.add(buffer);
        }
        off = next + 1;
      } else {
        buffer = remain ? trim(string.substring(off)) : trim(string.substring(off, next));
        if (!buffer.isEmpty()) {
          list.add(buffer);
        }
        off = length;
        break;
      }
    }
    if (off == 0) {
      return Collections.singletonList(string);
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

  // endregion

  // region trim

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

  // endregion

  // region url encode/decode

  /**
   * Url 인코딩 (UTF-8)
   *
   * @param url 인코딩할 Url 문자열
   * @return 인코딩된 문자열
   * @since 0.1.0
   */
  public static String encodeUrl(String url) {
    if (StringUtil.isNullOrEmpty(url)) {
      return url;
    }
    String encodedUrl;
    try {
      encodedUrl = URLEncoder.encode(url, DEFAULT_CHARSET_NAME);
    } catch (UnsupportedEncodingException e) {
      encodedUrl = url;
    }
    return encodedUrl;
  }

  /**
   * Url 디코딩 (UTF-8)
   *
   * @param encodedUrl Url 인코딩된 문자열
   * @return 디코딩된 문자열
   * @since 0.1.0
   */
  public static String decodeUrl(String encodedUrl) {
    if (StringUtil.isNullOrEmpty(encodedUrl)) {
      return encodedUrl;
    }
    String url;
    try {
      url = URLDecoder.decode(encodedUrl, DEFAULT_CHARSET_NAME);
    } catch (UnsupportedEncodingException e) {
      url = encodedUrl;
    }
    return url;
  }

  // endregion

  // region base64 encode/decode
  public static String encodeBase64(final String message) {
    return new String(encodeBase64Bytes(message), CHARSET_UTF8);
  }

  public static byte[] encodeBase64(final byte[] message) {
    return Base64.getEncoder().encode(message);
  }

  public static byte[] encodeBase64Bytes(final String message) {
    return encodeBase64(message.getBytes());
  }

  public static String encodeBase64String(final byte[] message) {
    return new String(Base64.getEncoder().encode(message), CHARSET_UTF8);
  }

  public static String decodeBase64(final String base64) {
    return new String(decodeBase64Bytes(base64));
  }

  public static byte[] decodeBase64Bytes(final String base64) {
    return Base64.getDecoder().decode(base64);
  }
  // endregion

  // region rsa encrypt/decrypt
  private static final Object _rsaLock = new Object();
  private static Cipher rsaCipher;
  private static KeyFactory rsaKeyFactory;
  private static KeyPair rsaDefaultKeyPair;

  public static String encryptRsa(final PublicKey publicKey, final String plainText) {
    try {
      if (rsaCipher == null) {
        synchronized (_rsaLock) {
          if (rsaCipher == null) {
            rsaCipher = Cipher.getInstance("RSA");
          }
        }
      }
      rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] cipherText = rsaCipher.doFinal(plainText.getBytes());
      return encodeBase64String(cipherText);
    } catch (Exception ex) {
      return plainText;
    }
  }

  public static String decryptRsa(final PrivateKey privateKey, final String cipherText) {
    try {
      if (rsaCipher == null) {
        synchronized (_rsaLock) {
          if (rsaCipher == null) {
            rsaCipher = Cipher.getInstance("RSA");
          }
        }
      }
      rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] plainText = rsaCipher.doFinal(decodeBase64Bytes(cipherText));
      return new String(plainText);
    } catch (Exception ex) {
      return cipherText;
    }
  }

  public static KeyPair createKeyPairRsa(final byte[] publicKeyBytes, final byte[] privateKeyBytes) {
    try {
      if (rsaKeyFactory == null) {
        synchronized (_rsaLock) {
          if (rsaKeyFactory == null) {
            rsaKeyFactory = KeyFactory.getInstance("RSA");
          }
        }
      }
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      PublicKey publicKey = rsaKeyFactory.generatePublic(publicKeySpec);
      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      PrivateKey privateKey = rsaKeyFactory.generatePrivate(privateKeySpec);
      return new KeyPair(publicKey, privateKey);
    } catch (Exception ex) {
      return getDefaultKeyPairRsa();
    }
  }

  public static KeyPair createRandomKeyPairRsa() {
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      return generator.generateKeyPair();
    } catch (Exception ex) {
      return getDefaultKeyPairRsa();
    }
  }

  public static KeyPair getDefaultKeyPairRsa() {
    if (rsaDefaultKeyPair == null) {
      synchronized (_rsaLock) {
        if (rsaDefaultKeyPair == null) {
          rsaDefaultKeyPair = createKeyPairRsa(
              decodeBase64Bytes(
                  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXOIKgHFGJFjNenq3vlF8FkBTgq5BDL/G1AjeI9ViKvjgkalpncwLSHGuq3UNLd8cqQ8fM4peEMjMm0gg6VhEqxd3yXqK1LQNswJggtAV0hNaD4a9OymKMwEPhMQQU8ykDUGxBTOdVcqNtWU+puhcHaTNhoQOE8jokYHN/e8VQrn4yJoF0KayF+gsr3ov0p1aQMJP62hhMz1Tx5VXCJYU2c6so2Yyu/174v4IeZzuUXriyzyRBHI1Fc45ARL+jLEIgR5Vw2fvJ47VmCKmOd43yPZgt8753qkk1TSEm4pvobFYKqlol843KYnb0RYNAuPBzwlwbRAHDW3B6vylEyYuwIDAQAB"),
              decodeBase64Bytes(
                  "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRc4gqAcUYkWM16ere+UXwWQFOCrkEMv8bUCN4j1WIq+OCRqWmdzAtIca6rdQ0t3xypDx8zil4QyMybSCDpWESrF3fJeorUtA2zAmCC0BXSE1oPhr07KYozAQ+ExBBTzKQNQbEFM51Vyo21ZT6m6FwdpM2GhA4TyOiRgc397xVCufjImgXQprIX6Cyvei/SnVpAwk/raGEzPVPHlVcIlhTZzqyjZjK7/Xvi/gh5nO5ReuLLPJEEcjUVzjkBEv6MsQiBHlXDZ+8njtWYIqY53jfI9mC3zvneqSTVNISbim+hsVgqqWiXzjcpidvRFg0C48HPCXBtEAcNbcHq/KUTJi7AgMBAAECggEAOaX3xuZyrt0Y3Ep9G6jiznMIcF0RnZd0wueNV4A/3255Oq4zg3nj709ey6iP3eEHgwyTKMgxaYf6kEbuRx8qDVOh1Qra+BbXjZBrCE7bTnzKqVFML90Hsk3CNLQrkicInF1X9Clm9tz4T0lxxa4fW0qz6BKGcTr0naFxxP38eBvAdDiS+NP31G+6d09/hqA2hJESZJZH2x7NGXT+bangIAra8VrBd2fsIjhe/Z3NV+dSYmGTH7sRAF5n0fnIcMUZofeKMAquTwvFWWULA3mhkTlHmVZJN+mavTMvklo74B5+VoFAwLb/cMBA4yjvBNE3O23JGlEtOcN2cv5d2CmgkQKBgQDypUIlTurntVKHD0zK/ARM/NrXxQaAOtHbywmWPBCEYeoxqbLSb3Ple8CpepDBw2wuGzIB1OUZpAC9PvOqsuZEl59L6BdR2JURixLEjHBD7MsrpAsuXhqiPjP1nfYWVTvRG4wzeQT+ep9Qwj20yyj/QmlNM6Dx7dQSVDnnR0FskwKBgQCZdNw85RKEL+wz+OkFfzu9NIu0utsLjamwo78LC+v284ngmElbVV7MLlOjWmJPjgZX/xCOtnLW34cKMV+l+WUD2g58Xw5ydTVco0GquUmiS9tTwRrZqht5+1gkxTBGes13wQShqn5EwOZoQlmwoOl1xe/nqaOMC6nBQhFw26ZkOQKBgDCGH+46E/v2ZOShiKfnMiz7PAB+ZEhset9LgUVMCbmPozf/ScWPiEvSLbs2yZAWNqIZyixXmOFBzOwLlMsEL8xzzeVuKouxlk4F0+D+fMz4o8C8c4f4RbdAXT+3MSlSLj4pFiaNAxSpDQcncROgtTgm3cwUkREQkKKBuXqo40qFAoGAQV2x4o6BEKWJK6o/OAQ2YiXbzKQ7YfR577AQVJhDbvHWLUExHiKDOt4Q6mg5sEGDGkCfwOqeiEC2uPTHFV/iU32y5e9nrAGZNVilRB+g6ez+A/MhiM4Y3iDeLut/4MW2d+hUHLkPCCJTAt4gbkhcqboisr9j1uew641E+JnXiqECgYEA18lm9kwdcW8bRezznZ9z9GkTwzrUPSbaJn01Ej+zASnd+xJu3LDQMc3y7eGorHOZS5zkkOv3xcXACBvF94V0DbldurJvSSxNhDBrLTLCfL3GwFuS7LmMDUF6+vv7Pkg3IVica8iQ6Ng4PJgEm7faVzJH1eMEazkcVpvpS2ibWHg="));
        }
      }
    }
    return rsaDefaultKeyPair;
  }
  // endregion

  // region uri

  /**
   * uri를 합친다
   *
   * @param first first uri
   * @param second second uri
   * @return 합쳐진 uri
   * @since 0.1.7
   */
  @Deprecated
  public static String combineUri(String first, String second) {
    return UriUtil.combineUri(first, second);
  }

  /**
   * uri를 합친다
   *
   * @param first first uri
   * @param uris remaining uris
   * @return 합쳐진 uri
   * @since 0.1.7
   */
  @Deprecated
  public static String combineUri(String first, String... uris) {
    return UriUtil.combineUri(first, uris);
  }
  // endregion
}