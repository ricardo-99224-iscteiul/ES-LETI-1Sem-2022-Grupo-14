package pt.es2022.grupo14;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class WeekCalendar extends Calendar {

    private Week week;

    /**
     * Construtor da classe WeekCalendar
     * @param events é uma lista com eventos
     */
    public WeekCalendar(ArrayList<CalendarEvent> events) {
        super(events);
        week = new Week(LocalDate.now());
    }

    @Override
    protected boolean dateInRange(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        return Week.getStartOfWeek(date).equals(week.getDay(DayOfWeek.MONDAY));
    }

    @Override
    protected LocalDate getDateFromDay(DayOfWeek day) {
        if (day == null) throw new IllegalArgumentException("Day cannot be null");

        return week.getDay(day);
    }

    /**
     * Número de dias a serem mostrados
     */
    protected int numDaysToShow() {
        return 5;
    }

    /**
     * Definir primeiro dia da semana
     */
    @Override
    protected DayOfWeek getStartDay() {
        return DayOfWeek.MONDAY;
    }

    /**
     * Definir último dia da semana
     */
    @Override
    protected DayOfWeek getEndDay() {
        return DayOfWeek.FRIDAY;
    }

    /**
     * Cria uma nova semana com o dia atual
     */
    @Override
    protected void setRangeToToday() {
        week = new Week(LocalDate.now());
    }

    @Override
    protected double dayToPixel(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) throw new IllegalArgumentException("DayOfWeek cannot be null");

        return TIME_COL_WIDTH + getDayWidth() * (dayOfWeek.getValue() - 1);
    }

    /**
     * Viaja para a próxima semana
     */
    public void nextWeek() {
        week = week.nextWeek();
        repaint();
    }

    /**
     * Viaja para a semana anterior
     */
    public void prevWeek() {
        week = week.prevWeek();
        repaint();
    }

    /**
     * Viaja para o próximo mês
     */
    public void nextMonth() {
        week = new Week(week.getDay(DayOfWeek.MONDAY).plusWeeks(4));
        repaint();
    }

    /**
     * Viaja para o mês anterior
     */
    public void prevMonth() {
        week = new Week(week.getDay(DayOfWeek.MONDAY).minusWeeks(4));
        repaint();
    }

}
