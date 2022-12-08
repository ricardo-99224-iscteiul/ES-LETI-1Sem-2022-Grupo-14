package pt.es2022.grupo14;

import javax.swing.*;

public class Main
{
	private static JFrame frm = getFrm();
    private static CalendarScreen calendarScreen = new CalendarScreen();
    private static MenuScreen menuScreen = new MenuScreen();
    public static boolean darkMode = true;

    public static void main(String[] args) {
        frm.setVisible(true);
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frm.setSize(1000, 900);
        changeScreen(Screen.CALENDAR);
    }

    public static void changeScreen(Screen screen)
    {
        if (screen == null) throw new IllegalArgumentException("Screen cannot be null");

        switch (screen) {
            case CALENDAR:
                calendarScreen.showCalendar();
                break;
            case MENU:
                menuScreen.showMenu();
                break;
        }
    }

    public static JFrame getFrm()
    {
        if (frm != null)
            return frm;
        else return frm = new JFrame();
    }
}