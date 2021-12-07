package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "shipment")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Shipment extends BaseEntity implements UpdateShipment<MaterialLot>, BaseShipment<MaterialLot>, CrudEntity<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SHIPMENT_NUMBER = "shipmentNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SHIPMENT_STATUS = "shipmentStatus";
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
    private ShipmentStatus shipmentStatus;

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
        this.setId(id);
    }

    public Shipment(Long id, String shipmentNumber, String description, ShipmentStatus shipmentStatus, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated,
            List<MaterialLot> lots, Integer version) {
        this(id);
        this.setShipmentNumber(shipmentNumber);
        this.setDescription(description);
        this.setShipmentStatus(shipmentStatus);
        this.setDeliveryDueDate(deliveryDueDate);
        this.setDeliveredDate(deliveredDate);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
        this.setLots(lots);
        this.setVersion(version);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getShipmentNumber() {
        return this.shipmentNumber;
    }

    @Override
    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ShipmentStatus getShipmentStatus() {
        return this.shipmentStatus;
    }

    @Override
    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    @Override
    public LocalDateTime getDeliveryDueDate() {
        return this.deliveryDueDate;
    }

    @Override
    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.deliveryDueDate = deliveryDueDate;
    }

    @Override
    public LocalDateTime getDeliveredDate() {
        return this.deliveredDate;
    }

    @Override
    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
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

        if (lots == null) {
            this.lots = null;
        } else {
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

        final boolean removed = this.lots.remove(lot);

        if (removed) {
            lot.setShipment(null);
        }

        return removed;
    }
    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonIgnore
    public int getLotCount() {
        int count = 0;
        if (this.lots != null) {
            count = this.lots.size();
        }

        return count;
    }

    @Override
    @JsonIgnore
    public void setInvoiceItemsFromInvoice(Invoice invoice) {
        if (invoice != null && invoice.getItemCount() > 0) {
            if (this.getLotCount() != invoice.getItemCount()) {
                String msg = String.format("Expected same number of invoiceItems as the lots. Found %s invoiceItems and %s lots", invoice.getItemCount(), this.getLotCount());
                throw new IllegalStateException(msg);
            }

            Iterator<InvoiceItem> invoiceItems = invoice.getInvoiceItems().iterator();
            this.lots.forEach(lot -> lot.setInvoiceItem(invoiceItems.next()));
        }
    }
}
