import java.util.Scanner;

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

            System.out.print("Add meg a lépést (honnanR honnanC hovaR hovaC): ");
            try {
                int fromR = scanner.nextInt();
                int fromC = scanner.nextInt();
                int toR = scanner.nextInt();
                int toC = scanner.nextInt();

                boolean success = board.move(fromR, fromC, toR, toC);
                if (!success) {
                    System.out.println("Hiba: Érvénytelen lépés!\n");
                }
            } catch (Exception e) {
                System.out.println("Hibás bemenet! Számokat adj meg.\n");
                scanner.nextLine();
            }
            System.out.println();
        }
        scanner.close();
    }
}