package abstractions.allpairs.nbody;

/** All-pairs: simulates the bodies and returns their final positions. */
public interface NBody {
    double[][] solve(NBodyInput input, int threads);
}
