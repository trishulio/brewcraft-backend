package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class BrewDto extends BaseDto {
    
    private Long id;
    
    private Long batchId;
    
    private ProductDto product;
    
    private Long parentBrewId;
    
    private LocalDateTime createdAt;
            
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;
    
    private Integer version;

    public BrewDto(Long id, Long batchId, ProductDto productDto, Long parentBrewId, LocalDateTime createdAt,
            LocalDateTime startedAt, LocalDateTime endedAt, Integer version) {
        super();
        this.id = id;
        this.batchId = batchId;
        this.product = productDto;
        this.parentBrewId = parentBrewId;
        this.createdAt = createdAt;
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

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
