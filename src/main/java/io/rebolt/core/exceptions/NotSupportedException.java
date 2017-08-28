package io.rebolt.core.exceptions;

import org.slf4j.event.Level;

/**
 * 지원하지 않는 기능을 호출할 때 발생
 */
public final class NotSupportedException extends ReboltException {
  private static final long serialVersionUID = -1954009198862871085L;

  public NotSupportedException(String message) {
    super.setMessage(message);
  }

  @Override
  protected Level setLogLevel() {
    return Level.DEBUG;
  }
}
