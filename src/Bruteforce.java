import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bruteforce {
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
        int maxProduct = 0;

        // tracks position
        List<Integer> maxSequence = new ArrayList<>();
        String maxDirection = "";
        int maxRow = -1;
        int maxCol = -1;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                // horizontal check W--E
                if (col + lengthOfSequence <= grid[0].length) {
                    int product = 1;
                    List<Integer> sequence = new ArrayList<>();
                    for (int k = 0; k < lengthOfSequence; k++) {
                        int value = grid[row][col + k];
                        product *= value;
                        sequence.add(value);
                    }
                    if (product > maxProduct) {
                        maxProduct = product;
                        maxSequence = sequence;
                        maxDirection = "Horizontal";
                        maxRow = row;
                        maxCol = col;
                    }
                }

                // vertical check N--S
                if (row + lengthOfSequence <= grid.length) {
                    int product = 1;
                    List<Integer> sequence = new ArrayList<>();
                    for (int k = 0; k < lengthOfSequence; k++) {
                        int value = grid[row + k][col];
                        product *= value;
                        sequence.add(value);
                    }
                    if (product > maxProduct) {
                        maxProduct = product;
                        maxSequence = sequence;
                        maxDirection = "Vertical";
                        maxRow = row;
                        maxCol = col;
                    }
                }

                // diagonal check N--SE
                if (row + lengthOfSequence <= grid.length && col + lengthOfSequence <= grid[0].length) {
                    int product = 1;
                    List<Integer> sequence = new ArrayList<>();
                    for (int k = 0; k < lengthOfSequence; k++) {
                        int value = grid[row + k][col + k];
                        product *= value;
                        sequence.add(value);
                    }
                    if (product > maxProduct) {
                        maxProduct = product;
                        maxSequence = sequence;
                        maxDirection = "Diagonal";
                        maxRow = row;
                        maxCol = col;
                    }
                }

                // diagonal check N--SW
                if (row + lengthOfSequence <= grid.length && col - lengthOfSequence + 1 >= 0) {
                    int product = 1;
                    List<Integer> sequence = new ArrayList<>();
                    for (int k = 0; k < lengthOfSequence; k++) {
                        int value = grid[row + k][col - k];
                        product *= value;
                        sequence.add(value);
                    }
                    if (product > maxProduct) {
                        maxProduct = product;
                        maxSequence = sequence;
                        maxDirection = "Diagonal";
                        maxRow = row;
                        maxCol = col;
                    }
                }
            }
        }
        System.out.println("Maximum product found is: " + maxProduct);
        System.out.println("Sequence of integers: " + maxSequence);
        System.out.printf("Starting at row %d, column %d\n", maxRow, maxCol);
        System.out.println("Direction of sequence found: " + maxDirection);
    }
}