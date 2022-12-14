package pt.es2022.grupo14;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONConverter
{
    /**
     * Método para inserir um evento no ficheiro JSON
     * @param username é o nome do ficheiro
     * @param name é o nome do evento
     * @throws IOException
     */
    public void insert(String username, String name) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or empty");

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

    /**
     * Método para inserir um evento no ficheiro JSON
     * @param username é o nome do ficheiro
     * @param name é o nome do evento
     * @param start é a data de início do evento
     * @param end é a data de fim do evento
     * @throws IOException
     */
    public void insert(String username, String name, String start, String end) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or empty");
        if (start == null || start.isBlank()) throw new IllegalArgumentException("Start cannot be null or empty");
        if (end == null || end.isBlank()) throw new IllegalArgumentException("End cannot be null or empty");

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

    /**
     * Método que cria o ficheiro JSON
     * @param username é o nome do ficheiro
     * @throws IOException
     */
    public void createFile(String username, String webcal) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");

        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        if (jsonFile.exists())
            jsonFile.delete();

        jsonFile.createNewFile();

        FileWriter fw = new FileWriter(jsonFile, true);

        fw.write("{\n\t\"URL\" : \"" + webcal + "\",\n\t\"Cadeiras\" : [\n");

        fw.close();

    }

    /**
     * Método que acaba o ficheiro JSON
     * @param username é o nome do ficheiro
     * @throws IOException
     */
    public void closeFile(String username) throws IOException
    {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be null or empty");

        Path webcalPath = Paths.get("src/calendars/" + username + ".json");
        File jsonFile = new File(webcalPath.toUri());

        BufferedReader bf = new BufferedReader(new FileReader(jsonFile));
        StringBuilder sb = new StringBuilder();
        String line;

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
