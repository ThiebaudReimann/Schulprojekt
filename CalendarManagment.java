import java.util.ArrayList;
import java.time.LocalDateTime;
/**
 * Beschreiben Sie hier die Klasse CalenderManagment.
 * Zugriff auf ArrayList mit allen CalenderEntrys
 * @author (Philipp) 
 * @version (0.1)
 */
public class CalendarManagment
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private CalendarEntry calendarentry;
    private ArrayList<CalendarEntry> CalendarEntrys;
    
    //Konstruktor
    /**
     * Konstruktor für Objekte der Klasse CalendarManagment
     */
    public CalendarManagment()
    {
        CalendarEntrys = new ArrayList<CalendarEntry>();
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
    public void AddEntry(String titel, String discription, LocalDateTime startTime, LocalDateTime endTime) {
        calendarentry = new CalendarEntry(titel, discription, startTime, endTime);
        CalendarEntrys.add(calendarentry);
    }
    
    /**
     * 
     * @param titel Titel des zu entfernenden Entrys
     */
    public void RemoveEntry(String title) {
        CalendarEntry tempentry;
        for(int i=0; i<CalendarEntrys.size(); i++) {
            tempentry = CalendarEntrys.get(i);
            if(tempentry.getTitle() == title) {
                CalendarEntrys.remove(i);
                break;
            }
            else {
                
            }
        }
    }
    
    public CalendarEntry getEntry(String title) {
        CalendarEntry tempentry;
        for(int i=0; i<CalendarEntrys.size(); i++) {
            tempentry = CalendarEntrys.get(i);
            if(tempentry.getTitle() == title) {
                return tempentry;
            }
            else {
                
            }
        }
        return CalendarEntrys.get(0);
    }    
}
