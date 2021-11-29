package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.QuantityDto;

public class AddProcurementMaterialLotDto extends BaseDto {
    private String lotNumber;
    private QuantityDto qty;
    private Long storageId;

    public AddProcurementMaterialLotDto() {
    }

    public AddProcurementMaterialLotDto(String lotNumber, QuantityDto qty, Long storageId) {
        setLotNumber(lotNumber);
        setQuantity(qty);
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

    public Long getStorageId() {
        return this.storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}