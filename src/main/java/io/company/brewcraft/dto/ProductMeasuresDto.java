package io.company.brewcraft.dto;

public class ProductMeasuresDto extends BaseDto {
    
    private Double abv;
    
    private Double ibu;
    
    private Double ph;

    private Double mashTemperature;
    
    private Double gravity;
    
    private Double yield;
    
    private Double brewhouseDuration;

    private Double fermentationDays;

    private Double conditioningDays;
    
    public ProductMeasuresDto() {
        super();
    }

    public ProductMeasuresDto(Double abv, Double ibu, Double ph, Double mashTemperature,
            Double gravity, Double yield, Double brewhouseDuration, Double fermentationDays,
            Double conditioningDays) {
        super();
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

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double aBV) {
        abv = aBV;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double iBU) {
        ibu = iBU;
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
