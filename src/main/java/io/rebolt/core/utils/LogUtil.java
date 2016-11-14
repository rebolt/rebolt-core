package io.rebolt.core.utils;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.rebolt.core.constants.Constants.LOGGER_REBOLT;

/**
 * 프레임워크 내부에서 사용하는 로거에 대한 정의
 * <p>
 * 로거는 Log4j2를 사용하며 {@link LogUtil}에는 API만 정의되어 있다
 * 실제로 로그를 파일 또는 원격지에 저장하기 위해서는 rebolt-log 라이브러리를 포함해야 한다
 */
public final class LogUtil {

  private final static Logger defaultLogger = LogManager.getLogger(LOGGER_REBOLT);
  private final static Map<String, Logger> loggerMap = Maps.newHashMap();

  /**
   * 프레임워크 내부에서 사용하는 기본로거
   *
   * @return {@link Logger}
   */
  public static Logger getLogger() {
    return defaultLogger;
  }

  /**
   * 사용자 정의 이름이 부여된 로거
   * 자유롭게 이름을 정의할 수 있다
   * <p>
   * 이름을 반복해서 사용한다면 기존에 생성된 로거를 재활용한다
   *
   * @param name 사용자 정의 로거명
   * @return {@link Logger} (싱글턴)
   */
  public static Logger getLogger(String name) {
    Logger logger = loggerMap.get(name);
    if (logger == null) {
      synchronized (loggerMap) {
        logger = LogManager.getLogger(name);
        loggerMap.put(name, logger);
      }
    }
    return logger;
  }

  /**
   * 로그레벨 : Warning
   * 프레임워크 내부에서만 사용한다
   *
   * @param t {@link Throwable}
   */
  public static void warn(Throwable t) {
    defaultLogger.catching(Level.WARN, t);
  }

  /**
   * 로그레벨 : Error
   * 프레임워크 내부에서만 사용한다
   *
   * @param t {@link Throwable}
   */
  public static void error(Throwable t) {
    defaultLogger.catching(Level.ERROR, t);
  }

  /**
   * 로그레벨 : Fatal
   * 프레임워크 내부에서만 사용한다
   *
   * @param t {@link Throwable}
   */
  public static void fatal(Throwable t) {
    defaultLogger.catching(Level.FATAL, t);
  }
}
