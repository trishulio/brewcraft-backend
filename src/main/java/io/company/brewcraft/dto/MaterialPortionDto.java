package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MaterialPortionDto extends BaseDto {

    private Long id;
    
    private Long mixtureId;

    private MaterialLotDto materialLot;

    private QuantityDto quantity;
    
    private LocalDateTime addedAt;

    public MaterialPortionDto() {
        super();
    }

    public MaterialPortionDto(Long id) {
        this();
        this.id = id;
    }

    public MaterialPortionDto(Long id, Long mixtureId, MaterialLotDto materialLot, QuantityDto quantity, LocalDateTime addedAt) {
        this(id);
        this.mixtureId = mixtureId;
        this.materialLot = materialLot;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
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

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}