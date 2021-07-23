package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AddMixtureRecordingDto extends BaseDto {

    @NotEmpty
    private String name;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String value;

    private LocalDateTime recordedAt;
    
    public AddMixtureRecordingDto() {
    	super();
    }
    
	public AddMixtureRecordingDto(@NotEmpty String name,
			@Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank") String value, LocalDateTime recordedAt) {
		this();
		this.name = name;
		this.value = value;
		this.recordedAt = recordedAt;
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

}
