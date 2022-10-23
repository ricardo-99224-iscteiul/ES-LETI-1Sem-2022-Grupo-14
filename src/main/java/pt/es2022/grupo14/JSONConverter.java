package pt.es2022.grupo14;

import java.io.*;

public class JSONConverter
{
    public void insert(String username, String name) throws IOException
    {
        File jsonFile = new File(username + ".json");

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;

        boolean exame = name.contains("Exame");

        while ((line = bf.readLine()) != null)
        {
            if (line.contains(name) && (exame == line.contains("Exame"))) return;
            sb.append(line).append("\n");
        }
        sb.append("\t\"").append(name).append("\": {\n").append("\t}\n");
        bf.close();

        FileWriter fw = new FileWriter(jsonFile);
        fw.write(sb.toString());
        fw.close();
    }

    public void insert(String username, String name, String start, String end) throws IOException
    {
        insert(username, name);

        File jsonFile = new File(username + ".json");

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
                    sb.append("\t\t\"DT\" : [\n").append("\t\t\t{\"DTSTART\": \"").append(start).append("\", " +
                            "\"DTEND\": \"").append(end).append("\"}\n").append("\t\t]\n").append("\t}\n");
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
                    sb.append("\t\t\t{\"DTSTART\": \"").append(start).append("\", " +
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
        File jsonFile = new File(username + ".json");

        if (jsonFile.exists())
            jsonFile.delete();

        jsonFile.createNewFile();

        FileWriter fw = new FileWriter(jsonFile, true);

        fw.write("{\n");

        fw.close();

    }

    public void closeFile(String username) throws IOException
    {
        File jsonFile = new File(username + ".json");

        FileWriter fw = new FileWriter(jsonFile, true);

        fw.write("}");

        fw.close();
    }
}
