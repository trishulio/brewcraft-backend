package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MixtureMaterialPortionDto extends BaseDto {

    private Long id;

    private MaterialLotDto materialLot;

    private QuantityDto quantity;

    private MixtureDto mixtureDto;

    private LocalDateTime addedAt;

    private Integer version;

    public MixtureMaterialPortionDto() {
        super();
    }

    public MixtureMaterialPortionDto(Long id) {
        this();
        this.id = id;
    }

    public MixtureMaterialPortionDto(Long id, MaterialLotDto materialLot, QuantityDto quantity, MixtureDto mixtureDto, LocalDateTime addedAt, Integer version) {
        this(id);
        this.materialLot = materialLot;
        this.quantity = quantity;
        this.mixtureDto = mixtureDto;
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

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public MixtureDto getMixture() {
        return this.mixtureDto;
    }

    public void setMixture(MixtureDto mixtureDto) {
        this.mixtureDto = mixtureDto;
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