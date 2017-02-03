package utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.rebolt.core.utils.StringUtil;
import org.junit.Test;

import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.rebolt.core.constants.Constants.CHARSET_UTF8;
import static io.rebolt.core.constants.Constants.STRING_COMMA;
import static io.rebolt.core.utils.StringUtil.isNullOrEmpty;
import static io.rebolt.core.utils.StringUtil.isNullOrEmptyWithTrim;
import static io.rebolt.core.utils.StringUtil.isNumeric;
import static org.junit.Assert.assertArrayEquals;
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
        "#@!$#(FRJZKFLDJ#I@r590fjzsd932ui90412#!@3029fUZ(83092-   )(0fdskljzj 9xk,.,x.kjzoifdjK!9309JdfklJDKLJ#@#I%%$&$^&%^J&%KL^JKL%$#JLKFJDSLKFZJDFKjlkdfsja;dpijfzodifjzoi",
        io.rebolt.core.utils.StringUtil.randomAlpha(1024)
    };
    List<byte[]> bytes = Lists.newArrayListWithCapacity(values.length);
    String[] convertedValues = new String[values.length];
    List<byte[]> convertedBytes = Lists.newArrayListWithExpectedSize(values.length);
    String[] lastValues = new String[values.length];

    for (int i = 0; i < values.length; i++) {
      bytes.add(values[i].getBytes(CHARSET_UTF8));
      convertedValues[i] = io.rebolt.core.utils.StringUtil.byteArrayToHex(bytes.get(i));
      convertedBytes.add(io.rebolt.core.utils.StringUtil.hexToByteArray(convertedValues[i]));
      lastValues[i] = io.rebolt.core.utils.StringUtil.byteArrayToHex(convertedBytes.get(i));
    }

    for (int i = 0; i < values.length; i++) {
      assertTrue(Arrays.equals(bytes.get(i), convertedBytes.get(i)));
    }
    assertTrue(Arrays.equals(convertedValues, lastValues));
  }

  @Test
  public void test_randomAlpha1() {
    int count = 2000;
    Set<String> randomAlphas = Sets.newLinkedHashSetWithExpectedSize(count);
    for (int i = 0; i < count; i++) {
      randomAlphas.add(io.rebolt.core.utils.StringUtil.randomAlpha(128));
    }
    assertTrue(count == randomAlphas.size());
  }

  @Test
  public void test_randomAlpha2() {
    int count = 2000;
    Set<String> randomAlphas = Sets.newLinkedHashSetWithExpectedSize(count);
    for (int i = 0; i < count; i++) {
      randomAlphas.add(io.rebolt.core.utils.StringUtil.randomAlpha(128, 256));
    }
    assertTrue(count == randomAlphas.size());
    randomAlphas.parallelStream().forEach(entry -> assertTrue(entry.length() >= 128 && entry.length() <= 256));
  }

  @Test
  public void test_join() {
    int count = 1000;
    final String[] initValues = new String[count];
    for (int i = 0; i < count; i++) {
      if (i % 10 == 0) {
        initValues[i] = null;
      } else {
        initValues[i] = StringUtil.randomAlpha(10);
      }
    }

    String value1 = StringUtil.join(STRING_COMMA, initValues);
    String value2 = StringUtil.join(STRING_COMMA, (CharSequence[]) initValues);
    String value3 = StringUtil.join(STRING_COMMA, Arrays.asList(initValues));
    String value4 = StringUtil.join(STRING_COMMA, Arrays.asList(initValues).iterator());

    assertTrue(value1.equals(value2));
    assertTrue(value1.equals(value3));
    assertTrue(value1.equals(value4));
  }

  @Test
  public void test_trim() {
    String value = "1a 닭하야4";
    String value1 = "1a 닭하야4";
    String value2 = "  1a 닭하야4   ";
    String value3 = "1a 닭하야4    ";
    String value4 = "            1a 닭하야4";

    assertTrue(value.equals(StringUtil.trim(value1)));
    assertTrue(value.equals(StringUtil.trim(value2)));
    assertTrue(value.equals(StringUtil.trim(value3)));
    assertTrue(value.equals(StringUtil.trim(value4)));
  }

  @Test
  public void test_split() {
    String value = "123,4214,      123,21,31,23,12     ,31,2311     231,      ,3123123,";
    List<String> originSplit = Splitter.on(',').omitEmptyStrings().trimResults().splitToList(value);
    List<String> split = StringUtil.split(',', value);

    assertTrue(originSplit.size() == split.size());
    assertArrayEquals(originSplit.toArray(), split.toArray());
  }

  @Test
  public void test_urlEncode() {
    String value = "http://\\!@#$43%^^...21!!!#@$%$^$%----__=+_++_+_~!@~good.com/good-job/key=value&key1=value%&;";

    String encodedUrl = StringUtil.encodeUrl(value);
    String decodedUrl = StringUtil.decodeUrl(encodedUrl);

    assertTrue(value.equals(decodedUrl));
  }

  @Test
  public void test_base64() {
    String value = "http://\\!@#$43%^^...21!!!#@$%$^$%----__=+_++_+_~!@~good.com/good-job/key=value&key1=value%&;동해물과백두사닉ⓐ";

    String encodedBase64 = StringUtil.encodeBase64(value);
    String decodedBase64 = StringUtil.decodeBase64(encodedBase64);

    assertTrue(value.equals(decodedBase64));
  }

  @Test
  public void test_encryptRsa() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    final String value = "http://\\!@#$43%^^...21!!!#@$%$^%--__=+_++_+_~!@~good.co          m/good-job/key=value&key1=value%&;닭꺼져ⓐ";

    KeyPair keyPair = StringUtil.createRandomKeyPairRsa();
    final String encryptedValue = StringUtil.encryptRsa(keyPair.getPublic(), value);
    final String decryptedValue = StringUtil.decryptRsa(keyPair.getPrivate(), encryptedValue);

    assertTrue(value.equals(decryptedValue));
  }

  @Test
  public void test_rsaKeyPair() {
    KeyPair keyPair = StringUtil.createRandomKeyPairRsa();
    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();
    KeyPair keyPair1 = StringUtil.createKeyPairRsa(publicKey.getEncoded(), privateKey.getEncoded());

    final String value = "http://\\!@#$43%^^...21!!!#@$%$^%--__=+_++_+_~!@~good.co          m/good-job/key=value&key1=value%&;닭꺼져ⓐ";
    final String encryptedValue = StringUtil.encryptRsa(keyPair.getPublic(), value);
    final String decryptedValue = StringUtil.decryptRsa(keyPair1.getPrivate(), encryptedValue);

    assertTrue(value.equals(decryptedValue));
  }

  @Test
  public void test_rsaDefaultKeyPair() {
    KeyPair defaultKeyPair = StringUtil.getDefaultKeyPairRsa();
    final String value = "http://\\!@#$43%^^...21!!!#@$%$^%--__=+_++_+_~!@~good.co          m/good-job/key=value&key1=value%&;닭꺼져ⓐ";
    final String encryptedValue = StringUtil.encryptRsa(defaultKeyPair.getPublic(), value);
    final String decryptedValue = StringUtil.decryptRsa(defaultKeyPair.getPrivate(), encryptedValue);
    assertTrue(value.equals(decryptedValue));
  }
}
