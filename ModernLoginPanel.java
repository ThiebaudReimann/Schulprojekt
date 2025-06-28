/**
 * Moderne Login-Panel Implementierung mit abgerundeten Ecken und verbessertem Alignment
 * 
 * @author Thiébaud Reimann
 * @version 2.0
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class ModernLoginPanel extends JPanel {
    private final MainFrame frame;
    private final JTextField emailField;
    private final JPasswordField passField;
    private final JButton loginBtn;
    private final FirebaseAuthService authService = new FirebaseAuthService();

    public ModernLoginPanel(MainFrame frame) {
        this.frame = frame;
        
        // Hauptpanel mit zentriertem Layout
        setLayout(new BorderLayout());
        setBackground(Components.BACKGROUND_COLOR);

        // Header mit Gradient
        JPanel header = Components.gradientCardPanel();
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel headerTitle = Components.headerTitle("Willkommen");
        headerTitle.setFont(Components.DISPLAY_FONT);
        header.add(headerTitle);
        add(header, BorderLayout.NORTH);

        // Zentriertes Formular
        JPanel centerPanel = Components.createCenteredFormPanel();
        JPanel formContainer = Components.createFormContainer();
        
        // Formular-Titel
        JLabel formTitle = Components.createFormTitle("Anmelden");
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
        formContainer.add(Components.createVerticalSpacer(24));
        
        // Login Button
        loginBtn = Components.primaryButton("Anmelden", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonContainer = Components.createButtonContainer();
        buttonContainer.add(loginBtn);
        buttonContainer.add(Components.createVerticalSpacer(16));
        
        // "Passwort vergessen" Link
        JButton forgotPasswordBtn = Components.createLinkButton("Passwort vergessen?", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleForgotPassword();
            }
        });
        buttonContainer.add(forgotPasswordBtn);
        buttonContainer.add(Components.createVerticalSpacer(24));
        
        // "Registrieren" Link
        JPanel signUpSection = new JPanel();
        signUpSection.setOpaque(false);
        signUpSection.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel signUpText = Components.captionLabel("Noch kein Konto?");
        JButton signUpBtn = Components.createLinkButton("Hier registrieren", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showSignUp();
            }
        });
        
        signUpSection.add(signUpText);
        signUpSection.add(signUpBtn);
        buttonContainer.add(signUpSection);
        
        formContainer.add(buttonContainer);
        
        // Container zentrieren
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 40, 40, 40);
        centerPanel.add(formContainer, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Bitte füllen Sie alle Felder aus.");
            return;
        }

        // Lade-Animation zeigen (vereinfacht)
        loginBtn.setText("Wird angemeldet...");
        loginBtn.setEnabled(false);

        // Async login in background thread
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    return authService.signIn(email, password);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void done() {
                loginBtn.setText("Anmelden");
                loginBtn.setEnabled(true);
                
                try {
                    if (get()) {
                        // Erfolgreiche Anmeldung
                        frame.showHome();
                    } else {
                        showErrorDialog("Anmeldung fehlgeschlagen. Bitte prüfen Sie Ihre Eingaben.");
                    }
                } catch (Exception e) {
                    showErrorDialog("Ein Fehler ist aufgetreten: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void handleForgotPassword() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            showErrorDialog("Bitte geben Sie Ihre E-Mail-Adresse ein.");
            return;
        }

        try {
            authService.resetPassword(email);
            showSuccessDialog("Ein Link zum Zurücksetzen des Passworts wurde an Ihre E-Mail gesendet.");
        } catch (IOException e) {
            showErrorDialog("Fehler beim Senden der E-Mail: " + e.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erfolg", JOptionPane.INFORMATION_MESSAGE);
    }
}
