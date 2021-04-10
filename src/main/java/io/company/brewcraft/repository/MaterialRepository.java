package io.company.brewcraft.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import io.company.brewcraft.model.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long>, JpaSpecificationExecutor<MaterialEntity> {

    @Query("select count(m) > 0 from MATERIAL m where m.id in (:ids)")
    boolean existsByIds(Set<Long> ids);
}
