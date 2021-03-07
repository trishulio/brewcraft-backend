package io.company.brewcraft.pojo;

import io.company.brewcraft.dto.UpdateProductMeasures;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class ProductMeasures extends BaseModel implements UpdateProductMeasures, Identified {
    
    private Long id;
    
    private Double abv;
    
    private Double ibu;
    
    private Double ph;

    private Double mashTemperature;
    
    private Double gravity;
    
    private Double yield;
    
    private Double brewhouseDuration;

    private Double fermentationDays;

    private Double conditioningDays;
    
    public ProductMeasures() {
        
    }

    public ProductMeasures(Long id, Double abv, Double ibu, Double ph, Double mashTemperature, Double gravity,
            Double yield, Double brewhouseDuration, Double fermentationDays, Double conditioningDays) {
        super();
        this.id = id;
        this.abv = abv;
        this.ibu = ibu;
        this.ph = ph;
        this.mashTemperature = mashTemperature;
        this.gravity = gravity;
        this.yield = yield;
        this.brewhouseDuration = brewhouseDuration;
        this.fermentationDays = fermentationDays;
        this.conditioningDays = conditioningDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getMashTemperature() {
        return mashTemperature;
    }

    public void setMashTemperature(Double mashTemperature) {
        this.mashTemperature = mashTemperature;
    }

    public Double getGravity() {
        return gravity;
    }

    public void setGravity(Double gravity) {
        this.gravity = gravity;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public Double getBrewhouseDuration() {
        return brewhouseDuration;
    }

    public void setBrewhouseDuration(Double brewhouseDuration) {
        this.brewhouseDuration = brewhouseDuration;
    }

    public Double getFermentationDays() {
        return fermentationDays;
    }

    public void setFermentationDays(Double fermentationDays) {
        this.fermentationDays = fermentationDays;
    }

    public Double getConditioningDays() {
        return conditioningDays;
    }

    public void setConditioningDays(Double conditioningDays) {
        this.conditioningDays = conditioningDays;
    }
}
