package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddProductDto extends BaseDto {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long categoryId;

    @Valid
    private List<AddProductMeasureValueDto> targetMeasures;
    
    private String imageSrc;

    public AddProductDto() {
        super();
    }

    public AddProductDto(String name, String description, Long categoryId, List<AddProductMeasureValueDto> targetMeasures, String imageSrc) {
        this();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.targetMeasures = targetMeasures;
        this.imageSrc = imageSrc;
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

    public List<AddProductMeasureValueDto> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<AddProductMeasureValueDto> targetMeasures) {
        this.targetMeasures = targetMeasures;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
