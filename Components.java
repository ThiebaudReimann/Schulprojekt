import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Beschreiben Sie hier die Klasse Components.
 * 
 * @author Thiébaud Reimann 
 * @version 1.0
 */

public class Components {
    // Farb- und Font-Konstanten
    public static final Color PRIMARY_COLOR    = new Color(33, 150, 243);
    public static final Color ACCENT_COLOR     = new Color(76, 175, 80);
    public static final Color NAV_BG_COLOR     = new Color(245, 245, 245);
    public static final Color HEADER_BG_COLOR  = PRIMARY_COLOR;
    public static final Color BORDER_COLOR     = new Color(200, 200, 200);

    public static final Font TITLE_FONT   = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font NAV_FONT     = new Font("Segoe UI", Font.PLAIN, 12);

    // Gestyltes Textfeld
    public static JTextField textField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(DEFAULT_FONT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return field;
    }

    // Gestyltes Passwortfeld
    public static JPasswordField passwordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(DEFAULT_FONT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return field;
    }

    // Primärer Aktionsbutton
    public static JButton primaryButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBackground(ACCENT_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 35));
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // Navigationsbutton (Bottom-Bar)
    public static JButton navButton(String text, boolean active, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(NAV_FONT);
        btn.setBackground(active ? NAV_BG_COLOR.darker() : NAV_BG_COLOR);
        btn.setBorder(null);
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // App-Header-Titel
    public static JLabel headerTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(TITLE_FONT);
        return label;
    }
}
