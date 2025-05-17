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
7. [Erweiterungsmöglichkeiten](#erweiterungsmöglichkeiten)

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

### Schritt 4: .gitignore erstellen


```
# Java/Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar
!gradle/wrapper/gradle-wrapper.properties
*.class
bin/
out/

# Node.js
node_modules/
npm-debug.log*
package-lock.json

# IDE
.idea/
.vscode/
*.iml
.classpath
.project
.settings/

# Logs
logs/
*.log

# Temp files
.DS_Store
Thumbs.db
```

---

## Backend implementieren

### Schritt 1: Erstes Datenmodell erstellen



### Schritt 2: Repository-Interface erstellen


### Schritt 3: REST-Controller implementieren

```java
// src/main/java/com/gpm/controller/PackageController.java
package com.gpm.controller;

import com.gpm.model.GalacticPackage;
import com.gpm.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "*") // Für Entwicklungszwecke
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;

    // Alle Pakete abrufen
    @GetMapping
    public List<GalacticPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    // Ein bestimmtes Paket abrufen
    @GetMapping("/{id}")
    public Optional<GalacticPackage> getPackageById(@PathVariable Long id) {
        return packageRepository.findById(id);
    }

    // Nach Paketen suchen
    @GetMapping("/search")
    public List<GalacticPackage> searchPackages(@RequestParam String query) {
        return packageRepository.findByNameContainingIgnoreCase(query);
    }

    // Pakete nach Kompatibilität filtern
    @GetMapping("/compatibility/{faction}")
    public List<GalacticPackage> getPackagesByCompatibility(@PathVariable String faction) {
        return packageRepository.findByCompatibility(faction);
    }

    // Neues Paket erstellen
    @PostMapping
    public GalacticPackage createPackage(@RequestBody GalacticPackage galacticPackage) {
        return packageRepository.save(galacticPackage);
    }

    // Paket aktualisieren
    @PutMapping("/{id}")
    public GalacticPackage updatePackage(@PathVariable Long id, @RequestBody GalacticPackage updatedPackage) {
        // Prüfen, ob Paket existiert
        packageRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Package with id " + id + " not found"));
        
        updatedPackage.setId(id);
        return packageRepository.save(updatedPackage);
    }

    // Paket löschen
    @DeleteMapping("/{id}")
    public void deletePackage(@PathVariable Long id) {
        packageRepository.deleteById(id);
    }
}
```

### Schritt 4: Demo-Daten für Testbetrieb erstellen

in config - Datainitializer

### Schritt 5: Spring Boot-Anwendung starten

```bash
./gradlew bootRun
```

Wenn alles funktioniert, sollte die Anwendung auf http://localhost:8080 starten und die REST-API verfügbar sein.
Und das ist sie: 

<img src="images/Bild1.png" alt="DevOpsLogo" width="157" height="80">

Unser erster Whitelabel-Error!

## Frontend entwickeln

### Schritt 1: Frontend-Verzeichnis einrichten

```bash
mkdir -p frontend/css frontend/js
cd frontend
npm init -y
npm install particles.js chart.js animejs --save
```

### Schritt 2: HTML-Grundstruktur erstellen

```html
<!-- frontend/index.html -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Galactic Package Manager</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <!-- Particles.js Container für Sternenhintergrund -->
    <div id="particles-js"></div>
    
    <header>
        <div class="logo-container">
            <h1>Galactic Package Manager</h1>
            <p class="subtitle">May the source be with you</p>
        </div>
        
        <div class="search-container">
            <input type="text" id="search-input" placeholder="Search for packages...">
            <button id="search-button">Search</button>
        </div>
        
        <div class="filter-container">
            <select id="compatibility-filter">
                <option value="">All Factions</option>
                <option value="Republic">Republic</option>
                <option value="Empire">Empire</option>
                <option value="Neutral">Neutral</option>
            </select>
            
            <select id="category-filter">
                <option value="">All Categories</option>
                <option value="Navigation">Navigation</option>
                <option value="Communication">Communication</option>
                <option value="Visual">Visual</option>
                <option value="Security">Security</option>
            </select>
        </div>
    </header>
    
    <main>
        <section class="packages-container">
            <h2>Available Packages</h2>
            <div id="packages-list">
                <!-- Packages will be loaded here dynamically -->
            </div>
        </section>
        
        <section class="package-details hidden" id="package-details">
            <h2 id="package-name"></h2>
            <div class="package-info">
                <p><strong>Author:</strong> <span id="package-author"></span></p>
                <p><strong>Version:</strong> <span id="package-version"></span></p>
                <p><strong>Category:</strong> <span id="package-category"></span></p>
                <p><strong>Compatibility:</strong> <span id="package-compatibility"></span></p>
                <p><strong>Downloads:</strong> <span id="package-downloads"></span></p>
                <p><strong>Description:</strong> <span id="package-description"></span></p>
            </div>
            <div class="package-actions">
                <button id="install-package">Install Package</button>
                <button id="back-to-list">Back to List</button>
            </div>
        </section>
        
        <!-- Mini-Game für die Paket-Installation -->
        <section class="installation-game hidden" id="installation-game">
            <h2>Package Installation</h2>
            <p>Click on the appearing rebel ships to protect your data transfer!</p>
            <div class="game-container">
                <div id="game-area"></div>
                <div class="progress-bar">
                    <div id="installation-progress"></div>
                </div>
            </div>
            <p>Installation Progress: <span id="progress-percentage">0</span>%</p>
            <button id="cancel-installation">Cancel</button>
        </section>
        
        <!-- Erfolgsanzeige nach Installation -->
        <section class="installation-success hidden" id="installation-success">
            <h2>Installation Complete!</h2>
            <p>The package has been successfully installed.</p>
            <div id="package-stats">
                <canvas id="stats-chart"></canvas>
            </div>
            <button id="return-to-details">Return to Package Details</button>
        </section>
    </main>
    
    <footer>
        <p>© 2025 Galactic Package Manager - For educational purposes only</p>
        <p>Not affiliated with the Jedi Council or the Galactic Empire</p>
    </footer>

    <!-- JavaScript-Bibliotheken -->
    <script src="node_modules/particles.js/particles.js"></script>
    <script src="node_modules/chart.js/dist/chart.umd.js"></script>
    <script src="node_modules/animejs/lib/anime.min.js"></script>
    
    <!-- Eigene JavaScript-Dateien -->
    <script src="js/particles-config.js"></script>
    <script src="js/api-service.js"></script>
    <script src="js/package-renderer.js"></script>
    <script src="js/installation-game.js"></script>
    <script src="js/main.js"></script>
</body>
</html>
```

### Schritt 3: CSS für stilvolle Galaxis-Oberfläche

```css
/* frontend/css/style.css */
:root {
    --primary-color: #0b3d91;     /* NASA Blue */
    --secondary-color: #fc3d21;   /* NASA Red */
    --accent-color: #4c9f38;      /* Lightsaber Green */
    --dark-bg: #0a0e17;           /* Space Black */
    --light-text: #e1e1e1;        /* Star Light */
    --card-bg: rgba(16, 23, 41, 0.8); /* Transparent Star Destroyer Gray */
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Arial', sans-serif;
    color: var(--light-text);
    background-color: var(--dark-bg);
    position: relative;
    min-height: 100vh;
}

#particles-js {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: -1;
}

header {
    background-color: rgba(11, 61, 145, 0.8);
    padding: 1rem 2rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

.logo-container {
    text-align: center;
    margin-bottom: 1.5rem;
}

h1 {
    font-size: 2.5rem;
    text-shadow: 0 0 10px rgba(252, 61, 33, 0.6);
    margin-bottom: 0.5rem;
}

.subtitle {
    font-style: italic;
    opacity: 0.8;
}

.search-container {
    display: flex;
    max-width: 600px;
    margin: 0 auto 1rem;
}

.search-container input {
    flex-grow: 1;
    padding: 0.7rem;
    border: none;
    border-radius: 4px 0 0 4px;
    font-size: 1rem;
}

.search-container button {
    background-color: var(--secondary-color);
    color: white;
    border: none;
    border-radius: 0 4px 4px 0;
    padding: 0 1.2rem;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.2s;
}

.search-container button:hover {
    background-color: #e93315;
}

.filter-container {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin-bottom: 1rem;
}

.filter-container select {
    padding: 0.5rem;
    border-radius: 4px;
    background-color: var(--card-bg);
    color: var(--light-text);
    border: 1px solid #2a4580;
}

main {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 0 1rem;
}

.packages-container {
    background-color: var(--card-bg);
    border-radius: 8px;
    padding: 1.5rem;
    margin-bottom: 2rem;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.packages-container h2 {
    border-bottom: 2px solid var(--primary-color);
    padding-bottom: 0.5rem;
    margin-bottom: 1.5rem;
}

#packages-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1.5rem;
}

.package-card {
    background-color: rgba(22, 38, 73, 0.8);
    border-radius: 6px;
    padding: 1.2rem;
    transition: transform 0.2s, box-shadow 0.2s;
    cursor: pointer;
    border-left: 4px solid var(--primary-color);
}

.package-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
}

.package-card h3 {
    margin-bottom: 0.7rem;
    color: white;
}

.package-card p {
    margin-bottom: 0.5rem;
    opacity: 0.8;
    font-size: 0.9rem;
}

.package-card .compatibility {
    display: inline-block;
    padding: 0.2rem 0.5rem;
    border-radius: 3px;
    font-size: 0.8rem;
    margin-top: 0.5rem;
}

.compatibility.Republic {
    background-color: var(--accent-color);
}

.compatibility.Empire {
    background-color: #c80815;
}

.compatibility.Neutral {
    background-color: #7b7b7b;
}

.package-details {
    background-color: var(--card-bg);
    border-radius: 8px;
    padding: 2rem;
    margin-bottom: 2rem;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.package-info {
    margin: 1.5rem 0;
}

.package-info p {
    margin-bottom: 0.8rem;
}

.package-actions {
    display: flex;
    gap: 1rem;
    margin-top: 2rem;
}

.package-actions button {
    padding: 0.8rem 1.5rem;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.2s;
}

#install-package {
    background-color: var(--accent-color);
    color: white;
}

#install-package:hover {
    background-color: #3d8029;
}

#back-to-list {
    background-color: #4a4a4a;
    color: white;
}

#back-to-list:hover {
    background-color: #333;
}

.installation-game, .installation-success {
    background-color: var(--card-bg);
    border-radius: 8px;
    padding: 2rem;
    text-align: center;
    margin-bottom: 2rem;
}

.game-container {
    position: relative;
    width: 100%;
    height: 300px;
    background-color: rgba(0, 0, 0, 0.3);
    margin: 1.5rem 0;
    border-radius: 8px;
    overflow: hidden;
}

#game-area {
    width: 100%;
    height: 100%;
    position: relative;
}

.target {
    position: absolute;
    width: 40px;
    height: 40px;
    background-image: url('../img/tie-fighter.png');
    background-size: contain;
    background-repeat: no-repeat;
    cursor: pointer;
}

.progress-bar {
    height: 20px;
    background-color: #333;
    border-radius: 10px;
    margin-top: 1rem;
    overflow: hidden;
}

#installation-progress {
    height: 100%;
    width: 0%;
    background-color: var(--accent-color);
    transition: width 0.3s;
}

#package-stats {
    margin: 2rem auto;
    max-width: 500px;
}

.hidden {
    display: none;
}

footer {
    text-align: center;
    padding: 2rem;
    background-color: rgba(10, 14, 23, 0.8);
    margin-top: 3rem;
}

footer p {
    opacity: 0.7;
    margin-bottom: 0.5rem;
}

@media (max-width: 768px) {
    h1 {
        font-size: 1.8rem;
    }
    
    .filter-container {
        flex-direction: column;
        align-items: center;
    }
    
    #packages-list {
        grid-template-columns: 1fr;
    }
    
    .package-actions {
        flex-direction: column;
    }
}
```

### Schritt 4: JavaScript-Dateien für Frontend-Logik erstellen

1. Particles.js Konfiguration:

```javascript
// frontend/js/particles-config.js
particlesJS('particles-js', {
    particles: {
        number: {
            value: 100,
            density: {
                enable: true,
                value_area: 800
            }
        },
        color: {
            value: "#ffffff"
        },
        shape: {
            type: "circle",
            stroke: {
                width: 0,
                color: "#000000"
            }
        },
        opacity: {
            value: 0.5,
            random: true,
            anim: {
                enable: true,
                speed: 1,
                opacity_min: 0.1,
                sync: false
            }
        },
        size: {
            value: 3,
            random: true,
            anim: {
                enable: true,
                speed: 2,
                size_min: 0.3,
                sync: false
            }
        },
        line_linked: {
            enable: false
        },
        move: {
            enable: true,
            speed: 0.5,
            direction: "none",
            random: true,
            straight: false,
            out_mode: "out",
            bounce: false
        }
    },
    interactivity: {
        detect_on: "canvas",
        events: {
            onhover: {
                enable: true,
                mode: "bubble"
            },
            onclick: {
                enable: true,
                mode: "repulse"
            },
            resize: true
        },
        modes: {
            bubble: {
                distance: 200,
                size: 5,
                duration: 2,
                opacity: 1,
                speed: 3
            },
            repulse: {
                distance: 200,
                duration: 0.4
            }
        }
    },
    retina_detect: true
});
```

2. API Service für Backend-Kommunikation:

```javascript
// frontend/js/api-service.js
class ApiService {
    constructor(baseUrl = 'http://localhost:8080/api') {
        this.baseUrl = baseUrl;
    }

    async getAllPackages() {
        try {
            const response = await fetch(`${this.baseUrl}/packages`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching packages:", error);
            return [];
        }
    }

    async getPackageById(id) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching package ${id}:`, error);
            return null;
        }
    }

    async searchPackages(query) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/search?query=${encodeURIComponent(query)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error searching packages:", error);
            return [];
        }
    }

    async getPackagesByCompatibility(faction) {
        try {
            const response = await fetch(`${this.baseUrl}/packages/compatibility/${encodeURIComponent(faction)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching packages for faction ${faction}:`, error);
            return [];
        }
    }

    // Weitere Methoden für POST, PUT, DELETE könnten hier hinzugefügt werden
}
```

3. Package Renderer für UI-Updates:

```javascript
// frontend/js/package-renderer.js
class PackageRenderer {
    constructor(apiService) {
        this.apiService = apiService;
        this.packagesListElement = document.getElementById('packages-list');
        this.packageDetailsElement = document.getElementById('package-details');
        this.installationGameElement = document.getElementById('installation-game');
        this.installationSuccessElement = document.getElementById('installation-success');
        
        // Package Details-Elemente
        this.packageNameElement = document.getElementById('package-name');
        this.packageAuthorElement = document.getElementById('package-author');
        this.packageVersionElement = document.getElementById('package-version');
        this.packageCategoryElement = document.getElementById('package-category');
        this.packageCompatibilityElement = document.getElementById('package-compatibility');
        this.packageDownloadsElement = document.getElementById('package-downloads');
        this.packageDescriptionElement = document.getElementById('package-description');
        
        // Button-Event-Listener
        document.getElementById('back-to-list').addEventListener('click', () => this.showPackagesList());
        document.getElementById('install-package').addEventListener('click', () => this.startInstallation());
        document.getElementById('cancel-installation').addEventListener('click', () => this.cancelInstallation());
        document.getElementById('return-to-details').addEventListener('click', () => {
            this.installationSuccessElement.classList.add('hidden');
            this.packageDetailsElement.classList.remove('hidden');
        });
        
        // Filter-Event-Listener
        document.getElementById('compatibility-filter').addEventListener('change', (e) => {
            this.filterByCompatibility(e.target.value);
        });
        
        // Search-Event-Listener
        document.getElementById('search-button').addEventListener('click', () => {
            const query = document.getElementById('search-input').value;
            this.searchPackages(query);
        });
        
        document.getElementById('search-input').addEventListener('keyup', (e) => {
            if (e.key === 'Enter') {
                const query = e.target.value;
                this.searchPackages(query);
            }
        });
    }
    
