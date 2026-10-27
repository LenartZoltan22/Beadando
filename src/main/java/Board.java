import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] grid = new char[8][8];
    private Position foxPos;
    private List<Position> houndPositions;
    private boolean isFoxTurn;

    public Board() {
        initBoard();
    }

    public void initBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                grid[r][c] = '.';
            }
        }

        houndPositions = new ArrayList<>();
        houndPositions.add(new Position(0, 1));
        houndPositions.add(new Position(0, 3));
        houndPositions.add(new Position(0, 5));
        houndPositions.add(new Position(0, 7));

        for (Position h : houndPositions) {
            grid[h.row][h.col] = 'H';
        }

        foxPos = new Position(7, 4);
        grid[foxPos.row][foxPos.col] = 'F';

        isFoxTurn = true;
    }

    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int r = 0; r < 8; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < 8; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isFoxTurn() {
        return isFoxTurn;
    }

    public boolean move(int fromR, int fromC, int toR, int toC) {
        Position from = new Position(fromR, fromC);
        Position to = new Position(toR, toC);

        if (!from.isValid() || !to.isValid()) {
            return false;
        }

        if (isFoxTurn) {
            if (!from.equals(foxPos) || !isValidFoxMove(from, to)) {
                return false;
            }
            grid[foxPos.row][foxPos.col] = '.';
            foxPos = to;
            grid[foxPos.row][foxPos.col] = 'F';
        } else {
            Position selectedHound = null;
            for (Position h : houndPositions) {
                if (h.equals(from)) {
                    selectedHound = h;
                    break;
                }
            }

            if (selectedHound == null || !isValidHoundMove(from, to)) {
                return false;
            }

            grid[selectedHound.row][selectedHound.col] = '.';
            selectedHound.row = to.row;
            selectedHound.col = to.col;
            grid[to.row][to.col] = 'H';
        }

        isFoxTurn = !isFoxTurn;
        return true;
    }

    private boolean isValidFoxMove(Position from, Position to) {
        int rowDiff = Math.abs(to.row - from.row);
        int colDiff = Math.abs(to.col - from.col);
        return rowDiff == 1 && colDiff == 1 && grid[to.row][to.col] == '.';
    }

    private boolean isValidHoundMove(Position from, Position to) {
        int rowDiff = to.row - from.row;
        int colDiff = Math.abs(to.col - from.col);
        return rowDiff == 1 && colDiff == 1 && grid[to.row][to.col] == '.';
    }

    public String checkGameStatus() {
        if (foxPos.row == 0) {
            return "FOX_WIN";
        }
        if (getValidMovesForFox().isEmpty()) {
            return "HOUNDS_WIN";
        }
        return "ONGOING";
    }

    private List<Position> getValidMovesForFox() {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            Position p = new Position(foxPos.row + dir[0], foxPos.col + dir[1]);
            if (p.isValid() && isValidFoxMove(foxPos, p)) {
                moves.add(p);
            }
        }
        return moves;
    }
}