package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "BREW_MEASURE_VALUE")
public class BrewMeasureValue extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_BREW_LOG = "brewLog";
    public static final String FIELD_PRODUCT_MEASURE = "productMeasure";
    public static final String FIELD_VALUE = "value";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_measure_value_generator")
    @SequenceGenerator(name = "brew_measure_value_generator", sequenceName = "brew_measure_value_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brew_log_id", referencedColumnName = "id")
    private BrewLog brewLog;    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_measure_name", referencedColumnName = "name")
    private ProductMeasure productMeasure;
    
    @Column(name = "brew_measure_value")
    private String value;
      
    public BrewMeasureValue() {
        super();
    }

    public BrewMeasureValue(Long id, BrewLog brewLog, ProductMeasure productMeasure,  String value) {
        this();
        this.id = id;
        this.brewLog = brewLog;
        this.productMeasure = productMeasure;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrewLog getBrewLog() {
        return brewLog;
    }

    public void setBrewLog(BrewLog brewLog) {
        this.brewLog = brewLog;
    }

    public ProductMeasure getProductMeasure() {
        return productMeasure;
    }

    public void setProductMeasure(ProductMeasure productMeasure) {
        this.productMeasure = productMeasure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