    async loadPackages() {
        const packages = await this.apiService.getAllPackages();
        this.renderPackagesList(packages);
    }
    
    renderPackagesList(packages) {
        this.packagesListElement.innerHTML = '';
        
        if (packages.length === 0) {
            this.packagesListElement.innerHTML = '<p class="no-results">No packages found. Try a different search.</p>';
            return;
        }
        
        packages.forEach(pkg => {
            const packageCard = document.createElement('div');
            packageCard.className = 'package-card';
            packageCard.dataset.id = pkg.id;
            
            packageCard.innerHTML = `
                <h3>${pkg.name}</h3>
                <p>${pkg.description.substring(0, 80)}${pkg.description.length > 80 ? '...' : ''}</p>
                <p><strong>Author:</strong> ${pkg.author}</p>
                <p><strong>Version:</strong> ${pkg.version}</p>
                <span class="compatibility ${pkg.compatibility}">${pkg.compatibility}</span>
            `;
            
            packageCard.addEventListener('click', () => this.showPackageDetails(pkg.id));
            this.packagesListElement.appendChild(packageCard);
        });
    }
    
    async showPackageDetails(id) {
        const pkg = await this.apiService.getPackageById(id);
        if (!pkg) return;
        
        this.packageNameElement.textContent = pkg.name;
        this.packageAuthorElement.textContent = pkg.author;
        this.packageVersionElement.textContent = pkg.version;
        this.packageCategoryElement.textContent = pkg.category;
        this.packageCompatibilityElement.textContent = pkg.compatibility;
        this.packageDownloadsElement.textContent = pkg.downloads.toLocaleString();
        this.packageDescriptionElement.textContent = pkg.description;
        
        // Animation mit anime.js
        this.packagesListElement.parentElement.classList.add('hidden');
        this.packageDetailsElement.classList.remove('hidden');
        
        anime({
            targets: this.packageDetailsElement,
            opacity: [0, 1],
            translateY: [20, 0],
            easing: 'easeOutExpo',
            duration: 800
        });
        
        // Speichere aktuelle Paket-ID für die Installation
        this.currentPackageId = id;
    }
    
