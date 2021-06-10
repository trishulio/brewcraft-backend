package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class UpdateBrewStageDto extends BaseDto {
    
    private Long taskId;
    
    private Long statusId;
                
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;

    private Integer version;

    public UpdateBrewStageDto(Long taskId, Long statusId, LocalDateTime startedAt, LocalDateTime endedAt, Integer version) {
        super();
        this.taskId = taskId;
        this.statusId = statusId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
