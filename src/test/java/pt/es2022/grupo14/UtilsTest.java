package pt.es2022.grupo14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest
{

    @Test
    void getCalendars()
    {
        assertDoesNotThrow(() -> new Utils().getCalendars());
    }
}