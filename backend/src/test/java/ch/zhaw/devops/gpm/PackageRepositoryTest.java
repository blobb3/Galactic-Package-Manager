package ch.zhaw.devops.gpm;

import ch.zhaw.devops.gpm.entity.GalacticPackage;
import ch.zhaw.devops.gpm.repository.PackageRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PackageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void testFindByNameContainingIgnoreCase() {
        // Arrange
        GalacticPackage pkg1 = new GalacticPackage("droid-api", "API for droids", "R2D2", 
                                                  "1.0.0", "API", 100, "Republic", new Date());
        GalacticPackage pkg2 = new GalacticPackage("saber-effects", "Lightsaber effects", "Luke", 
                                                  "2.0.0", "Visual", 200, "Republic", new Date());
        entityManager.persist(pkg1);
        entityManager.persist(pkg2);
        entityManager.flush();

        // Act
        List<GalacticPackage> result1 = packageRepository.findByNameContainingIgnoreCase("droid");
        List<GalacticPackage> result2 = packageRepository.findByNameContainingIgnoreCase("DROID");
        List<GalacticPackage> result3 = packageRepository.findByNameContainingIgnoreCase("saber");
        List<GalacticPackage> result4 = packageRepository.findByNameContainingIgnoreCase("nonexistent");

        // Assert
        assertEquals(1, result1.size());
        assertEquals(pkg1.getId(), result1.get(0).getId());
        
        assertEquals(1, result2.size()); // Testing case-insensitivity
        assertEquals(pkg1.getId(), result2.get(0).getId());
        
        assertEquals(1, result3.size());
        assertEquals(pkg2.getId(), result3.get(0).getId());
        
        assertEquals(0, result4.size());
    }

    @Test
    public void testFindByCompatibility() {
        // Arrange
        GalacticPackage pkg1 = new GalacticPackage("republic-lib", "Republic library", "Obi-Wan", 
                                                  "1.0.0", "Utility", 100, "Republic", new Date());
        GalacticPackage pkg2 = new GalacticPackage("empire-toolkit", "Empire toolkit", "Vader", 
                                                  "1.0.0", "Toolkit", 200, "Empire", new Date());
        GalacticPackage pkg3 = new GalacticPackage("neutral-api", "Neutral API", "Boba Fett", 
                                                  "1.0.0", "API", 300, "Neutral", new Date());
        entityManager.persist(pkg1);
        entityManager.persist(pkg2);
        entityManager.persist(pkg3);
        entityManager.flush();

        // Act
        List<GalacticPackage> republicPackages = packageRepository.findByCompatibility("Republic");
        List<GalacticPackage> empirePackages = packageRepository.findByCompatibility("Empire");
        List<GalacticPackage> neutralPackages = packageRepository.findByCompatibility("Neutral");
        List<GalacticPackage> rebelPackages = packageRepository.findByCompatibility("Rebel");

        // Assert
        assertEquals(1, republicPackages.size());
        assertEquals(pkg1.getId(), republicPackages.get(0).getId());
        
        assertEquals(1, empirePackages.size());
        assertEquals(pkg2.getId(), empirePackages.get(0).getId());
        
        assertEquals(1, neutralPackages.size());
        assertEquals(pkg3.getId(), neutralPackages.get(0).getId());
        
        assertEquals(0, rebelPackages.size());
    }

    @Test
    public void testFindByCategory() {
        // Arrange
        GalacticPackage pkg1 = new GalacticPackage("nav-lib", "Navigation lib", "Han Solo", 
                                                  "1.0.0", "Navigation", 100, "Neutral", new Date());
        GalacticPackage pkg2 = new GalacticPackage("comm-api", "Communication API", "Leia", 
                                                  "1.0.0", "Communication", 200, "Republic", new Date());
        entityManager.persist(pkg1);
        entityManager.persist(pkg2);
        entityManager.flush();

        // Act
        List<GalacticPackage> navPackages = packageRepository.findByCategory("Navigation");
        List<GalacticPackage> commPackages = packageRepository.findByCategory("Communication");
        List<GalacticPackage> securityPackages = packageRepository.findByCategory("Security");

        // Assert
        assertEquals(1, navPackages.size());
        assertEquals(pkg1.getId(), navPackages.get(0).getId());
        
        assertEquals(1, commPackages.size());
        assertEquals(pkg2.getId(), commPackages.get(0).getId());
        
        assertEquals(0, securityPackages.size());
    }
}