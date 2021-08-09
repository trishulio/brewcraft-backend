package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "material_lot")
@Table
public class MaterialLot extends BaseEntity implements UpdateMaterialLot<Shipment>, BaseMaterialLot<Shipment>, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOT_NUMBER = "lotNumber";
    public static final String FIELD_QTY = "quantity";
    public static final String FIELD_SHIPMENT = "shipment";
    public static final String FIELD_INVOICE_ITEM = "invoiceItem";
    public static final String FIELD_STORAGE = "storage";
    public static final String FIELD_LAST_UPDATED = "lastUpdated";
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_VERSION = "version";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_lot_generator")
    @SequenceGenerator(name = "material_lot_generator", sequenceName = "material_lot_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "lot_number")
    private String lotNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "qty_value"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "qty_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @JsonBackReference
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_item_id", referencedColumnName = "id")
    private InvoiceItem invoiceItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private Storage storage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public MaterialLot() {
    }

    public MaterialLot(Long id) {
        this();
        setId(id);
    }

    public MaterialLot(Long id, String lotNumber, Quantity<?> qty, InvoiceItem item, Storage storage, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setInvoiceItem(item);
        setStorage(storage);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public MaterialLot(InvoiceItem item) {
        setInvoiceItem(item);
        setQuantity(item.getQuantity());
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
    public String getLotNumber() {
        return lotNumber;
    }

    @Override
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.quantity);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    public void setQuantity(QuantityEntity quantityEntity) {
        this.quantity = quantityEntity;
    }

    @Override
    public Shipment getShipment() {
        return this.shipment;
    }

    @Override
    public void setShipment(Shipment shipment) {
        if (this.shipment != null) {
            this.shipment.removeLot(this);
        }

        this.shipment = shipment;

        if (this.shipment != null) {
            this.shipment.addLot(this);
        }
    }

    @Override
    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    @Override
    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    @Override
    public Storage getStorage() {
        return this.storage;
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
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
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
