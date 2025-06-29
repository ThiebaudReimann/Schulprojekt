
/**
 * Beschreiben Sie hier die Klasse User.
 * 
 * @author Thiébaud Reimann 
 * @version 1.0
 */

import java.util.Map;

public class User
{
    private String uid;
    private String displayName;
    private String email;
    
    // Statische Instanz für den aktuell eingeloggten Benutzer
    private static User currentUser = null;
    
    public User(String uid, String displayName, String email) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
    }
    
    public static User fromJson(Map<String, Object> json) {
        String uid = (String) json.get("uid");
        String displayName = (String) json.get("displayName");
        String email = (String) json.get("email");
        
        return new User(uid, displayName, email);
    }
    
    // Getter-Methoden
    public String getUid() {
        return uid;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setter für displayName falls es aktualisiert werden muss
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    // Statische Methoden für User-Management
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    public static void logout() {
        currentUser = null;
    }
    
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
