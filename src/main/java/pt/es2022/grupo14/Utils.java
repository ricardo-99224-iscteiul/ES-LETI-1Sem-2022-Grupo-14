package pt.es2022.grupo14;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Utils
{
    public static final String LIGHT_MODE = "./src/main/java/pt/es2022/grupo14/light_mode.png";
    public static final String DARK_MODE = "./src/main/java/pt/es2022/grupo14/dark_mode.png";
    public static final String CALENDARS = "src/calendars/";

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
