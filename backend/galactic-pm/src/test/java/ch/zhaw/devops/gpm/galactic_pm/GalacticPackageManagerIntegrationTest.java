package ch.zhaw.devops.gpm.galactic_pm;

import ch.zhaw.devops.gpm.galactic_pm.entity.GalacticPackage;
import ch.zhaw.devops.gpm.galactic_pm.repository.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GalacticPackageManagerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PackageRepository packageRepository;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/packages";
    }

    @SuppressWarnings("null")
    @Test
    public void testGetAllPackages() {
        ResponseEntity<GalacticPackage[]> response = restTemplate.getForEntity(
            getRootUrl(), GalacticPackage[].class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @SuppressWarnings("null")
    @Test
    public void testCRUDOperations() {
        // Create a new package
        GalacticPackage newPackage = new GalacticPackage(
            "test-integration", "Integration test package", "Tester", 
            "1.0.0", "Testing", 0, "Neutral", new Date()
        );
        
        ResponseEntity<GalacticPackage> createResponse = restTemplate.postForEntity(
            getRootUrl(), newPackage, GalacticPackage.class);
        
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("test-integration", createResponse.getBody().getName());
        
        Long newId = createResponse.getBody().getId();
        
        // Read the created package
        ResponseEntity<GalacticPackage> getResponse = restTemplate.getForEntity(
            getRootUrl() + "/" + newId, GalacticPackage.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(newId, getResponse.getBody().getId());
        
        // Update the package
        GalacticPackage updatedPackage = getResponse.getBody();
        updatedPackage.setName("updated-integration");
        updatedPackage.setDownloads(100);
        
        HttpEntity<GalacticPackage> requestEntity = new HttpEntity<>(updatedPackage);
        ResponseEntity<GalacticPackage> updateResponse = restTemplate.exchange(
            getRootUrl() + "/" + newId, HttpMethod.PUT, requestEntity, GalacticPackage.class);
        
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("updated-integration", updateResponse.getBody().getName());
        assertEquals(100, updateResponse.getBody().getDownloads());
        
        // Delete the package
        restTemplate.delete(getRootUrl() + "/" + newId);
        
        // Verify it's deleted
        ResponseEntity<GalacticPackage> verifyDeleteResponse = restTemplate.getForEntity(
            getRootUrl() + "/" + newId, GalacticPackage.class);
        
        assertEquals(HttpStatus.OK, verifyDeleteResponse.getStatusCode());
        assertNull(verifyDeleteResponse.getBody());
    }

    @SuppressWarnings("null")
    @Test
    public void testSearchByName() {
        // First ensure we have a package with a unique name for testing
        GalacticPackage uniquePackage = new GalacticPackage(
            "unique-search-test", "Package for search test", "Searcher", 
            "1.0.0", "Testing", 0, "Neutral", new Date()
        );
        packageRepository.save(uniquePackage);
        
        ResponseEntity<GalacticPackage[]> response = restTemplate.getForEntity(
            getRootUrl() + "/search?query=unique-search", GalacticPackage[].class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
        assertEquals("unique-search-test", response.getBody()[0].getName());
        
        // Clean up
        packageRepository.delete(uniquePackage);
    }

    @SuppressWarnings("null")
    @Test
    public void testFilterByCompatibility() {
        // First ensure we have a package with Empire compatibility
        GalacticPackage empirePackage = new GalacticPackage(
            "empire-only-package", "Empire compatibility test", "Vader", 
            "1.0.0", "Testing", 0, "Empire", new Date()
        );
        packageRepository.save(empirePackage);
        
        ResponseEntity<GalacticPackage[]> response = restTemplate.getForEntity(
            getRootUrl() + "/compatibility/Empire", GalacticPackage[].class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
        
        boolean found = false;
        for (GalacticPackage pkg : response.getBody()) {
            if (pkg.getName().equals("empire-only-package")) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Empire package should be in filtered results");
        
        // Clean up
        packageRepository.delete(empirePackage);
    }
}