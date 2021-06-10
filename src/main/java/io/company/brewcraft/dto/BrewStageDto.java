package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class BrewStageDto extends BaseDto {
    
    private Long id;
    
    private Long brewId;
    
    private BrewStageStatusDto status;
    
    private BrewTaskDto task;
                    
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;

    private Integer version;
    
    public BrewStageDto() {
    	super();
    }

    public BrewStageDto(Long id, Long brewId, BrewStageStatusDto status, BrewTaskDto task, LocalDateTime startedAt, LocalDateTime endedAt,
            Integer version) {
        super();
        this.id = id;
        this.brewId = brewId;
        this.status = status;
        this.task = task;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getBrewId() {
        return brewId;
    }

    public void setBrewId(Long brewId) {
        this.brewId = brewId;
    }
    
    public BrewStageStatusDto getStatus() {
        return status;
    }

    public void setStatus(BrewStageStatusDto status) {
        this.status = status;
    }

    public BrewTaskDto getTask() {
        return task;
    }

    public void setTask(BrewTaskDto task) {
        this.task = task;
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
