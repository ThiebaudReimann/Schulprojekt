import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Lokaler Datenspeicher-Service als Alternative zu Firestore
 * Verwendet Properties-Dateien für die Persistierung (ohne externe Abhängigkeiten)
 * 
 * @author Thiébaud Reimann
 * @version 1.0
 */
public class LocalDataService {
    private static final String DATA_DIR = "local_data";
    private static final AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());
    
    // In-Memory-Cache für bessere Performance
    private static Map<String, Map<String, Properties>> collections = new HashMap<>();
    private static boolean initialized = false;
    
    /**
     * Initialisiert den lokalen Datenspeicher
     */
    public static void initialize() throws IOException {
        if (initialized) return;
        
        // Datenordner erstellen
        Path dataPath = Paths.get(DATA_DIR);
        if (!Files.exists(dataPath)) {
            Files.createDirectories(dataPath);
        }
        
        // Bestehende Daten laden
        loadCollections();
        initialized = true;
        System.out.println("LocalDataService initialisiert. Datenordner: " + dataPath.toAbsolutePath());
    }
    
    /**
     * Fügt ein Dokument in eine Collection ein (mit automatischer ID)
     */
    public static String addDocument(String collection, Map<String, Object> data) throws IOException {
        ensureInitialized();
        
        String docId = generateId();
        setDocument(collection, docId, data);
        return docId;
    }
    
    /**
     * Speichert oder überschreibt ein Dokument unter vorgegebener ID
     */
    public static void setDocument(String collection, String docId, Map<String, Object> data) throws IOException {
        ensureInitialized();
        
        // Collection erstellen falls nicht vorhanden
        collections.putIfAbsent(collection, new HashMap<>());
        
        // Properties-Objekt erstellen
        Properties props = new Properties();
        
        // Daten in Properties umwandeln (nur String-Werte)
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                props.setProperty(key, value.toString());
            }
        }
        
        // Metadaten hinzufügen
        props.setProperty("_id", docId);
        props.setProperty("_createdAt", String.valueOf(System.currentTimeMillis()));
        props.setProperty("_updatedAt", String.valueOf(System.currentTimeMillis()));
        
        // Dokument in Memory speichern
        collections.get(collection).put(docId, props);
        
        // Auf Festplatte persistieren
        saveDocument(collection, docId, props);
        
        System.out.println("Dokument gespeichert: " + collection + "/" + docId);
    }
    
    /**
     * Liest ein einzelnes Dokument aus
     */
    public static Map<String, Object> getDocument(String collection, String docId) throws IOException {
        ensureInitialized();
        
        Map<String, Properties> coll = collections.get(collection);
        if (coll == null) {
            return null;
        }
        
        Properties props = coll.get(docId);
        if (props == null) {
            return null;
        }
        
        return propertiesToMap(props);
    }
    
    /**
     * Liest alle Dokumente einer Collection aus
     */
    public static List<Map<String, Object>> getAllDocuments(String collection) throws IOException {
        ensureInitialized();
        
        Map<String, Properties> coll = collections.get(collection);
        if (coll == null) {
            return new ArrayList<>();
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Properties props : coll.values()) {
            result.add(propertiesToMap(props));
        }
        
        return result;
    }
    
    /**
     * Löscht ein Dokument
     */
    public static boolean deleteDocument(String collection, String docId) throws IOException {
        ensureInitialized();
        
        Map<String, Properties> coll = collections.get(collection);
        if (coll == null) {
            return false;
        }
        
        boolean removed = coll.remove(docId) != null;
        if (removed) {
            // Datei von Festplatte löschen
            Path docPath = getDocumentPath(collection, docId);
            if (Files.exists(docPath)) {
                Files.delete(docPath);
            }
            System.out.println("Dokument gelöscht: " + collection + "/" + docId);
        }
        
        return removed;
    }
    
    /**
     * Sucht Dokumente basierend auf einem Feld-Wert-Paar
     */
    public static List<Map<String, Object>> queryDocuments(String collection, String field, Object value) throws IOException {
        ensureInitialized();
        
        List<Map<String, Object>> results = new ArrayList<>();
        List<Map<String, Object>> allDocs = getAllDocuments(collection);
        
        String searchValue = value != null ? value.toString() : null;
        
        for (Map<String, Object> doc : allDocs) {
            Object fieldValue = doc.get(field);
            String fieldString = fieldValue != null ? fieldValue.toString() : null;
            
            if (Objects.equals(fieldString, searchValue)) {
                results.add(doc);
            }
        }
        
        return results;
    }
    
    /**
     * Löscht eine komplette Collection
     */
    public static boolean deleteCollection(String collection) throws IOException {
        ensureInitialized();
        
        Map<String, Properties> coll = collections.remove(collection);
        if (coll != null) {
            // Ordner von Festplatte löschen
            Path collectionPath = getCollectionPath(collection);
            if (Files.exists(collectionPath)) {
                Files.walk(collectionPath)
                     .sorted(Comparator.reverseOrder())
                     .map(Path::toFile)
                     .forEach(File::delete);
            }
            System.out.println("Collection gelöscht: " + collection);
            return true;
        }
        
        return false;
    }
    
    /**
     * Gibt alle verfügbaren Collection-Namen zurück
     */
    public static Set<String> getCollectionNames() {
        ensureInitialized();
        return new HashSet<>(collections.keySet());
    }
    
    /**
     * Zählt Dokumente in einer Collection
     */
    public static int countDocuments(String collection) {
        ensureInitialized();
        
        Map<String, Properties> coll = collections.get(collection);
        return coll != null ? coll.size() : 0;
    }
    
    // Private Helper-Methoden
    
    private static void ensureInitialized() {
        if (!initialized) {
            try {
                initialize();
            } catch (IOException e) {
                throw new RuntimeException("Fehler bei der Initialisierung des LocalDataService", e);
            }
        }
    }
    
    private static String generateId() {
        return "doc_" + idCounter.incrementAndGet();
    }
    
    private static void loadCollections() throws IOException {
        Path dataPath = Paths.get(DATA_DIR);
        if (!Files.exists(dataPath)) {
            return;
        }
        
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dataPath)) {
            for (Path collectionDir : dirStream) {
                if (Files.isDirectory(collectionDir)) {
                    String collectionName = collectionDir.getFileName().toString();
                    loadCollection(collectionName);
                }
            }
        }
        
        System.out.println("Daten geladen: " + collections.size() + " Collections");
    }
    
    private static void loadCollection(String collectionName) throws IOException {
        Path collectionPath = getCollectionPath(collectionName);
        if (!Files.exists(collectionPath)) {
            return;
        }
        
        Map<String, Properties> collectionMap = new HashMap<>();
        
        try (DirectoryStream<Path> docStream = Files.newDirectoryStream(collectionPath, "*.properties")) {
            for (Path docFile : docStream) {
                String fileName = docFile.getFileName().toString();
                String docId = fileName.substring(0, fileName.lastIndexOf('.'));
                
                Properties props = new Properties();
                try (InputStream is = Files.newInputStream(docFile)) {
                    props.load(is);
                    collectionMap.put(docId, props);
                }
            }
        }
        
        collections.put(collectionName, collectionMap);
    }
    
    private static void saveDocument(String collection, String docId, Properties props) throws IOException {
        Path collectionPath = getCollectionPath(collection);
        if (!Files.exists(collectionPath)) {
            Files.createDirectories(collectionPath);
        }
        
        Path docPath = getDocumentPath(collection, docId);
        try (OutputStream os = Files.newOutputStream(docPath)) {
            props.store(os, "Document: " + collection + "/" + docId);
        }
    }
    
    private static Path getCollectionPath(String collection) {
        return Paths.get(DATA_DIR, collection);
    }
    
    private static Path getDocumentPath(String collection, String docId) {
        return getCollectionPath(collection).resolve(docId + ".properties");
    }
    
    private static Map<String, Object> propertiesToMap(Properties props) {
        Map<String, Object> map = new HashMap<>();
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }
        return map;
    }
}