    showPackagesList() {
        this.packageDetailsElement.classList.add('hidden');
        this.packagesListElement.parentElement.classList.remove('hidden');
        
        anime({
            targets: this.packagesListElement.parentElement,
            opacity: [0, 1],
            translateY: [20, 0],
            easing: 'easeOutExpo',
            duration: 800
        });
    }
    
    startInstallation() {
        this.packageDetailsElement.classList.add('hidden');
        this.installationGameElement.classList.remove('hidden');
        
        // Starte das Minispiel
        if (window.installationGame) {
            window.installationGame.startGame();
        }
    }
    
    cancelInstallation() {
        if (window.installationGame) {
            window.installationGame.stopGame();
        }
        
        this.installationGameElement.classList.add('hidden');
        this.packageDetailsElement.classList.remove('hidden');
    }
    
    showInstallationSuccess(packageData) {
        this.installationGameElement.classList.add('hidden');
        this.installationSuccessElement.classList.remove('hidden');
        
        // Chart.js für die Paketstatistik
        const ctx = document.getElementById('stats-chart').getContext('2d');
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: ['Speed', 'Reliability', 'Security', 'Size', 'Compatibility'],
                datasets: [{
                    label: 'Package Stats',
                    data: [
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100)
                    ],
                    backgroundColor: 'rgba(76, 159, 56, 0.2)',
                    borderColor: 'rgba(76, 159, 56, 1)',
                    pointBackgroundColor: 'rgba(76, 159, 56, 1)',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(76, 159, 56, 1)'
                }]
            },
            options: {
                scales: {
                    r: {
                        angleLines: {
                            color: 'rgba(255, 255, 255, 0.2)'
                        },
                        grid: {
                            color: 'rgba(255, 255, 255, 0.2)'
                        },
                        pointLabels: {
                            color: 'rgba(255, 255, 255, 0.8)'
                        },
                        ticks: {
                            color: 'rgba(255, 255, 255, 0.8)',
                            backdropColor: 'transparent'
                        }
                    }
                },
                plugins: {
                    legend: {
                        labels: {
                            color: 'rgba(255, 255, 255, 0.8)'
                        }
                    }
                }
            }
        });
    }
    
    async searchPackages(query) {
        if (!query.trim()) {
            // Wenn die Suche leer ist, lade alle Pakete
            await this.loadPackages();
            return;
        }
        
        const packages = await this.apiService.searchPackages(query);
        this.renderPackagesList(packages);
    }
    
    async filterByCompatibility(faction) {
        if (!faction) {
            // Wenn kein Filter ausgewählt ist, lade alle Pakete
            await this.loadPackages();
            return;
        }
        
        const packages = await this.apiService.getPackagesByCompatibility(faction);
        this.renderPackagesList(packages);
    }
}
```

4. Mini-Spiel für die Paket-Installation:

```javascript
// frontend/js/installation-game.js
class InstallationGame {
    constructor(packageRenderer) {
        this.packageRenderer = packageRenderer;
        this.gameArea = document.getElementById('game-area');
        this.progressBar = document.getElementById('installation-progress');
        this.progressPercentage = document.getElementById('progress-percentage');
        this.progress = 0;
        this.targetCount = 0;
        this.clickedTargets = 0;
        this.gameInterval = null;
        this.targets = [];
    }
    
