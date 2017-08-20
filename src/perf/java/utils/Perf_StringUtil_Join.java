package utils;

import com.google.common.base.Joiner;
import io.rebolt.core.utils.StringUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rebolt.core.constants.Constants.STRING_COMMA;

public class Perf_StringUtil_Join {

  private static final int count = 1000;
  private static final String[] initValues = new String[count];

  static {
    for (int i = 0; i < count; i++) {
      initValues[i] = StringUtil.randomAlpha(10);
    }
  }

  @Benchmark
  public void test_guava() {
    @SuppressWarnings("unused")
    String value = Joiner.on(STRING_COMMA).join(initValues);
  }

  /**
   * very poor performance
   */
  @Benchmark
  public void test_stream() {
    @SuppressWarnings("unused")
    String value = Stream.of(initValues).map(String::valueOf).collect(Collectors.joining(STRING_COMMA));
  }

  @Benchmark
  public void test_native() {
    @SuppressWarnings("unused")
    String value = String.join(STRING_COMMA, (CharSequence[]) initValues);
  }

  @Benchmark
  public void test_util() {
    @SuppressWarnings("unused")
    String value = StringUtil.join(STRING_COMMA, initValues);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_StringUtil_Join.class.getSimpleName())
        .forks(1)
        .measurementIterations(3)
        .warmupIterations(5)
        .build();
    new Runner(opt).run();
  }

}

/*
Benchmark                          Mode  Cnt      Score       Error  Units
Perf_StringUtil_Join.test_guava   thrpt    3  42099.345 ± 34022.347  ops/s
Perf_StringUtil_Join.test_native  thrpt    3  40663.711 ±  6381.123  ops/s
Perf_StringUtil_Join.test_stream  thrpt    3  37709.746 ±  3580.409  ops/s
Perf_StringUtil_Join.test_util    thrpt    3  40234.401 ±  4172.017  ops/s
 */