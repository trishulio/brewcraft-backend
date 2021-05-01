package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class UpdateShipmentDto extends BaseDto {
    private String shipmentNumber;
    private String description;
    private Long statusId;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private Collection<UpdateMaterialLotDto> lots;
    private Integer version;

    public UpdateShipmentDto() {
    }

    public UpdateShipmentDto(String shipmentNumber, String description, Long statusId, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Collection<UpdateMaterialLotDto> lots, Integer version) {
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setStatusId(statusId);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setLots(lots);
        setVersion(version);
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public Collection<UpdateMaterialLotDto> getLots() {
        return lots;
    }

    public void setLots(Collection<UpdateMaterialLotDto> lots) {
        this.lots = lots;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
