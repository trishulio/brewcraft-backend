package io.company.brewcraft.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

public class AddMixtureDto extends BaseDto {

    private Set<Long> parentMixtureIds;

    @NotNull
    private QuantityDto quantity;

    private Long equipmentId;

    @NotNull
    private Long brewStageId;

    public AddMixtureDto() {
        super();
    }

    public AddMixtureDto(Set<Long> parentMixtureIds, QuantityDto quantity, Long equipmentId,
            Long brewStageId) {
        this();
        this.parentMixtureIds = parentMixtureIds;
        this.quantity = quantity;
        this.equipmentId = equipmentId;
        this.brewStageId = brewStageId;
    }

    public Set<Long> getParentMixtureIds() {
        return parentMixtureIds;
    }

    public void setParentMixtureIds(Set<Long> parentMixtureId) {
        this.parentMixtureIds = parentMixtureId;
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
}
