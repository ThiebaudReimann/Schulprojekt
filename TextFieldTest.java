import java.awt.*;
import javax.swing.*;

/**
 * Test class to verify that text fields don't cut off text
 */
public class TextFieldTest extends JFrame {
    
    public TextFieldTest() {
        setTitle("Text Field Test - WinUI 3 Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Set WinUI 3 background
        getContentPane().setBackground(Components.BACKGROUND_COLOR);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Components.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Text Field Test");
        titleLabel.setFont(Components.TITLE_FONT);
        titleLabel.setForeground(Components.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);
        
        // Test different text lengths
        String[] testTexts = {
            "Short",
            "Medium length text",
            "This is a longer text to test if it gets cut off",
            "Very very very long text that should definitely wrap or be visible"
        };
        
        for (int i = 0; i < testTexts.length; i++) {
            JLabel label = new JLabel("Test " + (i + 1) + ":");
            label.setFont(Components.BODY_FONT);
            label.setForeground(Components.TEXT_PRIMARY);
            gbc.gridy = i * 2 + 1;
            mainPanel.add(label, gbc);
            
            JTextField textField = Components.textField(30);
            textField.setText(testTexts[i]);
            textField.setFont(Components.BODY_FONT);
            gbc.gridy = i * 2 + 2;
            mainPanel.add(textField, gbc);
        }
        
        add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextFieldTest().setVisible(true);
        });
    }
}
