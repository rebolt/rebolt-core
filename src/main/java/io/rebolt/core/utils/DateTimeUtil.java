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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static io.rebolt.core.utils.ObjectUtil.isNull;
import static io.rebolt.core.utils.StringUtil.isNullOrEmpty;

/**
 * @since 0.1.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
  private static final SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  public final static ZoneId TIME_ZONE_UTC = ZoneId.of("UTC");
  public final static ZoneId TIME_ZONE_ID_DEFAULT = TIME_ZONE_UTC;

  public static ZonedDateTime nowUtc0() {
    return ZonedDateTime.now(TIME_ZONE_UTC);
  }

  public static ZonedDateTime now(ZoneId zoneId) {
    return ZonedDateTime.now(zoneId);
  }

  /**
   * from, to의 시간 차이
   *
   * @param from {@link ZonedDateTime}
   * @param to {@link ZonedDateTime}
   * @return seconds
   */
  public static long compare(ZonedDateTime from, ZonedDateTime to) {
    return from.toOffsetDateTime().toEpochSecond() - to.toOffsetDateTime().toEpochSecond();
  }

  /**
   * from, to의 시간 차이
   *
   * @param from {@link ZonedDateTime}
   * @param to {@link ZonedDateTime}
   * @return milliseconds
   */
  public static long compareMillis(ZonedDateTime from, ZonedDateTime to) {
    return from.toOffsetDateTime().toInstant().toEpochMilli() - to.toOffsetDateTime().toInstant().toEpochMilli();
  }

  /**
   * {@link ZonedDateTime} 파싱
   *
   * @param isoDateTime yyyy-MM-ddTHH:mm:ss+00:00[UTC]
   * @return {@link ZonedDateTime}
   */
  public static ZonedDateTime parse(String isoDateTime) {
    return parse(isoDateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  /**
   * {@link ZonedDateTime} 파싱
   *
   * @param dateTime 날짜 문자열
   * @param pattern 날짜 패턴
   * @return {@link ZonedDateTime} (utc0)
   */
  public static ZonedDateTime parse(String dateTime, DateTimeFormatter pattern) {
    if (isNullOrEmpty(dateTime)) {
      return null;
    }
    return ZonedDateTime.parse(dateTime, pattern);
  }

  /**
   * {@link Date} 파싱
   *
   * @param dateString yyyy-MM-dd 형태의 날짜
   * @return {@link Date}
   */
  public static Date parseDate(String dateString) {
    try {
      return _simpleDateFormat.parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * {@link Date} 파싱
   *
   * @param dateString 정의된 형태의 날짜 문자열
   * @param pattern 날짜 패턴
   * @return {@link Date}
   */
  public static Date parseDate(String dateString, String pattern) {
    try {
      return new SimpleDateFormat(pattern).parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * {@link ZonedDateTime} 문자열 변경
   *
   * @param dateTime {@link ZonedDateTime}
   * @return yyyy-MM-ddTHH:mm:ss.SSS+00:00[UTC]
   * @since 0.1.14
   */
  public static String format(ZonedDateTime dateTime) {
    return format(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  /**
   * {@link ZonedDateTime} 문자열 변경
   *
   * @param dateTime {@link ZonedDateTime}
   * @return yyyy-MM-ddTHH:mm:ss.SSSZ
   * @since 0.2.5
   */
  public static String formatInstant(ZonedDateTime dateTime) {
    return format(dateTime, DateTimeFormatter.ISO_INSTANT);
  }

  /**
   * {@link ZonedDateTime} 문자열 변경
   *
   * @param dateTime {@link ZonedDateTime}
   * @return yyyy-MM-ddTHH:mm:ssZ
   * @since 0.2.5
   */
  public static String formatInstantWithoutMillis(ZonedDateTime dateTime) {
    return format(dateTime.truncatedTo(ChronoUnit.SECONDS), DateTimeFormatter.ISO_INSTANT);
  }

  /**
   * {@link ZonedDateTime} 문자열 변경
   *
   * @param dateTime {@link ZonedDateTime}
   * @param pattern 날짜 패턴
   * @return yyyy-MM-dd'T'HH:mm:ss.SSS
   * @since 0.1.14
   */
  public static String format(ZonedDateTime dateTime, DateTimeFormatter pattern) {
    if (isNull(dateTime)) {
      return null;
    }
    return dateTime.format(pattern);
  }

  /**
   * {@link Date} 포맷
   *
   * @param date {@link Date}
   * @return yyyy-MM-dd 형태의 문자열
   * @since 0.1.14
   */
  public static String format(Date date) {
    return _simpleDateFormat.format(date);
  }

  /**
   * {@link Date} 포맷
   *
   * @param date {@link Date}
   * @param pattern yyyyMMdd 패턴 정의
   * @return 패턴이 적용된 날짜 문자열
   * @since 0.2.5
   */
  public static String format(Date date, String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }
}