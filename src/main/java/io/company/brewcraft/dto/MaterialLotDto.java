package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MaterialLotDto extends BaseDto{
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private MaterialDto material;
    private InvoiceItemDto invoiceItem;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public MaterialLotDto() {
    }

    public MaterialLotDto(Long id) {
        this();
        setId(id);
    }

    public MaterialLotDto(Long id, String lotNumber, QuantityDto qty, MaterialDto material, InvoiceItemDto invoiceItem, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setMaterial(material);
        setInvoiceItem(invoiceItem);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public InvoiceItemDto getInvoiceItem() {
        return this.invoiceItem;
    }

    public void setInvoiceItem(InvoiceItemDto invoiceItem) { 
        this.invoiceItem = invoiceItem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
