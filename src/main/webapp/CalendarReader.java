
	package main.webapp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CalendarReader {

	public void read(String webcal) {

//		String webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=aefcs@iscte.pt&password=v4m2d3fKKnQprqri84bKwiACNzkvW0wgeuKC1yBN1zqoMmqGrfX1eVVLZ1ZrAdkTJ7D9oaJ5ymumul522r7ItOQxTgOVhlhr7DhQyxmkHM7K7RoVqiaMlevpoLUwS6tI";
//		String webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rdlpo@iscte.pt&password=YK69wQ5t4QT3bNCfs7ufNpAyUdaLkVzY8j2EHptfXtckXwGG2odZxl3fylYWZ7oFIZBuWjVfZ9ZYbOt8mmtjhu5cpKgEbGfZIQg2xZU4N5iq5FRbfGvEkVeUStqvOZ82";

		String user = webcal.split("username=")[1];
		user = user.split("@")[0];

		String date = null;

		String dateStart = null;
		String dataStart = null;
		String horaStart = null;

		String dateEnd = null;
		String dataEnd = null;
		String horaEnd = null;

		String summary = null;

		JSONConverter jc = new JSONConverter();
		
		try {

			webcal = webcal.replace("webcal", "https");
			URL url = new URL(webcal);
			Files.copy(url.openStream(), Paths.get("webcal.txt"), REPLACE_EXISTING);

			Scanner scanner = new Scanner(new File("webcal.txt"));

			Map<String, List<String>> eventos = new TreeMap<>();
			
			jc.createFile(user);

			while (scanner.hasNextLine()) {

				if (scanner.nextLine().equals("BEGIN:VEVENT")) {

					date = scanner.nextLine(); // nao usado
					dateStart = scanner.nextLine().split(":")[1];
					dataStart = dateStart.split("T")[0];
					horaStart = dateStart.split("T")[1];
					horaStart = horaStart.split("Z")[0];

					dateEnd = scanner.nextLine().split(":")[1];
					dataEnd = dateStart.split("T")[0];
					horaEnd = dateStart.split("T")[1];
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
						eventos.put(summary, new ArrayList<String>());
						eventos.get(summary).add(dataStart + horaStart + " " + dataEnd + horaEnd);

					} else {
						eventos.get(summary).add(dataStart + horaStart + " " + dataEnd + horaEnd);
						Collections.sort(eventos.get(summary));
					}
				}
			}
			
			scanner.close();

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
