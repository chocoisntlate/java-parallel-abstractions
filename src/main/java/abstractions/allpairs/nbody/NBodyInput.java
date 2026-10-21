package abstractions.allpairs.nbody;

public record NBodyInput(double[][] positions, double[][] velocities, double[] masses, int steps, double dt) {

    /** Builds a reproducible instance of the given size. */
    public static NBodyInput generate(int size, long seed) {
        // TODO: generate a random instance using new java.util.Random(seed)
        throw new UnsupportedOperationException("TODO");
    }
}
