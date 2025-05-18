package ch.zhaw.devops.gpm.galactic_pm;

import org.junit.jupiter.api.Test;
import ch.zhaw.devops.gpm.galactic_pm.entity.GalacticPackage;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class GalacticPackageTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String name = "test-package";
        String description = "Test description";
        String author = "Test Author";
        String version = "1.0.0";
        String category = "Test";
        int downloads = 100;
        String compatibility = "Neutral";
        Date releaseDate = new Date();

        // Act
        GalacticPackage pkg = new GalacticPackage(name, description, author, version, 
                                                 category, downloads, compatibility, releaseDate);

        // Assert
        assertEquals(name, pkg.getName());
        assertEquals(description, pkg.getDescription());
        assertEquals(author, pkg.getAuthor());
        assertEquals(version, pkg.getVersion());
        assertEquals(category, pkg.getCategory());
        assertEquals(downloads, pkg.getDownloads());
        assertEquals(compatibility, pkg.getCompatibility());
        assertEquals(releaseDate, pkg.getReleaseDate());
    }

    @Test
    public void testSetters() {
        // Arrange
        GalacticPackage pkg = new GalacticPackage();
        Long id = 1L;
        String name = "updated-package";
        String description = "Updated description";
        String author = "Updated Author";
        String version = "2.0.0";
        String category = "Updated";
        int downloads = 200;
        String compatibility = "Empire";
        Date releaseDate = new Date();

        // Act
        pkg.setId(id);
        pkg.setName(name);
        pkg.setDescription(description);
        pkg.setAuthor(author);
        pkg.setVersion(version);
        pkg.setCategory(category);
        pkg.setDownloads(downloads);
        pkg.setCompatibility(compatibility);
        pkg.setReleaseDate(releaseDate);

        // Assert
        assertEquals(id, pkg.getId());
        assertEquals(name, pkg.getName());
        assertEquals(description, pkg.getDescription());
        assertEquals(author, pkg.getAuthor());
        assertEquals(version, pkg.getVersion());
        assertEquals(category, pkg.getCategory());
        assertEquals(downloads, pkg.getDownloads());
        assertEquals(compatibility, pkg.getCompatibility());
        assertEquals(releaseDate, pkg.getReleaseDate());
    }
}