package io.rebolt.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static io.rebolt.core.constants.Constants.CHARACTER_SLASH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtil {

  /**
   * Host (도메인) 동일 여부
   *
   * @param origin 원본 도메인
   * @param diff 비교대상 도메인
   * @return 도메인 동일 여부
   */
  public static boolean containsHost(String origin, String diff) {
    if (StringUtil.isNullOrEmpty(origin, diff)) {
      return false;
    }
    try {
      String originHost = new URI(origin).getHost().toLowerCase();
      String diffHost = new URI(diff).getHost().toLowerCase();
      return originHost.replace("www.", "").equals(diffHost.replace("www.", ""));
    } catch (Exception ignored) {
      return false;
    }
  }

  /**
   * Uri 병합
   *
   * @param first first uri (with domain)
   * @param second second uri
   * @return 병합된 uri
   */
  public static String combineUri(String first, String second) {
    StringBuilder builder = new StringBuilder();
    builder.append(first);
    char firstLast = StringUtil.isNullOrEmpty(first) ? 0 : first.charAt(first.length() - 1);
    if (firstLast == CHARACTER_SLASH) {
      if (second.charAt(0) == CHARACTER_SLASH) {
        builder.append(second.substring(1));
      } else {
        builder.append(second);
      }
    } else {
      if (second.charAt(0) == CHARACTER_SLASH) {
        builder.append(second);
      } else {
        if (firstLast == 0) {
          builder.append(second);
        } else {
          builder.append(CHARACTER_SLASH).append(second);
        }
      }
    }
    return builder.toString();
  }

  /**
   * Uri 병합 \
   *
   * @param first first uri (with domain)
   * @param uris 이어 붙일 uri 목록
   * @return 병합된 uri
   */
  public static String combineUri(String first, String... uris) {
    if (uris.length == 0) {
      return first;
    } else if (uris.length == 1) {
      return combineUri(first, uris[0]);
    } else {
      return combineUri(first, combineUri(uris[0], Arrays.copyOfRange(uris, 1, uris.length)));
    }
  }
}
