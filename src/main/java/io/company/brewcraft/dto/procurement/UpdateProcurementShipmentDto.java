package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;

public class UpdateProcurementShipmentDto extends BaseDto {
    private Long id;
    private String shipmentNumber;
    private String description;
    private Long shipmentStatusId;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private Integer version;

    public UpdateProcurementShipmentDto() {
        super();
    }

    public UpdateProcurementShipmentDto(Long id) {
        this();
        setId(id);
    }

    public UpdateProcurementShipmentDto(Long id, String shipmentNumber, String description, Long shipmentStatusId, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setShipmentStatusId(shipmentStatusId);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
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

    public Long getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(Long shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
