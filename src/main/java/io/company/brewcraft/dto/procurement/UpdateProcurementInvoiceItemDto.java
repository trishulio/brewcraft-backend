package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxDto;

public class UpdateProcurementInvoiceItemDto extends BaseDto {
    private Long id;
    private String description;
    private QuantityDto quantity;
    private MoneyDto price;
    private TaxDto tax;
    private Long materialId;
    private Integer version;

    public UpdateProcurementInvoiceItemDto() {
    }

    public UpdateProcurementInvoiceItemDto(Long id) {
        this();
        setId(id);
    }

    public UpdateProcurementInvoiceItemDto(Long id, String description, QuantityDto quantity, MoneyDto price, TaxDto tax, Long materialId, Integer version) {
        this(id);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterialId(materialId);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public MoneyDto getPrice() {
        return price;
    }

    public void setPrice(MoneyDto price) {
        this.price = price;
    }

    public TaxDto getTax() {
        return tax;
    }

    public void setTax(TaxDto tax) {
        this.tax = tax;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
