/**
 * Universeller Datenspeicher-Service
 * Kann zwischen lokaler Speicherung und Firebase Firestore wechseln
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UniversalDataService {
    private static boolean useFirebase = false;
    private static boolean initialized = false;
    
    /**
     * Initialisiert den Service mit Firebase Firestore
     * @param serviceAccountPath Pfad zur Service-Account-JSON
     * @param databaseUrl Firebase Database URL
     */
    public static void initializeWithFirebase(String serviceAccountPath, String databaseUrl) {
        try {
            // Versuche Firebase zu initialisieren
            FirestoreService.initialize(serviceAccountPath, databaseUrl);
            useFirebase = true;
            initialized = true;
            System.out.println("✅ Firebase Firestore erfolgreich initialisiert");
        } catch (Exception e) {
            System.err.println("❌ Firebase-Initialisierung fehlgeschlagen: " + e.getMessage());
            System.out.println("🔄 Wechsle zu lokaler Datenspeicherung...");
            initializeLocal();
        }
    }
    
    /**
     * Initialisiert den Service mit lokaler Datenspeicherung
     */
    public static void initializeLocal() {
        try {
            LocalDataService.initialize();
            useFirebase = false;
            initialized = true;
            System.out.println("✅ Lokaler Datenspeicher erfolgreich initialisiert");
        } catch (IOException e) {
            throw new RuntimeException("Kritischer Fehler: Lokale Initialisierung fehlgeschlagen", e);
        }
    }
    
    /**
     * Automatische Initialisierung - versucht zuerst Firebase, dann lokal
     */
    public static void initialize() {
        if (initialized) return;
        
        // Prüfe ob Firebase verfügbar ist
        try {
            Class.forName("com.google.firebase.FirebaseApp");
            // Firebase-Klassen sind verfügbar, versuche Standard-Initialisierung
            try {
                // Versuche mit Application Default Credentials
                FirestoreService.initializeWithDefaultCredentials();
                useFirebase = true;
                initialized = true;
                System.out.println("✅ Firebase mit Default Credentials initialisiert");
                return;
            } catch (Exception e) {
                System.out.println("⚠️ Firebase Default Credentials fehlgeschlagen, verwende lokale Speicherung");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ℹ️ Firebase-Bibliotheken nicht gefunden, verwende lokale Speicherung");
        }
        
        // Fallback zu lokaler Speicherung
        initializeLocal();
    }
    
    private static void ensureInitialized() {
        if (!initialized) {
            initialize();
        }
    }
    
    // Einheitliche API-Methoden
    
    public static String addDocument(String collection, Map<String, Object> data) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                return FirestoreService.addDocument(collection, data);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Hinzufügen des Dokuments", e);
            }
        } else {
            return LocalDataService.addDocument(collection, data);
        }
    }
    
    public static void setDocument(String collection, String docId, Map<String, Object> data) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                FirestoreService.setDocument(collection, docId, data);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Setzen des Dokuments", e);
            }
        } else {
            LocalDataService.setDocument(collection, docId, data);
        }
    }
    
    public static Map<String, Object> getDocument(String collection, String docId) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                return FirestoreService.getDocument(collection, docId);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Lesen des Dokuments", e);
            }
        } else {
            return LocalDataService.getDocument(collection, docId);
        }
    }
    
    public static List<Map<String, Object>> getAllDocuments(String collection) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                return FirestoreService.getAllDocuments(collection);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Lesen aller Dokumente", e);
            }
        } else {
            return LocalDataService.getAllDocuments(collection);
        }
    }
    
    public static boolean deleteDocument(String collection, String docId) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                FirestoreService.deleteDocument(collection, docId);
                return true;
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Löschen des Dokuments", e);
            }
        } else {
            return LocalDataService.deleteDocument(collection, docId);
        }
    }
    
    public static List<Map<String, Object>> queryDocuments(String collection, String field, Object value) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                return FirestoreService.queryDocuments(collection, field, value);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler bei der Abfrage", e);
            }
        } else {
            return LocalDataService.queryDocuments(collection, field, value);
        }
    }
    
    public static int countDocuments(String collection) throws Exception {
        ensureInitialized();
        
        if (useFirebase) {
            try {
                return FirestoreService.countDocuments(collection);
            } catch (InterruptedException | ExecutionException e) {
                throw new Exception("Firebase-Fehler beim Zählen der Dokumente", e);
            }
        } else {
            return LocalDataService.countDocuments(collection);
        }
    }
    
    // Utility-Methoden
    
    public static boolean isUsingFirebase() {
        return useFirebase;
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
    
    public static String getStorageType() {
        ensureInitialized();
        return useFirebase ? "Firebase Firestore" : "Lokale Datenspeicherung";
    }
    
    /**
     * Wechselt zur lokalen Speicherung (für Offline-Modus)
     */
    public static void switchToLocal() throws IOException {
        LocalDataService.initialize();
        useFirebase = false;
        System.out.println("🔄 Zu lokaler Datenspeicherung gewechselt");
    }
    
    /**
     * Test-Methode zur Überprüfung der Verbindung
     */
    public static boolean testConnection() {
        try {
            ensureInitialized();
            
            // Test mit einem kleinen Dokument
            Map<String, Object> testData = new java.util.HashMap<>();
            testData.put("test", "connection");
            testData.put("timestamp", System.currentTimeMillis());
            
            String docId = addDocument("_test", testData);
            Map<String, Object> retrieved = getDocument("_test", docId);
            deleteDocument("_test", docId);
            
            boolean success = retrieved != null && "connection".equals(retrieved.get("test"));
            System.out.println(success ? "✅ Verbindungstest erfolgreich" : "❌ Verbindungstest fehlgeschlagen");
            return success;
            
        } catch (Exception e) {
            System.err.println("❌ Verbindungstest fehlgeschlagen: " + e.getMessage());
            return false;
        }
    }
}
