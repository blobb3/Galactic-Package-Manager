package ch.zhaw.devops.gpm;

import ch.zhaw.devops.gpm.controller.PackageController;
import ch.zhaw.devops.gpm.entity.GalacticPackage;
import ch.zhaw.devops.gpm.repository.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackageController.class)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageRepository packageRepository;


    @Test
    public void testGetAllPackages() throws Exception {
        // Arrange
        GalacticPackage pkg1 = new GalacticPackage("pkg1", "desc1", "author1", "1.0", "cat1", 100, "Republic", new Date());
        GalacticPackage pkg2 = new GalacticPackage("pkg2", "desc2", "author2", "2.0", "cat2", 200, "Empire", new Date());
        when(packageRepository.findAll()).thenReturn(Arrays.asList(pkg1, pkg2));

        // Act & Assert
        mockMvc.perform(get("/api/packages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("pkg1")))
                .andExpect(jsonPath("$[1].name", is("pkg2")));

        verify(packageRepository, times(1)).findAll();
    }

    @Test
    public void testGetPackageById() throws Exception {
        // Arrange
        GalacticPackage pkg = new GalacticPackage("test-pkg", "desc", "author", "1.0", "cat", 100, "Neutral", new Date());
        when(packageRepository.findById(1L)).thenReturn(Optional.of(pkg));
        when(packageRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert - Package found
        mockMvc.perform(get("/api/packages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test-pkg")));

        // Act & Assert - Package not found
        mockMvc.perform(get("/api/packages/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(packageRepository, times(1)).findById(1L);
        verify(packageRepository, times(1)).findById(2L);
    }

    @Test
    public void testSearchPackages() throws Exception {
        // Arrange
        GalacticPackage pkg = new GalacticPackage("droid-api", "desc", "author", "1.0", "cat", 100, "Republic", new Date());
        when(packageRepository.findByNameContainingIgnoreCase("droid")).thenReturn(Arrays.asList(pkg));
        when(packageRepository.findByNameContainingIgnoreCase("nonexistent")).thenReturn(Arrays.asList());

        // Act & Assert - Found packages
        mockMvc.perform(get("/api/packages/search?query=droid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("droid-api")));

        // Act & Assert - No packages found
        mockMvc.perform(get("/api/packages/search?query=nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(packageRepository, times(1)).findByNameContainingIgnoreCase("droid");
        verify(packageRepository, times(1)).findByNameContainingIgnoreCase("nonexistent");
    }

    @Test
    public void testDeletePackage() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/packages/1"))
                .andExpect(status().isOk());

        verify(packageRepository, times(1)).deleteById(1L);
    }
}