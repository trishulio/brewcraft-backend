package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.user.UserDto;

public class BrewDto extends BaseDto {

    private Long id;

    private String name;

    private String description;

    private String batchId;

    private ProductDto product;

    private Long parentBrewId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private UserDto assignedTo;

    private UserDto ownedBy;

    private LocalDateTime createdAt;

    private Integer version;

    public BrewDto() {
        super();
    }

    public BrewDto(Long id) {
        this();
        this.id = id;
    }

    public BrewDto(Long id, String name, String description, String batchId, ProductDto productDto, Long parentBrewId,
            LocalDateTime startedAt, LocalDateTime endedAt, UserDto assignedTo, UserDto ownedBy, LocalDateTime createdAt, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.batchId = batchId;
        this.product = productDto;
        this.parentBrewId = parentBrewId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.assignedTo = assignedTo;
        this.ownedBy = ownedBy;
        this.createdAt = createdAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Long getParentBrewId() {
        return parentBrewId;
    }

    public void setParentBrewId(Long parentBrewId) {
        this.parentBrewId = parentBrewId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public UserDto getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserDto assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserDto getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(UserDto ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
