package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class AddShipmentDto extends BaseDto {
    private String shipmentNumber;
    private String description;
    private String status;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private Collection<AddMaterialLotDto> lots;

    public AddShipmentDto() {
        
    } 
    
    public AddShipmentDto(String shipmentNumber, String description, String status, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Collection<AddMaterialLotDto> lots) {
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setStatus(status);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setLots(lots);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Collection<AddMaterialLotDto> getLots() {
        return lots;
    }

    public void setLots(Collection<AddMaterialLotDto> lots) {
        this.lots = lots;
    }
}