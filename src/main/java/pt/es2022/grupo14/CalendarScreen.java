package pt.es2022.grupo14;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class CalendarScreen
{
    JFrame frm;
    Image lightMode = null;
    Image darkMode = null;
    ArrayList<CalendarEvent> events = new ArrayList<>();

    public void showCalendar()
    {
        boolean bDarkMode = true;

        frm = Main.getFrm();
        frm.getContentPane().removeAll();
        frm.repaint();

        JPanel panel = new JPanel();

        JPanel weekControls = new JPanel();
        Color alphaGray = new Color(200, 200, 200, 64);
        weekControls.setBackground(alphaGray);

        Utils utils = new Utils();
        JSONParser parser = new JSONParser();

        ArrayList<String> calendarNames = utils.getCalendars();

        for (String username : calendarNames)
        {
            try
            {
                addEventsToCal(parser.getAllEvents(username));
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        
        //addEventsToCal(new CalendarEvent(LocalDate.of(2022, 11, 11), LocalTime.of(14, 0), LocalTime.of(15, 30), "Test 11/11 14:00-14:20"));

        LocalTime earlier = LocalTime.MAX;
        LocalTime later = LocalTime.MIN;

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getStart().compareTo(earlier) < 0) {
                earlier = events.get(i).getStart();
            }

            if (events.get(i).getEnd().compareTo(later) < 0) {
                earlier = events.get(i).getEnd();
            }
        }

        WeekCalendar cal = new WeekCalendar(events);

//        cal.setStartTime(earlier);
//        cal.setEndTime(later);

        JButton goToTodayBtn = new JButton("Today");
        goToTodayBtn.addActionListener(e -> cal.goToToday());

        JButton nextWeekBtn = new JButton("Next Week");
        nextWeekBtn.addActionListener(e -> cal.nextWeek());

        JButton prevWeekBtn = new JButton("Previous Week");
        prevWeekBtn.addActionListener(e -> cal.prevWeek());

        JButton nextMonthBtn = new JButton("Next Month");
        nextMonthBtn.addActionListener(e -> cal.nextMonth());

        JButton prevMonthBtn = new JButton("Previous Month");
        prevMonthBtn.addActionListener(e -> cal.prevMonth());


        try
        {
            lightMode = ImageIO.read(new File(Utils.LIGHT_MODE));
            lightMode = lightMode.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH );
            darkMode = ImageIO.read(new File(Utils.DARK_MODE));
            darkMode = darkMode.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH );
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        JToggleButton mode = new JToggleButton();
        mode.setIcon(new ImageIcon(lightMode));
        mode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                cal.changeColor();
                if (state == ItemEvent.SELECTED)
                {
                    mode.setIcon(new ImageIcon(darkMode));
                    weekControls.setBackground(Color.darkGray);
                }
                else
                {
                    mode.setIcon(new ImageIcon(lightMode));
                    weekControls.setBackground(alphaGray);
                }
                frm.repaint();
            }
        });

        JButton menuBtn = new JButton();
        try {
            Image img = ImageIO.read(new File("./src/main/java/pt/es2022/grupo14/menu.png"));
            img = img.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH ) ;
            menuBtn.setIcon(new ImageIcon(img));
            menuBtn.addActionListener(e -> Main.changeScreen(Screen.MENU));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        weekControls.add(menuBtn);
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);
        weekControls.add(mode);

        frm.add(weekControls, BorderLayout.NORTH);
        frm.add(cal, BorderLayout.CENTER);

        frm.revalidate();
        frm.repaint();
    }

    public void addEventsToCal(CalendarEvent event) {
        events.add(event);
    }

    public void addEventsToCal(ArrayList<CalendarEvent> events) {
        this.events.addAll(events);
    }

    public void addEventsToCal(ArrayList<CalendarEvent> events, boolean clear) {
        if (clear)
            this.events =  events;
        else addEventsToCal(events);
    }
}
