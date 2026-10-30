package abstractions.allpairs.nbody;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import abstractions.common.Config;
import java.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class NBodyTest {

  private static final int SIZE = 50;

  @Test
  void generateIsReproducible() {
    NBodyInput first = NBodyInput.generate(SIZE, Config.SEED);
    NBodyInput second = NBodyInput.generate(SIZE, Config.SEED);

    assertArrayEquals(first.positions(), second.positions());
    assertArrayEquals(first.velocities(), second.velocities());
    assertArrayEquals(first.masses(), second.masses());
    assertEquals(first.steps(), second.steps());
    assertEquals(first.dt(), second.dt());
  }

  @Test
  void generateDependsOnSeed() {
    NBodyInput first = NBodyInput.generate(SIZE, Config.SEED);
    NBodyInput second = NBodyInput.generate(SIZE, Config.SEED + 1);

    assertFalse(Arrays.deepEquals(first.positions(), second.positions()));
  }

  @Test
  void generateHasExpectedShapeAndRanges() {
    NBodyInput input = NBodyInput.generate(SIZE, Config.SEED);

    assertEquals(SIZE, input.positions().length);
    assertEquals(SIZE, input.velocities().length);
    assertEquals(SIZE, input.masses().length);
    assertEquals(NBodyInput.STEPS, input.steps());
    assertEquals(NBodyInput.DT, input.dt());

    for (int i = 0; i < SIZE; i++) {
      assertEquals(NBodyInput.DIMS, input.positions()[i].length);
      assertEquals(NBodyInput.DIMS, input.velocities()[i].length);
      for (int d = 0; d < NBodyInput.DIMS; d++) {
        assertInRange(input.positions()[i][d], -NBodyInput.POSITION_RANGE, NBodyInput.POSITION_RANGE);
        assertInRange(input.velocities()[i][d], -NBodyInput.VELOCITY_RANGE, NBodyInput.VELOCITY_RANGE);
      }
      assertInRange(input.masses()[i], NBodyInput.MIN_MASS, NBodyInput.MAX_MASS);
    }
  }

  @Test
  void sequentialProducesFiniteResult() {
    NBodyInput input = NBodyInput.generate(SIZE, Config.SEED);
    double[][] startPositions = NBodyInput.generate(SIZE, Config.SEED).positions();

    double[][] result = new NBodySequential().solve(input, 1);

    assertEquals(SIZE, result.length);
    for (double[] body : result) {
      for (double coordinate : body) {
        assertTrue(Double.isFinite(coordinate));
      }
    }
    assertFalse(Arrays.deepEquals(startPositions, result));
    assertArrayEquals(startPositions, input.positions());
  }

  @Test
  @Disabled("TODO")
  void allImplementationsMatchSequential() {
    // TODO: generate an input with Config.SEED, run NBodySequential,
    // and assert every other implementation returns the same result.
  }

  private static void assertInRange(double value, double min, double max) {
    assertTrue(value >= min && value < max, value + " not in [" + min + ", " + max + ")");
  }
}
