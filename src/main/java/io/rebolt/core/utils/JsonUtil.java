package io.rebolt.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static io.rebolt.core.constants.Constants.STRING_JSON_INITIALIZE;

/**
 * Json 해석기
 *
 * @since 0.1.3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {
  private final static JsonFactory jsonFactory = new MappingJsonFactory();

  public static <T> T read(String value, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).readValue(value, type);
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  public static JsonNode read(String value) {
    try {
      return new ObjectMapper(jsonFactory).readTree(value);
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  public static <T> T read(JsonNode jsonNode, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).treeToValue(jsonNode, type);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  public static String write(Object object) {
    ObjectUtil.requireNonNull(object);
    try {
      return new ObjectMapper(jsonFactory).writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return STRING_JSON_INITIALIZE;
    }
  }
}
