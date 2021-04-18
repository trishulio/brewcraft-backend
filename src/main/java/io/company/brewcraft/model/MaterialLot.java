package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.measure.Quantity;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "shipment_lot")
@Table
public class MaterialLot extends BaseEntity implements UpdateMaterialLot, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOT_NUMBER = "lotNumber";
    public static final String FIELD_QTY = "qty";
    public static final String FIELD_MATERIAL = "material";
    public static final String FIELD_SHIPMENT = "shipment";
    public static final String FIELD_INVOICE_ITEM = "invoiceItem";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_lot_generator")
    @SequenceGenerator(name = "shipment_lot_generator", sequenceName = "shipment_lot_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "lot_number")
    private String lotNumber;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "qty_id", referencedColumnName = "id")
    private QuantityEntity qty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_item_id", referencedColumnName = "id")
    private InvoiceItem invoiceItem;

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

    public MaterialLot(Long id, String lotNumber, Quantity<?> qty, Material material, Shipment shipment, InvoiceItem item, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setQuantity(qty);
        setMaterial(material);
        setShipment(shipment);
        setInvoiceItem(item);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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
    public String getLotNumber() {
        return lotNumber;
    }

    @Override
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.qty);
    }

    @Override
    public void setQuantity(Quantity<?> qty) {
        this.qty = QuantityMapper.INSTANCE.toEntity(qty);
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
            this.shipment.getLots().remove(this);
        }

        if (shipment != null) {
            if (shipment.getLots() == null) {
                shipment.setLots(new ArrayList<>(0));
            }
            shipment.getLots().add(this);
        }

        this.shipment = shipment;
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
    public void setLastUpdated(LocalDateTime updatedAt) {
        this.lastUpdated = updatedAt;
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
