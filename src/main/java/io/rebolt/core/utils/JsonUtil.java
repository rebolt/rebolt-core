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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.rebolt.core.constants.Constants.STRING_JSON_INITIALIZE;

/**
 * Json 해석기
 *
 * @since 0.1.3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {
  private final static JsonFactory _jsonFactory = new MappingJsonFactory();
  private final static ObjectMapper _objectMapper = new ObjectMapper(_jsonFactory);

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
      return _objectMapper.readValue(jsonString, type);
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * Json 문자열을 사용자 객체로 전환 (exception 포함)
   *
   * @param jsonString json 문자열
   * @param type 사용자 객체
   * @param exception exception 포함 여부
   * @param <T> 사용자 객체 타입
   * @return 전환된 사용자 객체
   * @throws IOException
   * @since 0.2.17
   */
  public static <T> T read(String jsonString, Class<T> type, boolean exception) throws IOException {
    if (exception) {
      return _objectMapper.readValue(jsonString, type);
    } else {
      return read(jsonString, type);
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
      return _objectMapper.readTree(jsonString);
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
      return _objectMapper.treeToValue(jsonNode, type);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * {@link JsonNode}로부터 사용자 객체로 전환 (exception 포함)
   *
   * @param jsonNode {@link JsonNode}
   * @param type 사용자 객체
   * @param exception exception 포함 여부
   * @param <T> 사용자 객체 타입
   * @return 전환된 사용자 객체
   * @throws JsonProcessingException
   * @since 0.2.17
   */
  public static <T> T read(JsonNode jsonNode, Class<T> type, boolean exception) throws JsonProcessingException {
    if (exception) {
      return _objectMapper.treeToValue(jsonNode, type);
    } else {
      return read(jsonNode, type);
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
    try {
      return _objectMapper.readValue(jsonArray, _objectMapper.getTypeFactory().constructCollectionType(List.class, type));
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
    return readList(jsonArray, String.class);
  }

  /**
   * Json 문자열로부터 맵으로 전환
   *
   * @param json json 문자열
   * @param keyType key 클래스타입
   * @param valueType value 클래스타입
   * @param <K> key
   * @param <V> value
   * @return 전환된 맵 (K, V)
   * @since 0.2.10
   */
  public static <K, V> Map<K, V> readMap(String json, Class<K> keyType, Class<V> valueType) {
    try {
      return _objectMapper.readValue(json, _objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
    } catch (IOException e) {
      LogUtil.debug(e);
      return null;
    }
  }

  /**
   * 사용자객체로부터 맵으로 전환
   *
   * @param object 사용자객체
   * @param <T> 사용자객체 타입
   * @return 전환된 맵 (String, Object)
   * @since 0.2.11
   */
  public static <T> Map<String, Object> readStringMap(T object) {
    return _objectMapper.convertValue(object, _objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
  }

  /**
   * Json 문자열로부터 맵으로 전환
   *
   * @param json json 문자열
   * @return 전환된 맵 (String, Object)
   * @since 0.2.10
   */
  public static Map<String, Object> readStringMap(String json) {
    return readMap(json, String.class, Object.class);
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
      return _objectMapper.writeValueAsString(object);
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
      return _objectMapper.writeValueAsString(list);
    } catch (JsonProcessingException e) {
      LogUtil.debug(e);
      return "[]";
    }
  }
  // endregion

}
