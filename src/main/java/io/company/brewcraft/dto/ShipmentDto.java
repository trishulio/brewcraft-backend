package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;


public class ShipmentDto extends BaseDto {
    private Long id;
    private String shipmentNumber;
    private String description;
    private ShipmentStatusDto shipmentStatus;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private List<MaterialLotDto> lots;
    private Integer version;

    public ShipmentDto() {
    }

    public ShipmentDto(Long id) {
        this();
        setId(id);
    }

    public ShipmentDto(Long id, String shipmentNumber, String description, ShipmentStatusDto shipmentStatus, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated, List<MaterialLotDto> lots, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setShipmentStatus(shipmentStatus);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setLots(lots);
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

    public List<MaterialLotDto> getLots() {
        return lots;
    }

    public void setLots(List<MaterialLotDto> lots) {
        this.lots = lots;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
