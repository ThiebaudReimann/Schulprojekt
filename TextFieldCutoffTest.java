import java.awt.*;
import javax.swing.*;

/**
 * Comprehensive test for text field text cutoff issues
 */
public class TextFieldCutoffTest extends JFrame {
    
    public TextFieldCutoffTest() {
        setTitle("Text Field Cut-off Test - WinUI 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(Components.BACKGROUND_COLOR);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Components.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Text Field Cut-off Test");
        titleLabel.setFont(Components.TITLE_FONT);
        titleLabel.setForeground(Components.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Test 1: Standard text field
        addTestSection(mainPanel, "Standard WinUI 3 Text Field:", 
                      Components.textField(30), "Normal text input");
        
        // Test 2: Password field
        addTestSection(mainPanel, "WinUI 3 Password Field:", 
                      Components.passwordField(30), "password123");
        
        // Test 3: Text with special characters
        JTextField specialField = Components.textField(30);
        addTestSection(mainPanel, "Special Characters:", 
                      specialField, "Text with ÄÖÜ äöü ß and symbols: @#$%");
        
        // Test 4: Numbers and mixed content
        JTextField mixedField = Components.textField(30);
        addTestSection(mainPanel, "Mixed Content:", 
                      mixedField, "Email: test@example.com Phone: +49 123 456789");
        
        // Test 5: Long text that should scroll
        JTextField longField = Components.textField(30);
        addTestSection(mainPanel, "Long Text (should scroll):", 
                      longField, "This is a very long text that should demonstrate scrolling behavior in the text field when the content exceeds the visible area");
        
        add(new JScrollPane(mainPanel));
    }
    
    private void addTestSection(JPanel parent, String labelText, JTextField field, String testText) {
        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(Components.BODY_FONT);
        label.setForeground(Components.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createVerticalStrut(5));
        
        // Field
        field.setText(testText);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, field.getPreferredSize().height));
        parent.add(field);
        parent.add(Box.createVerticalStrut(15));
        
        // Debug info
        JLabel debugLabel = new JLabel(String.format("Field Height: %dpx, Font Size: %dpx, Border Insets: %s", 
                field.getPreferredSize().height, 
                field.getFont().getSize(),
                field.getBorder() != null ? field.getBorder().getBorderInsets(field).toString() : "none"));
        debugLabel.setFont(Components.CAPTION_FONT);
        debugLabel.setForeground(Components.TEXT_SECONDARY);
        debugLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(debugLabel);
        parent.add(Box.createVerticalStrut(10));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextFieldCutoffTest().setVisible(true);
        });
    }
}
