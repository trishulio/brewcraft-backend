package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddBrewDto extends BaseDto {
    private String name;

    private String description;

    @NotBlank
    private String batchId;

    @NotNull
    private Long productId;

    private Long parentBrewId;

    private Long assignedToUserId;

    private Long ownedByUserId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public AddBrewDto() {
        super();
    }

    public AddBrewDto(String name, String description, @NotNull String batchId, @NotNull Long productId,
            Long parentBrewId, LocalDateTime startedAt, LocalDateTime endedAt, Long assignedToUserId, Long ownedByUserId) {
        this();
        this.name = name;
        this.description = description;
        this.batchId = batchId;
        this.productId = productId;
        this.parentBrewId = parentBrewId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.assignedToUserId = assignedToUserId;
        this.ownedByUserId = ownedByUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getParentBrewId() {
        return parentBrewId;
    }

    public void setParentBrewId(Long parentBrewId) {
        this.parentBrewId = parentBrewId;
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

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public Long getOwnedByUserId() {
        return ownedByUserId;
    }

    public void setOwnedByUserId(Long ownedByUserId) {
        this.ownedByUserId = ownedByUserId;
    }
}
