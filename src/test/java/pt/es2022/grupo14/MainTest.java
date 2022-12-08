package pt.es2022.grupo14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest
{

    @Test
    void changeScreen()
    {
        assertThrows(IllegalArgumentException.class, () -> Main.changeScreen(null));
    }
}