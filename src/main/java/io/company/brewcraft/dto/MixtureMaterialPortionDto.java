package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class MixtureMaterialPortionDto extends MaterialPortionBaseDto {
    
    private MixtureDto mixtureDto;

    public MixtureMaterialPortionDto() {
        super();
    }

    public MixtureMaterialPortionDto(Long id) {
        super(id);
    }

    public MixtureMaterialPortionDto(Long id, MaterialLotDto materialLot, QuantityDto quantity, MixtureDto mixtureDto, LocalDateTime addedAt, Integer version) {
        super(id, materialLot, quantity, addedAt, version);
        this.mixtureDto = mixtureDto;
    }

  
    public MixtureDto getMixture() {
        return this.mixtureDto;
    }

    public void setMixture(MixtureDto mixtureDto) {
        this.mixtureDto = mixtureDto;
    }

}