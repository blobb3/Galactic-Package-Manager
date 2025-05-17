package ch.zhaw.devops.gpm.galactic_pm.config;


import ch.zhaw.devops.gpm.galactic_pm.entity.GalacticPackage;
import ch.zhaw.devops.gpm.galactic_pm.repository.PackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PackageRepository  packageRepository;

    @Override
    public void run(String... args) {
        // Demo-Pakete nur erstellen, wenn Repository leer ist
        if (packageRepository.count() == 0) {
            createSamplePackages();
        }
    }

    private void createSamplePackages() {
        // Beispielpaket 1: Hyperspace Navigation
        GalacticPackage hyperNav = new GalacticPackage();
        hyperNav.setName("hyperspace-navigation");
        hyperNav.setDescription("Advanced navigation library for hyperspace jumps with minimal calculation errors.");
        hyperNav.setAuthor("Han Solo");
        hyperNav.setVersion("2.3.1");
        hyperNav.setCategory("Navigation");
        hyperNav.setDownloads(12420);
        hyperNav.setCompatibility("Neutral");
        hyperNav.setReleaseDate(new Date());
        packageRepository.save(hyperNav);

        // Beispielpaket 2: Droid Communication
        GalacticPackage droidComm = new GalacticPackage();
        droidComm.setName("droid-communication");
        droidComm.setDescription("API for seamless communication with all types of droids. Binary translation included.");
        droidComm.setAuthor("C-3PO");
        droidComm.setVersion("1.0.8");
        droidComm.setCategory("Communication");
        droidComm.setDownloads(8753);
        droidComm.setCompatibility("Republic");
        droidComm.setReleaseDate(new Date());
        packageRepository.save(droidComm);

        // Beispielpaket 3: Lightsaber Effects
        GalacticPackage saberFX = new GalacticPackage();
        saberFX.setName("lightsaber-effects");
        saberFX.setDescription("Visual effects and sound library for lightsaber animations. Used by 9/10 Jedi Knights.");
        saberFX.setAuthor("Mace Windu");
        saberFX.setVersion("3.5.2");
        saberFX.setCategory("Visual");
        saberFX.setDownloads(22876);
        saberFX.setCompatibility("Republic");
        saberFX.setReleaseDate(new Date());
        packageRepository.save(saberFX);

        // Beispielpaket 4: Imperial Scanner
        GalacticPackage impScanner = new GalacticPackage();
        impScanner.setName("imperial-scanner");
        impScanner.setDescription("Advanced scanning algorithms to detect rebel ships. 60% of the time, it works every time.");
        impScanner.setAuthor("Darth Vader");
        impScanner.setVersion("1.6.6");
        impScanner.setCategory("Security");
        impScanner.setDownloads(15840);
        impScanner.setCompatibility("Empire");
        impScanner.setReleaseDate(new Date());
        packageRepository.save(impScanner);

        // Weitere Pakete nach Bedarf hinzufügen...
    }
}