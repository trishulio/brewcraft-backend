package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;

public class UpdateProcurementItemDto extends BaseDto {
    private UpdateInvoiceItemDto invoiceItem;
    private UpdateMaterialLotDto lot;

    public UpdateProcurementItemDto() {
        this.invoiceItem = new UpdateInvoiceItemDto();
        this.lot = new UpdateMaterialLotDto();
    }

    public UpdateProcurementItemDto(ProcurementItemIdDto id) {
        this();
        this.setId(id);
    }

    public UpdateProcurementItemDto(ProcurementItemIdDto id, String description, String lotNumber, QuantityDto quantity, MoneyDto price, TaxDto tax, Long materialId, Long storageId, Integer invoiceItemVersion, Integer version) {
        this(id);
        setDescription(description);
        setLotNumber(lotNumber);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterialId(materialId);
        setStorageId(storageId);
        setInvoiceItemVersion(invoiceItemVersion);
        setVersion(version);
    }

    public ProcurementItemIdDto getId() {
        ProcurementItemIdDto id = null;
        if (this.lot.getId() != null || this.invoiceItem.getId() != null) {
            id = new ProcurementItemIdDto(this.lot.getId(), this.invoiceItem.getId());
        }

        return id;
    }

    public void setId(ProcurementItemIdDto id) {
        if (id != null) {
            this.lot.setId(id.getLotId());
            this.invoiceItem.setId(id.getInvoiceItemId());
        } else {
            this.lot.setId(null);
            this.invoiceItem.setId(null);
        }
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

    public Integer getInvoiceItemVersion() {
        return this.invoiceItem.getVersion();
    }

    public void setInvoiceItemVersion(Integer invoiceItemVersion) {
        this.invoiceItem.setVersion(invoiceItemVersion);
    }

    public Integer getVersion() {
        return this.lot.getVersion();
    }

    public void setVersion(Integer version) {
        this.lot.setVersion(version);
    }
}
