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

import static io.rebolt.core.constants.Constant.COMMA;

public class Perf_StringUtil_Join {

  private static final int count = 1000;
  private static final String[] initValues = new String[count];

  static {
    for (int i = 0; i < count; i++) {
      initValues[i] = StringUtil.randomAlpha(10);
    }
  }

  @Benchmark
  public void test_join1() {
    @SuppressWarnings("unused")
    String value = Joiner.on(COMMA).join(initValues);
  }

  /**
   * very poor performance
   */
  @Benchmark
  public void test_join2() {
    @SuppressWarnings("unused")
    String value = Stream.of(initValues).map(String::valueOf).collect(Collectors.joining(COMMA));
  }

  @Benchmark
  public void test_join3() {
    @SuppressWarnings("unused")
    String value = String.join(COMMA, (CharSequence[]) initValues);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_StringUtil_Join.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }

}
