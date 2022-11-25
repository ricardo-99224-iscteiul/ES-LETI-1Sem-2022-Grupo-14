package pt.es2022.grupo14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeekTest
{

    Week week;

    @BeforeEach
    void setUp()
    {
        week = new Week(LocalDate.of(2022, 11, 25));
    }

    @Test
    void testWeek()
    {
        assertThrows(IllegalArgumentException.class, () -> new Week(null));
        assertDoesNotThrow(() -> new Week(LocalDate.now()));
    }

    @Test
    void getStartOfWeek()
    {
        assertThrows(IllegalArgumentException.class, () -> Week.getStartOfWeek(null));
        assertEquals(LocalDate.of(2022, 11, 21), Week.getStartOfWeek(LocalDate.of(2022, 11, 25)), "getStartOfWeek() should work");
    }

    @Test
    void getDay()
    {
        assertThrows(IllegalArgumentException.class, () -> week.getDay(null));
        assertEquals(LocalDate.of(2022, 11, 25), week.getDay(DayOfWeek.FRIDAY), "getDay() should work");
    }

    @Test
    void nextWeek()
    {
        assertEquals(new Week(LocalDate.of(2022, 11, 28)), week.nextWeek(), "nextWeek() should work");
    }

    @Test
    void prevWeek()
    {
        assertEquals(new Week(LocalDate.of(2022, 11, 14)), week.prevWeek(), "prevWeek() should work");
    }

    @Test
    void testEquals()
    {
        assertNotEquals(null, week);
        assertNotEquals("", week);

        assertEquals(new Week(LocalDate.of(2022, 11, 25)), week);
    }
}