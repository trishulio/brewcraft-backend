package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MaterialPortionDto extends BaseDto {

    private Long id;
    
    private MaterialLotDto materialLot;

    private QuantityDto quantity;
    
    private Long mixtureId;
    
    private LocalDateTime addedAt;
    
    private Integer version;

    public MaterialPortionDto() {
        super();
    }

    public MaterialPortionDto(Long id) {
        this();
        this.id = id;
    }

    public MaterialPortionDto(Long id, MaterialLotDto materialLot, QuantityDto quantity, Long mixtureId, LocalDateTime addedAt, Integer version) {
        this(id);
        this.materialLot = materialLot;
        this.quantity = quantity;
        this.mixtureId = mixtureId;
        this.addedAt = addedAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialLotDto getMaterialLot() {
        return materialLot;
    }

    public void setMaterialLot(MaterialLotDto materialLot) {
        this.materialLot = materialLot;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }
    
    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}