package pt.es2022.grupo14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest
{

    @Test
    void TestMain()
    {
        assertDoesNotThrow(() -> Main.main(new String[]{""}));
    }

    @Test
    void changeScreen()
    {
        assertThrows(IllegalArgumentException.class, () -> Main.changeScreen(null));
        assertDoesNotThrow(() -> Main.changeScreen(Screen.CALENDAR));
        assertDoesNotThrow(() -> Main.changeScreen(Screen.MENU));
    }
}