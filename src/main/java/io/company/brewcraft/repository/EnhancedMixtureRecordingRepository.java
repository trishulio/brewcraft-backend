package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MixtureRecording;

public interface EnhancedMixtureRecordingRepository {
    
	void refresh(Collection<MixtureRecording> mixtureRecordings);
    
}
