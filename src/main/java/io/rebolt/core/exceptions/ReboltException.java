package io.rebolt.core.exceptions;

import io.rebolt.core.options.DefaultOption;
import io.rebolt.core.utils.LogUtil;
import io.rebolt.core.utils.StringUtil;
import lombok.Setter;
import org.apache.logging.log4j.Level;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Rebolt Framework 내부에서 사용되는 {@link RuntimeException}
 */
public abstract class ReboltException extends RuntimeException {
  private static final long serialVersionUID = -985635644581389914L;
  private static final String prefix = "-Exception: ";
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
   */
  protected abstract Level setLogLevel();

  /**
   * 로그 작성 가능 여부
   * <p>
   * JVM Option으로 부터 설정값을 조회한다 (기본값: false)
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
    if (isExceptionLog()) {
      LogUtil.getLogger().catching(level, this);
    }
    if (StringUtil.isNullOrEmpty(message)) {
      return prefix + super.getMessage();
    } else {
      return prefix + message + "\n" + super.getMessage();
    }
  }

}
