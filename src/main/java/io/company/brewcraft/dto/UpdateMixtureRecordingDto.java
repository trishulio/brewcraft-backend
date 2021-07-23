package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateMixtureRecordingDto extends BaseDto {

    private String name;
    
    private String value;

    private LocalDateTime recordedAt;
    
    private Integer version;
    
    public UpdateMixtureRecordingDto() {
    	super();
    }

	public UpdateMixtureRecordingDto(String name, String value, LocalDateTime recordedAt, Integer version) {
		this();
		this.name = name;
		this.value = value;
		this.recordedAt = recordedAt;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
