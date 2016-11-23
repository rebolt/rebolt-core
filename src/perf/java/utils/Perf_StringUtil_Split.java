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
  public void perf_split1() {
    List<String> split = StringUtil.split(',', string);
  }

  @Benchmark
  public void perf_split2() {
    String[] split = string.split(",");
  }

  @Benchmark
  public void perf_split3() {
    List<String> split = Splitter.on(",").splitToList(string);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_StringUtil_Join.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }
}



