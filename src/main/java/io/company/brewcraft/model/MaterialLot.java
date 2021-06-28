package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "material_lot")
@Table
public class MaterialLot extends BaseEntity implements UpdateMaterialLot<Shipment>, BaseMaterialLot<Shipment>, Audited, Identified<Long> {
    public static enum AggregationField implements PathProvider {
        LOT_NUMBER("lotNumber"),
        MATERIAL("material"),
        SHIPMENT("shipment"),
        STORAGE("storage"),
        QUANTITY_UNIT("quantity", "unit"),
        QUANTITY_VALUE("quantity", "value");

        private String[] path; 

        private AggregationField(String... path) {
            this.path = path;
        }

        @Override
        public String[] getPath() {
            return this.path;
        }
    }

    public static final String FIELD_ID = "id";
    public static final String FIELD_LOT_NUMBER = "lotNumber";
    public static final String FIELD_QTY = "qty";
    public static final String FIELD_MATERIAL = "material";
    public static final String FIELD_SHIPMENT = "shipment";
    public static final String FIELD_INVOICE_ITEM = "invoiceItem";
    public static final String FIELD_STORAGE = "storage";

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
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
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

    public MaterialLot(Long id, String lotNumber, Quantity<?> qty, Material material, InvoiceItem item, Storage storage, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setMaterial(material);
        setInvoiceItem(item);
        setStorage(storage);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public MaterialLot(String lotNumber, UnitEntity unit, BigDecimal value) {
        setLotNumber(lotNumber);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(Shipment shipment, UnitEntity unit, BigDecimal value) {
        setShipment(shipment);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(Material material, UnitEntity unit, BigDecimal value) {
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(Storage storage, UnitEntity unit, BigDecimal value) {
        setStorage(storage);
        setQuantity(new QuantityEntity(unit, value));
    }

    public MaterialLot(String lotNumber, Material material, UnitEntity unit, BigDecimal value) {
        setLotNumber(lotNumber);
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(Shipment shipment, Material material, UnitEntity unit, BigDecimal value) {
        setShipment(shipment);
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(Storage storage, Material material, UnitEntity unit, BigDecimal value) {
        setStorage(storage);
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }
    
    public MaterialLot(String lotNumber, Shipment shipment, Material material, UnitEntity unit, BigDecimal value) {
        setLotNumber(lotNumber);
        setShipment(shipment);
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }

    public MaterialLot(String lotNumber, Shipment shipment, Storage storage, Material material, UnitEntity unit, BigDecimal value) {
        setLotNumber(lotNumber);
        setShipment(shipment);
        setStorage(storage);
        setMaterial(material);
        setQuantity(new QuantityEntity(unit, value));
    }

    public MaterialLot(InvoiceItem item) {
        setInvoiceItem(item);
        setMaterial(item.getMaterial());
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
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
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
