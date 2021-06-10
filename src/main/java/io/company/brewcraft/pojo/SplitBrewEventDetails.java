package io.company.brewcraft.pojo;

import javax.measure.Quantity;

import io.company.brewcraft.dto.AddBrewStageDto;

public class SplitBrewEventDetails implements ISplitBrewEventDetails {
    
    private Long parentBrewId;

    private Long childBrewId;
    
    private AddBrewStageDto addStageForChild;

    private Quantity<?> quantity;
    
    private Long mixtureId;
    
    public SplitBrewEventDetails(Long parentBrewId, Long childBrewId, Quantity<?> quantity) {
        super();
        this.parentBrewId = parentBrewId;
        this.childBrewId = childBrewId;
        this.quantity = quantity;
    }

    public Long getParentBrewId() {
        return parentBrewId;
    }

    public void setParentBrewId(Long parentBrewId) {
        this.parentBrewId = parentBrewId;
    }

    public Long getChildBrewId() {
        return childBrewId;
    }

    public void setChildBrewId(Long childBrewId) {
        this.childBrewId = childBrewId;
    }

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }

}
