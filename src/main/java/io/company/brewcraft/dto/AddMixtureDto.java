package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddMixtureDto extends BaseDto {
    
    private Long parentMixtureId;
    
    @NotNull
    private QuantityDto quantity;
                
    @NotNull
    private Long equipmentId;
    
    private List<AddMaterialPortionDto> materialPortions;

	public AddMixtureDto(Long parentMixtureId, @NotNull QuantityDto quantity, @NotNull Long equipmentId,
			List<AddMaterialPortionDto> materialPortions) {
		super();
		this.parentMixtureId = parentMixtureId;
		this.quantity = quantity;
		this.equipmentId = equipmentId;
		this.materialPortions = materialPortions;
	}

	public Long getParentMixtureId() {
		return parentMixtureId;
	}

	public void setParentMixtureId(Long parentMixtureId) {
		this.parentMixtureId = parentMixtureId;
	}

	public QuantityDto getQuantity() {
		return quantity;
	}

	public void setQuantity(QuantityDto quantity) {
		this.quantity = quantity;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public List<AddMaterialPortionDto> getMaterialPortions() {
		return materialPortions;
	}

	public void setMaterialPortions(List<AddMaterialPortionDto> materialPortions) {
		this.materialPortions = materialPortions;
	}
        
}
