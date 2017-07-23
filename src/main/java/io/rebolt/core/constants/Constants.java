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

package io.rebolt.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;

/**
 * 전역에서 사용되는 Constants 모음
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
  public static final String STRING_AND = "&";
  public static final String STRING_BLANK = " ";
  public static final String STRING_COLON = ":";
  public static final String STRING_COMMA = ",";
  public static final String STRING_DASH = "-";
  public static final String STRING_DOUBLE_COLON = "::";
  public static final String STRING_EMPTY = "";
  public static final String STRING_EQUAL = "=";
  public static final String STRING_JSON_INITIALIZE = "{}";
  public static final String STRING_QUESTION = "?";
  public static final String STRING_SHARP = "#";
  public static final String STRING_SLASH = "/";

  public static final Character CHARACTER_AND = STRING_AND.charAt(0);
  public static final Character CHARACTER_BLANK = STRING_BLANK.charAt(0);
  public static final Character CHARACTER_COLON = STRING_COLON.charAt(0);
  public static final Character CHARACTER_COMMA = STRING_COMMA.charAt(0);
  public static final Character CHARACTER_DASH = STRING_DASH.charAt(0);
  public static final Character CHARACTER_EQUAL = STRING_EQUAL.charAt(0);
  public static final Character CHARACTER_QUESTION = STRING_QUESTION.charAt(0);
  public static final Character CHARACTER_SHARP = STRING_SHARP.charAt(0);
  public static final Character CHARACTER_SLASH = STRING_SLASH.charAt(0);
  public static final CharSequence CHARS_EMPTY = STRING_EMPTY;

  public static final String CHARSET_NAME_DEFAULT = "UTF-8";
  public static final Charset CHARSET_DEFAULT = Charset.forName(CHARSET_NAME_DEFAULT);
  public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
}
