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

public class JSONParser
{
    public ArrayList<CalendarEvent> getAllEvents(String username) throws IOException
    {
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

        //EventAdder eventAdder = new EventAdder();

        JSONObject obj = new JSONObject(json);

        JSONArray jsonArray = obj.getJSONArray("Cadeiras");

        for (Object course : jsonArray)
        {
            //System.out.println(jsonArray.getJSONObject(i).getString("name"));
            String name = ((JSONObject) course).getString("name");

            JSONArray arrayDT = ((JSONObject) course).getJSONArray("DT");

            for (Object datetime : arrayDT)
            {
                //addEventsToCal(new CalendarEvent(LocalDate.of(2022, 11, 11), LocalTime.of(14, 0), LocalTime.of(15, 30), "Test 11/11 14:00-14:20"));
                String start = ((JSONObject) datetime).getString("DTSTART");
                String end = ((JSONObject) datetime).getString("DTEND");


                int year = Integer.parseInt(start.substring(0, 4));
                int month = Integer.parseInt(start.substring(4, 6));
                int day = Integer.parseInt(start.substring(6, 8));
                int startTimeHour = Integer.parseInt(start.substring(8, 10));
                int startTimeMin = Integer.parseInt(start.substring(10, 12));
                int endTimeHour = Integer.parseInt(end.substring(8, 10));
                int endTimeMin = Integer.parseInt(end.substring(10, 12));

                if ((3 < month && month < 10) || (month == 3 && 27 <= day) || (month == 10 && day <= 29))
                {
                    startTimeHour += 1;
                    endTimeHour += 1;
                }

                events.add(new CalendarEvent(LocalDate.of(year, month, day), LocalTime.of(startTimeHour, startTimeMin), LocalTime.of(endTimeHour, endTimeMin), name));
            }
        }

        return events;
    }
}
