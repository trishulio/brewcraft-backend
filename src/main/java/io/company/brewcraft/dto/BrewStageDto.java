package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class BrewStageDto extends BaseDto {
    
    private Long id;
    
    private Long brewId;
    
    private String task;
    
    private String status;
                
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;

    private Integer version;

    public BrewStageDto(Long id, Long brewId, String task, String status, LocalDateTime startedAt, LocalDateTime endedAt,
            Integer version) {
        super();
        this.id = id;
        this.brewId = brewId;
        this.task = task;
        this.status = status;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
