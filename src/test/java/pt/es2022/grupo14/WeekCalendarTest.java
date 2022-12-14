package pt.es2022.grupo14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WeekCalendarTest
{

    ArrayList<CalendarEvent> events;
    WeekCalendar weekCalendar;

    @BeforeEach
    void setUp()
    {
        events = new ArrayList<>();
        events.add(new CalendarEvent(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(23, 59), "Event 1"));
        events.add(new CalendarEvent(LocalDate.of(1900, 12, 11), LocalTime.of(12, 0), LocalTime.of(23, 59), "Event 2"));

        weekCalendar = new WeekCalendar(events);
    }

    @Test
    void testWeekCalendar()
    {
        assertThrows(IllegalArgumentException.class, () -> new WeekCalendar(null));
    }

    @Test
    void dateInRange()
    {
        assertThrows(IllegalArgumentException.class, () -> weekCalendar.dateInRange(null));

        assertTrue(weekCalendar.dateInRange(events.get(0).getDate()), "Should be in range");
        assertFalse(weekCalendar.dateInRange(events.get(1).getDate()), "Shouldn't be in range");
    }

    @Test
    void getDateFromDay()
    {
        assertThrows(IllegalArgumentException.class, () -> weekCalendar.getDateFromDay(null));

        assertEquals(LocalDate.now(), weekCalendar.getDateFromDay(LocalDate.now().getDayOfWeek()));
    }

    @Test
    void numDaysToShow()
    {
        assertEquals(5, weekCalendar.numDaysToShow());
    }

    @Test
    void getStartDay()
    {
        assertEquals(DayOfWeek.MONDAY, weekCalendar.getStartDay());
    }

    @Test
    void getEndDay()
    {
        assertEquals(DayOfWeek.FRIDAY, weekCalendar.getEndDay());
    }

    @Test
    void dayToPixel()
    {
        assertThrows(IllegalArgumentException.class, () -> weekCalendar.dayToPixel(null));
        assertDoesNotThrow(() -> weekCalendar.dayToPixel(DayOfWeek.MONDAY));
    }

    @Test
    void setRangeToToday()
    {
        assertDoesNotThrow(() -> weekCalendar.setRangeToToday());
    }

    @Test
    void nextWeek()
    {
        assertDoesNotThrow(() -> weekCalendar.nextWeek());
    }

    @Test
    void prevWeek()
    {
        assertDoesNotThrow(() -> weekCalendar.prevWeek());
    }

    @Test
    void nextMonth()
    {
        assertDoesNotThrow(() -> weekCalendar.nextMonth());
    }

    @Test
    void prevMonth()
    {
        assertDoesNotThrow(() -> weekCalendar.prevMonth());
    }
}