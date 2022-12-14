package pt.es2022.grupo14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuScreenTest
{

    MenuScreen menuScreen;
    @BeforeEach
    void setUp()
    {
        menuScreen = new MenuScreen();
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void showMenu()
    {
        assertDoesNotThrow(() -> menuScreen.showMenu());
        Main.darkMode = false;
        assertDoesNotThrow(() -> menuScreen.showMenu());
    }
}