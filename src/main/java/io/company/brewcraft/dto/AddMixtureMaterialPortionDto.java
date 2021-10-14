package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddMixtureMaterialPortionDto extends AddMaterialPortionBaseDto {
    
    @NotNull
    private Long mixtureId;
        
    public AddMixtureMaterialPortionDto() {
        super();
    }

    public AddMixtureMaterialPortionDto(Long materialLotId, QuantityDto quantity, Long mixtureId, LocalDateTime addedAt) {
        super(materialLotId, quantity, addedAt);
        this.mixtureId = mixtureId;
    }
    
    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

}