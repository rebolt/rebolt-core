package utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rebolt.core.utils.JsonUtil;
import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

public class Perf_JsonUtil_Factory {

  public final static String value = "{\"id\":\"id_value\", \"value\":\"value_value\"}";

  @Data
  public static class Json {
    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
  }

  @Benchmark
  public void read_util() {
    Json json = JsonUtil.read(value, Json.class);
  }

  @Benchmark
  public void read_native() throws IOException {
    Json json = new ObjectMapper().readValue(value, Json.class);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(Perf_JsonUtil_Factory.class.getSimpleName())
        .forks(1)
        .measurementIterations(3)
        .warmupIterations(5)
        .build();
    new Runner(opt).run();
  }
}

/*
Benchmark                           Mode  Cnt       Score       Error  Units
Perf_JsonUtil_Factory.read_native  thrpt    3  157866.140 ± 22468.541  ops/s
Perf_JsonUtil_Factory.read_util    thrpt    3  175337.157 ± 52734.487  ops/s
 */