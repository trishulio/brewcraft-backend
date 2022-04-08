package io.company.brewcraft.dto;

import java.util.Set;

public class MixtureDto extends BaseDto {
    private Long id;

    private Set<Long> parentMixtureIds;

    private QuantityDto quantity;

    private FacilityEquipmentDto equipment;

    private BrewStageDto brewStage;

    private Integer version;

    public MixtureDto() {
        super();
    }

    public MixtureDto(Long id) {
        this();
        this.id = id;
    }

    public MixtureDto(Long id, Set<Long> parentMixtureIds, QuantityDto quantity, FacilityEquipmentDto equipment,
            BrewStageDto brewStage, Integer version) {
        this(id);
        this.parentMixtureIds = parentMixtureIds;
        this.quantity = quantity;
        this.equipment = equipment;
        this.brewStage = brewStage;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getParentMixtureIds() {
        return parentMixtureIds;
    }

    public void setParentMixtureIds(Set<Long> parentMixtureIds) {
        this.parentMixtureIds = parentMixtureIds;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
