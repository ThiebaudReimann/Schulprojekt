import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalendarPanel - Ein Panel das das CalendarWidget enthält und Navigation ermöglicht
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */
public class CalendarPanel extends JPanel {
    private MainFrame frame;
    private CalendarWidget calendarWidget;
    
    public CalendarPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Components.BACKGROUND_COLOR);
        
        // CalendarWidget erstellen
        calendarWidget = new CalendarWidget();
        
        // Navigation Button im CalendarWidget konfigurieren
        setupNavigation();
        
        add(calendarWidget, BorderLayout.CENTER);
    }
    
    /**
     * Konfiguriert die Navigation im CalendarWidget
     */
    private void setupNavigation() {
        // Den Navigation Button im CalendarWidget finden und Action hinzufügen
        JPanel appBar = calendarWidget.getAppBar();
        Component[] components = appBar.getComponents();
        
        // Der erste Component sollte der Navigation Button sein
        if (components.length > 0 && components[0] instanceof JButton) {
            JButton navButton = (JButton) components[0];
            
            // Alle bestehenden ActionListener entfernen
            ActionListener[] listeners = navButton.getActionListeners();
            for (ActionListener listener : listeners) {
                navButton.removeActionListener(listener);
            }
            
            // Neue Navigation Action hinzufügen
            navButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.showHomePanel();
                }
            });
        }
    }
    
    /**
     * Gibt das CalendarWidget zurück
     * @return Das CalendarWidget
     */
    public CalendarWidget getCalendarWidget() {
        return calendarWidget;
    }
}
