package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.company.brewcraft.service.MeasureAccessor;
import io.company.brewcraft.service.MixtureAccessor;

public interface BaseMixtureRecording extends MixtureAccessor, MeasureAccessor {
    BigDecimal getValue();

    void setValue(BigDecimal value);

    LocalDateTime getRecordedAt();

    void setRecordedAt(LocalDateTime recordedAt);

}
