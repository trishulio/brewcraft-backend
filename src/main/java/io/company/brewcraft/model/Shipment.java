package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "shipment")
@Table
public class Shipment extends BaseEntity implements UpdateShipment<ShipmentItem>, Identified, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SHIPMENT_NUMBER = "shipmentNumber";
    public static final String FIELD_LOT_NUMBER = "lotNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_INVOICE = "invoice";
    public static final String FIELD_DELIVERY_DUE_DATE = "deliveryDueDate";
    public static final String FIELD_DELIVERED_DATE = "deliveredDate";
    public static final String FIELD_ITEMS = "items";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_generator")
    @SequenceGenerator(name = "shipment_generator", sequenceName = "shipment_sequence", allocationSize = 1)
    private Long id;
    
    @Column(name = "shipment_number")
    private String shipmentNumber;
    
    @Column(name = "lot_number")
    private String lotNumber;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_status_id", referencedColumnName = "id")
    private ShipmentStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;
    
    @Column(name = "delivery_due_date")
    private LocalDateTime deliveryDueDate;
    
    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShipmentItem> items;
    
    @Version
    private Integer version;

    public Shipment() {
    }

    public Shipment(Long id) {
        this();
        setId(id);
    }

    public Shipment(Long id, String shipmentNumber, String lotNumber, String description, ShipmentStatus shipmentStatus, Invoice invoice, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated,
            List<ShipmentItem> items, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setLotNumber(lotNumber);
        setDescription(description);
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public void setInvoice(Invoice invoice) {
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
    public List<ShipmentItem> getItems() {
        return items;
    }

    @Override
    public void setItems(List<ShipmentItem> items) {
        if (this.getItems() != null) {
            this.getItems().clear();
            this.getItems().addAll(items);
        } else if (items != null) {
            this.items = new ArrayList<>();
            items.forEach(item -> this.items.add(item));
        } else {
            this.items = null;
        }

        if (this.getItems() != null) {
            this.getItems().forEach(item -> item.setShipment(this));
        }
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Shipment clone() {
        return (Shipment) super.clone();
    }
}
