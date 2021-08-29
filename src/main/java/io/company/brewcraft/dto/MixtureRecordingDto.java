package io.company.brewcraft.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MixtureRecordingDto extends BaseDto {

    private Long id;

    private MixtureDto mixtureDto;

    private MeasureDto measure;

    private BigDecimal value;

    private LocalDateTime recordedAt;

    private Integer version;

    public MixtureRecordingDto() {
        super();
    }

    public MixtureRecordingDto(Long id) {
        this();
        this.id = id;
    }

    public MixtureRecordingDto(Long id, MixtureDto mixtureDto) {
        this(id);
        this.mixtureDto = mixtureDto;
    }

    public MixtureRecordingDto(Long id, MixtureDto mixtureDto, MeasureDto measure,
            BigDecimal value, LocalDateTime recordedAt, Integer version) {
        this(id, mixtureDto);
        this.measure = measure;
        this.value = value;
        this.recordedAt = recordedAt;
        this.version = version;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MixtureDto getMixture() {
        return this.mixtureDto;
    }

    public void setMixture(MixtureDto mixtureDto) {
        this.mixtureDto = mixtureDto;
    }

    public MeasureDto getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureDto measure) {
        this.measure = measure;
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
