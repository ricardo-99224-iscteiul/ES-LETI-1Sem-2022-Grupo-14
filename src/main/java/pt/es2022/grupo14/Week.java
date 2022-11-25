package pt.es2022.grupo14;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Week {

    private ArrayList<LocalDate> days;

    public Week(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        days = new ArrayList<>();
        LocalDate monday = getStartOfWeek(date);
        days.add(monday);
        for (int i = 1; i < 5; i++) {
            days.add(monday.plusDays(i));
        }
    }

    public static LocalDate getStartOfWeek(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        LocalDate day = date;
        while (day.getDayOfWeek() != DayOfWeek.MONDAY) {
            day = day.minusDays(1);
        }
        return day;
    }

    public LocalDate getDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek cannot be null");

        return days.get(dayOfWeek.getValue() - 1);
    }

    public Week nextWeek() {
        final LocalDate friday = getDay(DayOfWeek.FRIDAY);
        return new Week(friday.plusDays(3));
    }

    public Week prevWeek() {
        final LocalDate monday = getDay(DayOfWeek.MONDAY);
        return new Week(monday.minusDays(3));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Week)) return false;

        return this.days.equals(((Week) obj).days);
    }
}
