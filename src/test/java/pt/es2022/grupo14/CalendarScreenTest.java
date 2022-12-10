package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarScreenTest
{
    CalendarScreen calendarScreen;

    @BeforeEach
    void setUp()
    {
        calendarScreen = new CalendarScreen();
    }

    @Test
    void addEventsToCal()
    {
        assertThrows(IllegalArgumentException.class, () -> calendarScreen.addEventsToCal(null));

    }

    @Test
    void changeColor()
    {
        assertThrows(IllegalArgumentException.class, () -> calendarScreen.changeColor(null));
    }
}