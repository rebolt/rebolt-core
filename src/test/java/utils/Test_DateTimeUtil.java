package utils;

import io.rebolt.core.utils.DateTimeUtil;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public final class Test_DateTimeUtil {

  @Test
  public void test_compare() {
    final ZonedDateTime nowUtc0 = DateTimeUtil.nowUtc0();
    final ZonedDateTime tomorrowLocal = DateTimeUtil.now(ZoneId.of("GMT+9")).plusDays(1);

    final long untilSeconds = DateTimeUtil.compare(tomorrowLocal, nowUtc0);
    final long untilMillis = DateTimeUtil.compareMillis(tomorrowLocal, nowUtc0);

    assertTrue(untilSeconds / 60 / 60 == 24);
    assertTrue(untilMillis / 1000 / 60 / 60 == 24);
  }

  @Test
  public void test_parse() {
    final ZonedDateTime nowUtc0 = DateTimeUtil.nowUtc0();
    final String nowUtc0String = nowUtc0.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    final ZonedDateTime nowUtc0_2 = DateTimeUtil.parse(nowUtc0String);

    assertTrue(DateTimeUtil.compare(nowUtc0, nowUtc0_2) == 0);
  }

  @Test
  public void test_parseDate() {
    final Date now = Date.from(Instant.now());
    final String nowString = DateTimeUtil.format(now);

    final Date now2 = DateTimeUtil.parseDate(nowString);

    assertTrue(now.after(now2));
  }

  @Test
  public void test_format() {
    final ZonedDateTime dateTime = DateTimeUtil.nowUtc0();
    final String dateTimeString = DateTimeUtil.format(dateTime);
    final ZonedDateTime dateTime2 = DateTimeUtil.parse(dateTimeString);

    assertTrue(DateTimeUtil.compare(dateTime, dateTime2) == 0);
  }
}
