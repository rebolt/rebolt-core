package io.rebolt.core.exceptions;

import org.apache.logging.log4j.Level;

public final class NotImplementedException extends ReboltException {
  private static final long serialVersionUID = -4788397409651612329L;

  public NotImplementedException(String message) {
    super.setMessage(message);
  }

  public NotImplementedException(Throwable cause) {
    super(cause);
  }

  @Override
  protected Level setLogLevel() {
    return Level.DEBUG;
  }
}
