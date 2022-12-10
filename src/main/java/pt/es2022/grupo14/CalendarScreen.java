package pt.es2022.grupo14;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static pt.es2022.grupo14.Utils.alphaGray;

public class CalendarScreen
{
	JFrame frm;
	Image lightMode = null;
	Image darkMode = null;
	ArrayList<CalendarEvent> events = new ArrayList<>();
	HashMap<String, Boolean> checkboxes = new HashMap<>();
	WeekCalendar cal = null;
	
	/**
	 * Método para criar a GUI do calendário
	 */
	public void showCalendar()
	{

		frm = Main.getFrm();
		frm.getContentPane().removeAll();
		frm.repaint();

		JPanel weekControls = new JPanel();

		JPanel convertControls = new JPanel();

		JPanel calControls = new JPanel();
		calControls.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Utils utils = new Utils();

		ArrayList<String> calendarNames = utils.getCalendars();

		for (String username : calendarNames)
		{
			JCheckBox b = new JCheckBox(username);
			if (checkboxes.containsKey(b.getText()))
				b.setSelected(checkboxes.get(b.getText()));
			else
			{
				checkboxes.put(b.getText(), b.isSelected());
			}
			b.addItemListener(itemEvent ->
			{
				checkboxes.put(b.getText(), b.isSelected());
				updateEvents();
				cal.setEvents(events);
			});

			calControls.add(b, gbc);
		}

		updateEvents();

		cal = new WeekCalendar(events);

		JButton goToTodayBtn = new JButton("Today");
		goToTodayBtn.addActionListener(e -> cal.goToToday());

		JButton nextWeekBtn = new JButton("Next Week");
		nextWeekBtn.addActionListener(e -> cal.nextWeek());

		JButton prevWeekBtn = new JButton("Previous Week");
		prevWeekBtn.addActionListener(e -> cal.prevWeek());

		JButton nextMonthBtn = new JButton("Next Month");
		nextMonthBtn.addActionListener(e -> cal.nextMonth());

		JButton prevMonthBtn = new JButton("Previous Month");
		prevMonthBtn.addActionListener(e -> cal.prevMonth());

		JButton toPdf = new JButton("PDF");
		toPdf.addActionListener(e -> {
			try {
				createPdf();
			} catch (IOException | DocumentException e1) {
				e1.printStackTrace();
			}
		});

		JButton toEmail = new JButton("EMAIL");
		toEmail.addActionListener(e -> cal.prevMonth());

		try
		{
			lightMode = ImageIO.read(new File(Utils.LIGHT_MODE));
			lightMode = lightMode.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH );
			darkMode = ImageIO.read(new File(Utils.DARK_MODE));
			darkMode = darkMode.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH );
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		JToggleButton mode = new JToggleButton();
		if (Main.darkMode)
		{
			weekControls.setBackground(Color.darkGray);
			convertControls.setBackground(Color.darkGray);
			mode.setIcon(new ImageIcon(darkMode));
			mode.setSelected(true);
			calControls.setBackground(Color.darkGray);
			cal.changeColor();
		}
		else
		{
			weekControls.setBackground(alphaGray);
			convertControls.setBackground(alphaGray);
			calControls.setBackground(alphaGray);
			mode.setIcon(new ImageIcon(lightMode));
			mode.setSelected(false);
		}
		mode.addItemListener(e ->
		{
			int state = e.getStateChange();
			cal.changeColor();
			if (state == ItemEvent.SELECTED)
			{
				mode.setIcon(new ImageIcon(darkMode));
				weekControls.setBackground(Color.darkGray);
				convertControls.setBackground(Color.darkGray);
				calControls.setBackground(Color.darkGray);
				Main.darkMode = true;
			}
			else
			{
				Main.darkMode = false;
				mode.setIcon(new ImageIcon(lightMode));
				weekControls.setBackground(alphaGray);
				convertControls.setBackground(alphaGray);
				calControls.setBackground(alphaGray);
			}
			frm.repaint();
		});

		JButton menuBtn = new JButton();
		try {
			Image img = ImageIO.read(new File(Utils.MENU));
			img = img.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH ) ;
			menuBtn.setIcon(new ImageIcon(img));
			menuBtn.addActionListener(e -> Main.changeScreen(Screen.MENU));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		weekControls.add(menuBtn);
		weekControls.add(prevMonthBtn);
		weekControls.add(prevWeekBtn);
		weekControls.add(goToTodayBtn);
		weekControls.add(nextWeekBtn);
		weekControls.add(nextMonthBtn);
		weekControls.add(mode);

		convertControls.add(toPdf);
		convertControls.add(toEmail);

		frm.add(weekControls, BorderLayout.NORTH);

		frm.add(convertControls, BorderLayout.SOUTH);

		frm.add(calControls, BorderLayout.EAST);

		frm.add(cal, BorderLayout.CENTER);

		frm.revalidate();
		frm.repaint();
	}

	/**
	 * Adiciona eventos ao calendário
	 * @param events é uma lista de eventos
	 */
	public void addEventsToCal(ArrayList<CalendarEvent> events) {
		if (events == null) throw new IllegalArgumentException("Events cannot be null");

		this.events.addAll(events);
	}

	/**
	 * Dá update aos eventos
	 */
	public void updateEvents() {
		events = new ArrayList<>();

		Utils utils = new Utils();
		JSONParser parser = new JSONParser();

		ArrayList<String> calendarNames = utils.getCalendars();

		for (String username : calendarNames)
		{
			for (String name : checkboxes.keySet())
			{
				if (checkboxes.get(name) && name.equals(username))
				{
					try
					{
						ArrayList<CalendarEvent> additions = changeColor(parser.getAllEvents(username));
						addEventsToCal(additions);
					} catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	/**
	 * Muda a cor dos eventos
	 * @param newEvents é uma lista de eventos
	 * @return da lista dos eventos com a cor alterada
	 */
	public ArrayList<CalendarEvent> changeColor(ArrayList<CalendarEvent> newEvents)
	{
		if (newEvents == null) throw new IllegalArgumentException("NewEvents cannot be null");

		ArrayList<CalendarEvent> additions = new ArrayList<>();
		for (CalendarEvent event : newEvents)
		{
			int index = events.indexOf(event);
			if (index != -1)
			{
				Color cur = events.get(index).getColor();
				if (cur.equals(Utils.LIGHT_COLOR))
					cur = Utils.COLOR;
				else if (cur.equals(Utils.COLOR))
					cur = Utils.DARK_COLOR;

				events.get(index).setColor(cur);
			}
			else additions.add(event);
		}
		return additions;
	}

	/**
	 * Cria um PDF a partir do calendário
	 */
	@SuppressWarnings("deprecation")
	public void createPdf() throws IOException, DocumentException {
		int width = cal.getWidth();
		int height = cal.getHeight();
		Document document = new Document(new com.itextpdf.text.Rectangle(width, height));
		try {
			PdfWriter writer;
			writer = PdfWriter.getInstance(document, new FileOutputStream("./Calendário.pdf"));
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(width, height);
			Graphics2D g2;
			g2 = tp.createGraphics(width, height);
			cal.print(g2);
			g2.dispose();
			cb.addTemplate(tp, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();

	}
}
