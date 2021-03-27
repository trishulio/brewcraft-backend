package io.company.brewcraft.dto;

import java.util.List;

public class ProductDto extends BaseDto {
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private CategoryDto productClass;

    private CategoryDto type;

    private CategoryDto style;

    private List<ProductMeasureDto> targetMeasures;
        
    private Integer version;
    
    public ProductDto() {
        super();
    }

    public ProductDto(Long id, String name, String description, CategoryDto productClass, CategoryDto type,
            CategoryDto style, List<ProductMeasureDto> targetMeasures, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.productClass = productClass;
        this.type = type;
        this.style = style;
        this.targetMeasures = targetMeasures;
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

    public CategoryDto getProductClass() {
        return productClass;
    }

    public void setProductClass(CategoryDto productClass) {
        this.productClass = productClass;
    }

    public CategoryDto getType() {
        return type;
    }

    public void setType(CategoryDto type) {
        this.type = type;
    }

    public CategoryDto getStyle() {
        return style;
    }

    public void setStyle(CategoryDto style) {
        this.style = style;
    }

    public List<ProductMeasureDto> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<ProductMeasureDto> targetMeasures) {
        this.targetMeasures = targetMeasures;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    } 
}
