package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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
    
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_measure_generator")
    @SequenceGenerator(name = "product_measure_generator", sequenceName = "product_measure_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    public ProductMeasure() {
        super();
    }
    
    public ProductMeasure(Long id) {
    	this();
    	setId(id);
    }
        
    public ProductMeasure(Long id, String name) {
    	this(id);
        setName(name);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
