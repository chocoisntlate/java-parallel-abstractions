package abstractions.bench;

import abstractions.allpairs.nbody.NBody;
import abstractions.allpairs.nbody.NBodyInput;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class NBodyBenchmark {

    @Param({"Sequential", "Threads", "ThreadPool", "ForkJoin"})
    public String abstraction;

    @Param({"1", "2", "4", "8"})
    public int threads;

    @Param({"100"}) // TODO: pick sizes
    public int size;

    private NBody impl;
    private NBodyInput input;

    @Setup
    public void setup() {
        // TODO: pick impl based on `abstraction`, and generate `input`
    }

    @Benchmark
    public double[][] run() {
        return impl.solve(input, threads);
    }
}
