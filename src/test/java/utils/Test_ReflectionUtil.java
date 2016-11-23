package utils;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ReflectionUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.lang.invoke.MethodHandle;

import static org.junit.Assert.assertTrue;

public final class Test_ReflectionUtil {

  @ToString
  public static class NewType<A, B> {
    @Getter @Setter Class<A> a;
    @Getter @Setter Class<B> b;
    final @Getter int c = 0;
  }

  public static class AType extends NewType<Integer, Long> {
    int d = 0;
    public int get() {
      return ++d;
    }
  }

  public static class BType extends NewType<Double, String> {
    int d = 0;
    public int get() {
      return ++d;
    }
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  public static final Throwable exception = new NotInitializedException("hey");

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

  @Test
  public void test_invoke() {
    AType aType = new AType();

    MethodHandle method = ReflectionUtil.extractMethodHandle(AType.class, "get", int.class);
    assertTrue(((Integer) ReflectionUtil.invoke(method, aType)) > 0);

    method = ReflectionUtil.extractMethodHandle(AType.class, "get", int.class);
    assertTrue(((Integer) ReflectionUtil.invoke(method, aType)) > 1);

    ReflectionUtil.invoke(ReflectionUtil.extractMethodHandle(NotInitializedException.class, "getMessage", String.class), exception);
  }

}
