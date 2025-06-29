import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Beschreiben Sie hier die Klasse HomePanel.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public class HomePanel extends JPanel {
    private MainFrame frame;
    
    public HomePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Components.BACKGROUND_COLOR);
        
        // Header-Bereich
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Listen-Bereich mit ScrollPane
        JPanel listPanel = createListPanel();
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Components.BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        
        // Begrüßung mit Benutzername
        String displayName = User.isLoggedIn() ? User.getCurrentUser().getDisplayName() : "Gast";
        JLabel welcomeLabel = new JLabel("Willkommen zurück, " + displayName + "!");
        welcomeLabel.setFont(Components.TITLE_FONT);
        welcomeLabel.setForeground(Components.TEXT_PRIMARY);
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Schön, dich zu sehen.");
        subtitleLabel.setFont(Components.BODY_FONT);
        subtitleLabel.setForeground(Components.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        // Logout Button hinzufügen
        if (User.isLoggedIn()) {
            headerPanel.add(Box.createVerticalStrut(15));
            JButton logoutButton = Components.secondaryButton("Abmelden", e -> {
                int result = JOptionPane.showConfirmDialog(
                    HomePanel.this,
                    "Möchten Sie sich wirklich abmelden?",
                    "Abmelden",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    // Persistente Login-Daten löschen
                    LoginPersistence.clearLoginData();
                    // Session-User löschen
                    User.logout();
                    frame.showLoginPanel();
                }
            });
            logoutButton.setAlignmentX(CENTER_ALIGNMENT);
            headerPanel.add(logoutButton);
        }
        
        return headerPanel;
    }
    
    private JPanel createListPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Components.BACKGROUND_COLOR);
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Überschrift für die Navigation
        JLabel navigationLabel = new JLabel("Navigation");
        navigationLabel.setFont(Components.SUBTITLE_FONT);
        navigationLabel.setForeground(Components.TEXT_PRIMARY);
        navigationLabel.setAlignmentX(LEFT_ALIGNMENT);
        listPanel.add(navigationLabel);
        listPanel.add(Box.createVerticalStrut(15));
        
        // Calendar ListTile
        Components.ListTile calendarTile = Components.createListTile(
            "Kalender", 
            "Termine und Ereignisse verwalten", 
            "📅", 
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.showCalendarPanel();
                }
            }
        );
        calendarTile.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendarTile.getPreferredSize().height));
        calendarTile.setAlignmentX(LEFT_ALIGNMENT);
        listPanel.add(calendarTile);
        listPanel.add(Box.createVerticalStrut(10));
        
        // Weitere ListTiles können hier hinzugefügt werden
        Components.ListTile profileTile = Components.createListTile(
            "Profil", 
            "Persönliche Einstellungen verwalten", 
            "👤", 
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Profil Panel wird in Zukunft implementiert.", "Navigation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        );
        profileTile.setMaximumSize(new Dimension(Integer.MAX_VALUE, profileTile.getPreferredSize().height));
        profileTile.setAlignmentX(LEFT_ALIGNMENT);
        listPanel.add(profileTile);
        listPanel.add(Box.createVerticalStrut(10));
        
        Components.ListTile settingsTile = Components.createListTile(
            "Einstellungen", 
            "App-Einstellungen konfigurieren", 
            "⚙️", 
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Einstellungen Panel wird in Zukunft implementiert.", "Navigation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        );
        settingsTile.setMaximumSize(new Dimension(Integer.MAX_VALUE, settingsTile.getPreferredSize().height));
        settingsTile.setAlignmentX(LEFT_ALIGNMENT);
        listPanel.add(settingsTile);
        
        // Flexibler Platz am Ende
        listPanel.add(Box.createVerticalGlue());
        
        return listPanel;
    }
}

