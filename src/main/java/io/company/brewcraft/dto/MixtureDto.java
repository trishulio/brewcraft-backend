package io.company.brewcraft.dto;

import java.util.List;

public class MixtureDto extends BaseDto {
	
    private Long id;
    
    private Long parentMixtureId;
    
    private QuantityDto quantity;
                
    private FacilityEquipmentDto equipment;
    
    private BrewStageDto brewStage;
    
    private List<MaterialPortionDto> materialPortions;
    
    private List<MixtureRecordingDto> recordedMeasures;
    
    private Integer version;

    public MixtureDto() {
    	
    }
    
	public MixtureDto(Long id, Long parentMixtureId, QuantityDto quantity, FacilityEquipmentDto equipment,
			BrewStageDto brewStage, List<MaterialPortionDto> materialPortions,
			List<MixtureRecordingDto> recordedMeasures, Integer version) {
		super();
        this.id = id;
		this.parentMixtureId = parentMixtureId;
		this.quantity = quantity;
		this.equipment = equipment;
		this.brewStage = brewStage;
		this.materialPortions = materialPortions;
		this.recordedMeasures = recordedMeasures;
		this.version = version;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public BrewStageDto getBrewStage() {
		return brewStage;
	}

	public void setBrewStage(BrewStageDto brewStage) {
		this.brewStage = brewStage;
	}

	public List<MaterialPortionDto> getMaterialPortions() {
		return materialPortions;
	}

	public void setMaterialPortions(List<MaterialPortionDto> materialPortions) {
		this.materialPortions = materialPortions;
	}

	public List<MixtureRecordingDto> getRecordedMeasures() {
		return recordedMeasures;
	}

	public void setRecordedMeasures(List<MixtureRecordingDto> recordedMeasures) {
		this.recordedMeasures = recordedMeasures;
	}
	
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
