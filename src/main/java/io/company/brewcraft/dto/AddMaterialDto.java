package io.company.brewcraft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddMaterialDto extends BaseDto {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long categoryId;

    @Size(max = 12)
    private String upc;

    @NotBlank
    private String baseQuantityUnit;

    private String imageSrc;

    public AddMaterialDto() {
        super();
    }

    public AddMaterialDto(String name, String description, Long categoryId, String upc, String baseQuantityUnit, String imageSrc) {
        super();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
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

    public void setCategoryId(Long category) {
        this.categoryId = category;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
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
}
