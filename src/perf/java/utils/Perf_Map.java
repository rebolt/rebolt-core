package utils;

import com.google.common.collect.Maps;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.StringUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Map;

public class Perf_Map {

  private static final int loopCount = 100_000;
  private static final Map<String, String> map = Maps.newHashMap();
  private static final Map<Long, String> map2 = Maps.newHashMap();
  private static final Map<Integer, String> map3 = Maps.newHashMap();

  static {
    for (int i = 0; i < loopCount; ++i) {
      map.put(String.valueOf(i), StringUtil.randomAlpha(256));
      map2.put(HashUtil.djb2Hash(String.valueOf(i)), StringUtil.randomAlpha(256));
      map3.put(i, StringUtil.randomAlpha(256));
    }
  }

  @Benchmark
  public void test1() {
    for (int i = 0; i < loopCount; ++i) {
      map.get(String.valueOf(i));
    }
  }

  @Benchmark
  public void test2() {
    for (int i = 0; i < loopCount; ++i) {
      map2.get(HashUtil.djb2Hash(String.valueOf(i)));
    }
  }

  /**
   * x7 faster
   */
  @Benchmark
  public void test3() {
    for (int i = 0; i < loopCount; ++i) {
      map3.get(i);
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_Map.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }
}

/*
Benchmark        Mode  Cnt    Score   Error  Units
Perf_Map.test1  thrpt   20   64.856 ± 0.975  ops/s
Perf_Map.test2  thrpt   20   66.504 ± 0.800  ops/s
Perf_Map.test3  thrpt   20  445.544 ± 7.004  ops/s
 */
