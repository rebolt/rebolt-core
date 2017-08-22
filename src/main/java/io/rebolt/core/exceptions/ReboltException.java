package io.rebolt.core.exceptions;

import io.rebolt.core.options.DefaultOption;
import io.rebolt.core.utils.LogUtil;
import io.rebolt.core.utils.StringUtil;
import lombok.Setter;
import org.slf4j.event.Level;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Rebolt Framework 내부에서 사용되는 {@link RuntimeException}
 */
public abstract class ReboltException extends RuntimeException {
  private static final long serialVersionUID = -985635644581389914L;
  private static final String prefix = "ReboltException: ";
  private static volatile AtomicBoolean exceptionLog;
  private @Setter String message;
  private Level level;

  protected ReboltException() {
    this.level = setLogLevel();
  }

  protected ReboltException(Throwable cause) {
    super(cause);
  }

  /**
   * 로그 레벨을 설정한다
   *
   * @return {@link Level}
   */
  protected abstract Level setLogLevel();

  /**
   * 로그 작성 가능 여부
   * <p>
   * JVM Option으로 부터 설정값을 조회한다 (기본값: false)
   *
   * @return Exception Log 사용여부
   */
  protected boolean isExceptionLog() {
    if (exceptionLog == null) {
      synchronized (this) {
        if (exceptionLog == null) {
          exceptionLog = new AtomicBoolean(DefaultOption.getInstance().isExceptionLog());
        }
      }
    }
    return exceptionLog.get();
  }

  @Override
  public String getMessage() {
    StringBuilder message = new StringBuilder();
    if (StringUtil.isNullOrEmpty(message)) {
      message.append(prefix).append(super.getMessage());
    } else {
      message.append(prefix).append(message).append("\n").append(super.getMessage());
    }
    LogUtil.log(level, message.toString());
    return message.toString();
  }

}
