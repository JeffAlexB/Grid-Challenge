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

    }
}