package utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import io.rebolt.core.utils.JsonUtil;
import io.rebolt.core.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
  public void test_readList() {
    final String value = "{\"id\":\"id_value\", \"value\":\"value_value\"}";
    final String array = "[" + value + ", " + value + "]";

    List<String> stringList = JsonUtil.readStringList("[\"key1\", \"key2\"]");
    List<Json> jsonList = JsonUtil.readList(array, Json.class);

    assertNotNull(stringList);
    assertNotNull(jsonList);
    assertFalse(ObjectUtil.isOrNull(stringList));
    assertFalse(ObjectUtil.isOrNull(jsonList));
  }

  @Test
  public void test_write() {
    Json json = new Json("id", "value");
    String jsonValue = JsonUtil.write(json);
    Json json2 = JsonUtil.read(jsonValue, Json.class);

    assertTrue(json.equals(json2));
  }

  @Test
  public void test_writeList() {
    List<Json> jsonList = Lists.newArrayList(new Json("id1", "value1"), new Json("id2", "value2"));
    String jsonArray = JsonUtil.writeList(jsonList);
    List<Json> restoredJsonList = JsonUtil.readList(jsonArray, Json.class);

    assertTrue(restoredJsonList != null && (jsonList.size() == restoredJsonList.size()));
    assertTrue(restoredJsonList.get(0).getId().equals("id1"));
  }

}
