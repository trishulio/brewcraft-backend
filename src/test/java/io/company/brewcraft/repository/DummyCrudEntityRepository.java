package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.DummyCrudEntity;
import io.company.brewcraft.model.DummyCrudEntityAccessor;

public interface DummyCrudEntityRepository extends JpaRepository<DummyCrudEntity, Long>, JpaSpecificationExecutor<DummyCrudEntity>, EnhancedRepository<DummyCrudEntity, DummyCrudEntityAccessor>, ExtendedRepository<Long> {
}