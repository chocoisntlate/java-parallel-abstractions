package abstractions.allpairs.editdistance;

/**
 * All-pairs: returns a matrix where {@code result[i][j]} is the edit distance
 * between {@code strings[i]} and {@code strings[j]}. The matrix is symmetric
 * with a zero diagonal.
 */
public interface EditDistance {
    int[][] solve(EditDistanceInput input, int threads);
}
