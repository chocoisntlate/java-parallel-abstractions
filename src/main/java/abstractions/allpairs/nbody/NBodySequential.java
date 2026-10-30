package abstractions.allpairs.nbody;

public class NBodySequential implements NBody {

  /**
   * Gravitational constant. 1.0 keeps the numbers simple ("simulation units").
   */
  static final double G = 1.0;

  /**
   * Softening term added to squared distances. Without it, two bodies that get
   * very close produce a near-zero distance and a huge (or infinite) force.
   */
  static final double SOFTENING = 1e-3;

  @Override
  public double[][] solve(NBodyInput input, int threads) {
    // Copy the input so the simulation does not modify it. Benchmarks reuse
    // the same input across runs, so it must stay unchanged.
    double[][] pos = deepCopy(input.positions());
    double[][] vel = deepCopy(input.velocities());
    double[] mass = input.masses();

    int n = pos.length; // number of bodies
    int dims = pos[0].length; // number of dimensions (usually 3: x, y, z)
    double dt = input.dt(); // length of one time step

    // Reused every step to hold the acceleration of each body.
    double[][] acc = new double[n][dims];

    for (int step = 0; step < input.steps(); step++) {
      // Phase 1: compute accelerations from all pairs of bodies.
      for (int i = 0; i < n; i++) {
        computeAcceleration(i, pos, mass, acc[i]);
      }

      // Phase 2: move every body. This must happen only after ALL
      // accelerations are computed, otherwise some bodies would see
      // positions from the new step and others from the old one.
      for (int i = 0; i < n; i++) {
        for (int d = 0; d < dims; d++) {
          vel[i][d] += acc[i][d] * dt;
          pos[i][d] += vel[i][d] * dt;
        }
      }
    }

    return pos;
  }

  /**
   * Writes into {@code out} the acceleration of body {@code i} caused by every
   * other body.
   *
   * <p>
   * Note: this does not use Newton's third law (the force of i on j equals
   * minus the force of j on i) to halve the work. Using it would mean writing
   * to another body's acceleration, which makes threads race with each other.
   * Computing each body's acceleration on its own lets the parallel versions
   * split bodies between threads with no shared writes, and keeps all
   * versions doing the same amount of work.
   */
  static void computeAcceleration(int i, double[][] pos, double[] mass, double[] out) {
    int dims = out.length;
    java.util.Arrays.fill(out, 0.0);

    for (int j = 0; j < pos.length; j++) {
      if (j == i) {
        continue; // a body does not pull on itself
      }

      // Squared distance between body i and body j.
      double distSq = SOFTENING;
      for (int d = 0; d < dims; d++) {
        double delta = pos[j][d] - pos[i][d];
        distSq += delta * delta;
      }

      // Newton's law of gravitation: a = G * m_j / r^2, pointing from i to j.
      // Multiplying the direction vector (delta, of length r) by
      // G * m_j / r^3 gives a vector of exactly that size.
      double invDist = 1.0 / Math.sqrt(distSq);
      double scale = G * mass[j] * invDist * invDist * invDist;

      for (int d = 0; d < dims; d++) {
        out[d] += (pos[j][d] - pos[i][d]) * scale;
      }
    }
  }

  private static double[][] deepCopy(double[][] src) {
    double[][] copy = new double[src.length][];
    for (int i = 0; i < src.length; i++) {
      copy[i] = src[i].clone();
    }
    return copy;
  }
}
