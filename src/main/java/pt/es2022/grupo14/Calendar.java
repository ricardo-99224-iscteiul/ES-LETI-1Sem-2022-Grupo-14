package pt.es2022.grupo14;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JComponent;

public abstract class Calendar extends JComponent {
	
    static LocalTime START_TIME = LocalTime.of(8, 0);
    static LocalTime END_TIME = LocalTime.of(22, 30);

    static final int MIN_WIDTH = 600;
    static final int MIN_HEIGHT = 600;

    static final int HEADER_HEIGHT = 30;
    static final int TIME_COL_WIDTH = 100;

    private static final int FONT_LETTER_PIXEL_WIDTH = 7;
    private ArrayList<CalendarEvent> events;
    private double timeScale;
    private double dayWidth;
    private Graphics2D g2;
    
    private boolean darkMode = false;

    /**
     * Construtor da classe Calendar
     * @param events são os eventos do calendário
     */
    Calendar(ArrayList<CalendarEvent> events) {
        if (events == null) throw new IllegalArgumentException("Events array cannot be null");
        this.events = events;
    }

    /**
     * @param date é uma data
     */
    protected abstract boolean dateInRange(LocalDate date);

    /**
     * @param day é um dia da semana
     */
    protected abstract LocalDate getDateFromDay(DayOfWeek day);

    /**
     * Método para calcular o tamanho das quadriculas
     */
    private void calculateScaleVars() {
        int width = getWidth();
        int height = getHeight();

        if (width < MIN_WIDTH) {
            width = MIN_WIDTH;
        }

        if (height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        }

        timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
        dayWidth = (width - TIME_COL_WIDTH) / (double) numDaysToShow();
    }

    protected abstract int numDaysToShow();

    /**
     * @param dayOfWeek é um dia da semana
     */
    protected abstract double dayToPixel(DayOfWeek dayOfWeek);

    /**
     * @param time é um tempo
     * @return da altura do pixel na hora
     */
    private double timeToPixel(LocalTime time) {
        return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT;
    }
    
    /**
     * Desenha a aplicação
     */
    @Override
    protected void paintComponent(Graphics g) {
        calculateScaleVars();
        g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(darkMode) {
        	g2.setColor(Color.black);
        } else {
        	g2.setColor(Color.white);
        }
        
        g2.fillRect(0, 0, getWidth(), getHeight());

        if(darkMode) {
            g2.setColor(Color.white);
        } else {
        	g2.setColor(Color.black);
    	}

        drawDayHeadings();
        drawTodayShade();
        drawGrid();
        drawTimes();

        g2.setColor(Color.white);
        
        drawEvents();
    }
    
    /**
     * Muda as cores entre DarkMode e LightMode
     */
    public void changeColor() {
    	darkMode = !darkMode;
    	repaint();
    }

    protected abstract DayOfWeek getStartDay();

    protected abstract DayOfWeek getEndDay();

    /**
     * Escreve os dias da semana
     */
    private void drawDayHeadings() {
        int y = 20;
        int x;
        LocalDate day;
        DayOfWeek dayOfWeek;

        for (int i = 0; i <= getEndDay().getValue(); i++) {
        	if (i != 0) {
            dayOfWeek = DayOfWeek.of(i);
            day = getDateFromDay(dayOfWeek);

            String text = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            x = (int) (dayToPixel(DayOfWeek.of(i)) + (dayWidth / 2) - (FONT_LETTER_PIXEL_WIDTH * text.length() / 2));
            g2.drawString(text, x, y - 6);
            
            text = day.getDayOfMonth() + "/" + day.getMonthValue() + "/" + day.getYear();
            x = (int) (dayToPixel(DayOfWeek.of(i)) + (dayWidth / 2) - (FONT_LETTER_PIXEL_WIDTH * text.length() / 2));
            g2.drawString(text, x, y + 6);
        	}
        	else {
                String text = "Hours/Days";
                x = FONT_LETTER_PIXEL_WIDTH * text.length() / 2;
                g2.drawString(text, x - 20, y);
        	}
        }
    }

