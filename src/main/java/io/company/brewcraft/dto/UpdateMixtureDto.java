package io.company.brewcraft.dto;

public class UpdateMixtureDto extends BaseDto {
    
    private Long parentMixtureId;
    
    private QuantityDto quantity;
                
    private Long equipmentId;
    
    private Long brewStageId;
            
    private Integer version;
    
    public UpdateMixtureDto() {
    	
    }

	public UpdateMixtureDto(Long parentMixtureId, QuantityDto quantity, Long equipmentId, Long brewStageId,
			Integer version) {
		super();
		this.parentMixtureId = parentMixtureId;
		this.quantity = quantity;
		this.equipmentId = equipmentId;
		this.brewStageId = brewStageId;
		this.version = version;
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

	public Long getBrewStageId() {
		return brewStageId;
	}

	public void setBrewStageId(Long brewStageId) {
		this.brewStageId = brewStageId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
