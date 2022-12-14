package pt.es2022.grupo14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarReaderTest
{
    CalendarReader calendarReader;

    @BeforeEach
    void setUp()
    {
        calendarReader = new CalendarReader();
    }

    @Test
    void read()
    {
        assertThrows(IllegalArgumentException.class, () -> calendarReader.read(null));
        assertThrows(IllegalArgumentException.class, () -> calendarReader.read(""));
        assertDoesNotThrow(() -> calendarReader.read("webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=xptoo@iscte.pt&password=randompass"));
    }
}