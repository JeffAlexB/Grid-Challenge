import java.util.List;

public class Direction {
    public long product;
    public List<Integer> sequence;
    public String direction;
    public int startRow;
    public int startCol;

    public Direction(long product, List<Integer> sequence, String direction, int row, int col) {
        this.product = product;
        this.sequence = sequence;
        this.direction = direction;
        this.startRow = row;
        this.startCol = col;
    }
}
