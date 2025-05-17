package ch.zhaw.devops.gpm.galactic_pm;

// import ch.zhaw.devops.gpm.galactic_pm.GalacticPackage;
// import ch.zhaw.devops.gpm.galactic_pm.PackageRepository;
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