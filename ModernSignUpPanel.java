/**
 * Moderne SignUp-Panel Implementierung mit abgerundeten Ecken und verbessertem Alignment
 * 
 * @author Thiébaud Reimann
 * @version 2.0
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class ModernSignUpPanel extends JPanel {
    private final MainFrame frame;
    private final JTextField emailField;
    private final JPasswordField passField;
    private final JPasswordField confirmPassField;
    private final JButton signUpBtn;
    private final FirebaseAuthService authService = new FirebaseAuthService();

    public ModernSignUpPanel(MainFrame frame) {
        this.frame = frame;
        
        // Hauptpanel mit zentriertem Layout
        setLayout(new BorderLayout());
        setBackground(Components.BACKGROUND_COLOR);

        // Header mit Gradient
        JPanel header = Components.gradientCardPanel();
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel headerTitle = Components.headerTitle("Registrierung");
        headerTitle.setFont(Components.DISPLAY_FONT);
        header.add(headerTitle);
        add(header, BorderLayout.NORTH);

        // Zentriertes Formular
        JPanel centerPanel = Components.createCenteredFormPanel();
        JPanel formContainer = Components.createFormContainer();
        formContainer.setPreferredSize(new Dimension(400, 600)); // Höher für zusätzliche Felder
        
        // Formular-Titel
        JLabel formTitle = Components.createFormTitle("Neues Konto erstellen");
        formContainer.add(formTitle);
        
        // E-Mail Sektion
        JLabel emailLabel = Components.createFieldLabel("E-Mail-Adresse");
        formContainer.add(emailLabel);
        
        emailField = Components.textField(20);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(emailField);
        formContainer.add(Components.createVerticalSpacer(16));
        
        // Passwort Sektion
        JLabel passLabel = Components.createFieldLabel("Passwort");
        formContainer.add(passLabel);
        
        passField = Components.passwordField(20);
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(passField);
        formContainer.add(Components.createVerticalSpacer(16));
        
        // Passwort bestätigen Sektion
        JLabel confirmPassLabel = Components.createFieldLabel("Passwort bestätigen");
        formContainer.add(confirmPassLabel);
        
        confirmPassField = Components.passwordField(20);
        confirmPassField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(confirmPassField);
        formContainer.add(Components.createVerticalSpacer(24));
        
        // Passwort-Hinweise
        JPanel passwordHints = new JPanel();
        passwordHints.setOpaque(false);
        passwordHints.setLayout(new BoxLayout(passwordHints, BoxLayout.Y_AXIS));
        passwordHints.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel hintTitle = Components.captionLabel("Passwort-Anforderungen:");
        hintTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordHints.add(hintTitle);
        passwordHints.add(Components.createVerticalSpacer(4));
        
        JLabel hint1 = Components.captionLabel("• Mindestens 8 Zeichen");
        hint1.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordHints.add(hint1);
        
        JLabel hint2 = Components.captionLabel("• Mindestens ein Großbuchstabe");
        hint2.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordHints.add(hint2);
        
        JLabel hint3 = Components.captionLabel("• Mindestens eine Zahl");
        hint3.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordHints.add(hint3);
        
        formContainer.add(passwordHints);
        formContainer.add(Components.createVerticalSpacer(24));
        
        // SignUp Button
        signUpBtn = Components.primaryButton("Registrieren", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonContainer = Components.createButtonContainer();
        buttonContainer.add(signUpBtn);
        buttonContainer.add(Components.createVerticalSpacer(24));
        
        // "Bereits registriert" Link
        JPanel loginSection = new JPanel();
        loginSection.setOpaque(false);
        loginSection.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel loginText = Components.captionLabel("Bereits registriert?");
        JButton loginBtn = Components.createLinkButton("Hier anmelden", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zurück zum Login - hier müsste die entsprechende Methode im MainFrame aufgerufen werden
                // frame.showLogin();
            }
        });
        
        loginSection.add(loginText);
        loginSection.add(loginBtn);
        buttonContainer.add(loginSection);
        
        formContainer.add(buttonContainer);
        
        // Container zentrieren
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 40, 40, 40);
        centerPanel.add(formContainer, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    private void handleSignUp() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());
        String confirmPassword = new String(confirmPassField.getPassword());

        // Validierung
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showErrorDialog("Bitte füllen Sie alle Felder aus.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorDialog("Die Passwörter stimmen nicht überein.");
            return;
        }

        if (!isValidPassword(password)) {
            showErrorDialog("Das Passwort erfüllt nicht die Anforderungen.");
            return;
        }

        // Lade-Animation zeigen
        signUpBtn.setText("Wird registriert...");
        signUpBtn.setEnabled(false);

        // Async signup in background thread
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    // Hier müsste die SignUp-Methode entsprechend angepasst werden
                    // return authService.signUp(email, password) != null;
                    return true; // Placeholder
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void done() {
                signUpBtn.setText("Registrieren");
                signUpBtn.setEnabled(true);
                
                try {
                    if (get()) {
                        showSuccessDialog("Registrierung erfolgreich! Sie können sich jetzt anmelden.");
                        // frame.showLogin();
                    } else {
                        showErrorDialog("Registrierung fehlgeschlagen. Bitte versuchen Sie es erneut.");
                    }
                } catch (Exception e) {
                    showErrorDialog("Ein Fehler ist aufgetreten: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[0-9].*")) return false;
        return true;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erfolg", JOptionPane.INFORMATION_MESSAGE);
    }
}
