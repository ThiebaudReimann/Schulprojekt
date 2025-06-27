
/**
 * Write a description of class FirebaseFirestoreService here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    private static Firestore db;

    /**
     * Initialisiert Firestore einmalig. 
     * @param serviceAccountPath Pfad zur Service-Account-JSON
     * @param databaseUrl        deine Datenbank-URL (https://<PROJECT_ID>.firebaseio.com)
     */
    public static void initialize(String serviceAccountPath, String databaseUrl)
            throws IOException {
        FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(databaseUrl)
            .build();

        // Nur einmal initialisieren
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        db = FirestoreClient.getFirestore();
    }

    /** Gibt das Firestore-Objekt zurück */
    public static Firestore getDb() {
        if (db == null) {
            throw new IllegalStateException("Firestore nicht initialisiert. Ruf initialize(...) auf.");
        }
        return db;
    }

    /** Fügt ein Dokument in eine Collection ein (mit eigener ID) */
    public static String addDocument(String collection, Map<String,Object> data)
            throws InterruptedException, ExecutionException {
        CollectionReference colRef = getDb().collection(collection);
        DocumentReference docRef = colRef.add(data).get();  // blockiert bis fertig
        return docRef.getId();
    }

    /** Speichert oder überschreibt ein Dokument unter vorgegebener ID */
    public static WriteResult setDocument(String collection, String docId, Map<String,Object> data)
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        return docRef.set(data).get();
    }

    /** Liest ein einzelnes Dokument aus */
    public static Map<String,Object> getDocument(String collection, String docId)
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        return docRef.get().get().getData();
    }

    /** Liest alle Dokumente einer Collection aus */
    public static List<Map<String,Object>> getAllDocuments(String collection)
            throws InterruptedException, ExecutionException {
        QuerySnapshot snap = getDb().collection(collection).get().get();
        return snap.toObjects(Map.class);
    }

    /** Löscht ein Dokument */
    public static WriteResult deleteDocument(String collection, String docId)
            throws InterruptedException, ExecutionException {
        DocumentReference docRef = getDb().collection(collection).document(docId);
        return docRef.delete().get();
    }
}
