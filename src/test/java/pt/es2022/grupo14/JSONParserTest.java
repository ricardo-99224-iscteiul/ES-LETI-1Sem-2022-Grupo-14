package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONParserTest
{
    JSONParser jsonParser;

    @BeforeEach
    void setUp()
    {
        jsonParser = new JSONParser();
    }

    @Test
    void getAllEvents()
    {
        assertThrows(IllegalArgumentException.class, () -> jsonParser.getAllEvents(null));
    }
}