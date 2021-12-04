package io.company.brewcraft.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddMixtureRecordingDto extends BaseDto {

    @NotNull
    private Long mixtureId;

    @NotNull
    private Long measureId;

    @NotNull
    private BigDecimal value;

    private LocalDateTime recordedAt;

    public AddMixtureRecordingDto() {
        super();
    }

    public AddMixtureRecordingDto(Long mixtureId, Long measureId, BigDecimal value, LocalDateTime recordedAt) {
        this();
        this.mixtureId = mixtureId;
        this.measureId = measureId;
        this.value = value;
        this.recordedAt = recordedAt;
    }

    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
