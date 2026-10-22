import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Position {
    int row;
    int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }
}

class Board {
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
            System.out.println("Hiba: A megadott mező kívül esik a táblán!");
            return false;
        }

        if (isFoxTurn) {
            if (!from.equals(foxPos)) {
                System.out.println("Hiba: Most a róka jön ('F')!");
                return false;
            }
            if (!isValidFoxMove(from, to)) {
                System.out.println("Hiba: Érvénytelen róka lépés!");
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

            if (selectedHound == null) {
                System.out.println("Hiba: Ezen a helyen nincs saját kutyád ('H')!");
                return false;
            }
            if (!isValidHoundMove(from, to)) {
                System.out.println("Hiba: Érvénytelen kutya lépés!");
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

public class FoxAndHoundsGame {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Fox and Hounds ===");
        System.out.println("Jelölések: 'F' = Róka, 'H' = Kutya, '.' = Üres mező\n");

        while (true) {
            board.printBoard();

            String status = board.checkGameStatus();
            if (status.equals("FOX_WIN")) {
                System.out.println("A Róka nyert!");
                break;
            } else if (status.equals("HOUNDS_WIN")) {
                System.out.println("A Kutyák nyertek!");
                break;
            }

            if (board.isFoxTurn()) {
                System.out.println("A RÓKA következik.");
            } else {
                System.out.println("A KUTYÁK következnek.");
            }

            System.out.print("Lépés (fromR fromC toR toC): ");
            try {
                int fromR = scanner.nextInt();
                int fromC = scanner.nextInt();
                int toR = scanner.nextInt();
                int toC = scanner.nextInt();

                board.move(fromR, fromC, toR, toC);
            } catch (Exception e) {
                System.out.println("Hibás bemenet!");
                scanner.nextLine();
            }
            System.out.println();
        }
        scanner.close();
    }
}