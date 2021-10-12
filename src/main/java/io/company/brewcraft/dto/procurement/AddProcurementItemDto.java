package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.AddInvoiceItemDto;
import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxDto;

public class AddProcurementItemDto extends BaseDto {
    private AddMaterialLotDto lot;
    private AddInvoiceItemDto invoiceItem;

    public AddProcurementItemDto() {
        this.lot = new AddMaterialLotDto();
        this.invoiceItem = new AddInvoiceItemDto();
    }

    public AddProcurementItemDto(String description, String lotNumber, QuantityDto quantity, MoneyDto price, TaxDto tax, Long materialId, Long storageId) {
        this();
        setDescription(description);
        setLotNumber(lotNumber);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterialId(materialId);
        setStorageId(storageId);
    }

    public String getDescription() {
        return this.invoiceItem.getDescription();
    }

    public void setDescription(String description) {
        this.invoiceItem.setDescription(description);
    }

    public String getLotNumber() {
        return this.lot.getLotNumber();
    }

    public void setLotNumber(String lotNumber) {
        this.lot.setLotNumber(lotNumber);
    }

    public QuantityDto getQuantity() {
        return this.lot.getQuantity();
    }

    public void setQuantity(QuantityDto quantity) {
        this.lot.setQuantity(quantity);
        this.invoiceItem.setQuantity(quantity);
    }

    public MoneyDto getPrice() {
        return this.invoiceItem.getPrice();
    }

    public void setPrice(MoneyDto price) {
        this.invoiceItem.setPrice(price);
    }

    public TaxDto getTax() {
        return this.invoiceItem.getTax();
    }

    public void setTax(TaxDto tax) {
        this.invoiceItem.setTax(tax);
    }

    public Long getMaterialId() {
        return this.invoiceItem.getMaterialId();
    }

    public void setMaterialId(Long materialId) {
        this.invoiceItem.setMaterialId(materialId);
    }

    public Long getStorageId() {
        return this.lot.getStorageId();
    }

    public void setStorageId(Long storageId) {
        this.lot.setStorageId(storageId);
    }
}
