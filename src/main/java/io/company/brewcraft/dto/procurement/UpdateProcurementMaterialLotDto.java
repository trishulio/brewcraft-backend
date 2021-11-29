package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.QuantityDto;

public class UpdateProcurementMaterialLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private Long storageId;
    private Integer version;

    public UpdateProcurementMaterialLotDto() {
    }

    public UpdateProcurementMaterialLotDto(Long id) {
        setId(id);
    }

    public UpdateProcurementMaterialLotDto(Long id, String lotNumber, QuantityDto qty, Long storageId, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
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
