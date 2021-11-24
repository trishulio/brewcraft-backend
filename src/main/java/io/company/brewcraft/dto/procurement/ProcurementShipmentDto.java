package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.ShipmentStatusDto;

public class ProcurementShipmentDto extends BaseDto {
    private Long id;
    private String shipmentNumber;
    private String description;
    private ShipmentStatusDto shipmentStatus;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public ProcurementShipmentDto() {
    }

    public ProcurementShipmentDto(Long id) {
        this();
        setId(id);
    }

    public ProcurementShipmentDto(Long id, String shipmentNumber, String description, ShipmentStatusDto shipmentStatus, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setShipmentStatus(shipmentStatus);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
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

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShipmentStatusDto getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatusDto shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public LocalDateTime getDeliveryDueDate() {
        return deliveryDueDate;
    }

    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.deliveryDueDate = deliveryDueDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
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
