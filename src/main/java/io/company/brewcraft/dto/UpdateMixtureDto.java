package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

import java.util.Set;

public class UpdateMixtureDto extends BaseDto {

    private Set<Long> parentMixtureIds;

    private QuantityDto quantity;

    private Long equipmentId;

    private Long brewStageId;

    @NotNull
    private Integer version;

    public UpdateMixtureDto() {

    }

    public UpdateMixtureDto(Set<Long> parentMixtureIds, QuantityDto quantity, Long equipmentId, Long brewStageId,
            Integer version) {
        super();
        this.parentMixtureIds = parentMixtureIds;
        this.quantity = quantity;
        this.equipmentId = equipmentId;
        this.brewStageId = brewStageId;
        this.version = version;
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
