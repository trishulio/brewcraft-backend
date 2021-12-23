package io.company.brewcraft.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;


public class UpdateMixtureRecordingDto extends BaseDto {

    private Long id;

    private Long mixtureId;

    private Long measureId;

    private BigDecimal value;

    private LocalDateTime recordedAt;

    @NotNull
    private Integer version;

    public UpdateMixtureRecordingDto() {
        super();
    }

    public UpdateMixtureRecordingDto(Long id, Long mixtureId, Long measureId, BigDecimal value, LocalDateTime recordedAt, Integer version) {
        this();
        this.id = id;
        this.mixtureId = mixtureId;
        this.measureId = measureId;
        this.value = value;
        this.recordedAt = recordedAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
