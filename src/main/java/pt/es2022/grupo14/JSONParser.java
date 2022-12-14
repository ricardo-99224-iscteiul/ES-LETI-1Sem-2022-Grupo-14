package pt.es2022.grupo14;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class JSONParser
{
    /**
     * Lê os eventos todos que estão dentro do ficheiro
     * @param username é o nome do ficheiro
     * @return dos eventos prontos a ser colocados no calendário
     * @throws IOException
     */
    public ArrayList<CalendarEvent> getAllEvents(String username) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");

        ArrayList<CalendarEvent> events = new ArrayList<>();
        Path webcalPath = Paths.get(Utils.CALENDARS + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        String json;

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null)
        {
            sb.append(line).append(" ");
        }
        bf.close();

        json = sb.toString();

        JSONObject obj = new JSONObject(json);

        JSONArray jsonArray = obj.getJSONArray("Cadeiras");

        for (Object course : jsonArray)
        {
            String name = ((JSONObject) course).getString("name");

            JSONArray arrayDT = ((JSONObject) course).getJSONArray("DT");

            for (Object datetime : arrayDT)
            {
                String start = ((JSONObject) datetime).getString("DTSTART");
                String end = ((JSONObject) datetime).getString("DTEND");


                int year = Integer.parseInt(start.substring(0, 4));
                int month = Integer.parseInt(start.substring(4, 6));
                int day = Integer.parseInt(start.substring(6, 8));
                int startTimeHour = Integer.parseInt(start.substring(8, 10));
                int startTimeMin = Integer.parseInt(start.substring(10, 12));
                int endTimeHour = Integer.parseInt(end.substring(8, 10));
                int endTimeMin = Integer.parseInt(end.substring(10, 12));

                LocalTime startTime = LocalTime.of(startTimeHour, startTimeMin);
                LocalTime endTime = LocalTime.of(endTimeHour, endTimeMin);

                if (TimeZone.getTimeZone( "Europe/Lisbon").inDaylightTime( new Date(year - 1900, month - 1, day) ))
                {
                    startTime = startTime.plusHours(1);
                    endTime = endTime.plusHours(1);
                }

                events.add(new CalendarEvent(LocalDate.of(year, month, day),
                        startTime,
                        endTime,
                        "",
                        Utils.LIGHT_COLOR));
            }
        }

        return events;
    }

    /**
     * Atualiza o ficheiro json a partir do url
     * @param username é o nome do ficheiro
     * @throws IOException
     */
    public void updateJSONFile(String username) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");

        ArrayList<CalendarEvent> events = new ArrayList<>();
        Path webcalPath = Paths.get(Utils.CALENDARS + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        String json;

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null)
        {
            sb.append(line).append(" ");
        }
        bf.close();

        json = sb.toString();

        JSONObject obj = new JSONObject(json);

        String webcal = obj.getString("URL");

        CalendarReader reader = new CalendarReader();

        reader.read(webcal);
    }
}
