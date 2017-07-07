package io.rebolt.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static io.rebolt.core.utils.StringUtil.isNullOrEmpty;

/**
 * @since 0.1.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
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
   * @param dateTime yyyy-MM-dd'T'HH:mm:ss
   * @return {@link ZonedDateTime} (utc0)
   */
  public static ZonedDateTime parse(String dateTime) {
    return parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
    return LocalDateTime.parse(dateTime, pattern).atZone(TIME_ZONE_UTC);
  }

}
