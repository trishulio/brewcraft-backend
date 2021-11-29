package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.SupplierDto;

public class ProcurementPurchaseOrderDto extends BaseDto {

    private Long id;
    private String orderNumber;
    private SupplierDto supplier;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public ProcurementPurchaseOrderDto() {
    }

    public ProcurementPurchaseOrderDto(Long id) {
        setId(id);
    }

    public ProcurementPurchaseOrderDto(Long id, String orderNumber, SupplierDto supplier, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setOrderNumber(orderNumber);
        setSupplier(supplier);
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
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
