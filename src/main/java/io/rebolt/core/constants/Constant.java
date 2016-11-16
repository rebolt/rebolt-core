package io.rebolt.core.constants;

import java.nio.charset.Charset;

/**
 * 전역에서 사용되는 Constant 모음
 */
public final class Constant {
  public static final String EMPTY = "";
  public static final String BLANLK = " ";
  public static final String COMMA = ",";

  public static final String CHARSET_NAME_DEFAULT = "UTF-8";
  public static final Charset CHARSET_DEFAULT = Charset.forName(CHARSET_NAME_DEFAULT);
  public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
}
