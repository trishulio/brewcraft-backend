package io.company.brewcraft.dto;

public class UpdateMaterialLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private Long materialId;
    private Long invoiceItemId;
    private Integer version;

    public UpdateMaterialLotDto() {
    }

    public UpdateMaterialLotDto(Long id) {
        setId(id);
    }

    public UpdateMaterialLotDto(Long id, String lotNumber, QuantityDto qty, Long materialId, Long invoiceItemId, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setMaterialId(materialId);
        setInvoiceItemId(invoiceItemId);
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
