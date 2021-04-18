package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MaterialLotDto extends BaseDto{
    private Long id;
    private QuantityDto qty;
    private MaterialDto material;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private Integer version;

    public MaterialLotDto() {
    }

    public MaterialLotDto(Long id) {
        this();
        setId(id);
    }

    public MaterialLotDto(Long id, QuantityDto qty, MaterialDto material, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setQuantity(qty);
        setMaterial(material);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuantityDto getQuantity() {
        return qty;
    }

    public void setQuantity(QuantityDto qty) {
        this.qty = qty;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
