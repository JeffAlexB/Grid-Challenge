import java.util.ArrayList;
import java.util.List;

public class ScanTraversal {
    public static Direction scanRight(int[][] grid, int row, int col, int length) {
        if (col + length > grid[0].length) return null;
        long product = 1;
        List<Integer> sequence = new ArrayList<>();
        for (int k = 0; k < length; k++) {
            int value = grid[row][col + k];
            product *= value;
            sequence.add(value);
        }
        return new Direction(product, sequence, "Horizontal", row, col);
    }

    public static Direction scanDown(int[][] grid, int row, int col, int length) {
        if (row + length > grid.length) return null;
        long product = 1;
        List<Integer> sequence = new ArrayList<>();
        for (int k = 0; k < length; k++) {
            int value = grid[row + k][col];
            product *= value;
            sequence.add(value);
        }
        return new Direction(product, sequence, "Vertical", row, col);
    }

    public static Direction scanDiagonalSE(int[][] grid, int row, int col, int length) {
        if (row + length > grid.length || col + length > grid[0].length) return null;
        long product = 1;
        List<Integer> sequence = new ArrayList<>();
        for (int k = 0; k < length; k++) {
            int value = grid[row + k][col + k];
            product *= value;
            sequence.add(value);
        }
        return new Direction(product, sequence, "Diagonal ↘", row, col);
    }

    public static Direction scanDiagonalSW(int[][] grid, int row, int col, int length) {
        if (row + length > grid.length || col - length + 1 < 0) return null;
        long product = 1;
        List<Integer> sequence = new ArrayList<>();
        for (int k = 0; k < length; k++) {
            int value = grid[row + k][col - k];
            product *= value;
            sequence.add(value);
        }
        return new Direction(product, sequence, "Diagonal ↙", row, col);
    }
}
