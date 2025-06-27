import java.time.LocalDateTime;
/**
 * Beschreiben Sie hier die Klasse Familienkalender.
 * 
 * @author (Nils Vogel und bischen luis renner) 
 * @version 1.0
 */

abstract public class CalendarEntryEntity
{
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CalendarEntryEntity(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // Abstrakte Methode, die von Unterklassen implementiert werden muss
    public abstract void remind();
}
