package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddBrewStageDto extends BaseDto {
    
    @NotNull
    private Long taskId;
    
    @NotNull
    private Long statusId;
                
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;

    public AddBrewStageDto(@NotNull Long taskId, @NotNull Long statusId, LocalDateTime startedAt, LocalDateTime endedAt) {
        super();
        this.taskId = taskId;
        this.statusId = statusId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
    
}
