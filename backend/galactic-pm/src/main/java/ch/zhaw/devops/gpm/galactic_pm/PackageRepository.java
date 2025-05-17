package ch.zhaw.devops.gpm.galactic_pm;

// import ch.zhaw.devops.gpm.galactic_pm.GalacticPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PackageRepository extends JpaRepository<GalacticPackage, Long> {
    List<GalacticPackage> findByNameContainingIgnoreCase(String name);
    List<GalacticPackage> findByCompatibility(String compatibility);
    List<GalacticPackage> findByCategory(String category);
}