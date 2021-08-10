package io.company.brewcraft.dto;

public class UpdateMaterialDto extends BaseDto {

    private String name;

    private String description;

    private Long categoryId;

    private String upc;

    private String baseQuantityUnit;

    private Integer version;

    public UpdateMaterialDto() {
        super();
    }

    public UpdateMaterialDto(String name, String description, Long categoryId, String upc, String baseQuantityUnit, Integer version) {
        super();
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
