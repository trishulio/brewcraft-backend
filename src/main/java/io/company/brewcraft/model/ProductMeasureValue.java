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

@Entity(name = "PRODUCT_MEASURE_VALUE")
public class ProductMeasureValue extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_measure_value_generator")
    @SequenceGenerator(name = "product_measure_value_generator", sequenceName = "product_measure_value_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Measure measure;
    
    @Column(name = "product_measure_value")
    private String value;
    
    
    public ProductMeasureValue() {
        super();
    }

    public ProductMeasureValue(Long id, Measure measure,  String value, Product product) {
        this();
        this.id = id;
        this.measure = measure;
        this.value = value;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
