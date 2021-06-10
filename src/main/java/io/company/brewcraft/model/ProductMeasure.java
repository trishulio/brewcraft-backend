package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "PRODUCT_MEASURE")
public class ProductMeasure extends BaseEntity implements Identified<Long> {
    public static final String ABV = "abv";
    public static final String IBU = "ibu";
    public static final String PH = "ph";
    public static final String MASH_TEMP = "mashTemperature";
    public static final String GRAVITY = "gravity";
    public static final String YIELD = "yield";
    public static final String BREWHOUSE_DURATION = "brewhouseDuration";
    public static final String FERMENTATION_DAYS = "fermentationDays";
    public static final String CONDITIONING_DAYS = "conditioningDays";
    
    @Id
    @Column(name = "name", unique = true, insertable = false, updatable = false)
    private String name;
    
    public ProductMeasure() {
        super();
    }
        
    public ProductMeasure(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
