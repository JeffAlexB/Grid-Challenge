import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            // System.out.println(Arrays.toString(row)); // debug that the file is read
        }
        scanner.close();

        int[][] grid = new int[gridList.size()][];
        for (int i = 0; i < gridList.size(); i++) {
            grid[i] = gridList.get(i);
        }

        /*
        // debug to print the full grid
        System.out.println("Data in grid:");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        */

        final int lengthOfSequence = 4;
        int maxProduct = 0;

        for (int row = 0; row < grid.length; row++) {
            // int product = 1;
            for (int col = 0; col < grid[0].length; col++) {
                // horizontal check W--E
                if (col + lengthOfSequence <= grid[0].length) {
                    int product = 1;
                    for (int k = 0; k < lengthOfSequence; k++) {
                        product *= grid[row][col + k];
                    }
                    maxProduct = Math.max(maxProduct, product);
                }

                // vertical check N--S
                if (row + lengthOfSequence <= grid.length) {
                    int product = 1;
                    for (int k = 0; k < lengthOfSequence; k++) {
                        product *= grid[row + k][col];
                    }
                    maxProduct = Math.max(maxProduct, product);
                }

                // diagonal check N--SE
                if (row + lengthOfSequence <= grid.length && col + lengthOfSequence <= grid[0].length) {
                    int product = 1;
                    for (int k = 0; k < lengthOfSequence; k++) {
                        product *= grid[row + k][col + k];
                    }
                    maxProduct = Math.max(maxProduct, product);
                }

                // diagonal check N--SW
                if (row + lengthOfSequence <= grid.length && col - lengthOfSequence + 1 >= 0) {
                    int product = 1;
                    for (int k = 0; k < lengthOfSequence; k++) {
                        product *= grid[row + k][col - k];
                    }
                    maxProduct = Math.max(maxProduct, product);
                }
            }
        }
        System.out.println("Maximum product found is: " + maxProduct); // debug print max possible product found in grid
    }
}