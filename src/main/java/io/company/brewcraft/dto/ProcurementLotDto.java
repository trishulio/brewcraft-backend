package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcurementLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto quantity;
    private InvoiceItemDto invoiceItem;
    private StorageDto storage;
    private MaterialDto material;

    public ProcurementLotDto() {
    }

    public ProcurementLotDto(Long id) {
        this();
        this.setId(id);
    }

    public ProcurementLotDto(Long id, String lotNumber, QuantityDto quantity, InvoiceItemDto invoiceItem, StorageDto storage, MaterialDto materialLotDto) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setQuantity(quantity);
        this.setInvoiceItem(invoiceItem);
        this.setStorage(storage);
        this.setMaterial(materialLotDto);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public QuantityDto getQuantity() {
        return this.quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public InvoiceItemDto getInvoiceItem() {
        return this.invoiceItem;
    }

    public void setInvoiceItem(InvoiceItemDto invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public StorageDto getStorage() {
        return this.storage;
    }

    public void setStorage(StorageDto storage) {
        this.storage = storage;
    }

    public MaterialDto getMaterial() {
        return this.material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }
}
