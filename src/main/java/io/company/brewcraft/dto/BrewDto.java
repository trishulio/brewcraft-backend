package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class BrewDto extends BaseDto {
    
    private Long id;
    
    private String name;

    private String description;
    
    private Long batchId;
    
    private ProductDto product;
    
    private Long parentBrewId;
                
    private LocalDateTime startedAt;
    
    private LocalDateTime endedAt;
    
    private LocalDateTime createdAt;
    
    private Integer version;
    
    public BrewDto() {
    	super();
    }

    public BrewDto(Long id, String name, String description, Long batchId, ProductDto productDto, Long parentBrewId,
            LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime createdAt, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.batchId = batchId;
        this.product = productDto;
        this.parentBrewId = parentBrewId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
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
