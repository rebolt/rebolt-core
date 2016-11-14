package io.rebolt.core.utils;

public final class StringUtil {

  private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private static final byte[] hexAlphabet = {0, 10, 11, 12, 13, 14, 15};

  // region isNullOrEmpty

  /**
   * {@link String}에 대한 null 또는 empty 반복 검사
   *
   * @param value {@link String}
   * @return true or false
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * {@link String}에 대한 null 또는 empty 반복 검사
   *
   * @param values {@link String} 배열
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
   * @return true or false
   */
  public static boolean isNullOrEmpty(StringBuilder value) {
    return value == null || value.length() == 0;
  }

  /**
   * Trim된 {@link String}에 대한 null 또는 empty 검사
   *
   * @param value Trim {@link String}
   * @return true or false
   */
  public static boolean isNullOrEmptyWithTrim(String value) {
    return value == null || isNullOrEmpty(value.trim());
  }

  /**
   * {@link String}에 대한 숫자검사
   *
   * @param value 숫자로된 {@link String}
   * @return true or false
   */
  public static boolean isNumeric(String value) {
    return !isNullOrEmpty(value) && !value.chars().anyMatch(entity -> entity < 48 || entity > 57);
  }
  // endregion

  // region hexString

  /**
   * HexString to ByteArray
   *
   * @param hexString Hex {@link String}
   * @return Byte array
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
      head = hexString.codePointAt(index);
      tail = hexString.codePointAt(index + 1);
      charBuffer[i] = (byte) (((head & 0x40) == 0 ? head & 0x0f : hexAlphabet[head & 0x0f]) * 16 | ((tail & 0x40) == 0 ? tail & 0x0f : hexAlphabet[tail & 0x0f]));
    }
    return charBuffer;
  }

  /**
   * ByteArray to HexString
   *
   * @param bytes Byte array
   * @return Hex {@link String}
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

}