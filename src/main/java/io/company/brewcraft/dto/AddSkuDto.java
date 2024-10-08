package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddSkuDto extends BaseDto {
    @NotBlank
    private String number;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long productId;

    @NotNull
    private List<AddSkuMaterialDto> materials;

    private QuantityDto quantity;

    @NotNull
    private boolean isPackageable;

    public AddSkuDto() {
        super();
    }

    public AddSkuDto(String number, String name, String description, @NotNull Long productId, @NotNull List<AddSkuMaterialDto> materials, QuantityDto quantity, boolean isPackageable) {
        super();
        this.number = number;
        this.name = name;
        this.description = description;
        this.productId = productId;
        this.materials = materials;
        this.quantity = quantity;
        this.isPackageable = isPackageable;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<AddSkuMaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<AddSkuMaterialDto> materials) {
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
}
