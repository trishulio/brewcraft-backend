package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class UpdateFinishedGoodDto extends BaseDto {
    
    private Long skuId;
    
    private List<UpdateMixturePortionDto> mixturePortions;
    
    private List<UpdateMaterialPortionDto> materialPortions;
    
    @NotNull
    private Integer version;
        
    public UpdateFinishedGoodDto() {
        super();
    }

    public UpdateFinishedGoodDto(Long skuId, List<UpdateMixturePortionDto> mixturePortions,
            List<UpdateMaterialPortionDto> materialPortions, Integer version) {
        this();
        this.skuId = skuId;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.version = version;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<UpdateMixturePortionDto> getMixturePortions() {
        return mixturePortions;
    }

    public void setMixturePortions(List<UpdateMixturePortionDto> mixturePortions) {
        this.mixturePortions = mixturePortions;
    }

    public List<UpdateMaterialPortionDto> getMaterialPortions() {
        return materialPortions;
    }

    public void setMaterialPortions(List<UpdateMaterialPortionDto> materialPortions) {
        this.materialPortions = materialPortions;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
         
}
