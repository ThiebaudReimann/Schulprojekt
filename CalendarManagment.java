import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Beschreiben Sie hier die Klasse CalenderManagment.
 * Zugriff auf ArrayList mit allen CalenderEntrys
 * @author Philipp, Thiébaud
 * @version 1.1
 */
public class CalendarManagment
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<CalendarEntry> calendarEntrys;
    
    //Konstruktor
    /**
     * Konstruktor für Objekte der Klasse CalendarManagment
     */
    public CalendarManagment()
    {
        calendarEntrys = new ArrayList<CalendarEntry>();
    }
    
    //Methoden
    /**
     * Entry hinzufügen
     * 
     * 
     * @param titel        Titel des Entrys
     * @param discription  Beschreibung
     * @param starttime    anfang des Entrys
     * @param endtime      ende des Entrys 
     * 
     */
    public void add(CalendarEntry entry) {
        calendarEntrys.add(entry);
    }
    
    /**
     * 
     * @param titel Titel des zu entfernenden Entrys
     */
    public void remove(int id) {
        calendarEntrys.removeIf(entry -> entry.id == id);
    }
    
    public CalendarEntry getEntry(int id) {
        return calendarEntrys.get(id);
    }
    
    public ArrayList<CalendarEntry> getAll() {
        return calendarEntrys;
    }
}
