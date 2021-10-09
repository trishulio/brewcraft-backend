package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class UpdateMixtureMaterialPortionDto extends UpdateMaterialPortionBaseDto {
    
    private Long mixtureId;
    
    public UpdateMixtureMaterialPortionDto() {
        super();
    }

    public UpdateMixtureMaterialPortionDto(Long materialLotId, QuantityDto quantity, Long mixtureId, LocalDateTime addedAt, Integer version) {
        super(materialLotId, quantity, addedAt, version);
        this.mixtureId = mixtureId;
    }

    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

}