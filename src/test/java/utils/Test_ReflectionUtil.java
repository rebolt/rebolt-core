package utils;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ReflectionUtil;
import org.junit.Test;

import java.lang.invoke.MethodHandle;

import static org.junit.Assert.assertTrue;

public final class Test_ReflectionUtil {

  public static class NewType<A, B> {
    Class<A> a;
    Class<B> b;
    final int c = 0;

    public Class<A> getA() {
      return a;
    }

    public void setA(Class<A> a) {
      this.a = a;
    }

    public Class<B> getB() {
      return b;
    }

    public void setB(Class<B> b) {
      this.b = b;
    }

    public int getC() {
      return c;
    }
  }

  public static class AType extends NewType<Integer, Long> {
    int d = 0;

    public int get(int a) {
      return d += a;
    }
  }

  public static class BType extends NewType<Double, String> {
    int d = 0;

    public int get(int a) {
      return d += a;
    }
  }

  public static class CType {
    int d = 14;

    public void get() {
      d++;
    }
  }

  @Test
  public void test_typeFinder() {
    AType type1 = new AType();
    BType type2 = new BType();

    type1.setA(ReflectionUtil.typeFinder(type1.getClass(), 0));
    type1.setB(ReflectionUtil.typeFinder(type1.getClass(), 1));
    type2.setA(ReflectionUtil.typeFinder(type2.getClass(), 0));
    type2.setB(ReflectionUtil.typeFinder(type2.getClass(), 1));

    assertTrue(Integer.class.equals(type1.getA()));
    assertTrue(Long.class.equals(type1.getB()));
    assertTrue(Double.class.equals(type2.getA()));
    assertTrue(String.class.equals(type2.getB()));
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  private static final Throwable exception = new NotInitializedException("hey");

  @Test
  public void test_invokeHandle() throws Throwable {
    AType aType = new AType();

    int param = 1;
    MethodHandle method = ReflectionUtil.extractMethodHandle(AType.class, aType, "get", int.class);
    int result = (int) method.invokeExact(param);
    assertTrue(result > 0);

    method = ReflectionUtil.extractMethodHandle(AType.class, aType, "get", int.class);
    result = (int) method.invokeExact(param);
    assertTrue(result > 0);

    MethodHandle method2 = ReflectionUtil.extractMethodHandle(CType.class, new CType(), "get");
    method2.invokeExact();

    MethodHandle method3 = ReflectionUtil.extractMethodHandle(NotInitializedException.class, exception, "getMessage");
    String resultString = (String) method3.invokeExact();
    assertTrue(resultString.length() > 0);
  }

}
