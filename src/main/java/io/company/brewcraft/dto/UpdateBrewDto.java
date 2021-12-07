package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class UpdateBrewDto extends BaseDto {

    private String name;

    private String description;

    @NullOrNotBlank
    private String batchId;

    private Long productId;

    private Long parentBrewId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @NotNull
    private Integer version;

    public UpdateBrewDto() {
        super();
    }

    public UpdateBrewDto(String name, String description, String batchId, Long productId, Long parentBrewId, LocalDateTime startedAt,
            LocalDateTime endedAt, Integer version) {
        super();
        this.name = name;
        this.description = description;
        this.batchId = batchId;
        this.productId = productId;
        this.parentBrewId = parentBrewId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
