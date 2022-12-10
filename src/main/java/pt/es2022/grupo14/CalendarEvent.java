package pt.es2022.grupo14;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarEvent {

    private static final Color DEFAULT_COLOR = Color.PINK;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String text;
    private Color color;

    /**
     * Construtor da classe CalendarEvent
     * @param date é a data do evento
     * @param start é a hora do início do evento
     * @param end é a hora do fim do evento
     * @param text é a descrição do evento
     */
    public CalendarEvent(LocalDate date, LocalTime start, LocalTime end, String text) {
        this(date, start, end, text, DEFAULT_COLOR);
    }

    /**
     * Construtor da classe CalendarEvent
     * @param date é a data do evento
     * @param start é a hora do início do evento
     * @param end é a hora do fim do evento
     * @param text é a descrição do evento
     * @param color é a cor do evento
     */
    public CalendarEvent(LocalDate date, LocalTime start, LocalTime end, String text, Color color) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        if (start == null) throw new IllegalArgumentException("Start time cannot be null");
        if (end == null) throw new IllegalArgumentException("End time cannot be null");
        if (text == null) throw new IllegalArgumentException("Text cannot be null");
        if (color == null) throw new IllegalArgumentException("Color cannot be null");

        this.date = date;
        this.start = start;
        this.end = end;
        this.text = text;
        this.color = color;
    }

    /**
     * @return da data do evento
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return da hora do início do evento
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * @return da hora do fim do evento
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * @return da descrição do evento
     */
    public String getText() {
        return text;
    }

    /**
     * @return da cor do evento
     */
    public Color getColor() {
        return color;
    }

    /**
	 * Muda a cor do evento
     * @param newColor é a nova cor do evento
     */
    public void setColor(Color newColor) {
        if (newColor == null) throw new IllegalArgumentException("Color cannot be null");
        color = newColor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarEvent that = (CalendarEvent) o;

        if (!date.equals(that.date)) return false;
        if (!start.equals(that.start)) return false;
        return end.equals(that.end);

    }
}
