package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddProductDto extends BaseDto {
    
    @NotEmpty
    private String name;
    
    private String description;
    
    @NotNull
    private Long categoryId;
            
    private ProductMeasuresDto targetMeasures;
    
    public AddProductDto() {
        super();
    }

    public AddProductDto(@NotEmpty String name, String description, @NotNull Long categoryId,
            ProductMeasuresDto targetMeasures) {
        super();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.targetMeasures = targetMeasures;
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
    
}
