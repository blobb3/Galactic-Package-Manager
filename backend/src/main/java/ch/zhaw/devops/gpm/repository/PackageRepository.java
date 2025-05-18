package ch.zhaw.devops.gpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.devops.gpm.entity.GalacticPackage;

import java.util.List;

public interface PackageRepository extends JpaRepository<GalacticPackage, Long> {
    List<GalacticPackage> findByNameContainingIgnoreCase(String name);
    List<GalacticPackage> findByCompatibility(String compatibility);
    List<GalacticPackage> findByCategory(String category);
}