package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class SendEmailTest
{
    @BeforeEach
    void setUp()
    {
        assertDoesNotThrow(SendEmail::new);
    }
}