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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@Entity(name = "material_lot")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class MaterialLot extends BaseEntity implements UpdateMaterialLot<Shipment>, BaseMaterialLot<Shipment>, Audited, CrudEntity<Long> {
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
        @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol"))
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
        this.setId(id);
    }

    public MaterialLot(Long id, String lotNumber, Quantity<?> qty, InvoiceItem item, Storage storage, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setQuantity(qty);
        this.setInvoiceItem(item);
        this.setStorage(storage);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
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
    public String getLotNumber() {
        return this.lotNumber;
    }

    @Override
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    @JsonSetter
    public void setQuantity(Quantity<?> quantity) {
        BaseQuantityUnitAccessor.validateUnit(getMaterial(), quantity);
        quantity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);

        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    protected void setQuantity(QuantityEntity quantityEntity) {
        this.quantity = quantityEntity;
    }

    @Override
    public Shipment getShipment() {
        return this.shipment;
    }

    @Override
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public InvoiceItem getInvoiceItem() {
        return this.invoiceItem;
    }

    @Override
    public void setInvoiceItem(InvoiceItem invoiceItem) {
        Material material = null;
        if (invoiceItem != null) {
            material = invoiceItem.getMaterial();
        }
        BaseQuantityUnitAccessor.validateUnit(material, getQuantity());

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
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonIgnore
    public Material getMaterial() {
        Material material = null;
        if (this.invoiceItem != null) {
            material = this.invoiceItem.getMaterial();
        }

        return material;
    }
}
