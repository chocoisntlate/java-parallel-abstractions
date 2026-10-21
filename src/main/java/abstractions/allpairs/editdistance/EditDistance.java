package abstractions.allpairs.editdistance;

/** All-pairs: returns the edit distance between the two strings. */
public interface EditDistance {
    int solve(EditDistanceInput input, int threads);
}
