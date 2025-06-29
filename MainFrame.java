import javax.swing.*;
/**
 * Beschreiben Sie hier die Klasse MainFrame.
 * 
 * @author Thiébaud Reimann 
 * @version 1.0
 */

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Meine App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // Versuche automatischen Login aus gespeicherten Daten
        User savedUser = LoginPersistence.loadAndAutoLogin();
        if (savedUser != null) {
            User.setCurrentUser(savedUser);
            showHomePanel();
            System.out.println("Automatischer Login erfolgreich für: " + savedUser.getDisplayName());
        } else {
            // Überprüfen, ob bereits ein Benutzer in der Session eingeloggt ist
            if (User.isLoggedIn()) {
                showHomePanel();
            } else {
                showLoginPanel(); // Start mit Login
            }
        }

        setVisible(true);
    }

    public void showLoginPanel() {
        setContentPane(new LoginPanel(this));
        revalidate();
    }

    public void showSignUpPanel() {
        setContentPane(new SignUpPanel(this));
        revalidate();
    }

    public void showHomePanel() {
        setContentPane(new HomePanel(this));
        revalidate();
    }
    
    public void showCalendarPanel() {
        setContentPane(new CalendarPanel(this));
        revalidate();
    }
}
