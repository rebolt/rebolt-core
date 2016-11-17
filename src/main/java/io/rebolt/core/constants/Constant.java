package io.rebolt.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;

/**
 * 전역에서 사용되는 Constant 모음
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {
  public static final String STRING_EMPTY = "";
  public static final String STRING_BLANK = " ";
  public static final String STRING_COMMA = ",";
  public static final CharSequence CHARS_EMPTY = STRING_EMPTY;

  public static final String CHARSET_NAME_DEFAULT = "UTF-8";
  public static final Charset CHARSET_DEFAULT = Charset.forName(CHARSET_NAME_DEFAULT);
  public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
}
