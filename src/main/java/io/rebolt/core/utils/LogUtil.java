/*
 * Copyright 2017 The Rebolt Framework
 *
 * The Rebolt Framework licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package io.rebolt.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * 프레임워크 내부에서 사용하는 로거에 대한 정의
 * <p>
 * 로거는 Slf4j를 사용하며 {@link LogUtil}에는 API만 정의되어 있다
 * 실제로 로그를 파일 또는 원격지에 저장하기 위해서는 rebolt-log 라이브러리를 포함해야 한다
 *
 * @since 1.0.0
 */
public final class LogUtil {
  private final static Logger _logger = LoggerFactory.getLogger("io.rebolt");
  private final static Map<String, Logger> _loggerMap = new HashMap<>();

  /**
   * 프레임워크 내부 로거
   *
   * @return {@link Logger}
   */
  public static Logger logger() {
    return _logger;
  }

  /**
   * 프레임워크 내부 로거
   *
   * @return {@link Logger}
   */
  public static Logger get() {
    return _logger;
  }

  /**
   * 프레임워크 내부 로거
   *
   * @param level 로그 {@link Level}
   * @param message 로그 메시지, {} 포함
   * @param args {}를 하나씩 대체
   */
  public static void log(Level level, String message, Object... args) {
    switch (level) {
      case TRACE:
        trace(message, args);
        break;
      default:
      case DEBUG:
        debug(message, args);
        break;
      case INFO:
        info(message, args);
        break;
      case WARN:
        warn(message, args);
        break;
      case ERROR:
        error(message, args);
        break;
    }
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
  @Deprecated
  public static Logger logger(String name) {
    Logger logger = _loggerMap.get(name);
    if (logger == null) {
      synchronized (_loggerMap) {
        logger = LoggerFactory.getLogger(name);
        _loggerMap.put(name, logger);
      }
    }
    return logger;
  }

  /**
   * 로그레벨 : trace
   *
   * @param t {@link Throwable}
   */
  public static void trace(Throwable t) {
    if (_logger.isTraceEnabled()) {
      _logger.trace(t.getMessage(), t);
    }
  }

  /**
   * 로그레벨 : trace
   *
   * @param message 로그메시지 포맷, {}를 포함한다. 예) "log message: {}, {}"
   * @param args {}를 하나씩 대체한다.
   */
  public static void trace(String message, Object... args) {
    if (_logger.isTraceEnabled()) {
      _logger.trace(message, args);
    }
  }

  /**
   * 로그레벨 : debug
   *
   * @param t {@link Throwable}
   */
  public static void debug(Throwable t) {
    if (_logger.isDebugEnabled()) {
      _logger.debug(t.getMessage(), t);
    }
  }

  /**
   * 로그레벨 : debug
   *
   * @param message 로그메시지 포맷, {}를 포함한다. 예) "log message: {}, {}"
   * @param args {}를 하나씩 대체한다.
   */
  public static void debug(String message, Object... args) {
    if (_logger.isDebugEnabled()) {
      _logger.debug(message, args);
    }
  }

  /**
   * 로그레벨 : info
   *
   * @param t {@link Throwable}
   */
  public static void info(Throwable t) {
    if (_logger.isInfoEnabled()) {
      _logger.info(t.getMessage(), t);
    }
  }

  /**
   * 로그레벨 : info
   *
   * @param message 로그메시지 포맷, {}를 포함한다. 예) "log message: {}, {}"
   * @param args {}를 하나씩 대체한다.
   */
  public static void info(String message, Object... args) {
    if (_logger.isInfoEnabled()) {
      _logger.info(message, args);
    }
  }

  /**
   * 로그레벨 : warning
   *
   * @param t {@link Throwable}
   */
  public static void warn(Throwable t) {
    _logger.warn(t.getMessage(), t);
  }

  /**
   * 로그레벨 : warning
   *
   * @param message 로그메시지 포맷, {}를 포함한다. 예) "log message: {}, {}"
   * @param args {}를 하나씩 대체한다.
   */
  public static void warn(String message, Object... args) {
    _logger.warn(message, args);
  }

  /**
   * 로그레벨 : error
   *
   * @param t {@link Throwable}
   */
  public static void error(Throwable t) {
    _logger.error(t.getMessage(), t);
  }

  /**
   * 로그레벨 : error
   *
   * @param message 로그메시지 포맷, {}를 포함한다. 예) "log message: {}, {}"
   * @param args {}를 하나씩 대체한다.
   */
  public static void error(String message, Object... args) {
    _logger.error(message, args);
  }

  /**
   * 로그레벨 : fatal
   *
   * @param t {@link Throwable}
   */
  @Deprecated
  public static void fatal(Throwable t) {
    _logger.error(t.getMessage(), t);
  }
}
