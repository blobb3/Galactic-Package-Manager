# Galactic Package Manager (GPM)

## Lernjournal: Implementierung einer Full-Stack DevOps-Anwendung

Dieses Lernjournal dokumentiert die Entwicklung des Galactic Package Managers (GPM), einer Full-Stack-Anwendung mit Spring Boot Backend und JavaScript Frontend. Das Projekt simuliert einen fiktiven Paketmanager für Weltraumtechnologien im Star Wars-Universum - quasi npm/Gradle für die Galaxis.

## Inhaltsverzeichnis

1. [Projektübersicht](#projektübersicht)
2. [Technologiestack](#technologiestack)
3. [Projektstruktur einrichten](#projektstruktur-einrichten)
4. [Backend implementieren](#backend-implementieren)
5. [Frontend entwickeln](#frontend-entwickeln)
6. [Integration und Tests](#integration-und-tests)
7. [UI-Tests mit Selenium und Cypress](#ui-tests-mit-selenium-und-cypress)
8. [JaCoCo-Testabdeckungsbericht](#jacoco-testabdeckungsbericht)
9. [SonarQube-Analyse](#sonarqube-analyse)
10. [Die Macht der Container: Vom Dev- zum Ops-System](#die-macht-der-container-vom-dev-planeten-zum-ops-system)
11. [Continuous Integration mit Jenkins](#continuous-integration-mit-jenkins)
12. [Docker-Deployment und Render-Integration](#docker-deployment-und-render-integration)
13. [Abschluss](#abschluss)

## Projektübersicht

Der Galactic Package Manager (GPM) ermöglicht Benutzern:
- Durchsuchen verfügbarer galaktischer Technologie-Pakete
- Anzeigen von Paketdetails und Bewertungen
- "Installation" von Paketen durch ein unterhaltsames Mini-Game
- Kompatibilitätsprüfung mit verschiedenen galaktischen Fraktionen

## Technologiestack

**Backend:**
- Spring Boot 3.x
- Java 21
- Gradle
- In-Memory Datenbank (für einfache Demonstration)

**Frontend:**
- HTML, CSS, JavaScript
- particles.js (für animierten Sternenhintergrund)
- chart.js (für Visualisierungen)
- anime.js (für Animationseffekte)

## Projektstruktur einrichten

### Schritt 1: Repository initialisieren

Dies wurde in Github manuell durchgeführt. 

### Schritt 2: Gradle-Projekt mit Spring Boot initialisieren

Es wurde Ctrl+Shift+P: "Spring Initializr: Create a Gradle Project...")
- Springboot-Version: 3.3.11
-java
- ch.zhaw.devops.gpm
- Artefakt ID: galactic-pm
- Jar
- Java Version 21

### Schritt 2: Gradle-Projekt mit Spring Boot initialisieren

Für die Erstellung des Spring Boot-Projekts wurde Visual Studio Code mit der Spring Boot Extension genutzt. Durch Drücken von `Ctrl+Shift+P` und Auswahl von "Spring Initializr: Create a Gradle Project..." öffnet sich der Projekt-Wizard, in dem folgende Parameter konfiguriert wurden:

- **Spring Boot Version:** 3.3.11 
- **Programmiersprache:** Java
- **Group ID:** ch.zhaw.devops.gpm (eindeutige Paket-Identifier im Maven-Format)
- **Artifact ID:** galactic-pm (Name des Projekts/der kompilierten Anwendung)
- **Packaging Type:** JAR (ausführbare Java-Anwendung)
- **Abhängigkeiten:** Spring Web (REST-API-Unterstützung)

Nach Bestätigung dieser Einstellungen (und der Auswahl des Backend-Ordners) generiert Spring Initializr automatisch die Grundstruktur des Projekts inklusive der erforderlichen Gradle-Konfiguration und Verzeichnisstruktur. Die resultierende `build.gradle`-Datei enthält bereits alle notwendigen Abhängigkeiten und Plugins für die Entwicklung der Galactic Package Manager-Backend-Komponente.

### Schritt 3: Projektstruktur für Monorepo einrichten

 Frontend-Teil einrichten:

1. In das `frontend`-Verzeichnis wechseln:
   ```bash
   cd frontend
   ```

2. Ein npm-Projekt initialisieren:
   ```bash
   npm init -y
   ```

3. Die benötigten Abhängigkeiten installieren:
   ```bash
   npm install particles.js chart.js animejs --save
   ```

4. Unterordner für CSS und JavaScript erstellen:
   ```bash
   mkdir css; mkdir js
   ```

5. Erste index.html-Datei im frontend-Verzeichnis erstellen

Vorest bleibt sie leer.

### Schritt 4: .gitignore erstellen und package.json erweitern

Anonsten gibt es vzu veile commits, gitignore schränkt es ein , auch da frontend und backend in einem repository
das packagejson 


---

## Backend implementieren

### Schritt 1: Datenmodell erstellen
Das Datenmodell `GalacticPackage.java` definiert die grundlegende Datenstruktur des Galactic Package Managers mit Feldern wie Name, Beschreibung, Autor, Version und Kompatibilität. Mit der `@Entity`-Annotation wird die Klasse als JPA-Entity markiert, was die automatische Persistierung in der Datenbank ermöglicht, während `@Id` und `@GeneratedValue` die Primärschlüsselverwaltung übernehmen.

### Schritt 2: Repository-Interface erstellen
Das Interface `PackageRepository.java` erweitert `JpaRepository` und ermöglicht dadurch den Datenbankzugriff mit vorgefertigten CRUD-Operationen ohne eigene SQL-Implementierungen. Zusätzlich wurden spezifische Suchmethoden wie `findByNameContainingIgnoreCase()`, `findByCompatibility()` und `findByCategory()` deklariert, welche Spring Data JPA automatisch in entsprechende SQL-Abfragen übersetzt.

### Schritt 3: REST-Controller implementieren
Der `PackageController.java` bildet das "Herzstück" (wie jeder Controller) der API und stellt mit entsprechenden `@RequestMapping`-Annotationen die REST-Endpunkte für Frontend-Anfragen bereit. Über Methoden wie `getAllPackages()`, `getPackageById()`, `searchPackages()` und weitere CRUD-Operationen wird die komplette Kommunikation zwischen Frontend und Backend ermöglicht, wobei die `@CrossOrigin`-Annotation den Zugriff von verschiedenen Domains erlaubt.

### Schritt 4: Demo-Daten für Testbetrieb erstellen
Die `DataInitializer.java`-Klasse ist mit `@Component` annotiert und implementiert `CommandLineRunner`, wodurch sie beim Anwendungsstart automatisch ausgeführt wird. Sie befüllt die Datenbank mit fiktiven Star Wars-Packages wie "hyperspace-navigation" und "imperial-scanner", um die Funktionalität ohne manuelles Anlegen von Testdaten demonstrieren zu können.

### Schritt 5: Spring Boot-Anwendung starten
```bash
./gradlew bootRun
```

Beim Start der Anwendung auf http://localhost:8080 erscheint zunächst der klassische Whitelabel Error - aber dies ist ein gewollter Fehler, denn er zeigt an, dass der Server läuft und auf API-Anfragen wartet. Um tatsächliche Daten zu sehen, muss nun entweder ein API-Client wie Postman verwendet werden (z.B. GET auf `/api/packages`) oder das Frontend implementiert werden, das diese Endpunkte anspricht.

<img src="images/Bild1.png" alt="DevOpsLogo" width="157" height="80">

### 6: Testen der API mit Postman

Bevor wir mit der Frontend-Entwicklung beginnen, soll nun dennoch die Backend-API auf korrekte Funktionalität geprüft werden. Postman bietet hierfür eine ideale Testumgebung, da es die direkte Interaktion mit den API-Endpunkten ohne Frontend-Code ermöglicht. Die drei grundlegenden HTTP-Methoden wurden erfolgreich getestet:

- **GET**: Abrufen aller Pakete und Überprüfung, ob die Demo-Daten korrekt initialisiert wurden

<img src="images/Bild2.png" alt="DevOpsLogo" width="157" height="80">

- **POST**: Erstellen eines neuen "force-calculations"-Pakets und Verifizierung der automatischen ID-Generierung

<img src="images/Bild3.png" alt="DevOpsLogo" width="157" height="80">

- **PUT**: Aktualisierung eines bestehenden Pakets mit verbesserten Eigenschaften, was die vollständige Datenpersistenz bestätigt
  
<img src="images/Bild4.png" alt="DevOpsLogo" width="157" height="80">

Diese API-Tests bilden nun das Fundament für die Frontend-Entwicklung, da sie sicherstellen, dass die Datenmanipulation wie erwartet funktioniert. Das "Whitelabel Error"-Problem soll nun bald behoben werden..

---

## Frontend entwickeln

### Frontend entwickeln

### Schritt 1: Frontend-Verzeichnis einrichten
Wurde bereits im vorherigen Abschnitt erledigt, wobei die grundlegende Ordnerstruktur und npm-Initialisierung durchgeführt wurden.

### Schritt 2: HTML-Grundstruktur erstellen
Die `index.html` bildet das "Skelett der Anwendung" mit mehreren Hauptbereichen: einem Container für den Sternenhintergrund (`particles-js`), einem Header mit Logo und Suchfunktion, dem Hauptbereich für Paketlisten und -details sowie einem Spielbereich für die Installation. Die Struktur folgt einem Single-Page-Application-Ansatz, bei dem verschiedene Bereiche durch JavaScript ein- und ausgeblendet werden.

### Schritt 3: CSS für stilvolle Galaxis-Oberfläche
Das CSS in `style.css` implementiert ein Star Wars-inspiriertes Farbschema mit dunklen Hintergründen und kontrastierenden Akzentfarben. Besonders hervorzuheben sind die responsiven Kartenlayouts mit CSS Grid, die Animation von UI-Elementen und die fraktionsspezifischen Farbcodierungen (Republik, Imperium, Neutral). Das Design ist vollständig responsiv und passt sich verschiedenen Bildschirmgrössen an.

### Schritt 4: JavaScript-Dateien für Frontend-Logik erstellen

1. **Particles.js Konfiguration:**
   Die `particles-config.js` konfiguriert den animierten Sternenhintergrund mit interaktiven Partikeln, die auf Mausbewegungen reagieren. Die Parameter bestimmen unter anderem Grösse, Anzahl, Bewegungsmuster und Transparenz der "Sterne", was die räumliche Atmosphäre der Anwendung erzeugt.

2. **API Service für Backend-Kommunikation:**
   Die `ApiService`-Klasse kapselt alle HTTP-Requests zum Backend mit async/await-Funktionen. Sie implementiert die CRUD-Funktionalität und bietet Methoden wie `getAllPackages()`, `searchPackages()` und `getPackagesByCompatibility()`, wodurch der Rest der Anwendung von den Details der API-Kommunikation abstrahiert wird.

3. **Package Renderer für UI-Updates:**
   Der `PackageRenderer` ist für die dynamische Aktualisierung der UI-Elemente zuständig. Er rendert die Paketliste, zeigt Detailansichten an und orchestriert die Übergänge zwischen verschiedenen Ansichten mit Anime.js-Animationen. Diese Komponente dient als Controller im clientseitigen MVC-Muster.

4. **Mini-Spiel für die Paket-Installation:**
   Die `InstallationGame`-Klasse implementiert ein interaktives Minispiel, bei dem der Benutzer auf auftauchende TIE-Fighter klicken muss, um den Installationsfortschritt zu beschleunigen. Die Spielmechanik verwendet Anime.js für flüssige Animationen und einen Fortschrittsbalken, der den Installationsstatus visualisiert.

5. **Hauptdatei für Komponenten-Initialisierung:**
   Die `main.js` orchestriert das Zusammenspiel aller Komponenten, initialisiert die API-Verbindung und registriert Event-Listener. Zudem implementiert sie die anfängliche Willkommensanimation mit gestaffelten Effekten, die die Benutzererfahrung beim ersten Laden der Anwendung verbessern.

### Schritt 5: Frontend starten
Die Frontend-Anwendung wird mit `npx serve` gestartet, wodurch ein lokaler Webserver auf Port 3000 die statischen Dateien bereitstellt. 

```bash
cd frontend
npx serve
```

> Die Anwendung ist nun unter http://localhost:3000 verfügbar, siehe:
> 
> <img src="images/Bild5.png" alt="DevOpsLogo" width="157" height="80">
> 
> Wenn man auf "Install Package" klickt, erscheint dann ein kleines Mini-Game:
> 
> <img src="images/Bild6.png" alt="DevOpsLogo" width="157" height="80">

Für diese Lösung ist keine komplexe Server-Konfiguration erforderlich. Der Frontend-Client kommuniziert über AJAX-Aufrufe mit dem Backend auf Port 8080 und nutzt Cross-Origin-Resource-Sharing (CORS), das im Backend explizit erlaubt wurde.

> Mit Frontend und Backend als separate Komponenten entwickelt, stehen wir nun vor der klassischen DevOps-Herausforderung: Die Integration beider Systeme zu einer funktionierenden Einheit.

---

## Integration und Tests

Nachdem beide Systeme unabhängig voneinander getestet wurden, ist es nun an der Zeit, die gesamte Anwendung als Einheit zu betrachten und sie einer Reihe integrierter Tests zu unterziehen – denn nur so kann sichergestellt werden, dass der Galactic Package Manager nicht an einem unerwarteten Fehler wie ein Todesstern an einer ungesicherten Belüftungsöffnung scheitert.

## Automatisierte Tests

Um eine hohe Codeabdeckung und langfristige Wartbarkeit des Galactic Package Managers zu gewährleisten, wurden verschiedene automatisierte Tests entwickelt. Diese testen die Anwendung auf verschiedenen Ebenen, von einzelnen Klassen bis hin zur Gesamtfunktionalität.

### Schritt 1: Unit-Tests für die Entity-Klasse

Die Datei `GalacticPackageTest.java` testet die Funktionalität der Entitätsklasse durch:
- Überprüfung des Konstruktors und aller Getter-Methoden
- Validierung der Setter-Methoden für die Aktualisierung von Objekteigenschaften
- Sicherstellung, dass alle Felder korrekt initialisiert und abgerufen werden können

Diese grundlegenden Tests stellen sicher, dass das Datenmodell wie erwartet funktioniert und bilden die Basis für alle weiteren Tests.

### Schritt 2: Repository-Tests

Die `PackageRepositoryTest.java` prüft, ob das Repository korrekt mit der Datenbank interagiert:
- Test der benutzerdefinierten Suchmethode `findByNameContainingIgnoreCase()` mit Berücksichtigung der Gross-/Kleinschreibung
- Validierung der Filtermethode `findByCompatibility()` für die verschiedenen Fraktionen
- Überprüfung der Kategoriefiltermethode `findByCategory()`

Diese Tests verwenden `@DataJpaTest` und einen `TestEntityManager`, um eine isolierte Datenbankumgebung zu schaffen, ohne die eigentliche Datenbank zu beeinflussen.

### Schritt 3: Controller-Tests

Der `PackageControllerTest.java` testet die API-Endpunkte durch simulierte HTTP-Anfragen:
- GET-Anfragen zum Abrufen aller Pakete und einzelner Pakete nach ID
- POST-Anfragen zum Erstellen neuer Pakete
- PUT-Anfragen zum Aktualisieren bestehender Pakete
- DELETE-Anfragen zum Entfernen von Paketen
- Überprüfung der korrekten HTTP-Statuscodes und Antwortinhalte

Durch den Einsatz von `@WebMvcTest` und MockMvc können HTTP-Anfragen simuliert werden, ohne einen tatsächlichen Server zu starten, was schnelle und zuverlässige Tests ermöglicht.

### Schritt 4: DataInitializer-Tests

Die `DataInitializerTest.java` verifiziert die korrekte Initialisierung der Demo-Daten:
- Test, ob Demo-Pakete erstellt werden, wenn das Repository leer ist
- Überprüfung, dass keine Pakete erstellt werden, wenn bereits Daten vorhanden sind

Diese Tests stellen sicher, dass der Anwendungsstartprozess korrekt funktioniert und die Testdaten nur bei Bedarf erstellt werden.

### Schritt 5: Integrationstests

Die umfassendste Testdatei `GalacticPackageManagerIntegrationTest.java` testet das Zusammenspiel aller Komponenten:
- Vollständige CRUD-Operationen über die tatsächliche REST-API
- Suchfunktionalität mit realen HTTP-Anfragen
- Filterfunktionen nach Kompatibilität
- Ende-zu-Ende-Tests mit einem eingebetteten Server

Im Gegensatz zu den Unit-Tests verwendet dieser Test `@SpringBootTest` mit `WebEnvironment.RANDOM_PORT`, um einen echten Server zu starten und eine reale Umgebung zu simulieren.

### Ausführung der Tests

Die Tests können mit Gradle ausgeführt werden:

```bash
./gradlew test
```
Natürlich klappt nicht immer alles wie geplant:

<img src="images/Bild8.png" alt="DevOpsLogo" width="157" height="80">

Die Problematik konnte dann aber behoben werden: 

<img src="images/Bild9.png" alt="DevOpsLogo" width="157" height="80">

Nach der Ausführung wird ein Testbericht generiert, welcher die Codeabdeckung und eventuelle Fehler anzeigt. Eine hohe Testabdeckung ist entscheidend für die Qualitätssicherung und erleichtert zukünftige Änderungen und Erweiterungen.

## UI-Tests mit Selenium und Cypress

UI-Tests sind ein wesentlicher Bestandteil des DevOps-Testprozesses und stellen sicher, dass die Benutzeroberfläche wie erwartet funktioniert. Für den Galactic Package Manager wurden drei verschiedene Test-Frameworks implementiert, um Cross-Browser-Kompatibilität zu gewährleisten und verschiedene Testansätze zu demonstrieren.

### Schritt 1: Selenium Chrome Tests

Selenium WebDriver ermöglicht die Automatisierung von Browser-Interaktionen, wodurch echte Benutzeraktionen simuliert werden können.

```javascript
// test/chrome-test.js (Kernkomponenten)
const { Builder, By, until } = require('selenium-webdriver');
const chrome = require('chromedriver');

// Browser-Instanz erstellen
driver = await new Builder().forBrowser('chrome').build();

// Elemente finden und Interaktionen durchführen
const searchButton = await driver.findElement(By.id('search-button'));
await searchButton.click();

// Screenshot für Debugging-Zwecke erstellen
const image = await driver.takeScreenshot();
fs.writeFileSync(screenshotPath, image, 'base64');
```

**Ausführung:**
```bash
npm run selenium:chrome
```

**Technischer Nutzen:** Selenium Chrome Tests simulieren reale Benutzerinteraktionen in Chrome, ermöglichen komplexe UI-Navigation und bieten detaillierte Fehleranalyse durch Screenshots. Sie sind besonders wertvoll für die Validierung kritischer Geschäftsprozesse.

### Schritt 2: Selenium Firefox Tests

Die gleichen Tests werden für Firefox implementiert, um die browserübergreifende Kompatibilität sicherzustellen.

```javascript
// test/firefox-test.js (Hauptunterschiede)
const firefox = require('geckodriver');
driver = await new Builder().forBrowser('firefox').build();
```

**Ausführung:**
```bash
npm run selenium:firefox
```

**Technischer Nutzen:** Die Firefox-Tests erweitern die Testabdeckung auf mehrere Browser und erhöhen die Sicherheit, dass die Anwendung unabhängig vom verwendeten Browser fehlerfrei funktioniert. Dies ist entscheidend für Webanwendungen, die eine breite Benutzerbasis ansprechen.

### Schritt 3: Cypress End-to-End Tests

Cypress ist ein moderneres Test-Framework, das speziell für Frontend-Tests entwickelt wurde und einige Vorteile gegenüber Selenium bietet.

```javascript
// cypress/e2e/galactic-pm.cy.js (Kernkomponenten)
describe('Galactic Package Manager Tests', () => {
  beforeEach(() => {
    // Vor jedem Test die Anwendung öffnen
    cy.visit('/');
  });

  it('sollte die Suchfunktion anzeigen', () => {
    // Automatisches Warten auf Elemente
    cy.get('#search-input').should('be.visible');
    cy.get('#search-button').click();
    // Screenshot erstellen
    cy.screenshot('after-search-click');
  });
});
```

**Ausführung im interaktiven Modus:**
```bash
npx cypress open
```

**Ausführung im Headless-Modus:**
```bash
npx cypress run
```

**Technischer Nutzen:** Cypress bietet automatisches Warten auf UI-Elemente, bessere Debug-Möglichkeiten durch Time-Travel und detaillierte Testprotokolle. Es ist ideal für schnelle Entwicklungszyklen und bietet eine stabilere Testausführung mit weniger Flakiness als Selenium.

### Schritt 4: Testinhalte

Alle drei Testsuiten überprüfen die gleichen Kernfunktionalitäten:

1. **UI-Basisprüfungen:**
   - Korrekter Seitentitel
   - Sichtbarkeit des Sternenhintergrunds
   - Vorhandensein der Suchfunktion

2. **Interaktive Elemente:**
   - Klick auf die Such-Schaltfläche
   - Paketauswahl und Navigation
   - Start und Abbruch des Installations-Mini-Games

3. **Navigationstests:**
   - Von der Paketliste zu Paketdetails
   - Zurück zur Paketliste nach Detailansicht
### Schritt 5: CI/CD-Integration

Die Tests wurden für die einfache Integration in CI/CD-Pipelines konzipiert:

```yaml
# Beispiel für Jenkins-Pipeline
pipeline {
    stages {
        stage('UI Tests') {
            parallel {
                stage('Chrome Tests') {
                    steps {
                        sh 'npm run selenium:chrome'
                    }
                }
                stage('Firefox Tests') {
                    steps {
                        sh 'npm run selenium:firefox'
                    }
                }
                stage('Cypress Tests') {
                    steps {
                        sh 'npm run cypress:run'
                    }
                }
            }
        }
    }
}
```

Diese parallele Ausführung maximiert die Effizienz der Testautomatisierung und beschleunigt Feedback-Zyklen im DevOps-Prozess.

---

## JaCoCo-Testabdeckungsbericht

### Schritt 1: JaCoCo in Gradle konfigurieren

Um die Testabdeckung des Projekts zu messen und zu visualisieren, wurde JaCoCo (Java Code Coverage) integriert. Die Konfiguration dazu erfolgt in der `build.gradle`-Datei.

### Schritt 2: JaCoCo-Berichte generieren

Nach dem Hinzufügen der JaCoCo-Konfiguration können Testabdeckungsberichte wie folgt generiert werden:

```bash
# Zuerst in das Unterverzeichnis wechseln
cd galactic-pm

# Dann überprüfen, ob gradlew.bat dort ist (denkfehler)
dir

# Wenn gradlew.bat vorhanden ist, dann den Befehl ausführen
.\gradlew.bat test jacocoTestReport

# Optional: Überprüft, ob die Testabdeckung die Mindestanforderungen erfüllt
.\gradlew jacocoTestCoverageVerification
```

### Schritt 3: Testabdeckungsberichte anzeigen

Nach der Ausführung der Tests mit JaCoCo wird ein detaillierter Bericht erstellt:

1. **HTML-Bericht**: Der visuelle Bericht ist unter `backend/build/reports/jacoco/test/html/index.html` verfügbar
2. **XML-Bericht**: Für CI/CD-Tools wird ein XML-Bericht unter `backend/build/reports/jacoco/test/jacocoTestReport.xml` generiert

Der HTML-Bericht bietet eine interaktive Oberfläche, mit der die Testabdeckung auf verschiedenen Ebenen analysiert werden kann:
- Paketübersicht mit Gesamtabdeckung
- Klassenübersicht innerhalb jedes Pakets
- Detaillierte Codeansicht mit farbiger Markierung der getesteten/nicht getesteten Zeilen

### Schritt 4: Interpretation der Ergebnisse

Der JaCoCo-Bericht zeigt verschiedene Metriken zur Codeabdeckung:

- **Instructions**: Anzahl der ausgeführten Java-Bytecode-Anweisungen
- **Branches**: Prozentsatz der ausgeführten Verzweigungen (if/else, switch)
- **Cyclomatic Complexity**: Abdeckung der Codekomplexität
- **Lines**: Prozentsatz der ausgeführten Codezeilen
- **Methods**: Prozentsatz der getesteten Methoden
- **Classes**: Prozentsatz der getesteten Klassen

Für den Galactic Package Manager wurde eine Zielabdeckung von mindestens 80% für alle Metriken festgelegt, um eine robuste Codebasis zu gewährleisten.

<img src="images/Bild10.png" alt="DevOpsLogo" width="157" height="80">

Der JaCoCo-Testabdeckungsbericht zeigt eine gute Gesamtabdeckung des Projekts mit 96% Instruction Coverage und 100% Class Coverage, was auf eine robuste Testsuite hinweist. Die Package-Analyse offenbart Unterschiede in der Testqualität, wobei das Basis-Package "galactic_pm" mit nur 37% Instruction Coverage deutliches Verbesserungspotential aufweist, während "config" und "entity" mit 100% vollständig abgedeckt sind. Die Controller-Komponente erreicht solide 89% Instruction Coverage, sollte aber noch optimiert werden, um die verbleibenden 11% abzudecken und damit die Zuverlässigkeit der API-Endpunkte weiter zu erhöhen.

> Die regelmässige Überprüfung der Testabdeckung hilft dabei, potenzielle Lücken in der Testabdeckung zu identifizieren und zu schliessen, bevor sie zu Problemen in der Produktion führen können.

### Beide Komponenten starten

Um die vollständige Anwendung zu testen, müssen sowohl Backend als auch Frontend gestartet werden:

1. Backend starten (im Hauptverzeichnis):
```bash
./gradlew bootRun
```

2. Frontend starten (im frontend-Verzeichnis):
```bash
cd frontend
npx serve
```

Nun sollte die Anwendung funktionieren:
- Backend läuft auf http://localhost:8080
- Frontend läuft auf http://localhost:3000
- REST-API ist unter http://localhost:8080/api/packages erreichbar
- Frontend kommuniziert mit dem Backend und zeigt die galaktischen Pakete an

<img src="images/Bild7.png" alt="DevOpsLogo" width="157" height="80">

### Manuelles Testen der Hauptfunktionen

1. **Pakete anzeigen:** Die Liste aller Pakete sollte beim Öffnen der Seite geladen werden
2. **Paketdetails:** Beim Klicken auf ein Paket sollten die Details angezeigt werden
3. **Suchfunktion:** Die Suchleiste sollte funktionieren und die Ergebnisse filtern
4. **Kompatibilitätsfilter:** Das Dropdown-Menü sollte Pakete nach Fraktion filtern
5. **Installation mit Mini-Spiel:** Das Klick-Spiel sollte funktionieren und zur Erfolgsseite führen

---

## SonarQube-Analyse

Um eine SonarQube-Analyse durchzuführen, sollte den nachfolgenden Schritten gefolgt werden:

### 1. SonarQube über Docker starten

Wenn Docker Desktop bereits installiert ist, kann SonarQube mit folgendem Befehl gestartet werden:

```powershell
docker start sonarqube
```

SonarQube wird dann unter http://localhost:9000 verfügbar sein.

### 2. SonarQube-Token erstellen

1. Zum http://localhost:9000 im Browser navigieren
2. Mit dem Standardzugang (admin/admin) anmelden
3. Passwort ändern, falls dazu aufgefordert 
4. Administration > Security > Users
5. Auf den Admin-Benutzer klicken
6. "Tokens" anwählen und auf "Generate" klicken
7. Namen eingeben ("galactic-pm-token") und Ablaufzeit wählen (möglichst bis nach dem Semester)
8. Das generierte Token kopieren - es wird nur einmal angezeigt!

<img src="images/Bild11.png" alt="DevOpsLogo" width="157" height="80">

<img src="images/Bild12.png" alt="DevOpsLogo" width="157" height="80">

<img src="images/Bild14.png" alt="DevOpsLogo" width="157" height="80">

### 3. Gradle-Konfiguration für SonarQube

Die `build.gradle`-Datei im `backend/galactic-pm`-Verzeichnis muss noch um ein sonarqube-Plugin ergänzt werden.

### 4. Analyse ausführen

Es muss zum `backend/galactic-pm`-Verzeichnis navigiert und den Befehl aus geführt werden:

```powershell
.\gradlew.bat sonar "-Dsonar.projectKey=gpm-devops" "-Dsonar.projectName=gpm-devops" "-Dsonar.host.url=http://localhost:9000" "-Dsonar.token=sqp_003250248f56dfec956f4f9cf63a24409300c77c"
```

>Ja, das Token sollte nicht hier stehen, aber leider ging es zu oft verloren :(.

Die Analyse des Backend-Codes wird damit ausgeführt und die Ergebnisse an den lokalen SonarQube-Server gesendet. Nach Abschluss kann man die Ergebnisse im SonarQube-Dashboard unter http://localhost:9000 einsehen.

### 5. SonarQube-Analyseergebnisse des Backends

In SonarQube kannst du nun viele Aspekte des Codes analysieren.

#### Codequalität und Metriken

Das Backend-Projekt "gpm-devops" hat beispielsweise den Quality Gate-Test bestanden, was auf eine grundsätzlich solide Codebasis hinweist. Diese erste Analyse zeigt gute Werte bei mehreren kritischen Metriken: eine beeindruckende Testabdeckung von 97,0% (189 von 283 Codezeilen) und 0,0% Codeduplikationen. Das Sicherheitsprofil erhält ein A-Rating mit 0 offenen Security-Issues, jedoch existiert 1 Security Hotspot (E-Rating), der untersucht werden sollte. Der Codebase umfasst 209 Zeilen in der Version 0.0.1-SNAPSHOT.

<img src="images/Bild15.png" alt="DevOpsLogo" width="157" height="80">

#### Identifizierte Problembereiche

Trotz des bestandenen Quality Gates wurden 28 Issues identifiziert, welche behoben werden sollten. Der Schwerpunkt liegt auf Wartbarkeitsproblemen (26 Issues). Die häufigsten Probleme betreffen:
- Dependency Injection: Field Injection sollte durch Constructor Injection ersetzt werden
- Übermässige Parameterzahl: Ein Konstruktor verwendet mehr als 7 Parameter, was die Komplexität erhöht
- Exception-Handling: Problematische Exception-Deklaration, die nicht aus der Methode geworfen werden kann
- Modifier-Probleme: Unnötige public Modifier
- Zuverlässigkeitsprobleme (C-Rating mit 2 Issues)

<img src="images/Bild13.png" alt="DevOpsLogo" width="157" height="80">

Diese technischen Schulden sollten - bei der Weiterführung des Projekts - wohl priorisiert angegangen werden, um die langfristige Wartbarkeit des Codes zu verbessern. Dennoch besteht das Projekt aktuell die Quality Gates!

---

## Die Macht der Container: Vom Dev- zum Ops-System

> *Die Entwicklungsphase ist abgeschlossen. Die Tests sind bestanden. Doch in einer Galaxie weit, weit entfernt wartet die wahre Herausforderung: das Deployment. Während unsere Helden den Dev-Hyperraum verlassen, steuern sie nun auf das Ops-System zu, wo Docker-Container und Jenkins-Pipelines darauf warten, die Macht der kontinuierlichen Integration zu entfesseln...*

Nachdem der Galactic Package Manager entwickelt und mit verschiedenen Test-Strategien abgesichert wurde, ist es an der Zeit, den Sprung ins Ops-Universum zu wagen. 

Als nächstes werdn die nachfolgenden Punkte angegangen:

1. **Docker-Container bauen** - Anwendung in leichtgewichtige, portable Container verpacken, damit sie überall identisch läuft ("It works on my machine" war noch nie ein gültiger Statuscode)

2. **Jenkins-Pipelines einrichten** - Automatisierte Builds aufsetzen, damit Code-Änderungen durch die Qualitätssicherung rasen

3. **Multi-Stage-Deployment implementieren** - Von der Entwicklungsumgebung über Staging bis zur Produktion - ohne dass ein Qualitätsproblem durchschlüpft

*"Es gibt keinen Versuch, nur Deployment"* - Master Yoda, wahrscheinlich

---

## Continuous Integration mit Jenkins - Oder: Wie ich lernte, die Build-Fehler zu lieben

Die Integration von Jenkins erwies sich als schwieriger wie ursprünglich geplant... wobei es wohl noch an meinen Fähigkeiten lag.

### Schritt 1: Jenkins-Projekt einrichten

Zunächst wurde ein "Free Style"-Projekt in Jenkins erstellt.

<img src="images/Bild16.png" alt="DevOpsLogo" width="157" height="80">

**Wichtige Einstellungen:**
- Projektname: "GPM-Build" 
- Git Repository URL: https://github.com/blobb3/Galactic-Package-Manager
- Branch: */main 

### Schritt 2: Source Code Management konfigurieren

Um Zugriff auf den Quellcode zu erhalten, wurde ein GitHub-Token eingerichtet.

### Schritt 3: Build-Umgebung einrichten

Damit Node.js und Gradle harmonisch zusammenarbeiten können, wurden folgende Einstellungen vorgenommen:

<img src="images/Bild17.png" alt="DevOpsLogo" width="157" height="80">

- "Provide Node & npm bin/ folder to PATH" aktiviert
- NodeJS 21.11.0 ausgewählt 

### Schritt 4: Backend-Build konfigurieren

Nach zahlreichen "Permission denied" und "Directory not found" Fehlern (die Abwehr war stark), wurde folgende Shell-Kommando-Sequenz eingesetzt:

```bash
cd backend
chmod +x ./gradlew
./gradlew test build
```

Der erste Befehl wechselt ins richtige Verzeichnis, der zweite macht den Gradle-Wrapper ausführbar, und der dritte führt den eigentlichen Build durch.

<img src="images/Bild18.png" alt="DevOpsLogo" width="157" height="80">

### Schritt 5: Frontend-Build konfigurieren

Für das Frontend wurde ein weiterer Shell-Befehl hinzugefügt:

```bash
npm install --prefix frontend
```

Der  `npm run lint:html`-Befehl wurde entfernt, nachdem bemerkt wurde, dass Copy-Pasten von Lösungen hier doch nicht sinvoll war (Lint wurde nämlich nie im Projekt eingesetzt).

### Schritt 6: Docker-Integration

Für die Containerisierung wurde die Docker-Integration eingerichtet:

```bash
export DOCKER_HOST=tcp://host.docker.internal:2375
# docker build -t heinejan/devopsdemo .
if [ -f "Dockerfile" ]; then
  docker build -t heinejan/devopsdemo:backend .
fi
```

Die auskommentierte Zeile ist eine Art Bauplan - bereit, aber noch nicht aktiviert.

### Schritt 7: JaCoCo-Testabdeckung konfigurieren

Zudem wurde JaCoCo für die Code-Coverage eingerichtet:

<img src="images/Bild19.png" alt="DevOpsLogo" width="157" height="80">

### Die JaCoCo-Testabdeckung

Der JaCoCo-Bericht zeigt nun die Testabdeckung und welche Bereiche des Codes angeschaut werden sollten.

<img src="images/Bild19.png" alt="DevOpsLogo" width="157" height="80">

Wie man sehen kann, haben wir eine gute Testabdeckung für unsere Kernkomponenten erreicht. 

### Die zahlreichen Build-Versuche

Nach nur *[überraschtes Husten]* 42 Build-Versuchen funktionierte Jenkins endlich wie gewünscht. 

Die häufigsten Hindernisse auf dem Weg:

1. **Die verlorene Gradle-Datei**: Wie sich herausstellte, befand sich unsere `build.gradle` nicht dort, wo Jenkins sie vermutete. 

2. **Der rebellische Gradle-Wrapper**: Mit `Permission denied` versuchte der Gradle-Wrapper, Jenkins den Zugang zu verweigern. Ein `chmod +x` löste den Widerstand.

3. **Das Phantom-Skript**: Das `npm run lint:html`-Skript existierte nicht.

4. **Der Docker-Connection-Konflikt**: "Ich bin dein Docker-Host" - sagte eine TCP-Verbindung, welche nicht existierte.

> Allerdings ist der Build nun grün, die Pipeline steht, und das Repository einigermassen sicher.
> Mit den richtigen Befehlen und etwas Geduld (viel Geduld... sehr viel Geduld) wird Jenkins zu einem treuen Verbündeten im Kampf für qualitativ hochwertigen Code.

---

## Docker-Deployment und Render-Integration

Nach der erfolgreichen Implementierung der JaCoCo-Testabdeckung folgte der komplexere Teil des DevOps-Workflows: Das Containerisieren der Anwendung mit Docker und das Deployment in die Cloud mittels Render.com.

### Separate Images für Frontend und Backend

Um die Architektur sauber zu trennen, wurden (nach 200 anderen Versuchen) zwei separate Docker-Images erstellt:

1. **Backend-Image** (`heinejan/galactic-pm:latest`):
   ```dockerfile
   FROM openjdk:21-slim as build
   WORKDIR /app
   COPY backend .
   RUN chmod +x ./gradlew
   RUN ./gradlew bootJar
   
   FROM openjdk:21-slim
   WORKDIR /app
   COPY --from=build /app/build/libs/*.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "app.jar"]
   ```

2. **Frontend-Image** (`heinejan/galactic-pm-frontend:latest`):
   ```dockerfile
   FROM node:18-alpine
   WORKDIR /app
   COPY frontend/package*.json ./
   RUN npm install
   COPY frontend/ .
   EXPOSE 3000
   CMD ["npm", "start"]
   ```

### Docker Hub Integration

Eine der grössten Herausforderungen war die korrekte Integration mit Docker Hub über Jenkins:

1. **Registry-Authentifizierung**: Die Einrichtung der Docker Hub-Credentials in Jenkins erforderte mehrere Iterationen
2. **Push-Prozess**: Das Pushen der Images zu Docker Hub innerhalb des Jenkins-Workflows erforderte spezifische Shell-Befehle und Umgebungsvariablen
3. **Port-Konfiguration**: Die korrekte Weiterleitung der Ports (8080 für Backend, 3000 für Frontend) war entscheidend für das lokale Testing (es gab viele Fehlversuche)

## Multi-Staged Deployment auf Render.com

### Separate Web Services

Um Frontend und Backend getrennt zu deployen, wurden zwei Web Services auf Render.com eingerichtet:

1. **Backend-Service** (https://galactic-pm-backend.onrender.com/):
   - Docker-Image: `heinejan/galactic-pm:latest`
   - Port: 8080
   - Umgebungsvariablen: Spring-spezifische Konfigurationen

2. **Frontend-Service** (https://galactic-package-manager2.onrender.com/):
   - Docker-Image: `heinejan/galactic-pm-frontend:latest`
   - Port: 3000
   - Angepasste API-URL, um mit dem Backend zu kommunizieren

### Herausforderungen und Probleme

Trotz erfolgreicher lokaler Tests und Docker-Integration traten bei der Deployment-Phase mehrere kritische Probleme auf:

1. **CORS-Konfiguration**: Das Backend musste speziell konfiguriert werden, um Cross-Origin-Requests vom getrennten Frontend zu akzeptieren
2. **API-Endpunkt-Anpassung**: Die harte Codierung der API-Endpunkte im Frontend erforderte Anpassungen und Neubau des Images
3. **Render.com "Sleep Mode"**: Im kostenlosen Tier schlafen inaktive Services ein, was zu unvorhersehbarem Verhalten führte
4. **Kommunikationsprobleme**: Trotz korrekter URL-Konfiguration konnte das Frontend nicht zuverlässig mit dem Backend kommunizieren

Letztendlich wurde das Projekt erfolgreich containerisiert und in die Cloud deployt, aber die vollständige Funktionalität zwischen den getrennten Services konnte nicht hergestellt werden. Dies unterstreicht die Komplexität verteilter Systeme und die Notwendigkeit umfassender Integrationstests vor dem Deployment.

> Frontend: https://galactic-package-manager2.onrender.com/
> Backend: https://galactic-pm-backend.onrender.com/

So oder so, die Erfahrungen waren nicht umsonst, da so ziemlich jeder Aspekt der Vorlesung nochmals durchgespielt wrude. Die Integration von Jenkins mit Docker und Cloud-Deployment-Plattformen bleibt zudem ein entscheidender Aspekt moderner DevOps-Workflows, erfordert jedoch sorgfältige Planung, etwas mehr Zeit und Konfiguration, um nahtlos zu funktionieren.

---

## Abschluss

Mit diesem Projekt wurde eine Full-Stack-Anwendung erstellt, welche als Basis für weitere DevOps-Übungen dienen kann. Es soll eine gute Lernumgebung geboten werden, um die verschiedenen Aspekte des DevOps-Zyklus zu verstehen und zu implementieren.

**Möge der Quellcode mit dir sein!** 🌌
