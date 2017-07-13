package io.rebolt.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

import static io.rebolt.core.constants.Constants.STRING_EMPTY;
import static io.rebolt.core.constants.Constants.STRING_JSON_INITIALIZE;

/**
 * Json 해석기
 *
 * @since 0.1.3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {
  private final static JsonFactory jsonFactory = new MappingJsonFactory();

  // region read
  /**
   * Json 문자열을 사용자 객체로 전환
   *
   * @param jsonString json 문자열
   * @param type 사용자 객체
   * @param <T> 사용자 객체 타입
   * @return 전환된 사용자 객체
   */
  public static <T> T read(String jsonString, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).readValue(jsonString, type);
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * Json 문자열로부터 {@link JsonNode}로 전환
   *
   * @param jsonString json 문자열
   * @return {@link JsonNode}
   */
  public static JsonNode read(String jsonString) {
    try {
      return new ObjectMapper(jsonFactory).readTree(jsonString);
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * {@link JsonNode}로부터 사용자 객체로 전환
   *
   * @param jsonNode {@link JsonNode}
   * @param type 사용자 객체
   * @param <T> 사용자 객체 타입
   * @return 전환된 사용자 객체
   */
  public static <T> T read(JsonNode jsonNode, Class<T> type) {
    try {
      return new ObjectMapper(jsonFactory).treeToValue(jsonNode, type);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * Json 배열로부터 사용자 객체 리스트로 전환
   *
   * @param jsonArray json 배열
   * @param type 사용자 객체
   * @param <T> 사용자 객체 타입
   * @return 전환된 사용자 객체 리스트
   * @since 0.2.8
   */
  public static <T> List<T> readList(String jsonArray, Class<T> type) {
    ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
    try {
      return objectMapper.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * Json 배열로부터 문자열 리스트로 전환
   *
   * @param jsonArray json 배열
   * @return 전환된 문자열 리스트
   * @since 0.2.8
   */
  public static List<String> readStringList(String jsonArray) {
    ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
    try {
      return objectMapper.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }
  // endregion

  // region write
  /**
   * 임의의 객체를 json 문자열로 전환
   *
   * @param object 임의의 객체
   * @return json 문자열
   */
  public static String write(Object object) {
    ObjectUtil.requireNonNull(object);
    try {
      return new ObjectMapper(jsonFactory).writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return STRING_JSON_INITIALIZE;
    }
  }

  /**
   * 리스트 객체를 json 배열로 전환
   *
   * @param list 리스트 객체
   * @return json 배열
   * @since 0.2.8
   */
  public static String writeList(List<?> list) {
    try {
      return new ObjectMapper(jsonFactory).writeValueAsString(list);
    } catch (JsonProcessingException e) {
      return STRING_EMPTY;
    }
  }
  // endregion

}
