package utils;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ReflectionUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Perf_ReflectionUtil {

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  private static final Throwable exception = new NotInitializedException("hey");

  private static final MethodHandle methodHandle = ReflectionUtil.extractMethodHandle(NotInitializedException.class, exception, "getMessage");
  private static final Method method = ReflectionUtil.extractMethod(NotInitializedException.class, "getMessage");

  @Benchmark
  public void test_origin() {
    exception.getMessage();
  }

  @Benchmark
  public void test_util() throws Throwable {
    String result = (String) ReflectionUtil.extractMethodHandle(NotInitializedException.class, exception, "getMessage").invokeExact();
  }

  @Benchmark
  public void test_util2() throws Throwable {
    String result = (String) methodHandle.invokeExact();
  }

  @Benchmark
  public void test_util3() {
    ReflectionUtil.invokeMethod(ReflectionUtil.extractMethod(NotInitializedException.class, "getMessage"), exception);
  }

  @Benchmark
  public void test_util4() {
    ReflectionUtil.invokeMethod(method, exception);
  }

  @Benchmark
  public void test_reflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    NotInitializedException.class.getMethod("getMessage").invoke(exception);
  }

  public static void main(String[] args) throws Exception {
    Options opt = new OptionsBuilder()
        .include(Perf_ReflectionUtil.class.getSimpleName())
        .forks(1)
        .measurementIterations(3)
        .warmupIterations(3)
        .build();
    new Runner(opt).run();
  }
}

/*
Benchmark                             Mode  Cnt         Score         Error  Units
Perf_ReflectionUtil.test_origin      thrpt    3  53645346.202 ± 9799404.321  ops/s
Perf_ReflectionUtil.test_reflection  thrpt    3   3509467.943 ±  274535.183  ops/s
Perf_ReflectionUtil.test_util        thrpt    3   6051889.191 ± 1031042.138  ops/s
Perf_ReflectionUtil.test_util2       thrpt    3  51684185.208 ± 1466316.831  ops/s
Perf_ReflectionUtil.test_util3       thrpt    3   2504073.641 ±  230119.066  ops/s
Perf_ReflectionUtil.test_util4       thrpt    3  17481783.780 ±  977649.551  ops/s
 */