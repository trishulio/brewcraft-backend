package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "PRODUCT_MEASURES")
public class ProductMeasuresEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ABV = "abv";
    public static final String FIELD_IBU = "ibu";
    public static final String FIELD_PH = "ph";
    public static final String FIELD_MASH_TEMP = "mashTemperature";
    public static final String FIELD_GRAVITY = "gravity";
    public static final String FIELD_YIELD = "yield";
    public static final String FIELD_BREWHOUSE_DURATION = "brewhouseDuration";
    public static final String FIELD_FERMENTATION_DAYS = "fermentationDays";
    public static final String FIELD_CONDITIONING_DAYS = "conditioningDays";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_measures_generator")
    @SequenceGenerator(name = "product_measures_generator", sequenceName = "product_measures_sequence", allocationSize = 1)
    private Long id;
    
    private Double abv;
    
    private Double ibu;
    
    @Column(name = "ph")
    private Double ph;

    @Column(name = "mash_temperature")
    private Double mashTemperature;
    
    @Column(name = "gravity")
    private Double gravity;
    
    @Column(name = "yield")
    private Double yield;
    
    @Column(name = "brewhouse_duration")
    private Double brewhouseDuration;

    @Column(name = "fermentation_days")
    private Double fermentationDays;

    @Column(name = "conditioning_days")
    private Double conditioningDays;
    
    public ProductMeasuresEntity() {
        super();
    }

    public ProductMeasuresEntity(Long id, Double abv, Double ibu, Double ph, Double mashTemperature, Double gravity,
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
