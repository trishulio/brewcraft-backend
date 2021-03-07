package io.company.brewcraft.dto;

public class UpdateProductDto extends BaseDto {
    
    private String name;
    
    private String description;
    
    private Long categoryId;
        
    private ProductMeasuresDto targetMeasures;
    
    private Integer version;
    
    public UpdateProductDto() {
        super();
    }

    public UpdateProductDto(String name, String description, Long categoryId, ProductMeasuresDto targetMeasures,
            Integer version) {
        super();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.targetMeasures = targetMeasures;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public ProductMeasuresDto getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(ProductMeasuresDto targetMeasures) {
        this.targetMeasures = targetMeasures;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
