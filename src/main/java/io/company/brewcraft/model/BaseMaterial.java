package io.company.brewcraft.model;

import javax.measure.Unit;

public interface BaseMaterial {

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public MaterialCategory getCategory();

    public void setCategory(MaterialCategory category);

    public String getUPC();

    public void setUPC(String upc);

    public Unit<?> getBaseQuantityUnit();

    public void setBaseQuantityUnit(Unit<?> baseQuantityUnit);
    
    public String getImageSrc();

    public void setImageSrc(String imageSrc);
}
