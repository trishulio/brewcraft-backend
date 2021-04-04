package io.company.brewcraft.dto;

public interface BaseProductMeasures {
    
    public Double getAbv();

    public void setAbv(Double abv);

    public Double getIbu();

    public void setIbu(Double ibu);

    public Double getPh();

    public void setPh(Double ph);

    public Double getMashTemperature();

    public void setMashTemperature(Double mashTemperature);

    public Double getGravity();

    public void setGravity(Double gravity);

    public Double getYield();

    public void setYield(Double yield);

    public Double getBrewhouseDuration();

    public void setBrewhouseDuration(Double brewhouseDuration);

    public Double getFermentationDays();

    public void setFermentationDays(Double fermentationDays);

    public Double getConditioningDays();

    public void setConditioningDays(Double conditioningDays);    
}
