package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.service.mapper.QuantityMapper;

public class ShipmentItem implements UpdateShipmentItem, Audited {

    private Long id;
    private QuantityEntity qty;
    private Material material;
    private Shipment shipment;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
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
