package io.rebolt.core.utils;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;
import static io.rebolt.core.utils.StringUtil.isNullOrEmpty;
import static io.rebolt.core.utils.StringUtil.isNullOrEmptyWithTrim;
import static io.rebolt.core.utils.StringUtil.isNumeric;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class Test_StringUtil {

  @SuppressWarnings("ConstantConditions")
  @Test
  public void test_isNullOrEmpty() {
    String value1 = null;
    String value2 = "";
    String value3 = "string";
    String value4 = "12345";
    StringBuilder value5 = null;
    StringBuilder value6 = new StringBuilder();
    StringBuilder value7 = new StringBuilder(value3);
    String value8 = "   ";

    assertTrue(isNullOrEmpty(value1));
    assertTrue(isNullOrEmpty(value2));
    assertFalse(isNullOrEmpty(value3));
    assertFalse(isNullOrEmpty(value4));
    assertFalse(isNullOrEmpty(value3, value4));
    assertTrue(isNullOrEmpty(value1, value2, value3, value4));
    assertTrue(isNullOrEmpty(value5));
    assertTrue(isNullOrEmpty(value6));
    assertFalse(isNullOrEmpty(value7));
    assertTrue(isNullOrEmptyWithTrim(value8));
    assertTrue(isNumeric(value4));
    assertFalse(isNumeric(value3));
  }

  @Test
  public void test_hexString() {
    String[] values = {
        "__hex_string__",
        "1234567890",
        "abCdi@!kzxkcj34KZ~12u90z90cklDfjzkdJfdixl0dSCJKFdlJZKDFJ399849!(#*@!$*)~_@~~jkfldjkz",
        "#@!$#(FRJZKFLDJ#I@r590fjzsd932ui90412#!@3029fUZ(83092-   )(0fdskljzj 9xk,.,x.kjzoifdjK!9309JdfklJDKLJ#@#I%%$&$^&%^J&%KL^JKL%$#JLKFJDSLKFZJDFKjlkdfsja;dpijfzodifjzoi"
    };
    List<byte[]> bytes = Lists.newArrayListWithCapacity(values.length);
    String[] convertedValues = new String[values.length];
    List<byte[]> convertedBytes = Lists.newArrayListWithExpectedSize(values.length);
    String[] lastValues = new String[values.length];

    for (int i = 0; i < values.length; i++) {
      bytes.add(values[i].getBytes(CHARSET_UTF8));
      convertedValues[i] = StringUtil.byteArrayToHex(bytes.get(i));
      convertedBytes.add(StringUtil.hexToByteArray(convertedValues[i]));
      lastValues[i] = StringUtil.byteArrayToHex(convertedBytes.get(i));
    }

    for (int i = 0; i < values.length; i++) {
      assertTrue(Arrays.equals(bytes.get(i), convertedBytes.get(i)));
    }
    assertTrue(Arrays.equals(convertedValues, lastValues));
  }

}
