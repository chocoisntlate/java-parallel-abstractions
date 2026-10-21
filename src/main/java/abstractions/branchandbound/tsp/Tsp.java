package abstractions.branchandbound.tsp;

/** Branch and bound: returns the cost of the shortest tour. */
public interface Tsp {
    int solve(TspInput input, int threads);
}
