package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.MixtureRecording;

public interface MixtureRecordingRepository extends JpaRepository<MixtureRecording, Long>, JpaSpecificationExecutor<MixtureRecording>, EnhancedMixtureRecordingRepository {

}
