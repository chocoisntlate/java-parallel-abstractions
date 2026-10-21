package abstractions.branchandbound.knapsack;

/** Branch and bound: returns the best total value that fits in the capacity. */
public interface Knapsack {
    int solve(KnapsackInput input, int threads);
}
