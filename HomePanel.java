import javax.swing.*;

/**
 * Beschreiben Sie hier die Klasse HomePanel.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public class HomePanel extends JPanel {
    public HomePanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Willkommen zurück! Schön, dich zu sehen.");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(100)); // Abstand von oben
        add(welcomeLabel);
    }
}

