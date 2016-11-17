package io.rebolt.core.exceptions;

import org.apache.logging.log4j.Level;

/**
 * 잘못된 파라미터가 부여된 경우에 발생하는 {@link ReboltException}
 */
public final class IllegalParameterException extends ReboltException {
  private static final long serialVersionUID = 1694025627144269959L;

  public IllegalParameterException(String message) {
    super.setMessage(message);
  }

  @Override
  protected Level setLogLevel() {
    return Level.ERROR;
  }
}
