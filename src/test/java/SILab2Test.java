import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class SILab2Test {

    @Test
    public void testEvery() {
        // allItems е null
        RuntimeException ex1 = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(null, "1234567890123456"));
        assertEquals("allItems list can't be null!", ex1.getMessage());

        // Item со празно име
        RuntimeException ex2 = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(List.of(new Item("", 1, 100, 0)), "1234567890123456"));
        assertEquals("Invalid item!", ex2.getMessage());

        //  Valid item, без попуст, без -30
        assertEquals(200, SILab2.checkCart(
                List.of(new Item("Item1", 2, 100, 0)),
                "1234567890123456"
        ), 0.01);

        // Item со цена > 300, -30 треба да се одземе
        assertEquals(470, SILab2.checkCart(
                List.of(new Item("Item2", 1, 500, 0)),
                "1234567890123456"
        ), 0.01);

        // Item со попуст > 0, пресметка со попуст
        assertEquals(360, SILab2.checkCart(
                List.of(new Item("Item3", 2, 200, 0.1)),
                "1234567890123456"
        ), 0.01);

        // Картичка со невалиден карактер
        RuntimeException ex3 = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(List.of(new Item("Item", 1, 100, 0)), "1234abcd90123456"));
        assertEquals("Invalid character in card number!", ex3.getMessage());

        // Картичка со помалку од 16 chars
        RuntimeException ex4 = assertThrows(RuntimeException.class, () ->
                SILab2.checkCart(List.of(new Item("Item", 1, 100, 0)), "1234"));
        assertEquals("Invalid card number!", ex4.getMessage());
    }

    @Test
    public void testMultiple() {
        //F F F → false → не се одзема -30
        assertEquals(100, SILab2.checkCart(
                List.of(new Item("Item", 1, 100, 0)),
                "1234567890123456"
        ), 0.01);

        //T F F → true → се одзема -30
        assertEquals(470, SILab2.checkCart(
                List.of(new Item("Item", 1, 500, 0)),
                "1234567890123456"
        ), 0.01);

        //F T F → true → се одзема -30
        assertEquals(330, SILab2.checkCart(
                List.of(new Item("Item", 1, 200, 0.1)),
                "1234567890123456"
        ), 0.01); // 200 -30 + (200*0.9) = 330

        //F F T → true → се одзема -30
        assertEquals(970, SILab2.checkCart(
                List.of(new Item("Item", 11, 100, 0)),
                "1234567890123456"
        ), 0.01); // 1100 - 30 = 1070
    }
}
