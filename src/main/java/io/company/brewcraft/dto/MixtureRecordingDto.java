package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MixtureRecordingDto extends BaseDto {
	
	private Long id;

    @NotNull
    private ProductMeasureDto measure;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
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

	public MixtureRecordingDto(Long id, @NotEmpty ProductMeasureDto measure,
			@Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank") String value, LocalDateTime recordedAt, Integer version) {
		this(id);
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

	public ProductMeasureDto getMeasure() {
		return measure;
	}

	public void setMeasure(ProductMeasureDto measure) {
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
