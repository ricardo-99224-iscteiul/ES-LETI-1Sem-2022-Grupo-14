package pt.es2022.grupo14;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONParser
{
    public void getAllEvents(String username) throws IOException
    {
        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
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

        EventAdder eventAdder = new EventAdder();

        JSONObject obj = new JSONObject(json);

        JSONArray jsonArray = obj.getJSONArray("Cadeira");
    }
}
