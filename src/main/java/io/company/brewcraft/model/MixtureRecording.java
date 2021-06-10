package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "MIXTURE_RECORDING")
public class MixtureRecording extends BaseEntity implements BaseMixtureRecording, UpdateMixtureRecording, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_MIXTURE = "mixture";
    public static final String FIELD_PRODUCT_MEASURE = "productMeasure";
    public static final String FIELD_VALUE = "value";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mixture_recording_generator")
    @SequenceGenerator(name = "mixture_recording_generator", sequenceName = "mixture_recording_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id")
    private Mixture mixture;    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "measure_name", referencedColumnName = "name")
    private ProductMeasure productMeasure;
    
    @Column(name = "measure_value")
    private String value;
    
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
      
    public MixtureRecording() {
        super();
    }

    public MixtureRecording(Long id, Mixture mixture, ProductMeasure productMeasure,  String value, LocalDateTime recordedAt,  LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer version) {
        this();
        setId(id);
        setMixture(mixture);
        setProductMeasure(productMeasure);
        setValue(value);
        setRecordedAt(recordedAt);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Mixture getMixture() {
        return mixture;
    }

    @Override
    public void setMixture(Mixture mixture) {
        this.mixture = mixture;
    }

    @Override
    public ProductMeasure getProductMeasure() {
        return productMeasure;
    }

    @Override
    public void setProductMeasure(ProductMeasure productMeasure) {
        this.productMeasure = productMeasure;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
	public LocalDateTime getRecordedAt() {
		return recordedAt;
	}

    @Override
	public void setRecordedAt(LocalDateTime recordedAt) {
		this.recordedAt = recordedAt;
	}
	
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

}
