package io.company.brewcraft.dto;

import java.util.List;

public class SkuDto extends BaseDto {
    private Long id;

    private String number;

    private String name;

    private String description;

    private ProductDto product;

    private List<SkuMaterialDto> materials;

    private QuantityDto quantity;

    private boolean isPackageable;

    private Integer version;

    public SkuDto() {
        super();
    }

    public SkuDto(Long id) {
        this();
        this.id = id;
    }

    public SkuDto(Long id, String number, String name, String description, ProductDto product, List<SkuMaterialDto> materials, QuantityDto quantity, boolean isPackageable, Integer version) {
        this(id);
        this.number = number;
        this.name = name;
        this.description = description;
        this.product = product;
        this.materials = materials;
        this.quantity = quantity;
        this.isPackageable = isPackageable;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public List<SkuMaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<SkuMaterialDto> materials) {
        this.materials = materials;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public boolean getIsPackageable() {
        return isPackageable;
    }

    public void setIsPackageable(boolean isPackageable) {
        this.isPackageable = isPackageable;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
