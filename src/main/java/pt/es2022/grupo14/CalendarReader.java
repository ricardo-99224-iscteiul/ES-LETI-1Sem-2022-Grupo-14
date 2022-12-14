package pt.es2022.grupo14;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CalendarReader {

	/**
	 * Lê um calendário
	 * @param webcal é um link de um calendário
	 */
	public void read(String webcal) {

		if (webcal == null) throw new IllegalArgumentException("Webcal cannot be null");

		if (webcal.isBlank()) throw new IllegalArgumentException("Webcal cannot be empty");

		String user = webcal.split("username=")[1];
		user = user.split("@")[0];

		String date = null;

		String dateStart;
		String dataStart;
		String horaStart;

		String dateEnd;
		String dataEnd;
		String horaEnd;

		String summary;

		JSONConverter jc = new JSONConverter();
		
		try {

			webcal = webcal.replace("webcal", "https");
			URL url = new URL(webcal);
			Path webcalPath = Paths.get(Utils.WEBCAL);
			Path calendarFolder = Paths.get(Utils.CALENDARS);
			if (!Files.exists(calendarFolder))
				Files.createDirectory(calendarFolder);
			Files.copy(url.openStream(), webcalPath, REPLACE_EXISTING);

			Scanner scanner = new Scanner(new File(webcalPath.toUri()));

			Map<String, List<String>> eventos = new TreeMap<>();
			
			jc.createFile(user, webcal);

			while (scanner.hasNextLine()) {

				if (scanner.nextLine().equals("BEGIN:VEVENT")) {

					date = scanner.nextLine(); // nao usado
					dateStart = scanner.nextLine().split(":")[1];
					dataStart = dateStart.split("T")[0];
					horaStart = dateStart.split("T")[1];
					horaStart = horaStart.split("Z")[0];

					dateEnd = scanner.nextLine().split(":")[1];
					dataEnd = dateEnd.split("T")[0];
					horaEnd = dateEnd.split("T")[1];
					horaEnd = horaEnd.split("Z")[0];

					summary = scanner.nextLine();

					String atualString = scanner.nextLine();

					if (!atualString.contains("UID:")) {
						summary += atualString;
					}

					summary = summary.split(" - ")[0]; //Nome em tuga

					if (summary.contains("Teste:") || summary.contains("Exame:")
							|| summary.contains("Avaliação Contínua:")) {
						summary = summary.split(":")[2] + " - Exame";
						summary = summary.strip();
						
					} else {
						summary = summary.split(":")[1];
					}

					if (!eventos.containsKey(summary)) {
						eventos.put(summary, new ArrayList<>());
						eventos.get(summary).add(dataStart + horaStart + " " + dataEnd + horaEnd);

					} else {
						eventos.get(summary).add(dataStart + horaStart + " " + dataEnd + horaEnd);
						Collections.sort(eventos.get(summary));
					}
				}
			}
			
			scanner.close();

			Files.deleteIfExists(webcalPath);

			for (String cadeira : eventos.keySet()) {

				for (String horarios : eventos.get(cadeira)) {
					jc.insert(user, cadeira, horarios.split(" ")[0], horarios.split(" ")[1]);
				}
			}
			
			jc.closeFile(user);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
