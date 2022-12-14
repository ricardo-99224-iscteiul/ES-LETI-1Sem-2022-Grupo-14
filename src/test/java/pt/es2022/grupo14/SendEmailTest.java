package pt.es2022.grupo14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SendEmailTest
{
    @Test
    void TestSendEmail()
    {
        assertDoesNotThrow(SendEmail::new);
    }
}