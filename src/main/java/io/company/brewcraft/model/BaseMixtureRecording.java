package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MeasureAccessor;

public interface BaseMixtureRecording extends MixtureAccessor, MeasureAccessor {

	BigDecimal getValue();
    
    void setValue(BigDecimal value);

	LocalDateTime getRecordedAt();

	void setRecordedAt(LocalDateTime recordedAt);

}
