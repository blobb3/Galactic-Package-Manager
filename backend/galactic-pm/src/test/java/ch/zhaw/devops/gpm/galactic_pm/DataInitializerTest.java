package ch.zhaw.devops.gpm.galactic_pm;

import ch.zhaw.devops.gpm.galactic_pm.config.DataInitializer;
import ch.zhaw.devops.gpm.galactic_pm.repository.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @MockBean
    private PackageRepository packageRepository;

    @Test
    public void testRunShouldCreateSamplePackagesWhenRepositoryIsEmpty() throws Exception {
        // Arrange
        when(packageRepository.count()).thenReturn(0L);

        // Act
        dataInitializer.run();

        // Assert
        // Verify that save was called 8 times (for each demo package)
        verify(packageRepository, times(8)).save(any());
    }

    @Test
    public void testRunShouldNotCreateSamplePackagesWhenRepositoryIsNotEmpty() throws Exception {
        // Arrange
        when(packageRepository.count()).thenReturn(5L);

        // Act
        dataInitializer.run();

        // Assert
        // Verify that save was never called
        verify(packageRepository, never()).save(any());
    }
}