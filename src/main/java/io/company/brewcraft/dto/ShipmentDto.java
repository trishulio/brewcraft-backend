package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentDto extends BaseDto {
    private Long id;
    private String shipmentNumber;
    private String lotNumber;
    private ShipmentStatusDto status;
    private InvoiceDto invoice;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Collection<ShipmentItemDto> items;
    private Integer version;

    public ShipmentDto() {
    }

    public ShipmentDto(Long id) {
        this();
        setId(id);
    }

    public ShipmentDto(Long id, String shipmentNumber, String lotNumber, ShipmentStatusDto status, InvoiceDto invoice, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated, Collection<ShipmentItemDto> items, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setLotNumber(lotNumber);
        setStatus(status);
        setInvoice(invoice);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setItems(items);
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public ShipmentStatusDto getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatusDto status) {
        this.status = status;
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
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

    public Collection<ShipmentItemDto> getItems() {
        return items;
    }

    public void setItems(Collection<ShipmentItemDto> items) {
        this.items = items;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
