package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material>, EnhancedMaterialRepository {
    @Query("select count(m) > 0 from MATERIAL m where m.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);
}
