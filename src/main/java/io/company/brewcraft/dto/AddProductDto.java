package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddProductDto extends BaseDto {

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private Long categoryId;

    @Valid
    private List<AddProductMeasureValueDto> targetMeasures;

    public AddProductDto() {
        super();
    }

    public AddProductDto(String name, String description, Long categoryId, List<AddProductMeasureValueDto> targetMeasures) {
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

    public List<AddProductMeasureValueDto> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<AddProductMeasureValueDto> targetMeasures) {
        this.targetMeasures = targetMeasures;
    }

}
