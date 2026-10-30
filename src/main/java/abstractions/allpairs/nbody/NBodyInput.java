package abstractions.allpairs.nbody;

public record NBodyInput(double[][] positions, double[][] velocities, double[] masses, int steps, double dt) {

    /** Number of dimensions: x, y, z. */
    static final int DIMS = 3;

    /** Time steps per run. Fixed, so that {@code size} alone sets the work (steps * size^2). */
    static final int STEPS = 100;

    /** Length of one time step, in simulation units. */
    static final double DT = 0.001;

    /** Bodies start inside a cube spanning [-POSITION_RANGE, POSITION_RANGE) on each axis. */
    static final double POSITION_RANGE = 1.0;

    /** Initial velocities span [-VELOCITY_RANGE, VELOCITY_RANGE) on each axis. */
    static final double VELOCITY_RANGE = 0.1;

    /** Masses span [MIN_MASS, MAX_MASS), so no body is massless. */
    static final double MIN_MASS = 0.5;
    static final double MAX_MASS = 1.5;

    /**
     * Builds a reproducible instance of {@code size} bodies. The same size and
     * seed always give the same instance, as long as the order of the random
     * draws below does not change.
     */
    public static NBodyInput generate(int size, long seed) {
        java.util.Random random = new java.util.Random(seed);

        double[][] positions = new double[size][DIMS];
        double[][] velocities = new double[size][DIMS];
        double[] masses = new double[size];

        for (int i = 0; i < size; i++) {
            for (int d = 0; d < DIMS; d++) {
                positions[i][d] = random.nextDouble(-POSITION_RANGE, POSITION_RANGE);
                velocities[i][d] = random.nextDouble(-VELOCITY_RANGE, VELOCITY_RANGE);
            }
            masses[i] = random.nextDouble(MIN_MASS, MAX_MASS);
        }

        return new NBodyInput(positions, velocities, masses, STEPS, DT);
    }
}
