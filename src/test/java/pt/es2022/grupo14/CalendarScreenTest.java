package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
    void showCalendar()
    {
        assertDoesNotThrow(() -> calendarScreen.showCalendar());
    }

    @Test
    void updateCalendarFiles()
    {
        assertDoesNotThrow(() -> calendarScreen.updateCalendarFiles());
    }

    @Test
    void addEventsToCal()
    {
        assertThrows(IllegalArgumentException.class, () -> calendarScreen.addEventsToCal(null));
        ArrayList<CalendarEvent> events = new ArrayList<>();
        events.add(event);
        assertDoesNotThrow(() -> calendarScreen.addEventsToCal(events));
        assertEquals(events, calendarScreen.events);
    }

    @Test
    void updateEvents()
    {
        calendarScreen.checkboxes.put("rdlpo", true);
        assertDoesNotThrow(() -> calendarScreen.updateEvents());
    }

    @Test
    void updateEventsAvailability()
    {
        calendarScreen.showCalendar();
        calendarScreen.checkboxes.put("rdlpo", true);
        assertDoesNotThrow(() -> calendarScreen.updateEventsAvailability());
    }

    @Test
    void existsEventsAt()
    {
        assertDoesNotThrow(() -> calendarScreen.existsEventAt(LocalDate.now(), LocalTime.now()));
    }

    @Test
    void changeColor()
    {
        assertThrows(IllegalArgumentException.class, () -> calendarScreen.changeColor(null));
        ArrayList<CalendarEvent> events = new ArrayList<>();
        CalendarEvent event = new CalendarEvent(
                LocalDate.now(),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                ""
        );
        events.add(event);

        assertEquals(events, calendarScreen.changeColor(events));
        assertDoesNotThrow(() -> calendarScreen.changeColor(events));
        assertDoesNotThrow(() -> calendarScreen.changeColor(events));
    }
}