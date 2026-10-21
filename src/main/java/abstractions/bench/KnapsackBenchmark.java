package abstractions.bench;

import abstractions.branchandbound.knapsack.Knapsack;
import abstractions.branchandbound.knapsack.KnapsackInput;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class KnapsackBenchmark {

    @Param({"Sequential", "Threads", "ThreadPool", "ForkJoin"})
    public String abstraction;

    @Param({"1", "2", "4", "8"})
    public int threads;

    @Param({"100"}) // TODO: pick sizes
    public int size;

    private Knapsack impl;
    private KnapsackInput input;

    @Setup
    public void setup() {
        // TODO: pick impl based on `abstraction`, and generate `input`
    }

    @Benchmark
    public int run() {
        return impl.solve(input, threads);
    }
}
