package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MeasureAccessor;

public interface BaseMixtureRecording extends MixtureAccessor, MeasureAccessor {

    String getValue();
    
    void setValue(String value);

	LocalDateTime getRecordedAt();

	void setRecordedAt(LocalDateTime recordedAt);

}
