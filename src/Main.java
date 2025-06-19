import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/test.txt"));
        List<int[]> gridList = new ArrayList<>();

        while (scanner.hasNextInt()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;
            String[] tokens = line.trim().split("\\s+"); // regex from stackoverflow
            int[] row = new int[tokens.length];
            for (int i = 0; i < row.length; i++) {
                row[i] = Integer.parseInt(tokens[i]);
            }
            gridList.add(row);
        }
        scanner.close();

        int[][] grid = new int[gridList.size()][];
        for (int i = 0; i < gridList.size(); i++) {
            grid[i] = gridList.get(i);
        }

        int lengthOfSequence = 4;
        long maxProduct = 0;

        // tracks position
        List<Integer> maxSequence = new ArrayList<>();
        String maxDirection = "";
        int maxRow = -1;
        int maxCol = -1;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                Direction[] results = {
                        ScanTraversal.scanRight(grid, row, col, lengthOfSequence),
                        ScanTraversal.scanDown(grid, row, col, lengthOfSequence),
                        ScanTraversal.scanDiagonalSE(grid, row, col, lengthOfSequence),
                        ScanTraversal.scanDiagonalSW(grid, row, col, lengthOfSequence),
                };

                for (Direction result : results) {
                    if (result != null && result.product > maxProduct){
                        maxProduct = result.product;
                        maxSequence = result.sequence;
                        maxDirection = result.direction;
                        maxRow = result.startRow;
                        maxCol = result.startCol;
                    }
                }
            }
        }


        System.out.printf("Maximum product found is: %,d\n", maxProduct);
        System.out.println("Sequence of integers: " + maxSequence);
        System.out.printf("Starting at row %d, column %d\n", maxRow, maxCol);
        System.out.println("Direction of sequence found: " + maxDirection);
    }
}