package pt.es2022.grupo14;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Utils
{
    public static final String LIGHT_MODE = "./src/main/java/pt/es2022/grupo14/light_mode.png";
    public static final String DARK_MODE = "./src/main/java/pt/es2022/grupo14/dark_mode.png";
    public static final String CALENDARS = "src/calendars/";
    public static final String WEBCAL = "src/calendars/webcal.txt";
    public static final String MENU = "./src/main/java/pt/es2022/grupo14/menu.png";
    public static final Color alphaGray = new Color(200, 200, 200, 64);

    public static final Color LIGHT_COLOR = new Color(0,191,255);
    public static final Color COLOR = new Color(30,144,255);
    public static final Color DARK_COLOR = new Color(0,0,255);

    public static final Color AVAILABLE = new Color(0,250,0);

    public static final Color NOT_AVAILABLE = new Color(250,0,0);

    /**
     * Vai buscar os ficheiros JSON à pasta "calendars" e guarda os seus nomes
     * @return de uma lista com os nomes dos ficheiros JSON
     */
    public ArrayList<String> getCalendars()
    {
        File calendarFolder = new File(CALENDARS);

        ArrayList<String> calendarNames = new ArrayList<>();

        if (!calendarFolder.exists()) return calendarNames;

        if (calendarFolder.listFiles() == null) return calendarNames;

        for (File file : Objects.requireNonNull(calendarFolder.listFiles()))
        {
            if (file.isFile() && file.getName().endsWith(".json"))
            {
                calendarNames.add(file.getName().replace(".json", ""));
            }
        }

        return calendarNames;
    }
}
