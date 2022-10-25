package pt.es2022.grupo14;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frm = new JFrame();

        ArrayList<CalendarEvent> events = new ArrayList<>();
        events.add(new CalendarEvent(LocalDate.of(2022, 11, 11), LocalTime.of(14, 0), LocalTime.of(15, 30), "Test 11/11 14:00-14:20"));
        
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

        JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);

        frm.add(weekControls, BorderLayout.NORTH);

        frm.add(cal, BorderLayout.CENTER);
        frm.setSize(1000, 900);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
