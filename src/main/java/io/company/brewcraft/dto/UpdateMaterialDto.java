package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateMaterialDto extends BaseDto {

    @NullOrNotBlank
    private String name;

    private String description;

    private Long categoryId;

    @Size(max = 12)
    private String upc;

    private String baseQuantityUnit;

    private String imageSrc;

    @NotNull
    private Integer version;

    public UpdateMaterialDto() {
        super();
    }

    public UpdateMaterialDto(String name, String description, Long categoryId, String upc, String baseQuantityUnit, String imageSrc, Integer version) {
        super();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
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

    public void setCategoryId(Long category) {
        this.categoryId = category;
    }

    public String getUPC() {
        return upc;
    }

    public void setUPC(String upc) {
        this.upc = upc;
    }

    public String getBaseQuantityUnit() {
        return baseQuantityUnit;
    }

    public void setBaseQuantityUnit(String baseQuantityUnit) {
        this.baseQuantityUnit = baseQuantityUnit;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
