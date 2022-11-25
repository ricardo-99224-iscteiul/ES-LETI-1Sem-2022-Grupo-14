package pt.es2022.grupo14;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CalendarEventTest
{

    LocalDate date;
    LocalTime start;
    LocalTime end;
    String text;
    Color color;
    CalendarEvent calendarEvent;

    @BeforeEach
    void setUp()
    {
        date = LocalDate.of(2022, 12, 25);
        start = LocalTime.of(12, 0);
        end = LocalTime.of(23, 59);
        text = "Random event";
        color = Color.black;
        calendarEvent = new CalendarEvent(date, start, end, text, color);
    }

    @Test
    void testCalendarEvent()
    {
        assertThrows(IllegalArgumentException.class, () -> new CalendarEvent(null, null, null, null));
    }

    @Test
    void getDate()
    {
        assertEquals(date, calendarEvent.getDate(), "getDate() should work");
    }

    @Test
    void getStart()
    {
        assertEquals(start, calendarEvent.getStart(), "getStart() should work");
    }

    @Test
    void getEnd()
    {
        assertEquals(end, calendarEvent.getEnd(), "getEnd() should work");
    }

    @Test
    void getText()
    {
        assertEquals(text, calendarEvent.getText(), "getText() should work");
    }

    @Test
    void getColor()
    {
        assertEquals(color, calendarEvent.getColor(), "getColor() should work");
    }

    @Test
    void setColor()
    {
        color = Color.black;
        calendarEvent.setColor(color);
        assertEquals(color, calendarEvent.getColor(), "setColor() should work");
        assertThrows(IllegalArgumentException.class, () -> calendarEvent.setColor(null));
    }

    @Test
    void testEquals()
    {
        assertEquals(calendarEvent, calendarEvent, "Should be equal to itself");
        assertNotEquals(null, calendarEvent, "Shouldn't be equal to null");
        assertNotEquals("string", calendarEvent, "Shouldn't be equal to different type");

        LocalDate diffdate = LocalDate.of(2022, 12, 21);
        CalendarEvent newcal = new CalendarEvent(diffdate, start, end, text);
        assertNotEquals(newcal, calendarEvent, "Date isn't equal");

        LocalTime difftime = LocalTime.of(11, 0);
        newcal = new CalendarEvent(date, difftime, end, text);
        assertNotEquals(newcal, calendarEvent, "Start isn't equal");

        newcal = new CalendarEvent(date, start, difftime, text);
        assertNotEquals(newcal, calendarEvent, "End isn't equal");

        newcal = new CalendarEvent(date, start, end, text);
        assertEquals(newcal, calendarEvent, "Should be equal");
    }
}