import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

/**
 * Klasse für persistente Login-Daten mit sicherer Hash-Speicherung
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */
public class LoginPersistence {
    private static final String LOGIN_FILE = "user_login.dat";
    private static final String SALT_KEY = "salt";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_HASH_KEY = "password_hash";
    private static final String DISPLAY_NAME_KEY = "display_name";
    private static final String UID_KEY = "uid";
    
    /**
     * Speichert Login-Daten persistent
     * @param email E-Mail des Benutzers
     * @param password Passwort des Benutzers (wird gehasht)
     * @param user User-Objekt mit weiteren Daten
     */
    public static void saveLoginData(String email, String password, User user) {
        try {
            // Salt generieren für sicheres Hashing
            String salt = generateSalt();
            String passwordHash = hashPassword(password, salt);
            
            Properties props = new Properties();
            props.setProperty(EMAIL_KEY, email);
            props.setProperty(PASSWORD_HASH_KEY, passwordHash);
            props.setProperty(SALT_KEY, salt);
            props.setProperty(DISPLAY_NAME_KEY, user.getDisplayName());
            props.setProperty(UID_KEY, user.getUid());
            
            // Datei in verstecktem Ordner speichern
            Path userHome = Paths.get(System.getProperty("user.home"));
            Path appDir = userHome.resolve(".meine_app");
            
            // Ordner erstellen falls nicht vorhanden
            if (!Files.exists(appDir)) {
                Files.createDirectories(appDir);
                // Ordner als versteckt markieren (Windows)
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    try {
                        Files.setAttribute(appDir, "dos:hidden", true);
                    } catch (Exception e) {
                        // Ignorieren falls nicht unterstützt
                    }
                }
            }
            
            Path loginFile = appDir.resolve(LOGIN_FILE);
            
            try (FileOutputStream fos = new FileOutputStream(loginFile.toFile())) {
                props.store(fos, "Encrypted Login Data - Do not edit manually");
            }
            
            System.out.println("Login-Daten erfolgreich gespeichert.");
            
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern der Login-Daten: " + e.getMessage());
        }
    }
    
    /**
     * Lädt gespeicherte Login-Daten und versucht automatischen Login
     * @return User-Objekt falls erfolgreich, null sonst
     */
    public static User loadAndAutoLogin() {
        try {
            Path userHome = Paths.get(System.getProperty("user.home"));
            Path appDir = userHome.resolve(".meine_app");
            Path loginFile = appDir.resolve(LOGIN_FILE);
            
            if (!Files.exists(loginFile)) {
                return null; // Keine gespeicherten Daten
            }
            
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(loginFile.toFile())) {
                props.load(fis);
            }
            
            String email = props.getProperty(EMAIL_KEY);
            String storedHash = props.getProperty(PASSWORD_HASH_KEY);
            String salt = props.getProperty(SALT_KEY);
            String displayName = props.getProperty(DISPLAY_NAME_KEY);
            String uid = props.getProperty(UID_KEY);
            
            if (email == null || storedHash == null || salt == null || displayName == null || uid == null) {
                return null; // Unvollständige Daten
            }
            
            // User-Objekt erstellen (ohne Passwort-Verification da bereits gespeichert)
            User user = new User(uid, displayName, email);
            return user;
            
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Login-Daten: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Löscht gespeicherte Login-Daten (für Logout)
     */
    public static void clearLoginData() {
        try {
            Path userHome = Paths.get(System.getProperty("user.home"));
            Path appDir = userHome.resolve(".meine_app");
            Path loginFile = appDir.resolve(LOGIN_FILE);
            
            if (Files.exists(loginFile)) {
                Files.delete(loginFile);
                System.out.println("Login-Daten erfolgreich gelöscht.");
            }
            
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen der Login-Daten: " + e.getMessage());
        }
    }
    
    /**
     * Überprüft ob gespeicherte Login-Daten existieren
     * @return true falls Daten vorhanden, false sonst
     */
    public static boolean hasStoredLoginData() {
        try {
            Path userHome = Paths.get(System.getProperty("user.home"));
            Path appDir = userHome.resolve(".meine_app");
            Path loginFile = appDir.resolve(LOGIN_FILE);
            
            return Files.exists(loginFile);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Generiert ein zufälliges Salt für Passwort-Hashing
     * @return Base64-codiertes Salt
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Hasht ein Passwort mit gegebenem Salt
     * @param password Das zu hashende Passwort
     * @param salt Das Salt für das Hashing
     * @return Base64-codierter Hash
     */
    private static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
    
    /**
     * Überprüft ob ein Passwort mit dem gespeicherten Hash übereinstimmt
     * @param password Das zu überprüfende Passwort
     * @param storedHash Der gespeicherte Hash
     * @param salt Das verwendete Salt
     * @return true falls Passwort korrekt, false sonst
     */
    public static boolean verifyPassword(String password, String storedHash, String salt) {
        try {
            String hashToCheck = hashPassword(password, salt);
            return hashToCheck.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Fehler beim Passwort-Verification: " + e.getMessage());
            return false;
        }
    }
}
