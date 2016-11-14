package io.rebolt.core.constants;

import java.nio.charset.Charset;

/**
 * 전역에서 사용되는 General Constants 모음
 */
public final class Constants {

  public static final String CHARSET_NAME_DEFAULT = "UTF-8";
  public static final Charset CHARSET_DEFAULT = Charset.forName(CHARSET_NAME_DEFAULT);
  public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

  // region logger names
  /**
   * Logger : Default
   */
  public final static String LOGGER_REBOLT = "io.rebolt";
  // endregion

}
