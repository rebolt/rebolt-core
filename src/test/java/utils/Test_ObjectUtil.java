package utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.ObjectUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public final class Test_ObjectUtil {

  @SuppressWarnings({"ConstantConditions", "ConfusingArgumentToVarargsMethod"})
  @Test
  public void test_isNull() {
    String value1 = null;
    String value2 = "";
    String value3 = "  ";
    String value4 = "abc123";
    String[] valueArray = new String[] { value1, value2, value3, value4 };
    String[] valueArray2 = new String[] { value1, value1, value1 };

    assertTrue(ObjectUtil.isNull(value1));
    assertFalse(ObjectUtil.isNull(value2));
    assertTrue(ObjectUtil.isOrNull(valueArray));
    assertFalse(ObjectUtil.isAndNull(valueArray));
    assertTrue(ObjectUtil.isAndNull(valueArray2));
    assertTrue(ObjectUtil.isOrNull(valueArray2));
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void test_isEmpty() {
    String value1 = null;
    String value2 = "";
    String value3 = "  ";
    String value4 = "abc123";
    List<String> list1 = Lists.newArrayList();
    List<String> list2 = Lists.newArrayList(value1, value1, value2);
    Map<String, String> map1 = null;
    Map<String, String> map2 = Maps.newHashMap(ImmutableMap.of("key1", value3, "key2", value2, "ket3", value2));

    assertTrue(ObjectUtil.isEmpty(value1));
    assertTrue(ObjectUtil.isEmpty(value2));
    assertFalse(ObjectUtil.isEmpty(value3));
    assertFalse(ObjectUtil.isEmpty(value4));

    assertTrue(ObjectUtil.isEmpty(list1));
    assertFalse(ObjectUtil.isEmpty(list2));

    assertTrue(ObjectUtil.isEmpty(map1));
    assertFalse(ObjectUtil.isEmpty(map2));

    assertTrue(ObjectUtil.isEmpty((CharSequence) value1));
    assertTrue(ObjectUtil.isEmpty((IModel) null));
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void test_nullGuard() {
    List<String> list = null;
    Set<String> set = null;
    Map<String, String> map = null;

    assertNotNull(ObjectUtil.nullGuard(list));
    assertNotNull(ObjectUtil.nullGuard(set));
    assertNotNull(ObjectUtil.nullGuard(map));
  }
}
