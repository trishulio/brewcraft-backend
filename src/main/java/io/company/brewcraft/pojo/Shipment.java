package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.Collection;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.InvoiceEntity;

public class Shipment extends BaseModel implements UpdateShipment<ShipmentItem>, Identified, Audited {
    private Long id;
    private String shipmentNumber;
    private String lotNumber;
    private ShipmentStatus status;
    private InvoiceEntity invoice;
    private LocalDateTime deliveryDueDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Collection<ShipmentItem> items;
    private Integer version;

    public Shipment() {
    }

    public Shipment(Long id) {
        this();
        setId(id);
    }

    public Shipment(Long id, String shipmentNumber, String lotNumber, ShipmentStatus shipmentStatus, InvoiceEntity invoice, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated, Collection<ShipmentItem> items, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setLotNumber(lotNumber);
        setStatus(shipmentStatus);
        setInvoice(invoice);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setItems(items);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getShipmentNumber() {
        return shipmentNumber;
    }

    @Override
    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    @Override
    public String getLotNumber() {
        return lotNumber;
    }

    @Override
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public ShipmentStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    @Override
    public InvoiceEntity getInvoice() {
        return invoice;
    }

    @Override
    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    @Override
    public LocalDateTime getDeliveryDueDate() {
        return deliveryDueDate;
    }

    @Override
    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.deliveryDueDate = deliveryDueDate;
    }

    @Override
    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    @Override
    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Collection<ShipmentItem> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<ShipmentItem> items) {
        this.items = items;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
