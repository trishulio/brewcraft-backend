package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.service.MeasureAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MixtureRecordingAccessor;

public class MixtureRecordingRefresher implements Refresher<MixtureRecording, MixtureRecordingAccessor> {
    private static final Logger log = LoggerFactory.getLogger(MixtureRecordingRefresher.class);

    private final Refresher<Measure, MeasureAccessor> measureRefresher;

    private final Refresher<Mixture, MixtureAccessor> mixtureRefresher;

    private final AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresher;

    public MixtureRecordingRefresher(Refresher<Measure, MeasureAccessor> measureRefresher, Refresher<Mixture, MixtureAccessor> mixtureRefresher, AccessorRefresher<Long, MixtureRecordingAccessor, MixtureRecording> refresher) {
        this.measureRefresher = measureRefresher;
        this.mixtureRefresher = mixtureRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MixtureRecording> mixtureRecordings) {
        this.measureRefresher.refreshAccessors(mixtureRecordings);
        this.mixtureRefresher.refreshAccessors(mixtureRecordings);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureRecordingAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
