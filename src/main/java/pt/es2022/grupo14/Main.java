package pt.es2022.grupo14;

public class Main
{
    public static void main(String[] args)
    {
        String webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rdlpo@iscte.pt&password=YK69wQ5t4QT3bNCfs7ufNpAyUdaLkVzY8j2EHptfXtckXwGG2odZxl3fylYWZ7oFIZBuWjVfZ9ZYbOt8mmtjhu5cpKgEbGfZIQg2xZU4N5iq5FRbfGvEkVeUStqvOZ82";
        CalendarReader reader = new CalendarReader();
        reader.read(webcal);
    }
}