    startGame() {
        this.progress = 0;
        this.targetCount = 0;
        this.clickedTargets = 0;
        this.targets = [];
        this.updateProgress(0);
        
        // Lösche alle vorhandenen Ziele
        while (this.gameArea.firstChild) {
            this.gameArea.removeChild(this.gameArea.firstChild);
        }
        
        // Starte das Spiel
        this.gameInterval = setInterval(() => this.gameLoop(), 1000);
    }
    
    stopGame() {
        if (this.gameInterval) {
            clearInterval(this.gameInterval);
            this.gameInterval = null;
        }
        
        // Lösche alle vorhandenen Ziele
        this.targets.forEach(target => {
            if (target.element && target.element.parentNode) {
                target.element.parentNode.removeChild(target.element);
            }
        });
        this.targets = [];
    }
    
    gameLoop() {
        // Spawn neues Ziel
        this.spawnTarget();
        
        // Erhöhe die Installationsgeschwindigkeit mit der Zeit
        this.progress += 2;
        
        // Beende das Spiel, wenn die Installation abgeschlossen ist
        if (this.progress >= 100) {
            this.completeInstallation();
        }
        
        this.updateProgress(this.progress);
    }
    
    spawnTarget() {
        const target = document.createElement('div');
        target.className = 'target';
        
        // Zufällige Position im Spielbereich
        const maxX = this.gameArea.clientWidth - 40;
        const maxY = this.gameArea.clientHeight - 40;
        const posX = Math.floor(Math.random() * maxX);
        const posY = Math.floor(Math.random() * maxY);
        
        target.style.left = `${posX}px`;
        target.style.top = `${posY}px`;
        
        // Animation mit anime.js
        target.style.opacity = '0';
        this.gameArea.appendChild(target);
        
        anime({
            targets: target,
            opacity: [0, 1],
            scale: [0.5, 1],
            duration: 500,
            easing: 'easeOutElastic(1, .8)'
        });
        
        // Klick-Event
        target.addEventListener('click', () => this.targetClicked(target));
        
        // Timer für Verschwinden
        const targetObj = {
            element: target,
            timeout: setTimeout(() => {
                if (target.parentNode) {
                    anime({
                        targets: target,
                        opacity: 0,
                        scale: 0.5,
                        duration: 500,
                        easing: 'easeInExpo',
                        complete: () => {
                            if (target.parentNode) {
                                target.parentNode.removeChild(target);
                            }
                            this.targets = this.targets.filter(t => t.element !== target);
                        }
                    });
                }
            }, 3000)
        };
        
        this.targets.push(targetObj);
        this.targetCount++;
    }
    
