package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class UpdateBrewStageDto extends BaseDto {

    private Long brewId;

    private Long statusId;

    private Long taskId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @NotNull
    private Integer version;

    public UpdateBrewStageDto() {
        super();
    }

    public UpdateBrewStageDto(Long brewId, Long statusId, Long taskId, LocalDateTime startedAt, LocalDateTime endedAt, Integer version) {
        this();
        this.brewId = brewId;
        this.statusId = statusId;
        this.taskId = taskId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.version = version;
    }

    public Long getBrewId() {
        return brewId;
    }

    public void setBrewId(Long brewId) {
        this.brewId = brewId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
