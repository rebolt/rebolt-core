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
  public void test_methodHandle() throws Throwable {
    String result = (String) ReflectionUtil.extractMethodHandle(NotInitializedException.class, exception, "getMessage").invokeExact();
  }

  @Benchmark
  public void test_methodHandleCached() throws Throwable {
    String result = (String) methodHandle.invokeExact();
  }

  @Benchmark
  public void test_method() {
    ReflectionUtil.invokeMethod(ReflectionUtil.extractMethod(NotInitializedException.class, "getMessage"), exception);
  }

  @Benchmark
  public void test_methodCached() {
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
Benchmark                                     Mode  Cnt         Score         Error  Units
Perf_ReflectionUtil.test_method              thrpt    3   3425057.546 ± 1224178.610  ops/s
Perf_ReflectionUtil.test_methodCached        thrpt    3  17817400.447 ± 2503087.320  ops/s
Perf_ReflectionUtil.test_methodHandle        thrpt    3  26497298.184 ± 3070004.136  ops/s
Perf_ReflectionUtil.test_methodHandleCached  thrpt    3  51608140.275 ± 3387418.426  ops/s
Perf_ReflectionUtil.test_origin              thrpt    3  53733156.365 ± 7151268.195  ops/s
Perf_ReflectionUtil.test_reflection          thrpt    3   3365737.393 ±  235788.078  ops/s
 */