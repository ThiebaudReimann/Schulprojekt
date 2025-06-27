/**
 * Beschreiben Sie hier die Klasse LoginPanel.
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;
import java.io.IOException;

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
        emailLabel.setFont(Components.DEFAULT_FONT);
        content.add(emailLabel);
        content.add(Box.createVerticalStrut(5));
        emailField = Components.textField(20);
        content.add(emailField);

        content.add(Box.createVerticalStrut(20));

        // Passwort
        JLabel passLabel = new JLabel("Passwort");
        passLabel.setFont(Components.DEFAULT_FONT);
        content.add(passLabel);
        content.add(Box.createVerticalStrut(5));
        passField = Components.passwordField(20);
        content.add(passField);

        content.add(Box.createVerticalStrut(30));

        // Login-Button
        loginBtn = Components.primaryButton("Login", e -> attemptLogin());
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(loginBtn);

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

    private void setInputsEnabled(boolean enabled) {
        emailField.setEnabled(enabled);
        passField.setEnabled(enabled);
        loginBtn.setEnabled(enabled);
    }
}
