package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MixtureRecordingAccessor;

public class EnhancedMixtureRecordingRepositoryImpl implements EnhancedMixtureRecordingRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRecordingRepositoryImpl.class);

    private MeasureRepository measureRepository;

    private MixtureRepository mixtureRepository;

    private AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresher;

    public EnhancedMixtureRecordingRepositoryImpl(MeasureRepository measureRepository, MixtureRepository mixtureRepository, AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresher) {
        this.measureRepository = measureRepository;
        this.mixtureRepository = mixtureRepository;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MixtureRecording> mixtureRecordings) {
        this.measureRepository.refreshAccessors(mixtureRecordings);
        this.mixtureRepository.refreshAccessors(mixtureRecordings);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureRecordingAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
