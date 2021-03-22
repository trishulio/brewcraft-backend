package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class UpdateShipmentDto extends BaseDto {
    private String shipmentNumber;
    private String lotNumber;
    private String description;
    private String status;
    private Long invoiceId;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private Collection<UpdateShipmentItemDto> items;
    private Integer version;

    public UpdateShipmentDto(String shipmentNumber, String lotNumber, String description, String status, Long invoiceId, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Collection<UpdateShipmentItemDto> items, Integer version) {
        setShipmentNumber(shipmentNumber);
        setLotNumber(lotNumber);
        setDescription(description);
        setStatus(status);
        setInvoiceId(invoiceId);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setItems(items);
        setVersion(version);
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public Collection<UpdateShipmentItemDto> getItems() {
        return items;
    }

    public void setItems(Collection<UpdateShipmentItemDto> items) {
        this.items = items;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
