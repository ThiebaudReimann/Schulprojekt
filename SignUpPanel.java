/**
 * Beschreiben Sie hier die Klasse SignUpPanel.
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class SignUpPanel extends JPanel {
    private final MainFrame frame;
    private final JTextField emailField;
    private final JPasswordField passField;
    private final JPasswordField confirmPassField;
    private final JButton signUpBtn;
    private final FirebaseAuthService authService = new FirebaseAuthService();

    public SignUpPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 1) HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Components.PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 50));
        header.add(Components.headerTitle("Registrierung"));
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

        content.add(Box.createVerticalStrut(20));

        // Passwort bestätigen
        JLabel confirmPassLabel = new JLabel("Passwort bestätigen");
        confirmPassLabel.setFont(Components.BODY_FONT);
        content.add(confirmPassLabel);
        content.add(Box.createVerticalStrut(5));
        confirmPassField = Components.passwordField(20);
        content.add(confirmPassField);

        content.add(Box.createVerticalStrut(30));

        // SignUp-Button
        signUpBtn = Components.primaryButton("Registrieren", e -> attemptSignUp());
        signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(signUpBtn);

        content.add(Box.createVerticalStrut(15));

        // Login Link
        JButton loginLinkBtn = new JButton("Bereits ein Konto? Hier anmelden");
        loginLinkBtn.setFont(Components.BODY_FONT);
        loginLinkBtn.setForeground(Components.PRIMARY_COLOR);
        loginLinkBtn.setBackground(Color.WHITE);
        loginLinkBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginLinkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLinkBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLinkBtn.addActionListener(e -> frame.showLoginPanel());
        content.add(loginLinkBtn);

        add(content, BorderLayout.CENTER);
    }

    private void attemptSignUp() {
        final String email = emailField.getText().trim();
        final String password = new String(passField.getPassword());
        final String confirmPassword = new String(confirmPassField.getPassword());

        // Validierung
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Bitte alle Felder ausfüllen.",
                "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                "Bitte eine gültige E-Mail-Adresse eingeben.",
                "Ungültige E-Mail", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Das Passwort muss mindestens 6 Zeichen lang sein.",
                "Passwort zu kurz", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Die Passwörter stimmen nicht überein.",
                "Passwort-Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable inputs while signing up
        setInputsEnabled(false);

        // Netzwerk-Call in neuem Thread, damit die UI nicht einfriert
        new Thread(() -> {
            try {
                FirebaseAuthService.SignUpResponse res = authService.signUp(email, password);
                SwingUtilities.invokeLater(() -> {
                    // Benutzer automatisch nach Registrierung einloggen
                    String displayName = generateDisplayNameFromEmail(email);
                    User user = new User(res.localId, displayName, email);
                    User.setCurrentUser(user);
                    
                    // Login-Daten persistent speichern
                    LoginPersistence.saveLoginData(email, password, user);
                    
                    JOptionPane.showMessageDialog(this,
                        "Registrierung erfolgreich!\nWillkommen, " + displayName + "!",
                        "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Direkt zum HomePanel weiterleiten
                    frame.showHomePanel();
                });
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                        "Registrierung fehlgeschlagen:\n" + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                });
            } finally {
                SwingUtilities.invokeLater(() -> setInputsEnabled(true));
            }
        }).start();
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    private void setInputsEnabled(boolean enabled) {
        emailField.setEnabled(enabled);
        passField.setEnabled(enabled);
        confirmPassField.setEnabled(enabled);
        signUpBtn.setEnabled(enabled);
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
