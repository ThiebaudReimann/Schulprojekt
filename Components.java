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
    // WinUI 3 Fluent Design Color Palette
    public static final Color PRIMARY_COLOR      = new Color(0, 120, 212);    // Windows Blue
    public static final Color PRIMARY_VARIANT    = new Color(0, 103, 192);    // Darker Blue
    public static final Color SECONDARY_COLOR    = new Color(118, 185, 0);    // Windows Green
    public static final Color ACCENT_COLOR       = new Color(136, 23, 152);   // Windows Purple
    public static final Color SUCCESS_COLOR      = new Color(16, 124, 16);    // Success Green
    public static final Color WARNING_COLOR      = new Color(255, 185, 0);    // Warning Amber
    public static final Color ERROR_COLOR        = new Color(196, 43, 28);    // Error Red
    
    // WinUI 3 Surface and Background Colors
    public static final Color SURFACE_COLOR      = new Color(255, 255, 255);  // Card Background
    public static final Color CARD_COLOR         = new Color(249, 249, 249);  // Control Background
    public static final Color BACKGROUND_COLOR   = new Color(243, 243, 243);  // Page Background
    public static final Color LAYER_COLOR        = new Color(255, 255, 255);  // Layer Fill
    
    // WinUI 3 Text Colors
    public static final Color ON_PRIMARY         = Color.WHITE;
    public static final Color TEXT_PRIMARY       = new Color(32, 31, 30);     // Text Primary
    public static final Color TEXT_SECONDARY     = new Color(96, 94, 92);     // Text Secondary
    public static final Color TEXT_TERTIARY      = new Color(128, 126, 124);  // Text Tertiary
    public static final Color TEXT_DISABLED      = new Color(161, 159, 157);  // Text Disabled
    
    // WinUI 3 Semantic Colors
    public static final Color NAV_BG_COLOR       = LAYER_COLOR;
    public static final Color HEADER_BG_COLOR    = PRIMARY_COLOR;
    public static final Color BORDER_COLOR       = new Color(227, 227, 227);  // Control Border
    public static final Color FOCUS_BORDER       = new Color(0, 120, 212);    // Focus Border
    public static final Color SHADOW_COLOR       = new Color(0, 0, 0, 16);    // Drop Shadow
    public static final Color HOVER_COLOR        = new Color(0, 0, 0, 6);     // Hover Overlay
    public static final Color PRESSED_COLOR      = new Color(0, 0, 0, 10);    // Pressed Overlay

    // WinUI 3 Typography - Segoe UI Variable (falls verfügbar) oder Segoe UI
    public static final Font DISPLAY_FONT   = new Font("Segoe UI", Font.BOLD, 28);   // Large Display
    public static final Font TITLE_FONT     = new Font("Segoe UI", Font.BOLD, 20);   // Title
    public static final Font SUBTITLE_FONT  = new Font("Segoe UI", Font.BOLD, 16);   // Subtitle
    public static final Font BODY_FONT       = new Font("Segoe UI", Font.PLAIN, 14);  // Body
    public static final Font BODY_STRONG_FONT = new Font("Segoe UI", Font.BOLD, 14); // Body Strong
    public static final Font BUTTON_FONT    = new Font("Segoe UI", Font.PLAIN, 14);   // Button Text
    public static final Font NAV_FONT       = new Font("Segoe UI", Font.PLAIN, 12);  // Navigation
    public static final Font CAPTION_FONT   = new Font("Segoe UI", Font.PLAIN, 12);  // Caption
    public static final Font LABEL_FONT     = new Font("Segoe UI", Font.PLAIN, 13);  // Form Labels

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
            return new Insets(7, 12, 7, 12);  // Optimiert für bessere Text-Zentrierung
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

    // WinUI 3 Text Field mit Fluent Design styling
    public static JTextField textField(int columns) {
        RoundedTextField field = new RoundedTextField(columns, 4);  // WinUI 3 hat kleinere Rundungen
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(SURFACE_COLOR);
        field.setBorder(new RoundedBorder(4, BORDER_COLOR, 1));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));  // WinUI 3 Standard-Höhe
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        
        // WinUI 3 Focus-Styles
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(4, FOCUS_BORDER, 2));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(4, BORDER_COLOR, 1));
            }
        });
        
        return field;
    }

    // WinUI 3 Password Field mit verbessertem Styling
    public static JPasswordField passwordField(int columns) {
        RoundedPasswordField field = new RoundedPasswordField(columns, 4);
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(SURFACE_COLOR);
        field.setBorder(new RoundedBorder(4, BORDER_COLOR, 1));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        
        // Focus-Styling
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(4, FOCUS_BORDER, 2));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(new RoundedBorder(4, BORDER_COLOR, 1));
            }
        });
        
        return field;
    }

    // WinUI 3 Primary Button (Accent Button)
    public static JButton primaryButton(String text, ActionListener action) {
        RoundedButton btn = new RoundedButton(text, 4);  // WinUI 3 kleinere Rundung
        btn.setBackground(PRIMARY_COLOR);
        btn.setHoverColor(PRIMARY_VARIANT);
        btn.setForeground(ON_PRIMARY);
        btn.setFont(BUTTON_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 32));  // WinUI 3 Standard-Button-Größe
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // WinUI 3 Secondary Button (Standard Button)
    public static JButton secondaryButton(String text, ActionListener action) {
        RoundedButton btn = new RoundedButton(text, 4);
        btn.setBackground(CARD_COLOR);
        btn.setHoverColor(new Color(235, 235, 235));  // Subtle hover
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(BUTTON_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 32));
        btn.setBorder(new RoundedBorder(4, BORDER_COLOR, 1));
        
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
            btn.setForeground(TEXT_SECONDARY);
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

    // Android AppBar style header
    public static JPanel headerTitle(String text) {
        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBackground(PRIMARY_COLOR);
        appBar.setPreferredSize(new Dimension(0, 56));  // Standard Android AppBar height
        appBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 12))); // Subtle shadow
        
        // Title label with Android styling
        JLabel titleLabel = new JLabel(text);
        titleLabel.setForeground(ON_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Android AppBar title size
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16)); // Android margins
        
        appBar.add(titleLabel, BorderLayout.WEST);
        return appBar;
    }
    
    // Complete Android AppBar with navigation icon
    public static JPanel createAndroidAppBar(String title, String navIcon, ActionListener navAction) {
        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBackground(PRIMARY_COLOR);
        appBar.setPreferredSize(new Dimension(0, 56));  // Standard Android AppBar height
        appBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 12))); // Subtle shadow
        
        // Navigation button (left side)
        if (navIcon != null && !navIcon.isEmpty()) {
            JButton navButton = new JButton(navIcon);
            navButton.setForeground(ON_PRIMARY);
            navButton.setBackground(new Color(0, 0, 0, 0)); // Transparent
            navButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            navButton.setFocusPainted(false);
            navButton.setBorderPainted(false);
            navButton.setContentAreaFilled(false);
            navButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            navButton.setPreferredSize(new Dimension(48, 48)); // Android touch target
            navButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            
            // Android-style hover effect
            navButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    navButton.setBackground(new Color(255, 255, 255, 20)); // Light overlay
                    navButton.setOpaque(true);
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    navButton.setBackground(new Color(0, 0, 0, 0));
                    navButton.setOpaque(false);
                }
            });
            
            if (navAction != null) navButton.addActionListener(navAction);
            appBar.add(navButton, BorderLayout.WEST);
        }
        
        // Title (center-left)
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(ON_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, navIcon != null ? 16 : 16, 0, 16));
        appBar.add(titleLabel, BorderLayout.CENTER);
        
        return appBar;
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
        btn.setForeground(TEXT_PRIMARY);
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
        valueLabel.setForeground(TEXT_PRIMARY);
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
        label.setForeground(TEXT_PRIMARY);
        return label;
    }
    
    // Subtitle Label
    public static JLabel subtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }
    
    // Caption Label for secondary text
    public static JLabel captionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(CAPTION_FONT);
        label.setForeground(TEXT_SECONDARY);
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
    // WinUI 3 Layout-Hilfsmethoden
    
    // WinUI 3 Zentriertes Panel für Formulare
    public static JPanel createCenteredFormPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        return mainPanel;
    }
    
    // WinUI 3 Formular-Container (Card-Style)
    public static JPanel createFormContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(SURFACE_COLOR);
        container.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COLOR, 1),  // WinUI 3 Card Border Radius
            BorderFactory.createEmptyBorder(24, 24, 24, 24)  // WinUI 3 Padding
        ));
        container.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        container.setPreferredSize(new Dimension(400, 480));
        return container;
    }
    
    // WinUI 3 Titel für Formulare
    public static JLabel createFormTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));  // Kleinerer Abstand
        return title;
    }
    
    // WinUI 3 Label für Formularfelder
    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));  // Kleinerer Abstand
        return label;
    }
    
    // Spacer für vertikalen Abstand
    public static Component createVerticalSpacer(int height) {
        return Box.createVerticalStrut(height);
    }
    
    // WinUI 3 Button-Container
    public static JPanel createButtonContainer() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));  // Kleinerer Abstand
        return container;
    }
    
    // WinUI 3 Hyperlink Button
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
        
        // WinUI 3 Hover-Effekt für Links
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(PRIMARY_VARIANT);
                // Underline-Effekt könnte hier hinzugefügt werden
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(PRIMARY_COLOR);
            }
        });
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }
    
    // WinUI 3 spezielle Komponenten
    
    // WinUI 3 Header mit Fluent Design
    public static JPanel createWinUI3Header(String title) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(LAYER_COLOR);
        header.setPreferredSize(new Dimension(0, 48));  // WinUI 3 Standard Header-Höhe
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        // Titel
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SUBTITLE_FONT);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        header.add(titleLabel, BorderLayout.WEST);
        
        return header;
    }
    
    // WinUI 3 Mica-Effekt Panel (vereinfacht)
    public static JPanel createMicaPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Mica-ähnlicher Effekt mit subtilen Gradienten
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(249, 249, 249, 240),
                    0, getHeight(), new Color(243, 243, 243, 240)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }
    
    // WinUI 3 Navigation Button
    public static JButton createNavButton(String text, boolean selected, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(NAV_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(200, 40));  // WinUI 3 Navigation-Button-Größe
        
        if (selected) {
            btn.setBackground(new Color(0, 120, 212, 30));  // Selected state
            btn.setForeground(PRIMARY_COLOR);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, PRIMARY_COLOR),  // Links-Border für Selected
                BorderFactory.createEmptyBorder(8, 13, 8, 16)
            ));
        } else {
            btn.setBackground(LAYER_COLOR);
            btn.setForeground(TEXT_PRIMARY);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }
        
        // WinUI 3 Hover-Effekte
        if (!selected) {
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 0, 0, 10));  // Subtle hover
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(LAYER_COLOR);
                }
            });
        }
        
        if (action != null) btn.addActionListener(action);
        return btn;
    }
}