    targetClicked(target) {
        if (target.clicked) return;
        target.clicked = true;
        
        // Animation beim Klicken
        anime({
            targets: target,
            opacity: 0,
            scale: 1.5,
            rotate: '1turn',
            duration: 500,
            easing: 'easeOutExpo',
            complete: () => {
                if (target.parentNode) {
                    target.parentNode.removeChild(target);
                }
                this.targets = this.targets.filter(t => t.element !== target);
            }
        });
        
        // Boost für den Installationsfortschritt
        this.progress += 5;
        this.clickedTargets++;
        this.updateProgress(this.progress);
        
        // Beende das Spiel, wenn die Installation abgeschlossen ist
        if (this.progress >= 100) {
            this.completeInstallation();
        }
    }
    
    updateProgress(value) {
        // Begrenze den Wert auf maximal 100
        value = Math.min(value, 100);
        this.progressBar.style.width = `${value}%`;
        this.progressPercentage.textContent = Math.floor(value);
    }
    
    completeInstallation() {
        this.stopGame();
        
        // Zeige Erfolgsseite mit Paketinformationen an
        this.packageRenderer.showInstallationSuccess({
            clickedTargets: this.clickedTargets,
            totalTargets: this.targetCount
        });
    }
}
```

5. Hauptdatei zum Initialisieren aller Komponenten:

```javascript
// frontend/js/main.js
document.addEventListener('DOMContentLoaded', () => {
    // API-Service initialisieren
    const apiService = new ApiService();
    
    // Package-Renderer initialisieren
    const packageRenderer = new PackageRenderer(apiService);
    
    // Installationsspiel initialisieren
    window.installationGame = new InstallationGame(packageRenderer);
    
    // Pakete laden
    packageRenderer.loadPackages();
    
    // Willkommensanimation mit anime.js
    anime({
        targets: '.logo-container',
        translateY: ['-30px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1500,
        delay: 500
    });
    
    anime({
        targets: '.search-container, .filter-container',
        translateY: ['20px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1200,
        delay: anime.stagger(200, {start: 1000})
    });
    
    anime({
        targets: '.packages-container',
        translateY: ['50px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1800,
        delay: 1500
    });
});
```

### Schritt 5: Frontend starten

Da das Frontend einfach statische Dateien enthält, können wir es einfach mit einem lokalen Server starten:

```bash
cd frontend
npx serve
```

Die Anwendung sollte nun unter http://localhost:3000 verfügbar sein.

## Integration und Tests

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

### Manuelles Testen der Hauptfunktionen

1. **Pakete anzeigen:** Die Liste aller Pakete sollte beim Öffnen der Seite geladen werden
2. **Paketdetails:** Beim Klicken auf ein Paket sollten die Details angezeigt werden
3. **Suchfunktion:** Die Suchleiste sollte funktionieren und die Ergebnisse filtern
4. **Kompatibilitätsfilter:** Das Dropdown-Menü sollte Pakete nach Fraktion filtern
5. **Installation mit Mini-Spiel:** Das Klick-Spiel sollte funktionieren und zur Erfolgsseite führen

## Erweiterungsmöglichkeiten

Dieses Basis-Projekt kann weiter ausgebaut werden, um DevOps-Praktiken zu vertiefen:

1. **Unit-Tests hinzufügen**
   - Backend-Tests mit JUnit für Controller und Repositories
   - Frontend-Tests mit Jest

2. **Docker-Container erstellen**
   - Dockerfile für Backend
   - Dockerfile für Frontend
   - Docker Compose für die Orchestrierung

3. **CI/CD mit Jenkins einrichten**
   - Build-Pipeline erstellen
   - Automatische Tests integrieren
   - Deployment-Automatisierung

4. **Weitere Backend-Features**
   - Benutzerauthentifizierung
   - Persistenz mit einer Datenbank (z.B. PostgreSQL)
   - Erweiterte Such- und Filterfunktionen

5. **Frontend-Verbesserungen**
   - Bewertungssystem für Pakete
   - Responsive Design optimieren
   - Fortgeschrittenere Visualisierungen

---

Mit diesem Projekt wurde eine funktionierende Full-Stack-Anwendung erstellt, die als Basis für weitere DevOps-Übungen dienen kann. Die Kombination aus Spring Boot und JavaScript-Bibliotheken bietet eine gute Lernumgebung, um die verschiedenen Aspekte des DevOps-Zyklus zu verstehen und zu implementieren.

**Möge der Quellcode mit dir sein!** 🌌
