package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Long>, JpaSpecificationExecutor<Measure> {
}
