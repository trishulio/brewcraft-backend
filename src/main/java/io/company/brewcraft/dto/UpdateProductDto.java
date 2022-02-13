package io.company.brewcraft.dto;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

public class UpdateProductDto extends BaseDto {

    @NullOrNotBlank
    private String name;

    private String description;

    private Long categoryId;

    @Valid
    private List<AddProductMeasureValueDto> targetMeasures;

    private URI imageSrc;

    private Integer version;

    public UpdateProductDto() {
        super();
    }

    public UpdateProductDto(String name, String description, Long categoryId, List<AddProductMeasureValueDto> targetMeasures,
            URI imageSrc, Integer version) {
        this();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.targetMeasures = targetMeasures;
        this.imageSrc = imageSrc;
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

    public List<AddProductMeasureValueDto> getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(List<AddProductMeasureValueDto> targetMeasures) {
        this.targetMeasures = targetMeasures;
    }

    public URI getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(URI imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
