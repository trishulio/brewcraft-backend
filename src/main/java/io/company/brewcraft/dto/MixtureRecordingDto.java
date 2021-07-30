package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MixtureRecordingDto extends BaseDto {
	
	private Long id;
	
	private Long mixtureId;

    private MeasureDto measure;
    
    private String value;

    private LocalDateTime recordedAt;
    
    private Integer version;
    
    public MixtureRecordingDto() {
    	super();
    }
    
    public MixtureRecordingDto(Long id) {
    	this();
    	this.id = id;
    }
    
    public MixtureRecordingDto(Long id, Long mixtureId) {
    	this(id);
    	this.mixtureId = mixtureId;
    }

	public MixtureRecordingDto(Long id, Long mixtureId, MeasureDto measure,
			String value, LocalDateTime recordedAt, Integer version) {
		this(id, mixtureId);
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
	
	public Long getMixtureId() {
		return this.mixtureId;
	}
	
	public void setMixtureId(Long mixtureId) {
		this.mixtureId = mixtureId;
	}

	public MeasureDto getMeasure() {
		return measure;
	}

	public void setMeasure(MeasureDto measure) {
		this.measure = measure;
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
