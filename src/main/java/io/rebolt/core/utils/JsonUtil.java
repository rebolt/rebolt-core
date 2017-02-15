package io.rebolt.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Json 해석기
 *
 * @since 0.1.3
 */
public final class JsonUtil {
  private final static Logger log = LogUtil.logger();
  private final static JsonFactory jsonFactory = new MappingJsonFactory();

  public static <T> T read(String value, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).readValue(value, type);
    } catch (IOException e) {
      log.catching(Level.DEBUG, e);
      return null;
    }
  }

  public static JsonNode read(String value) {
    try {
      return new ObjectMapper(jsonFactory).readTree(value);
    } catch (IOException e) {
      log.catching(Level.DEBUG, e);
      return null;
    }
  }

  public static <T> T read(JsonNode jsonNode, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).treeToValue(jsonNode, type);
    } catch (JsonProcessingException e) {
      log.catching(Level.DEBUG, e);
      return null;
    }
  }
}