    /**
     * Desenha a grelha
     */
    private void drawGrid() {

        final Color ORIG_COLOUR = g2.getColor();

        Color alphaGray = new Color(128, 128, 128, 128);
        Color alphaGrayLighter = new Color(200, 200, 200, 128);
        g2.setColor(alphaGray);

        double x;
        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            x = dayToPixel(DayOfWeek.of(i));
            g2.draw(new Line2D.Double(x, HEADER_HEIGHT - 30, x, timeToPixel(END_TIME)));
        }

        double y;
        int x1 = 0;
        for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(30)) {
            y = timeToPixel(time);
            if (time.getMinute() == 0) {
                g2.setColor(alphaGray);
            } else {
                g2.setColor(alphaGrayLighter);
            }
            g2.draw(new Line2D.Double(x1, y, dayToPixel(getEndDay()) + dayWidth, y));
        }

        g2.setColor(ORIG_COLOUR);
    }

    /**
     * Sombreia o dia atual
     */
    private void drawTodayShade() {
        LocalDate today = LocalDate.now();

        if (!dateInRange(today)) return;

        final double x = dayToPixel(today.getDayOfWeek());
        final double y = timeToPixel(START_TIME);
        final double width = dayWidth;
        final double height = timeToPixel(END_TIME) - timeToPixel(START_TIME);

        final Color origColor = g2.getColor();
        Color alphaGray = new Color(200, 200, 200, 64);
        g2.setColor(alphaGray);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
        g2.setColor(origColor);
    }

    /**
     * Escreve os períodos de tempo
     */
    private void drawTimes() {
        int y;
        for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(30)) {
            y = (int) timeToPixel(time) + 25;
            g2.drawString((time + "-" + time.plusMinutes(30).toString()), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 50, y);
        }
    }

    /**
     * Preenche as slots de tempo com eventos
     */
    private void drawEvents() {
        for (CalendarEvent event : events) {
            if (!dateInRange(event.getDate())) continue;

            double x = dayToPixel(event.getDate().getDayOfWeek());
            double y0 = timeToPixel(event.getStart());

            Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(event.getEnd()) - timeToPixel(event.getStart())));
            Color origColor = g2.getColor();
            g2.setColor(event.getColor());
            g2.fill(rect);
            g2.setColor(origColor);

            Font origFont = g2.getFont();

            final float fontSize = origFont.getSize() - 1.6F;

            Font newFont = origFont.deriveFont(Font.BOLD, fontSize);
            g2.setFont(newFont);

            g2.drawString(event.getStart() + " - " + event.getEnd(), (int) x + 5, (int) y0 + 11);

            g2.setFont(origFont.deriveFont(fontSize));

            String text = event.getText();

            for (int i = 0; i < 10; i++)
            {
                int toCut = cutString(text);
                String toPrint = text.substring(0, toCut);
                g2.drawString(toPrint, (int) x + 5, (int) y0 + 23 + 11*i);

                if (toCut == 0) break;

                text = text.substring(toCut).strip();
            }

            g2.setFont(origFont);
        }
    }

    /**
     * Corta a descrição do evento para caber no slot
     * @param text é a descrição do evento
     * @return do caracter onde a palavra deve ser cortada
     */
    private int cutString(String text)
    {
        String lastString = "";
        for (String cut : text.split(" "))
        {
            if (g2.getFontMetrics().stringWidth((lastString +  " " + cut)) < dayWidth)
            {
                lastString += " " + cut;
            }
            else break;
        }

        return lastString.length() - 1;
    }

    /**
     * @return da largura do dia
     */
    protected double getDayWidth() {
        return dayWidth;
    }

    protected abstract void setRangeToToday();

    /**
     * Viajar para a semana do dia atual
     */
    public void goToToday() {
        setRangeToToday();
        repaint();
    }

    /**
     * Altera a lista de eventos
     * @param events é uma lista com eventos
     */
    public void setEvents(ArrayList<CalendarEvent> events) {
        this.events = events;
        repaint();
    }
    
}
