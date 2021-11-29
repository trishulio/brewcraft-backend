package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;

public class ProcurementMaterialLotDto extends BaseDto {
    private Long id;
    private String lotNumber;
    private QuantityDto qty;
    private StorageDto storage;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public ProcurementMaterialLotDto() {
    }

    public ProcurementMaterialLotDto(Long id) {
        this();
        setId(id);
    }

    public ProcurementMaterialLotDto(Long id, String lotNumber, QuantityDto qty, StorageDto storage, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setStorage(storage);
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

    public StorageDto getStorage() {
        return this.storage;
    }

    public void setStorage(StorageDto storage) {
        this.storage = storage;
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
