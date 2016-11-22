package utils;

import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ReflectionUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.InvocationTargetException;

public class Perf_ReflectionUtil {

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  private static final Throwable exception = new NotInitializedException("hey");

  @Benchmark
  public void test_origin() {
    exception.getMessage();
  }

  @Benchmark
  public void test_util() {
    ReflectionUtil.invoke(ReflectionUtil.extractMethod(NotInitializedException.class, "getMessage"), exception);
  }

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
Perf_ReflectionUtil.test_origin      thrpt   20  44915829.357 ± 514810.826  ops/s
Perf_ReflectionUtil.test_reflection  thrpt   20   2885613.440 ±  11662.109  ops/s
Perf_ReflectionUtil.test_util        thrpt   20   4509617.531 ±  26958.963  ops/s

Benchmark                             Mode  Cnt         Score        Error  Units
Perf_ReflectionUtil.test_origin      thrpt   20  44523270.676 ± 236809.702  ops/s
Perf_ReflectionUtil.test_reflection  thrpt   20   2708089.047 ±  68650.920  ops/s
Perf_ReflectionUtil.test_util        thrpt   20   4634544.982 ±   6989.686  ops/s
*/