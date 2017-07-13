package io.rebolt.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

/**
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NetworkUtil {
  private static String host;

  public static String getHost() {
    if (host == null) {
      synchronized (NetworkUtil.class) {
        if (host == null) {
          try {
            host = InetAddress.getLocalHost().toString();
          } catch (Exception ex) {
            host = "0.0.0.0";
          }
        }
      }
    }
    return host;
  }
}
