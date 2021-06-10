package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MixtureRecordingDto extends BaseDto {

    @NotEmpty
    private String name;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String value;

    private LocalDateTime recordedAt;
    
    private Integer version;

	public MixtureRecordingDto(@NotEmpty String name,
			@Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank") String value, LocalDateTime recordedAt, Integer version) {
		super();
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
