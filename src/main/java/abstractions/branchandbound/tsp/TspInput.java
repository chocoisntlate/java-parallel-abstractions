package abstractions.branchandbound.tsp;

public record TspInput(int[][] distances) {

    /** Builds a reproducible instance of the given size. */
    public static TspInput generate(int size, long seed) {
        // TODO: generate a random instance using new java.util.Random(seed)
        throw new UnsupportedOperationException("TODO");
    }
}
