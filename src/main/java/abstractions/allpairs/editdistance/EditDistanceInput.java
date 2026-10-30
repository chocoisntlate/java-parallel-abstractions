package abstractions.allpairs.editdistance;

public record EditDistanceInput(String[] strings) {

    /** Builds a reproducible instance of {@code size} strings. */
    public static EditDistanceInput generate(int size, long seed) {
        // TODO: generate `size` random strings of a fixed length over a small
        //  alphabet using new java.util.Random(seed)
        throw new UnsupportedOperationException("TODO");
    }
}
