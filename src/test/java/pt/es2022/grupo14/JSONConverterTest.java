package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONConverterTest {

    JSONConverter jsonConverter;

    @BeforeEach
    void setUp() {
        jsonConverter = new JSONConverter();
    }

    @Test
    void insert() {
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert("username", null));
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert(null, "name"));

        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert("username", null, "start", "end"));
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert(null, "name", "start", "end"));
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert("username", "name", null, "end"));
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.insert("username", "name", "start", null));
    }

    @Test
    void createFile() {
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.createFile(null));
    }

    @Test
    void closeFile() {
        assertThrows(IllegalArgumentException.class, () -> jsonConverter.closeFile(null));

    }
}
