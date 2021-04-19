package io.company.brewcraft.dto;

public class AddMaterialLotDto extends BaseDto {
    private String lotNumber;
    private QuantityDto qty;
    private Long materialId;
    private Long invoiceItemId;
    private Long storageId;

    public AddMaterialLotDto() {
    }


    public AddMaterialLotDto(String lotNumber, QuantityDto qty, Long materialId, Long invoiceItemId, Long storageId) {
        setLotNumber(lotNumber);
        setQuantity(qty);
        setMaterialId(materialId);
        setInvoiceItemId(invoiceItemId);
        setStorageId(storageId);
    }

    public String getLotNumber() {
        return this.lotNumber;
    }
    
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public QuantityDto getQuantity() {
        return qty;
    }

    public void setQuantity(QuantityDto qty) {
        this.qty = qty;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
    
    public Long getInvoiceItemId() {
        return this.invoiceItemId;
    }
    
    public void setInvoiceItemId(Long invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Long getStorageId() {
        return this.storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}