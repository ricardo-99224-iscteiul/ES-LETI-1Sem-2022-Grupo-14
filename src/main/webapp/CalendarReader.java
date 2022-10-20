package main.webapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CalendarReader {
	
	

	public void read(String webcal) {
		
//		String webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=aefcs@iscte.pt&password=v4m2d3fKKnQprqri84bKwiACNzkvW0wgeuKC1yBN1zqoMmqGrfX1eVVLZ1ZrAdkTJ7D9oaJ5ymumul522r7ItOQxTgOVhlhr7DhQyxmkHM7K7RoVqiaMlevpoLUwS6tI"; 
		String user = webcal.split("username=")[1];
		user = user.split("@")[0];
		
		String date = null;
		String dateStart = null;
		String dateEnd = null;
		String summary = null;
		
		try {
			
			webcal = webcal.replace("webcal", "https");
            URL url = new URL(webcal);
            Files.copy(url.openStream(), Paths.get("webcal.txt"), REPLACE_EXISTING);
            
			Scanner scanner = new Scanner(new File("webcal.txt"));
			
			while (scanner.hasNextLine()) {			
				
				if (scanner.nextLine().equals("BEGIN:VEVENT")) {
					
					date = scanner.nextLine(); //n�o � usado
					dateStart = scanner.nextLine().split(":")[1];

					dateEnd = scanner.nextLine().split(":")[1];
					
					summary = scanner.nextLine();
					
					String atualString = scanner.nextLine();
					
					if (!atualString.contains("UID:")) {
						summary += atualString;
					}
					summary = summary.split(" - ")[0];
					
					System.out.println(summary);
					System.out.println("Starting timestamp: " + dateStart);
					System.out.println("Ending timestamp: " + dateEnd + "\n");

				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
