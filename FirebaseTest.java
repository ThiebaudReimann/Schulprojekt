/**
 * Test-Klasse für Firebase Firestore Verbindung
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */

import java.util.*;

public class FirebaseTest {
    
    public static void main(String[] args) {
        System.out.println("🚀 Firebase Firestore Test gestartet...\n");
        
        // Test 1: Automatische Initialisierung
        testAutoInitialization();
        
        // Test 2: Verbindungstest
        testConnection();
        
        // Test 3: CRUD-Operationen
        testCrudOperations();
        
        System.out.println("\n✅ Alle Tests abgeschlossen!");
    }
    
    private static void testAutoInitialization() {
        System.out.println("📋 Test 1: Automatische Initialisierung");
        try {
            UniversalDataService.initialize();
            System.out.println("   ✅ Initialisierung erfolgreich");
            System.out.println("   📊 Verwendeter Speicher: " + UniversalDataService.getStorageType());
        } catch (Exception e) {
            System.err.println("   ❌ Initialisierung fehlgeschlagen: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testConnection() {
        System.out.println("📋 Test 2: Verbindungstest");
        try {
            boolean connected = UniversalDataService.testConnection();
            if (connected) {
                System.out.println("   ✅ Verbindung erfolgreich");
            } else {
                System.out.println("   ❌ Verbindung fehlgeschlagen");
            }
        } catch (Exception e) {
            System.err.println("   ❌ Verbindungstest-Fehler: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testCrudOperations() {
        System.out.println("📋 Test 3: CRUD-Operationen");
        
        try {
            // CREATE - Dokument hinzufügen
            System.out.println("   📝 CREATE: Dokument hinzufügen...");
            Map<String, Object> testUser = new HashMap<>();
            testUser.put("name", "Test Benutzer");
            testUser.put("email", "test@example.com");
            testUser.put("created", new Date().toString());
            
            String docId = UniversalDataService.addDocument("test_users", testUser);
            System.out.println("   ✅ Dokument erstellt mit ID: " + docId);
            
            // READ - Dokument lesen
            System.out.println("   📖 READ: Dokument lesen...");
            Map<String, Object> retrievedUser = UniversalDataService.getDocument("test_users", docId);
            if (retrievedUser != null) {
                System.out.println("   ✅ Dokument gelesen: " + retrievedUser.get("name"));
            } else {
                System.out.println("   ❌ Dokument nicht gefunden");
                return;
            }
            
            // UPDATE - Dokument aktualisieren
            System.out.println("   🔄 UPDATE: Dokument aktualisieren...");
            testUser.put("name", "Aktualisierter Test Benutzer");
            testUser.put("updated", new Date().toString());
            UniversalDataService.setDocument("test_users", docId, testUser);
            System.out.println("   ✅ Dokument aktualisiert");
            
            // READ AGAIN - Aktualisierung prüfen
            Map<String, Object> updatedUser = UniversalDataService.getDocument("test_users", docId);
            if (updatedUser != null && updatedUser.get("name").toString().contains("Aktualisiert")) {
                System.out.println("   ✅ Aktualisierung bestätigt");
            } else {
                System.out.println("   ❌ Aktualisierung nicht bestätigt");
            }
            
            // QUERY - Dokument suchen
            System.out.println("   🔍 QUERY: Dokument suchen...");
            List<Map<String, Object>> found = UniversalDataService.queryDocuments("test_users", "email", "test@example.com");
            System.out.println("   ✅ Gefundene Dokumente: " + found.size());
            
            // COUNT - Dokumente zählen
            System.out.println("   🔢 COUNT: Dokumente zählen...");
            int count = UniversalDataService.countDocuments("test_users");
            System.out.println("   ✅ Anzahl Dokumente in test_users: " + count);
            
            // GET ALL - Alle Dokumente abrufen
            System.out.println("   📚 GET ALL: Alle Dokumente abrufen...");
            List<Map<String, Object>> allUsers = UniversalDataService.getAllDocuments("test_users");
            System.out.println("   ✅ Alle Dokumente abgerufen: " + allUsers.size());
            
            // DELETE - Dokument löschen
            System.out.println("   🗑️ DELETE: Dokument löschen...");
            boolean deleted = UniversalDataService.deleteDocument("test_users", docId);
            if (deleted) {
                System.out.println("   ✅ Dokument gelöscht");
            } else {
                System.out.println("   ❌ Dokument konnte nicht gelöscht werden");
            }
            
            // VERIFY DELETE - Löschung bestätigen
            Map<String, Object> deletedUser = UniversalDataService.getDocument("test_users", docId);
            if (deletedUser == null) {
                System.out.println("   ✅ Löschung bestätigt");
            } else {
                System.out.println("   ❌ Dokument wurde nicht gelöscht");
            }
            
        } catch (Exception e) {
            System.err.println("   ❌ CRUD-Test fehlgeschlagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Manueller Test mit Firebase Service Account
     * Rufen Sie diese Methode auf, wenn Sie einen Service Account Key haben
     */
    public static void testWithServiceAccount(String serviceAccountPath) {
        System.out.println("🔑 Test mit Service Account: " + serviceAccountPath);
        try {
            UniversalDataService.initializeWithFirebase(serviceAccountPath, null);
            System.out.println("✅ Firebase mit Service Account initialisiert");
            
            testCrudOperations();
            
        } catch (Exception e) {
            System.err.println("❌ Service Account Test fehlgeschlagen: " + e.getMessage());
        }
    }
    
    /**
     * Performance-Test
     */
    public static void performanceTest(int numOperations) {
        System.out.println("⚡ Performance Test mit " + numOperations + " Operationen...");
        
        try {
            UniversalDataService.initialize();
            
            long startTime = System.currentTimeMillis();
            
            List<String> docIds = new ArrayList<>();
            
            // Bulk Insert
            for (int i = 0; i < numOperations; i++) {
                Map<String, Object> doc = new HashMap<>();
                doc.put("index", i);
                doc.put("data", "Performance Test Document " + i);
                doc.put("timestamp", System.currentTimeMillis());
                
                String docId = UniversalDataService.addDocument("perf_test", doc);
                docIds.add(docId);
            }
            
            long insertTime = System.currentTimeMillis();
            System.out.println("✅ " + numOperations + " Dokumente eingefügt in " + (insertTime - startTime) + "ms");
            
            // Bulk Read
            for (String docId : docIds) {
                UniversalDataService.getDocument("perf_test", docId);
            }
            
            long readTime = System.currentTimeMillis();
            System.out.println("✅ " + numOperations + " Dokumente gelesen in " + (readTime - insertTime) + "ms");
            
            // Cleanup
            for (String docId : docIds) {
                UniversalDataService.deleteDocument("perf_test", docId);
            }
            
            long deleteTime = System.currentTimeMillis();
            System.out.println("✅ " + numOperations + " Dokumente gelöscht in " + (deleteTime - readTime) + "ms");
            
            long totalTime = deleteTime - startTime;
            System.out.println("📊 Gesamtzeit: " + totalTime + "ms (" + (totalTime / numOperations) + "ms pro Operation)");
            
        } catch (Exception e) {
            System.err.println("❌ Performance Test fehlgeschlagen: " + e.getMessage());
        }
    }
}
