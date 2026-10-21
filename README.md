# java-parallel-abstractions

Evaluates Java concurrency abstractions on two parallel programming patterns: **all pairs** and **branch and bound**.

| Pattern          | Algorithms                       |
|------------------|----------------------------------|
| All pairs        | N-body, Edit distance            |
| Branch and bound | 0/1 knapsack, Traveling salesman |

Each algorithm is implemented with four abstractions:

| Class suffix | Abstraction                                      |
|--------------|--------------------------------------------------|
| `Sequential` | Single-threaded baseline and correctness reference |
| `Threads`    | Hand-threaded with `java.lang.Thread`            |
| `ThreadPool` | `java.util.concurrent.ThreadPoolExecutor`        |
| `ForkJoin`   | `ForkJoinPool` with recursive tasks              |

## Project structure

```
pom.xml                          Maven build (Java 25, JUnit 5, JMH)
src/
  main/java/abstractions/
    common/Config.java           Shared settings (random seed)
    allpairs/
      nbody/                     N-body
      editdistance/              Edit distance
    branchandbound/
      knapsack/                  0/1 knapsack
      tsp/                       Traveling salesman
    bench/                       One JMH benchmark per algorithm
  test/java/abstractions/        One correctness test per algorithm, same packages
scripts/
  run-benchmarks.sh              Build and run JMH, save results
  plot.py                        Plot results
results/                         Raw JMH output (JSON), one file per run
docs/                            Write-up and figures
```

Every algorithm package has the same layout, shown here for N-body:

```
nbody/
  NBody.java                     Interface: solve(NBodyInput input, int threads)
  NBodyInput.java                Problem instance; generate(size, seed) builds it reproducibly
  NBodySequential.java
  NBodyThreads.java
  NBodyThreadPool.java
  NBodyForkJoin.java
```

All implementations of an algorithm share its interface, so tests and benchmarks can treat them interchangeably. Tests check every implementation against `Sequential`.

## Building and running

```sh
mvn test                         # run correctness tests
./scripts/run-benchmarks.sh      # build and run all benchmarks -> results/<timestamp>.json
./scripts/run-benchmarks.sh TspBenchmark   # run a single benchmark
python3 scripts/plot.py results/<file>.json
```

Benchmarks are parameterised by `abstraction`, `threads` and `size`; override any of them with JMH's `-p`, e.g. `-p threads=1,4,16`.
