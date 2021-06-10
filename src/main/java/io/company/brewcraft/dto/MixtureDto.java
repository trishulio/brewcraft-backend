package io.company.brewcraft.dto;

import java.util.List;

public class MixtureDto extends BaseDto {
    
    private Long parentMixtureId;
    
    private QuantityDto quantity;
                
    private FacilityEquipmentDto equipment;
    
    private List<MaterialPortionDto> materialPortions;

	public MixtureDto(Long parentMixtureId, QuantityDto quantity, FacilityEquipmentDto equipment,
			List<MaterialPortionDto> materialPortions) {
		super();
		this.parentMixtureId = parentMixtureId;
		this.quantity = quantity;
		this.equipment = equipment;
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

	public FacilityEquipmentDto getEquipment() {
		return equipment;
	}

	public void setEquipment(FacilityEquipmentDto equipment) {
		this.equipment = equipment;
	}

	public List<MaterialPortionDto> getMaterialPortions() {
		return materialPortions;
	}

	public void setMaterialPortions(List<MaterialPortionDto> materialPortions) {
		this.materialPortions = materialPortions;
	}

}
