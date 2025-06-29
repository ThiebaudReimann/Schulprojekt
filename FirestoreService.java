
/**
 * Firebase Firestore Service für echte Cloud-Datenbankanbindung
 * Benötigt Firebase Admin SDK Bibliotheken
 *
 * @author Thiébaud Reimann
 * @version 1.0
 */

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    private static Firestore db;
    private static boolean initialized = false;

    /**
     * Initialisiert Firestore mit Service Account Key
     * @param serviceAccountPath Pfad zur Service-Account-JSON-Datei
     * @param databaseUrl Firebase Database URL (optional, kann null sein)
     */
    public static void initialize(String serviceAccountPath, String databaseUrl) throws IOException {
        if (initialized) {
            return;
        }

        try {
            InputStream serviceAccount = new FileInputStream(serviceAccountPath);
            
            FirebaseOptions.Builder optionsBuilder = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount));
            
            if (databaseUrl != null && !databaseUrl.isEmpty()) {
                optionsBuilder.setDatabaseUrl(databaseUrl);
            }
            
            FirebaseOptions options = optionsBuilder.build();

            // Nur einmal initialisieren
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase App initialisiert");
            }
            
            db = FirestoreClient.getFirestore();
            initialized = true;
            System.out.println("Firestore erfolgreich initialisiert");
            
        } catch (Exception e) {
            System.err.println("Fehler bei der Firestore-Initialisierung: " + e.getMessage());
            throw new IOException("Firestore-Initialisierung fehlgeschlagen", e);
        }
    }
    
    /**
     * Alternative Initialisierung mit Standard-Credentials
     * Verwendet Application Default Credentials (ADC)
     */
    public static void initializeWithDefaultCredentials() throws IOException {
        if (initialized) {
            return;
        }

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase App mit Default Credentials initialisiert");
            }
            
            db = FirestoreClient.getFirestore();
            initialized = true;
            System.out.println("Firestore mit Default Credentials erfolgreich initialisiert");
            
        } catch (Exception e) {
            System.err.println("Fehler bei der Firestore-Initialisierung: " + e.getMessage());
            throw new IOException("Firestore-Initialisierung fehlgeschlagen", e);
        }
    }

    /** Gibt das Firestore-Objekt zurück */
    public static Firestore getDb() {
        if (db == null) {
            throw new IllegalStateException("Firestore nicht initialisiert. Rufen Sie initialize() auf.");
        }
        return db;
    }

    /** Fügt ein Dokument in eine Collection ein (mit automatischer ID) */
    public static String addDocument(String collection, Map<String, Object> data) 
            throws InterruptedException, ExecutionException {
        CollectionReference colRef = getDb().collection(collection);
        
        // Zeitstempel hinzufügen
        data = new HashMap<>(data);
        data.put("createdAt", FieldValue.serverTimestamp());
        data.put("updatedAt", FieldValue.serverTimestamp());
        
        DocumentReference docRef = colRef.add(data).get();
        String docId = docRef.getId();
        System.out.println("Dokument hinzugefügt: " + collection + "/" + docId);
        return docId;
    }

    /** Speichert oder überschreibt ein Dokument unter vorgegebener ID */
    public static WriteResult setDocument(String collection, String docId, Map<String, Object> data) 
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        
        // Zeitstempel hinzufügen
        data = new HashMap<>(data);
        data.put("updatedAt", FieldValue.serverTimestamp());
        
        WriteResult result = docRef.set(data).get();
        System.out.println("Dokument gesetzt: " + collection + "/" + docId);
        return result;
    }

    /** Liest ein einzelnes Dokument aus */
    public static Map<String, Object> getDocument(String collection, String docId) 
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        DocumentSnapshot document = docRef.get().get();
        
        if (document.exists()) {
            Map<String, Object> data = document.getData();
            if (data != null) {
                data.put("_id", document.getId());
                return data;
            }
        }
        return null;
    }

    /** Liest alle Dokumente einer Collection aus */
    public static List<Map<String, Object>> getAllDocuments(String collection) 
            throws InterruptedException, ExecutionException {
        QuerySnapshot querySnapshot = getDb().collection(collection).get().get();
        
        List<Map<String, Object>> documents = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Map<String, Object> data = document.getData();
            data.put("_id", document.getId());
            documents.add(data);
        }
        
        System.out.println("Gefundene Dokumente in " + collection + ": " + documents.size());
        return documents;
    }

    /** Löscht ein Dokument */
    public static WriteResult deleteDocument(String collection, String docId) 
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        WriteResult result = docRef.delete().get();
        System.out.println("Dokument gelöscht: " + collection + "/" + docId);
        return result;
    }
    
    /** Sucht Dokumente basierend auf einem Feld-Wert-Paar */
    public static List<Map<String, Object>> queryDocuments(String collection, String field, Object value) 
            throws InterruptedException, ExecutionException {
        Query query = getDb().collection(collection).whereEqualTo(field, value);
        QuerySnapshot querySnapshot = query.get().get();
        
        List<Map<String, Object>> documents = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Map<String, Object> data = document.getData();
            data.put("_id", document.getId());
            documents.add(data);
        }
        
        System.out.println("Query-Ergebnisse für " + field + "=" + value + ": " + documents.size());
        return documents;
    }
    
    /** Erweiterte Query mit mehreren Bedingungen */
    public static List<Map<String, Object>> queryDocumentsAdvanced(String collection, 
            Map<String, Object> conditions) throws InterruptedException, ExecutionException {
        Query query = getDb().collection(collection);
        
        // Bedingungen hinzufügen
        for (Map.Entry<String, Object> condition : conditions.entrySet()) {
            query = query.whereEqualTo(condition.getKey(), condition.getValue());
        }
        
        QuerySnapshot querySnapshot = query.get().get();
        
        List<Map<String, Object>> documents = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Map<String, Object> data = document.getData();
            data.put("_id", document.getId());
            documents.add(data);
        }
        
        return documents;
    }
    
    /** Löscht eine komplette Collection (Vorsicht!) */
    public static void deleteCollection(String collection) throws InterruptedException, ExecutionException {
        CollectionReference colRef = getDb().collection(collection);
        
        // Alle Dokumente der Collection abrufen
        QuerySnapshot snapshot = colRef.get().get();
        
        // Dokumente in Batches löschen (Firestore Limit: 500 pro Batch)
        WriteBatch batch = getDb().batch();
        int count = 0;
        
        for (QueryDocumentSnapshot document : snapshot) {
            batch.delete(document.getReference());
            count++;
            
            if (count >= 500) {
                batch.commit().get();
                batch = getDb().batch();
                count = 0;
            }
        }
        
        if (count > 0) {
            batch.commit().get();
        }
        
        System.out.println("Collection gelöscht: " + collection);
    }
    
    /** Zählt Dokumente in einer Collection */
    public static int countDocuments(String collection) throws InterruptedException, ExecutionException {
        QuerySnapshot snapshot = getDb().collection(collection).get().get();
        return snapshot.size();
    }
    
    /** Update einzelner Felder eines Dokuments */
    public static WriteResult updateDocument(String collection, String docId, Map<String, Object> updates) 
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        
        // Zeitstempel für Update hinzufügen
        updates = new HashMap<>(updates);
        updates.put("updatedAt", FieldValue.serverTimestamp());
        
        WriteResult result = docRef.update(updates).get();
        System.out.println("Dokument aktualisiert: " + collection + "/" + docId);
        return result;
    }
    
    /** Prüft ob Firestore initialisiert ist */
    public static boolean isInitialized() {
        return initialized;
    }
}
