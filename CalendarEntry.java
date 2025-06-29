import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Familienkalender.
 * 
 * @author (Nils Vogel und bischen luis renner) 
 * @version 1.0
 */

public class CalendarEntry
{
    final int id;
    private String title;
    private String description;
    private String location;
    private ArrayList<User> users;
    private boolean allUsers;
    private LocalDateTime createdOn;
    private LocalDateTime timestamp;
    private boolean allDay;
        

    public CalendarEntry(int id, String title, String description, LocalDateTime createdOn, LocalDateTime timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdOn = createdOn;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getcreatedOn() {
        return createdOn;
    }

    public void setcreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime gettimestamp() {
        return timestamp;
    }

    public void settimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Abstrakte Methode, die von Unterklassen implementiert werden muss
    //public void remind();
}
