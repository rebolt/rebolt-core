package io.rebolt.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @since 0.1.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
  public final static ZoneId TIME_ZONE_ID_DEFAULT = ZoneId.of("UTC");

  public static ZonedDateTime nowUtc0() {
    return ZonedDateTime.now(TIME_ZONE_ID_DEFAULT);
  }
}
