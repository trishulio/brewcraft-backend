package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.company.brewcraft.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment>, ExtendedRepository<Long> {

    @Override
    @Query("select count(i) > 0 from equipment u where e.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from equipment e where e.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}
