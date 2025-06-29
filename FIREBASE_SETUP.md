# Firebase Firestore Setup Anleitung

## 1. Firebase Projekt erstellen

1. Gehen Sie zu https://console.firebase.google.com/
2. Klicken Sie auf "Projekt hinzufügen"
3. Geben Sie einen Projektnamen ein (z.B. "meine-app")
4. Folgen Sie den Setup-Schritten

## 2. Firestore Datenbank aktivieren

1. In der Firebase Console, gehen Sie zu "Firestore Database"
2. Klicken Sie auf "Datenbank erstellen"
3. Wählen Sie "Im Testmodus starten" (für Entwicklung)
4. Wählen Sie eine Region (z.B. europe-west3)

## 3. Service Account Key erstellen

1. Gehen Sie zu "Projekteinstellungen" (Zahnrad-Symbol)
2. Wählen Sie den Tab "Dienstkonten"
3. Klicken Sie auf "Neuen privaten Schlüssel generieren"
4. Speichern Sie die JSON-Datei als "firebase-service-account.json" in Ihrem Projektordner

## 4. Firebase Admin SDK JAR-Dateien herunterladen

Da Sie BlueJ verwenden, müssen Sie die JAR-Dateien manuell herunterladen:

### Erforderliche JAR-Dateien:
1. firebase-admin-9.2.0.jar
2. google-cloud-firestore-3.15.9.jar
3. google-auth-library-oauth2-http-1.19.0.jar
4. google-auth-library-credentials-1.19.0.jar
5. google-cloud-core-2.21.1.jar
6. google-api-client-2.2.0.jar
7. google-http-client-1.42.3.jar
8. google-http-client-gson-1.42.3.jar
9. gson-2.10.1.jar
10. guava-32.1.2-jre.jar

### Download-Links (Maven Central):
- Gehen Sie zu https://search.maven.org/
- Suchen Sie nach jeder JAR-Datei und laden Sie sie herunter
- Speichern Sie alle JAR-Dateien in Ihrem lib/ Ordner

## 5. JAR-Dateien in BlueJ hinzufügen

1. Öffnen Sie BlueJ
2. Gehen Sie zu "Werkzeuge" > "Einstellungen"
3. Wählen Sie "Bibliotheken"
4. Klicken Sie auf "Hinzufügen" und wählen Sie alle JAR-Dateien aus

## 6. Alternative: Maven Dependencies (falls Sie Maven verwenden)

```xml
<dependencies>
    <dependency>
        <groupId>com.google.firebase</groupId>
        <artifactId>firebase-admin</artifactId>
        <version>9.2.0</version>
    </dependency>
    <dependency>
        <groupId>com.google.cloud</groupId>
        <artifactId>google-cloud-firestore</artifactId>
        <version>3.15.9</version>
    </dependency>
</dependencies>
```

## 7. Verwendung im Code

```java
// Initialisierung
try {
    FirestoreService.initialize("firebase-service-account.json", null);
} catch (IOException e) {
    e.printStackTrace();
}

// Dokument hinzufügen
Map<String, Object> data = new HashMap<>();
data.put("name", "Test");
data.put("email", "test@example.com");

try {
    String docId = FirestoreService.addDocument("users", data);
    System.out.println("Dokument erstellt mit ID: " + docId);
} catch (Exception e) {
    e.printStackTrace();
}
```

## 8. Firestore-Regeln (für Produktion)

In der Firebase Console unter "Firestore Database" > "Regeln":

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Erlaubt Lese-/Schreibzugriff nur für authentifizierte Benutzer
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 9. Debugging-Tipps

- Überprüfen Sie die Firebase Console auf Fehler
- Stellen Sie sicher, dass die service-account.json im richtigen Pfad liegt
- Prüfen Sie, ob alle JAR-Dateien korrekt geladen wurden
- Aktivieren Sie Logging für detaillierte Fehlermeldungen

## Wichtige Hinweise:

1. **Service Account Key sicher aufbewahren** - Niemals in Git committen!
2. **Firestore-Regeln** - In Produktion sichere Regeln verwenden
3. **Netzwerkverbindung** - Firestore benötigt Internetverbindung
4. **Kosten** - Firestore hat ein kostenloses Kontingent, darüber hinaus kostenpflichtig

Bei Problemen können Sie auch die lokale Alternative (LocalDataService) verwenden, die bereits funktioniert.
