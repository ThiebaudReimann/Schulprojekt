/**
 * Beschreiben Sie hier die Klasse CalendarWidget.
 * 
 * @author Anton Koch, Thiébaud Reimann
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;

public class CalendarWidget extends JPanel
{
    private JPanel appBar;
    private JPanel contentPanel;
    
    /**
     * Konstruktor für CalendarWidget
     */
    public CalendarWidget() {
        initializeComponents();
        setupLayout();
    }
    
    /**
     * Initialisiert die Komponenten des Calendar Widgets
     */
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(Components.BACKGROUND_COLOR);
        
        // App Bar erstellen mit Navigation Icon
        appBar = createCustomAppBarWithActions();
        
        // Content Panel für den Kalenderinhalt
        contentPanel = new JPanel();
        contentPanel.setBackground(Components.BACKGROUND_COLOR);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        
        // Platzhalter Label für Kalenderinhalt
        JLabel placeholderLabel = new JLabel("Kalender Inhalt kommt hier hin", SwingConstants.CENTER);
        placeholderLabel.setFont(Components.BODY_FONT);
        placeholderLabel.setForeground(Components.TEXT_SECONDARY);
        contentPanel.add(placeholderLabel, BorderLayout.CENTER);
    }
    
    /**
     * Erstellt eine custom App Bar mit Navigation und Add Action
     * @return JPanel mit der App Bar
     */
    private JPanel createCustomAppBarWithActions() {
        JPanel customAppBar = new JPanel(new BorderLayout());
        customAppBar.setBackground(Components.PRIMARY_COLOR);
        customAppBar.setPreferredSize(new Dimension(0, 56));
        customAppBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 12)));
        
        // Navigation button (left side)
        JButton navButton = new JButton("☰");
        navButton.setForeground(Components.ON_PRIMARY);
        navButton.setBackground(new Color(0, 0, 0, 0));
        navButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        navButton.setFocusPainted(false);
        navButton.setBorderPainted(false);
        navButton.setContentAreaFilled(false);
        navButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navButton.setPreferredSize(new Dimension(48, 48));
        navButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        // Navigation button hover effect
        navButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navButton.setBackground(new Color(255, 255, 255, 20));
                navButton.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navButton.setBackground(new Color(0, 0, 0, 0));
                navButton.setOpaque(false);
            }
        });
        
        navButton.addActionListener(e -> {
            System.out.println("Navigation button clicked");
        });
        
        // Title (center)
        JLabel titleLabel = new JLabel("Kalender");
        titleLabel.setForeground(Components.ON_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        
        // Add button (right side)
        JButton addButton = new JButton("+");
        addButton.setForeground(Components.ON_PRIMARY);
        addButton.setBackground(new Color(0, 0, 0, 0));
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(48, 48));
        addButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        // Add button hover effect
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(255, 255, 255, 20));
                addButton.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(0, 0, 0, 0));
                addButton.setOpaque(false);
            }
        });
        
        addButton.addActionListener(e -> {
            onAddButtonClicked();
        });
        
        // Layout setup
        customAppBar.add(navButton, BorderLayout.WEST);
        customAppBar.add(titleLabel, BorderLayout.CENTER);
        customAppBar.add(addButton, BorderLayout.EAST);
        
        return customAppBar;
    }
    
    /**
     * Wird aufgerufen wenn der Add Button geklickt wird
     */
    private void onAddButtonClicked() {
        System.out.println("Add button clicked - Neuer Kalendereintrag erstellen");
        // Hier kann später die Logik zum Hinzufügen eines neuen Kalendereintrags implementiert werden
    }
    
    /**
     * Setzt das Layout für das Calendar Widget auf
     */
    private void setupLayout() {
        add(appBar, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Gibt die App Bar zurück
     * @return Die App Bar des Calendar Widgets
     */
    public JPanel getAppBar() {
        return appBar;
    }
    
    /**
     * Gibt das Content Panel zurück
     * @return Das Content Panel des Calendar Widgets
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }
}