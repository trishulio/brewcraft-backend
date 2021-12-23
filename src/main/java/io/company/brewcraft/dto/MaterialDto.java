package io.company.brewcraft.dto;

public class MaterialDto extends BaseDto {
    private Long id;

    private String name;

    private String description;

    private CategoryDto materialClass;

    private CategoryDto category;

    private CategoryDto subcategory;

    private String upc;

    private String baseQuantityUnit;

    private String imageSrc;

    private Integer version;

    public MaterialDto() {
        super();
    }

    public MaterialDto(Long id) {
        this();
        this.id = id;
    }

    public MaterialDto(Long id, String name, String description, CategoryDto materialClass, CategoryDto type,
            CategoryDto subCategory, String upc, String baseQuantityUnit, String imageSrc, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.materialClass = materialClass;
        this.category = type;
        this.subcategory = subCategory;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
        this.imageSrc = imageSrc;
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

    public CategoryDto getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(CategoryDto materialClass) {
        this.materialClass = materialClass;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public CategoryDto getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(CategoryDto subcategory) {
        this.subcategory = subcategory;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
