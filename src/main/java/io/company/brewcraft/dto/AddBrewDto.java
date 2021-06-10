package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddBrewDto extends BaseDto {
    
    @NotNull
    private Long batchId;
    
    @NotNull
    private Long productId;
    
    private Long parentBrewId;
            
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;

    public AddBrewDto(@NotNull Long batchId, @NotNull Long productId, Long parentBrewId, LocalDateTime startedAt,
            LocalDateTime endedAt) {
        super();
        this.batchId = batchId;
        this.productId = productId;
        this.parentBrewId = parentBrewId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
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

}
