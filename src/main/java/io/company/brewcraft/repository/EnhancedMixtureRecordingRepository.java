package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MixtureRecordingAccessor;

public interface EnhancedMixtureRecordingRepository extends EnhancedRepository<MixtureRecording, MixtureRecordingAccessor> {

    void refresh(Collection<MixtureRecording> mixtureRecordings);

}
