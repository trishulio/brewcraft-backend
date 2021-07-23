package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddMixtureRecordingDto extends BaseDto {

    @NotNull
    private Long measureId;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String value;

    private LocalDateTime recordedAt;
    
    public AddMixtureRecordingDto() {
    	super();
    }
    
	public AddMixtureRecordingDto(Long measureId, String value, LocalDateTime recordedAt) {
		this();
		this.measureId = measureId;
		this.value = value;
		this.recordedAt = recordedAt;
	}

	public Long getMeasureId() {
		return measureId;
	}

	public void setMeasureId(Long measureId) {
		this.measureId = measureId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getRecordedAt() {
		return recordedAt;
	}

	public void setRecordedAt(LocalDateTime recordedAt) {
		this.recordedAt = recordedAt;
	}

}
