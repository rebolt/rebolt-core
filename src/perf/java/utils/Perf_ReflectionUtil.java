package utils;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ReflectionUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;

public class Perf_ReflectionUtil {

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  private static final Throwable exception = new NotInitializedException("hey");

  private static final MethodHandle methodHandle = ReflectionUtil.extractMethodHandle(NotInitializedException.class, "getMessage", String.class);

  @Benchmark
  public void test_origin() {
    exception.getMessage();
  }

  @Benchmark
  public void test_util() {
    ReflectionUtil.invoke(ReflectionUtil.extractMethodHandle(NotInitializedException.class, "getMessage", String.class), exception);
  }

  @Benchmark
  public void test_util2() {
    ReflectionUtil.invoke(methodHandle, exception);
  }

//  @Benchmark
//  public void test_util3() throws InvocationTargetException, IllegalAccessException {
//    methodHandle.invoke(exception);
//  }

  @Benchmark
  public void test_reflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    NotInitializedException.class.getMethod("getMessage").invoke(exception);
  }

  public static void main(String[] args) throws Exception {
    Options opt = new OptionsBuilder()
        .include(Perf_ReflectionUtil.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }
}

/*
Benchmark                             Mode  Cnt         Score        Error  Units
Perf_ReflectionUtil.test_origin      thrpt   20  44942275.404 ± 952396.638  ops/s
Perf_ReflectionUtil.test_reflection  thrpt   20   2901848.196 ±   5292.969  ops/s
Perf_ReflectionUtil.test_util        thrpt   20   4630720.403 ±  12562.949  ops/s
Perf_ReflectionUtil.test_util2       thrpt   20  15167119.012 ±  49621.433  ops/s
Perf_ReflectionUtil.test_util3       thrpt   20  15594955.590 ±  42841.588  ops/s
*/