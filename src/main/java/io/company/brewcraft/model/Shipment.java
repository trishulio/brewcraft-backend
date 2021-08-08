package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "shipment")
@Table
public class Shipment extends BaseEntity implements UpdateShipment<MaterialLot>, BaseShipment<MaterialLot>, Identified<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SHIPMENT_NUMBER = "shipmentNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DELIVERY_DUE_DATE = "deliveryDueDate";
    public static final String FIELD_DELIVERED_DATE = "deliveredDate";
    public static final String FIELD_LOTS = "lots";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_generator")
    @SequenceGenerator(name = "shipment_generator", sequenceName = "shipment_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_status_id", referencedColumnName = "id")
    private ShipmentStatus status;

    @Column(name = "delivery_due_date")
    private LocalDateTime deliveryDueDate;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MaterialLot> lots;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public Shipment() {
    }

    public Shipment(Long id) {
        this();
        setId(id);
    }

    public Shipment(Long id, String shipmentNumber, String description, ShipmentStatus shipmentStatus, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated,
            List<MaterialLot> lots, Integer version) {
        this(id);
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setStatus(shipmentStatus);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setLots(lots);
        setVersion(version);
    }

    public Shipment(Invoice invoice) {
        List<MaterialLot> lots = null;
        if (invoice.getItems() != null && invoice.getItems().size() != 0) {
            lots = invoice.getItems().stream().map(item -> new MaterialLot(item)).collect(Collectors.toList());
        }
        setShipmentNumber(invoice.getInvoiceNumber());
        setLots(lots);
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
    public List<MaterialLot> getLots() {
        List<MaterialLot> lots = null;
        if (this.lots != null) {
            lots = this.lots.stream().collect(Collectors.toList());
        }
        return lots;
    }

    @Override
    public void setLots(List<MaterialLot> lots) {
        if (this.lots == null) {
            this.lots = new ArrayList<>();
        } else {
            this.lots.stream().collect(Collectors.toList()).forEach(this::removeLot);
        }

        if (lots != null) {
            lots.stream().collect(Collectors.toList()).forEach(this::addLot);
        }
    }

    public void addLot(MaterialLot lot) {
        if (lot == null) {
            return;
        }

        if (this.lots == null) {
            this.lots = new ArrayList<>();
        }

        if (lot.getShipment() != this) {            
            lot.setShipment(this);
        }
        
        if (!this.lots.contains(lot)) {
            this.lots.add(lot);            
        }
    }

    public boolean removeLot(MaterialLot lot) {
        if (lot == null || this.lots == null) {
            return false;
        }

        boolean removed = this.lots.remove(lot);
        
        if (removed) {            
            lot.setShipment(null);
        }

        return removed;
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
