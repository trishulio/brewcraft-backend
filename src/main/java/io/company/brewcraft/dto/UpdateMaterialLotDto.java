package io.company.brewcraft.dto;

public class UpdateMaterialLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private Long invoiceItemId;
    private Long storageId;
    private Integer version;

    public UpdateMaterialLotDto() {
    }

    public UpdateMaterialLotDto(Long id) {
        setId(id);
    }

    public UpdateMaterialLotDto(Long id, String lotNumber, QuantityDto qty, Long invoiceItemId, Long storageId, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setInvoiceItemId(invoiceItemId);
        setStorageId(storageId);
        setVersion(version);
    }

    public Long getId() {
        return id;
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
        return qty;
    }

    public void setQuantity(QuantityDto qty) {
        this.qty = qty;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
