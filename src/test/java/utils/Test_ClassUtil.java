package utils;

import org.junit.Test;

import static io.rebolt.core.utils.ClassUtil.getSingleton;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class Test_ClassUtil {

  @Test
  public void test_getSingleton() {
    InnerClass clazz0 = getSingleton(InnerClass.class);
    InnerClass clazz1 = getSingleton(InnerClass.class);
    InnerClass clazz2 = new InnerClass();
    InnerClass clazz3 = new InnerClass();

    assertTrue(clazz0 == clazz1);
    assertFalse(clazz2 == clazz3);
  }

  @Test
  public void test_getSingletonWithArgs() {
    InnerClass clazz0 = getSingleton(InnerClass.class, new Object[] {"inner", 1}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz1 = getSingleton(InnerClass.class, new Object[] {"inner", 1}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz2 = getSingleton(InnerClass.class, new Object[] {"inner", 2}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz3 = getSingleton(InnerClass.class, new Object[] {"inner", 3}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz4 = getSingleton(InnerClass.class, new Object[] {"inner", 4}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz5 = getSingleton(InnerClass.class, new Object[] {"inner", 0}, new Class<?>[] {String.class, Integer.TYPE});
    InnerClass clazz6 = getSingleton(InnerClass.class, new Object[] {"inner1", 0}, new Class<?>[] {String.class, Integer.TYPE});

    assertTrue(clazz0 == clazz1);
    assertFalse(clazz0 == clazz2);
    assertFalse(clazz0 == clazz3);
    assertFalse(clazz0 == clazz4);
    assertFalse(clazz0 == clazz5);
    assertFalse(clazz0 == clazz6);
  }

  private static class InnerClass {
    String inner1;
    int inner2;

    InnerClass() {}

    InnerClass(String inner1, int inner2) {
      this.inner1 = inner1;
      this.inner2 = inner2;
    }
  }

}
