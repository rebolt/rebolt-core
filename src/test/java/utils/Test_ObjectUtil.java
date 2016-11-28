package utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.rebolt.core.models.IModel;
import io.rebolt.core.utils.ObjectUtil;
import io.rebolt.core.utils.StringUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public final class Test_ObjectUtil {

  @SuppressWarnings("ConstantConditions")
  @Test
  public void test_isNull() {
    String value1 = null;
    String value2 = "";
    String value3 = "  ";
    String value4 = "abc123";
    String[] valueArray = new String[] {value1, value2, value3, value4};
    String[] valueArray2 = new String[] {value1, value1, value1};

    assertTrue(ObjectUtil.isNull(value1));
    assertFalse(ObjectUtil.isNull(value2));
    assertTrue(ObjectUtil.isOrNull((Object[]) valueArray));
    assertFalse(ObjectUtil.isAndNull((Object[]) valueArray));
    assertTrue(ObjectUtil.isAndNull((Object[]) valueArray2));
    assertTrue(ObjectUtil.isOrNull((Object[]) valueArray2));
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
    Object[] objects = new Object[] {value1, list1, list2, map1, map2};

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
    assertFalse(ObjectUtil.isEmpty(objects));
  }

  @Test
  public void test_isEmpty2() {
    byte[] bytes = new byte[] {'a', 'b', 'c'};
    char[] chars = new char[] {'a', 'b', 'c'};
    char[] chars1 = new char[] {};
    char[] chars2 = null;
    int[] ints = new int[] {1, 2, 3};
    long[] longs = new long[] {1L, 2L, 3L};
    double[] doubles = new double[] {1.0, 2.0, 3.0};
    float[] floats = new float[] {1, 2, 3};
    short[] shorts = new short[] {1, 2, 3};

    assertFalse(ObjectUtil.isEmpty(bytes));
    assertFalse(ObjectUtil.isEmpty(chars));
    assertTrue(ObjectUtil.isEmpty(chars1));
    assertTrue(ObjectUtil.isEmpty(chars2));
    assertFalse(ObjectUtil.isEmpty(ints));
    assertFalse(ObjectUtil.isEmpty(longs));
    assertFalse(ObjectUtil.isEmpty(doubles));
    assertFalse(ObjectUtil.isEmpty(floats));
    assertFalse(ObjectUtil.isEmpty(shorts));
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

  @Test
  public void test_thenNonEmpty() {
    AtomicInteger count = new AtomicInteger(0);
    String value = StringUtil.randomAlpha(10);

    ObjectUtil.thenNonEmpty(value, entry -> count.incrementAndGet());
    ObjectUtil.thenNonEmpty(Lists.newArrayList(), entry -> count.incrementAndGet());
    ObjectUtil.thenNonEmpty(Maps.newHashMap(), entry -> count.incrementAndGet());

    assertTrue(count.get() == 1);
  }

}
