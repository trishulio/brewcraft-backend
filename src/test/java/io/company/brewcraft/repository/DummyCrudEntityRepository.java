package io.company.brewcraft.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.DummyCrudEntity;
import io.company.brewcraft.model.DummyCrudEntityAccessor;
import io.company.brewcraft.repository.ExtendedRepository;
import io.company.brewcraft.repository.Refresher;

@Profile("IgnoredFromSpringContextTests")
public interface DummyCrudEntityRepository extends JpaRepository<DummyCrudEntity, Long>, JpaSpecificationExecutor<DummyCrudEntity>, Refresher<DummyCrudEntity, DummyCrudEntityAccessor>, ExtendedRepository<Long> {
}