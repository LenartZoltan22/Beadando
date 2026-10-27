import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testInitialTurnIsFox() {
        assertTrue(board.isFoxTurn(), "A játéknak a róka körével kell kezdődnie.");
    }

    @Test
    void testValidFoxMove() {
        boolean success = board.move(7, 4, 6, 3);
        assertTrue(success, "A róka átlós lépésének érvényesnek kell lennie.");
    }

    @Test
    void testInvalidHoundMoveBackward() {
        board.move(7, 4, 6, 3); // Róka lép, hogy a kutyák jöjjenek
        boolean success = board.move(0, 1, -1, 0); // Kutya próbál hátra lépni
        assertFalse(success, "A kutya nem léphet hátra.");
    }
}