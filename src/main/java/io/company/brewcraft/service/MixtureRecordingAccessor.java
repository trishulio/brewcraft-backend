package io.company.brewcraft.service;

import io.company.brewcraft.model.MixtureRecording;

public interface MixtureRecordingAccessor {
    final String ATTR_MIXTURE_RECORDING = "mixtureRecording";

    MixtureRecording getMixtureRecording();

    void setMixtureRecording(MixtureRecording mixtureRecording);
}
