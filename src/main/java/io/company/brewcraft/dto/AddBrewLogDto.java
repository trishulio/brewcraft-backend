package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddBrewLogDto extends BaseDto {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long brewStageId;
    
    @NotNull
    private LocalDateTime recordedAt;
            
    private String comment;

	public AddBrewLogDto(@NotNull Long userId, @NotNull Long brewStageId, @NotNull LocalDateTime recordedAt,
			String comment) {
		super();
		this.userId = userId;
		this.brewStageId = brewStageId;
		this.recordedAt = recordedAt;
		this.comment = comment;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBrewStageId() {
		return brewStageId;
	}

	public void setBrewStageId(Long brewStageId) {
		this.brewStageId = brewStageId;
	}

	public LocalDateTime getRecordedAt() {
		return recordedAt;
	}

	public void setRecordedAt(LocalDateTime recordedAt) {
		this.recordedAt = recordedAt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
   
}
