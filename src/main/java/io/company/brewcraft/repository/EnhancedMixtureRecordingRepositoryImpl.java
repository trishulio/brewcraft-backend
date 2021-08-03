package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MixtureRecording;

public class EnhancedMixtureRecordingRepositoryImpl implements EnhancedMixtureRecordingRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRecordingRepositoryImpl.class);
    
    private MeasureRepository measureRepository;
    
    private MixtureRepository mixtureRepository;
        
    public EnhancedMixtureRecordingRepositoryImpl(MeasureRepository measureRepository, MixtureRepository mixtureRepository) {
        this.measureRepository = measureRepository;
        this.mixtureRepository = mixtureRepository;
    }

	@Override
	public void refresh(Collection<MixtureRecording> mixtureRecordings) {
        this.measureRepository.refreshAccessors(mixtureRecordings);
        this.mixtureRepository.refreshAccessors(mixtureRecordings);
	}
}
