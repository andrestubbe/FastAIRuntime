package fastairuntime.benchmark;

import fastairuntime.FastAIRuntime;
import fastairuntime.FastCommand;
import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import org.openjdk.jmh.annotations.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class Benchmark {

    private FastAIRuntime runtime;
    private FastCommand validCmd;
    private FastCommand unknownCmd;

    @Setup
    public void setup() {
        runtime = new FastAIRuntime();
        runtime.register(new FastTool() {
            @Override
            public String name() {
                return "noop.benchmark";
            }

            @Override
            public FastObservation execute(Map<String, Object> args) {
                return new FastObservation() {
                    @Override public boolean success() { return true; }
                    @Override public String message() { return "ok"; }
                };
            }
        });

        validCmd = new FastCommand("noop.benchmark", Map.of("key", "val"));
        unknownCmd = new FastCommand("unknown.tool", Map.of());
    }

    @org.openjdk.jmh.annotations.Benchmark
    public FastObservation benchmarkToolDispatch() {
        return runtime.execute(validCmd);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public FastObservation benchmarkMissingToolBoundary() {
        return runtime.execute(unknownCmd);
    }
}