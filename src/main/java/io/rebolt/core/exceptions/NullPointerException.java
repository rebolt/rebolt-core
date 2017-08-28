package io.rebolt.core.exceptions;

import org.slf4j.event.Level;

public final class NullPointerException extends ReboltException {
  private static final long serialVersionUID = -7742802316820796684L;

  public NullPointerException() {
  }

  public NullPointerException(String message) {
    super.setMessage(message);
  }

  @Override
  protected Level setLogLevel() {
    return Level.WARN;
  }
}
