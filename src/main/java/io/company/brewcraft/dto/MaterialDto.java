package io.company.brewcraft.dto;

public class MaterialDto extends BaseDto {
    private Long id;
    
    private String name;
    
    private String description;
    
    private MaterialCategoryDto materialClass;
    
    private MaterialCategoryDto category;

    private MaterialCategoryDto subcategory;
    
    private String upc;

    private String baseQuantityUnit;
    
    private Integer version;
    
    public MaterialDto() {
        super();
    }
    
    public MaterialDto(Long id) {
        this();
        this.id = id;
    }

    public MaterialDto(Long id, String name, String description, MaterialCategoryDto materialClass, MaterialCategoryDto type,
            MaterialCategoryDto subCategory, String upc, String baseQuantityUnit, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.materialClass = materialClass;
        this.category = type;
        this.subcategory = subCategory;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
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

    public MaterialCategoryDto getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(MaterialCategoryDto materialClass) {
        this.materialClass = materialClass;
    }

    public MaterialCategoryDto getCategory() {
        return category;
    }

    public void setCategory(MaterialCategoryDto category) {
        this.category = category;
    }

    public MaterialCategoryDto getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(MaterialCategoryDto subcategory) {
        this.subcategory = subcategory;
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
