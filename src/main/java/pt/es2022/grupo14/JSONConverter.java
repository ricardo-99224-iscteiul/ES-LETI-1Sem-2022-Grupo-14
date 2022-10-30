package pt.es2022.grupo14;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONConverter
{
    public void insert(String username, String name) throws IOException
    {
        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;

        boolean exame = name.contains("Exame");

        while ((line = bf.readLine()) != null)
        {
            if (line.contains(name) && (exame == line.contains("Exame"))) return;
            sb.append(line).append("\n");
        }
        sb.append("\t\t{\n").append("\t\t\t\"name\" : ").append("\"").append(name).append("\",\n").append("\t\t},\n");
        bf.close();

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(sb.toString());
        fw.close();
    }

    public void insert(String username, String name, String start, String end) throws IOException
    {
        insert(username, name);

        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;

        boolean exame = name.contains("Exame");

        boolean found = false;

        while ((line = bf.readLine()) != null)
        {
            sb.append(line).append("\n");
            if (!found && line.contains(name) && (exame == line.contains("Exame")))
            {
                found = true;

                line = bf.readLine();

                if (!line.contains("DT"))
                {
                    sb.append("\t\t\t\"DT\" : [\n").append("\t\t\t\t{\"DTSTART\": \"").append(start).append("\", " +
                            "\"DTEND\": \"").append(end).append("\"}\n").append("\t\t\t]\n").append("\t\t},\n");
                }
                else
                {
                    sb.append(line).append("\n");
                    line = bf.readLine();
                    while (line.endsWith(","))
                    {
                        sb.append(line).append("\n");
                        line = bf.readLine();
                    }
                    sb.append(line).append(",\n");
                    sb.append("\t\t\t\t{\"DTSTART\": \"").append(start).append("\", " +
                            "\"DTEND\": \"").append(end).append("\"}\n");
                }
            }
        }
        bf.close();

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(sb.toString());
        fw.close();
    }

    public void createFile(String username) throws IOException
    {
        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        if (jsonFile.exists())
            jsonFile.delete();

        jsonFile.createNewFile();

        FileWriter fw = new FileWriter(jsonFile, true);

        fw.write("{\n\t\"Cadeiras\" : [\n");

        fw.close();

    }

    public void closeFile(String username) throws IOException
    {
        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line, last = "";

        while ((line = bf.readLine()) != null)
        {
            sb.append(line).append("\n");
        }
        bf.close();

        sb.deleteCharAt(sb.toString().length() - 2);
        sb.append("\t]\n").append("}");

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(sb.toString());
        fw.close();
    }
}
