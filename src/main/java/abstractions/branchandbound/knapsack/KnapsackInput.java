package abstractions.branchandbound.knapsack;

public record KnapsackInput(int[] weights, int[] values, int capacity) {

    /** Builds a reproducible instance of the given size. */
    public static KnapsackInput generate(int size, long seed) {
        // TODO: generate a random instance using new java.util.Random(seed)
        throw new UnsupportedOperationException("TODO");
    }
}
