package io.rebolt.core.exceptions;

import org.apache.logging.log4j.Level;

/**
 * 클래스, 모듈등이 초기화되지 않았을 때 발생하는 {@link ReboltException}
 */
public final class NotInitializedException extends ReboltException {
  private static final long serialVersionUID = -8128940175426608039L;

  public NotInitializedException(String message) {
    super.setMessage(message);
  }

  @Override
  protected Level setLogLevel() {
    return Level.FATAL;
  }
}
