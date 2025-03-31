import java.util.Arrays;

public class MatrixKey {
    private final int[][] matrix;

    public MatrixKey(int[][] matrix){
        this.matrix = matrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixKey key)) return false;
        return Arrays.deepEquals(this.matrix, key.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.matrix);
    }
}
