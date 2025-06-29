/**
 * Beschreiben Sie hier die Klasse LoginPanel.
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private final MainFrame frame;
    private final JTextField emailField;
    private final JPasswordField passField;
    private final JButton loginBtn;
    private final FirebaseAuthService authService = new FirebaseAuthService();

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1) HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Components.PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 50));
        header.add(Components.headerTitle("Login"));
        add(header, BorderLayout.NORTH);

        // 2) CONTENT
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // E-Mail
        JLabel emailLabel = new JLabel("E-Mail");
        emailLabel.setFont(Components.BODY_FONT);
        content.add(emailLabel);
        content.add(Box.createVerticalStrut(5));
        emailField = Components.textField(20);
        content.add(emailField);

        content.add(Box.createVerticalStrut(20));

        // Passwort
        JLabel passLabel = new JLabel("Passwort");
        passLabel.setFont(Components.BODY_FONT);
        content.add(passLabel);
        content.add(Box.createVerticalStrut(5));
        passField = Components.passwordField(20);
        content.add(passField);

        content.add(Box.createVerticalStrut(30));

        // Login-Button
        loginBtn = Components.primaryButton("Login", e -> attemptLogin());
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(loginBtn);

        content.add(Box.createVerticalStrut(15));

        // Passwort vergessen Button
        JButton forgotPasswordBtn = new JButton("Passwort vergessen?");
        forgotPasswordBtn.setFont(Components.BODY_FONT);
        forgotPasswordBtn.setForeground(Components.PRIMARY_COLOR);
        forgotPasswordBtn.setBackground(Color.WHITE);
        forgotPasswordBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        forgotPasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordBtn.addActionListener(e -> attemptPasswordReset());
        content.add(forgotPasswordBtn);

        content.add(Box.createVerticalStrut(10));

        // Registrierung Link
        JButton signUpLinkBtn = new JButton("Noch kein Konto? Hier registrieren");
        signUpLinkBtn.setFont(Components.BODY_FONT);
        signUpLinkBtn.setForeground(Components.PRIMARY_COLOR);
        signUpLinkBtn.setBackground(Color.WHITE);
        signUpLinkBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        signUpLinkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLinkBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpLinkBtn.addActionListener(e -> frame.showSignUpPanel());
        content.add(signUpLinkBtn);

        add(content, BorderLayout.CENTER);
    }

    private void attemptLogin() {
        final String email = emailField.getText().trim();
        final String password = new String(passField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Bitte E-Mail und Passwort ausfüllen.",
                "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable inputs while logging in
        setInputsEnabled(false);

        // Netzwerk-Call in neuem Thread, damit die UI nicht einfriert
        new Thread(() -> {
            try {
                FirebaseAuthService.SignInResponse res = authService.signIn(email, password);
                SwingUtilities.invokeLater(() -> {
                    // Benutzer erstellen und als aktuell eingeloggten User setzen
                    // Hier verwenden wir einen Platzhalter-DisplayName basierend auf der E-Mail
                    String displayName = generateDisplayNameFromEmail(email);
                    User user = new User(res.localId, displayName, email);
                    User.setCurrentUser(user);
                    
                    // Login-Daten persistent speichern
                    LoginPersistence.saveLoginData(email, password, user);
                    
                    // Erfolg: weiter zur Home-Seite
                    frame.showHomePanel();
                });
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Login fehlgeschlagen:\n" + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                });
            } finally {
                SwingUtilities.invokeLater(() -> setInputsEnabled(true));
            }
        }).start();
    }

    private void attemptPasswordReset() {
        final String email = emailField.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Bitte geben Sie Ihre E-Mail-Adresse ein.",
                "E-Mail erforderlich", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Bestätigung anfordern
        int result = JOptionPane.showConfirmDialog(this,
            "Möchten Sie eine Passwort-Reset-E-Mail an " + email + " senden?",
            "Passwort zurücksetzen", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        // Disable inputs while processing
        setInputsEnabled(false);

        // Netzwerk-Call in neuem Thread
        new Thread(() -> {
            try {
                authService.resetPassword(email);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Eine E-Mail zum Zurücksetzen des Passworts wurde an " + email + " gesendet.\n" +
                        "Bitte überprüfen Sie Ihr E-Mail-Postfach.",
                        "E-Mail gesendet", JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Fehler beim Senden der Reset-E-Mail:\n" + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                });
            } finally {
                SwingUtilities.invokeLater(() -> setInputsEnabled(true));
            }
        }).start();
    }

    private void setInputsEnabled(boolean enabled) {
        emailField.setEnabled(enabled);
        passField.setEnabled(enabled);
        loginBtn.setEnabled(enabled);
    }
    
    /**
     * Generiert einen Display-Namen basierend auf der E-Mail-Adresse
     * @param email Die E-Mail-Adresse
     * @return Ein Display-Name (Teil vor dem @-Zeichen)
     */
    private String generateDisplayNameFromEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Benutzer";
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            String username = email.substring(0, atIndex);
            // Ersten Buchstaben groß schreiben
            if (!username.isEmpty()) {
                return username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
            }
        }
        
        return "Benutzer";
    }
}
