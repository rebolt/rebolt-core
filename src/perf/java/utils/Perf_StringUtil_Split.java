package utils;

import com.google.common.base.Splitter;
import io.rebolt.core.utils.StringUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;

public class Perf_StringUtil_Split {

  private static final int length = 1000;
  private static final StringBuilder buffer = new StringBuilder();
  private static String string;

  static {
    for (int i = 0; i < length; i++) {
      buffer.append(StringUtil.randomAlpha(128));
      buffer.append(",");
    }
    string = buffer.toString();
  }

  @Benchmark
  public void perf_util() {
    List<String> split = StringUtil.split(',', string);
  }

  @Benchmark
  public void perf_native() {
    String[] split = string.split(",");
  }

  @Benchmark
  public void perf_guava() {
    List<String> split = Splitter.on(",").splitToList(string);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_StringUtil_Split.class.getSimpleName())
        .forks(1)
        .measurementIterations(3)
        .warmupIterations(3)
        .build();
    new Runner(opt).run();
  }
}

/*
Benchmark                           Mode  Cnt      Score      Error  Units
Perf_StringUtil_Split.perf_guava   thrpt    3  17438.920 ± 7556.117  ops/s
Perf_StringUtil_Split.perf_native  thrpt    3  17051.244 ± 1386.465  ops/s
Perf_StringUtil_Split.perf_util    thrpt    3  18149.706 ± 7152.384  ops/s
 */


