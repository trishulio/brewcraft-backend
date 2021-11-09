package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.TaxDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcurementItemDto extends BaseDto {
    private InvoiceItemDto invoiceItem;
    private MaterialLotDto lot;

    public ProcurementItemDto() {
        this.invoiceItem = new InvoiceItemDto();
        this.lot = new MaterialLotDto();
    }

    public ProcurementItemDto(ProcurementItemIdDto id) {
        this();
        this.setId(id);
    }

    public ProcurementItemDto(ProcurementItemIdDto id, String description, String lotNumber, QuantityDto quantity, MoneyDto price, TaxDto tax, MoneyDto amount, MaterialDto material, StorageDto storage, LocalDateTime createdAt,
            LocalDateTime lastUpdated, Integer invoiceItemVersion, Integer version) {
        this(id);
        setDescription(description);
        setLotNumber(lotNumber);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setAmount(amount);
        setMaterial(material);
        setStorage(storage);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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

    public MoneyDto getAmount() {
        return this.invoiceItem.getAmount();
    }

    public void setAmount(MoneyDto amount) {
        this.invoiceItem.setAmount(amount);
    }

    public MaterialDto getMaterial() {
        return this.invoiceItem.getMaterial();
    }

    public void setMaterial(MaterialDto material) {
        this.invoiceItem.setMaterial(material);
    }

    public StorageDto getStorage() {
        return this.lot.getStorage();
    }

    public void setStorage(StorageDto storage) {
        this.lot.setStorage(storage);
    }

    public LocalDateTime getLastUpdated() {
        return this.lot.getLastUpdated();
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lot.setLastUpdated(lastUpdated);
        this.invoiceItem.setLastUpdated(lastUpdated);
    }

    public LocalDateTime getCreatedAt() {
        return this.lot.getCreatedAt();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.lot.setCreatedAt(createdAt);
        this.invoiceItem.setCreatedAt(createdAt);
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