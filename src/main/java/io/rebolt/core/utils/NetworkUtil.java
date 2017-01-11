package io.rebolt.core.utils;

import java.net.InetAddress;

public final class NetworkUtil {

  private static String host;

  public static String getHost() {
    if (host == null) {
      synchronized (NetworkUtil.class) {
        if (host == null) {
          try {
            host = InetAddress.getLocalHost().toString();
          } catch (Exception ex) {
            host = "(unknown host)";
          }
        }
      }
    }
    return host;
  }
}
