package io.company.brewcraft.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateMixtureRecordingDto extends BaseDto {
	
	private Long mixtureId;

    private Long measureId;
    
    private BigDecimal value;

    private LocalDateTime recordedAt;
    
    private Integer version;
    
    public UpdateMixtureRecordingDto() {
    	super();
    }

	public UpdateMixtureRecordingDto(Long mixtureId, Long measureId, BigDecimal value, LocalDateTime recordedAt, Integer version) {
		this();
		this.mixtureId = mixtureId;
		this.measureId = measureId;
		this.value = value;
		this.recordedAt = recordedAt;
		this.version = version;
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
