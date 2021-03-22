package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "shipment_item")
@Table
public class ShipmentItem extends BaseModel implements UpdateShipmentItem, Audited {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_item_generator")
    @SequenceGenerator(name = "shipment_item_generator", sequenceName = "shipment_item_sequence", allocationSize = 1)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "qty_id", referencedColumnName = "id")
    private QuantityEntity qty;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private MaterialEntity material;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    private Shipment shipment;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public ShipmentItem() {
    }

    public ShipmentItem(Long id) {
        this();
        setId(id);
    }

    public ShipmentItem(Long id, Quantity<?> qty, Shipment shipment, Material material, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setQuantity(qty);
        setMaterial(material);
        setShipment(shipment);
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
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.qty);
    }

    @Override
    public void setQuantity(Quantity<?> qty) {
        this.qty = QuantityMapper.INSTANCE.toEntity(qty);
    }

    @Override
    public Material getMaterial() {
        return MaterialMapper.INSTANCE.fromEntity(material, new CycleAvoidingMappingContext());
    }

    @Override
    public void setMaterial(Material material) {
        this.material = MaterialMapper.INSTANCE.toEntity(material, new CycleAvoidingMappingContext());
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
