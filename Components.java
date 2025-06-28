import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

/**
 * Beschreiben Sie hier die Klasse Components.
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

public class Components {
    // Modern Color Palette - Inspired by contemporary mobile app design
    public static final Color PRIMARY_COLOR      = new Color(64, 224, 208);   // Turquoise
    public static final Color PRIMARY_VARIANT    = new Color(32, 178, 170);   // Dark Turquoise
    public static final Color SECONDARY_COLOR    = new Color(135, 206, 235);  // Sky Blue
    public static final Color ACCENT_COLOR       = new Color(255, 99, 132);   // Coral Pink
    public static final Color SUCCESS_COLOR      = new Color(76, 175, 80);    // Green
    public static final Color WARNING_COLOR      = new Color(255, 193, 7);    // Amber
    
    // Surface and Background Colors
    public static final Color SURFACE_COLOR      = new Color(255, 255, 255);  // Pure White
    public static final Color CARD_COLOR         = new Color(248, 250, 252);  // Light Blue Gray
    public static final Color BACKGROUND_COLOR   = new Color(240, 248, 255);  // Very Light Blue
    
    // Text Colors
    public static final Color ON_PRIMARY         = Color.WHITE;
    public static final Color ON_SURFACE         = new Color(30, 41, 59);     // Slate Gray
    public static final Color ON_BACKGROUND      = new Color(71, 85, 105);    // Medium Slate
    public static final Color TEXT_SECONDARY     = new Color(148, 163, 184);  // Light Slate
    
    // Modern UI Colors
    public static final Color NAV_BG_COLOR       = SURFACE_COLOR;
    public static final Color HEADER_BG_COLOR    = PRIMARY_COLOR;
    public static final Color BORDER_COLOR       = new Color(226, 232, 240);  // Very Light Border
    public static final Color SHADOW_COLOR       = new Color(0, 0, 0, 12);    // Soft Shadow
    public static final Color HOVER_COLOR        = new Color(0, 0, 0, 6);     // Light Hover
    public static final Color FOCUS_COLOR        = new Color(64, 224, 208, 30); // Focus Ring

    // Modern Typography - Clean and contemporary
    public static final Font DISPLAY_FONT   = new Font("Segoe UI", Font.BOLD, 24);   // Large titles
    public static final Font TITLE_FONT     = new Font("Segoe UI", Font.BOLD, 18);   // Section titles
    public static final Font SUBTITLE_FONT  = new Font("Segoe UI", Font.PLAIN, 16);  // Subtitles
    public static final Font BODY_FONT       = new Font("Segoe UI", Font.PLAIN, 14);  // Body text
    public static final Font BUTTON_FONT    = new Font("Segoe UI", Font.BOLD, 14);   // Buttons
    public static final Font NAV_FONT       = new Font("Segoe UI", Font.PLAIN, 13);  // Navigation
    public static final Font CAPTION_FONT   = new Font("Segoe UI", Font.PLAIN, 12);  // Small text
    public static final Font LABEL_FONT     = new Font("Segoe UI", Font.PLAIN, 13);  // Form labels

    // Custom Border für abgerundete Ecken
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color borderColor;
        private int thickness;
        
        public RoundedBorder(int radius, Color borderColor, int thickness) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.thickness = thickness;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness/2, y + thickness/2, 
                             width - thickness, height - thickness, radius, radius);
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(12, 16, 12, 16);
        }
    }
    
    // Custom JTextField mit abgerundeten Ecken
    static class RoundedTextField extends JTextField {
        private int radius;
        
        public RoundedTextField(int columns, int radius) {
            super(columns);
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
    
    // Custom JPasswordField mit abgerundeten Ecken
    static class RoundedPasswordField extends JPasswordField {
        private int radius;
        
        public RoundedPasswordField(int columns, int radius) {
            super(columns);
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
    
    // Custom JButton mit abgerundeten Ecken
    static class RoundedButton extends JButton {
        private int radius;
        private Color hoverColor;
        
        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2d.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2d.setColor(hoverColor != null ? hoverColor : getBackground().brighter());
            } else {
                g2d.setColor(getBackground());
            }
            
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2d);
            g2d.dispose();
        }
        
        public void setHoverColor(Color hoverColor) {
            this.hoverColor = hoverColor;
        }
    }

    // Modern Text Field mit abgerundeten Ecken und zeitgemäßem Styling
    public static JTextField textField(int columns) {
        RoundedTextField field = new RoundedTextField(columns, 12);
        field.setFont(BODY_FONT);
        field.setForeground(ON_SURFACE);
        field.setBackground(SURFACE_COLOR);
        field.setBorder(new RoundedBorder(12, BORDER_COLOR, 1));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 44));
        
        // Moderne Focus-Styles hinzufügen
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(12, PRIMARY_COLOR, 2));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(12, BORDER_COLOR, 1));
            }
        });
        
        return field;
    }

    // Modern Password Field mit verbessertem Styling
    public static JPasswordField passwordField(int columns) {
        RoundedPasswordField field = new RoundedPasswordField(columns, 12);
        field.setFont(BODY_FONT);
        field.setForeground(ON_SURFACE);
        field.setBackground(SURFACE_COLOR);
        field.setBorder(new RoundedBorder(12, BORDER_COLOR, 1));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 44));
        
        // Focus-Styling hinzufügen
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(12, PRIMARY_COLOR, 2));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(12, BORDER_COLOR, 1));
            }
        });
        
        return field;
    }

    // Modern Primary Button mit zeitgemäßem abgerundetem Design
    public static JButton primaryButton(String text, ActionListener action) {
        RoundedButton btn = new RoundedButton(text, 12);
        btn.setBackground(PRIMARY_COLOR);
        btn.setHoverColor(PRIMARY_VARIANT);
        btn.setForeground(ON_PRIMARY);
        btn.setFont(BUTTON_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 48));
        btn.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // Modern Secondary Button mit sauberem umrandeten Stil
    public static JButton secondaryButton(String text, ActionListener action) {
        RoundedButton btn = new RoundedButton(text, 12);
        btn.setBackground(SURFACE_COLOR);
        btn.setHoverColor(CARD_COLOR);
        btn.setForeground(ON_SURFACE);
        btn.setFont(BUTTON_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 48));
        btn.setBorder(new RoundedBorder(12, BORDER_COLOR, 2));
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // Modern Navigation Button with subtle styling
    public static JButton navButton(String text, boolean active, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(NAV_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        
        if (active) {
            btn.setBackground(PRIMARY_COLOR);
            btn.setForeground(ON_PRIMARY);
            btn.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        } else {
            btn.setBackground(NAV_BG_COLOR);
            btn.setForeground(ON_BACKGROUND);
            btn.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        }
        
        // Add hover effects for inactive buttons
        if (!active) {
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(BACKGROUND_COLOR);
                }
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(NAV_BG_COLOR);
                }
            });
        }
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // Modern App Header with elevated styling
    public static JLabel headerTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ON_PRIMARY);
        label.setFont(TITLE_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        return label;
    }
    
    // Modern Card Panel with subtle shadow effect
    public static JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }
    
    // Modern Card Panel with gradient background and rounded corners
    public static JPanel gradientCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient from turquoise to blue
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }
    
    // Modern elevated card with subtle shadow
    public static JPanel elevatedCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                g2d.setColor(SHADOW_COLOR);
                g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 16, 16);
                
                // Draw card background
                g2d.setColor(SURFACE_COLOR);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 16, 16);
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 24, 24));
        return panel;
    }
    
    // Modern icon button with circular background
    public static JButton iconButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBackground(SURFACE_COLOR);
        btn.setForeground(ON_SURFACE);
        btn.setFont(CAPTION_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setOpaque(true);
        
        // Add hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(CARD_COLOR);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(SURFACE_COLOR);
            }
        });
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }
    
    // Modern info card with clean layout
    public static JPanel infoCardPanel(String title, String value, String subtitle) {
        JPanel panel = elevatedCardPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CAPTION_FONT);
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(TITLE_FONT);
        valueLabel.setForeground(ON_SURFACE);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Subtitle (optional)
        if (subtitle != null && !subtitle.isEmpty()) {
            JLabel subtitleLabel = new JLabel(subtitle);
            subtitleLabel.setFont(CAPTION_FONT);
            subtitleLabel.setForeground(TEXT_SECONDARY);
            subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(subtitleLabel);
        }
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(valueLabel);
        
        return panel;
    }

    // Modern Text Label with proper typography
    public static JLabel textLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(ON_SURFACE);
        return label;
    }
    
    // Subtitle Label
    public static JLabel subtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(ON_SURFACE);
        return label;
    }
    
    // Caption Label for secondary text
    public static JLabel captionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(CAPTION_FONT);
        label.setForeground(ON_BACKGROUND);
        return label;
    }
    
    // Modern Panel with background color
    public static JPanel backgroundPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }
    
    // Surface Panel (for cards, dialogs, etc.)
    public static JPanel surfacePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE_COLOR);
        return panel;
    }
    
    // Layout-Hilfsmethoden für besseres Alignment
    
    // Zentriertes Panel für Login/Signup-Formulare
    public static JPanel createCenteredFormPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        return mainPanel;
    }
    
    // Formular-Container mit optimaler Breite
    public static JPanel createFormContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(SURFACE_COLOR);
        container.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(16, BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(32, 32, 32, 32)
        ));
        container.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        container.setPreferredSize(new Dimension(400, 500));
        return container;
    }
    
    // Titel für Formulare
    public static JLabel createFormTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(DISPLAY_FONT);
        title.setForeground(ON_SURFACE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
        return title;
    }
    
    // Label für Formularfelder
    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(ON_SURFACE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return label;
    }
    
    // Spacer für vertikalen Abstand
    public static Component createVerticalSpacer(int height) {
        return Box.createVerticalStrut(height);
    }
    
    // Button-Container für bessere Ausrichtung
    public static JPanel createButtonContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        return container;
    }
    
    // Link-Button für "Passwort vergessen" etc.
    public static JButton createLinkButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(BODY_FONT);
        btn.setForeground(PRIMARY_COLOR);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setBorder(null);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hover-Effekt für Links
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(PRIMARY_VARIANT);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(PRIMARY_COLOR);
            }
        });
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }
}
