import javax.swing.*;
/**
 * Beschreiben Sie hier die Klasse MainFrame.
 * 
 * @author Thiébaud Reimann 
 * @version 1.0
 */

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Meine App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        showLoginPanel(); // Start mit Login

        setVisible(true);
    }

    public void showLoginPanel() {
        setContentPane(new LoginPanel(this));
        revalidate();
    }

    public void showHomePanel() {
        setContentPane(new HomePanel(this));
        revalidate();
    }
}
