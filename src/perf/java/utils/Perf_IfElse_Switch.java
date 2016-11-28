package utils;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

public class Perf_IfElse_Switch {

  private static final int boundry = 10;
  private static final String[] strings = new String[] {"A", "B"};

  @Benchmark
  public int test_ifElse() {
    int hit = 0;
    String target = strings[new Random().nextInt(strings.length)];
    if (target.equals("A")) {
      ++hit;
    } else if (target.equals("B")) {
      ++hit;
    } else if (target.equals("C")) {
      ++hit;
    } else if (target.equals("D")) {
      ++hit;
    } else if (target.equals("E")) {
      ++hit;
    } else if (target.equals("F")) {
      ++hit;
    } else if (target.equals("G")) {
      ++hit;
    } else if (target.equals("H")) {
      ++hit;
    } else if (target.equals("I")) {
      ++hit;
    } else if (target.equals("J")) {
      ++hit;
    }
    return hit;
  }

  @Benchmark
  public int test_switch() {
    int hit = 0;
    String target = strings[new Random().nextInt(strings.length)];
    switch (target) {
      case "A":
        ++hit;
        break;
      case "B":
        ++hit;
        break;
      case "C":
        ++hit;
        break;
      case "D":
        ++hit;
        break;
      case "E":
        ++hit;
        break;
      case "F":
        ++hit;
        break;
      case "G":
        ++hit;
        break;
      case "H":
        ++hit;
        break;
      case "I":
        ++hit;
        break;
      case "J":
        ++hit;
        break;
    }
    return hit;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_IfElse_Switch.class.getSimpleName())
        .forks(1)
        .measurementIterations(3)
        .warmupIterations(3)
        .build();
    new Runner(opt).run();
  }
}
