package abstractions.allpairs.editdistance;

public record EditDistanceInput(String a, String b) {

    /** Builds a reproducible instance of the given size. */
    public static EditDistanceInput generate(int size, long seed) {
        // TODO: generate a random instance using new java.util.Random(seed)
        throw new UnsupportedOperationException("TODO");
    }
}
