package utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.rebolt.core.utils.JsonUtil;
import io.rebolt.core.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_JsonUtil {

  @Data
  @AllArgsConstructor
  public static class Json {
    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
  }

  @Test
  public void test_read() {
    String value = "{\"id\":\"id_value\", \"value\":\"value_value\"}";

    Json json = JsonUtil.read(value, Json.class);
    JsonNode jsonNode = JsonUtil.read(value);
    Json json2 = JsonUtil.read(jsonNode, Json.class);

    assertTrue(!ObjectUtil.isOrNull(json, jsonNode, json2));
    assertTrue(json.equals(json2));
    assertTrue(json.getId().equals(jsonNode.get("id").asText()));
    assertTrue(json2.getValue().equals(jsonNode.get("value").asText()));
  }

  @Test
  public void test_write() {
    Json json = new Json("id", "value");
    String jsonValue = JsonUtil.write(json);
    Json json2 = JsonUtil.read(jsonValue, Json.class);

    assertTrue(json.equals(json2));
  }
}
