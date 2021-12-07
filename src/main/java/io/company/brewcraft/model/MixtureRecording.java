package io.company.brewcraft.model;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "MIXTURE_RECORDING")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class MixtureRecording extends BaseEntity implements UpdateMixtureRecording, CrudEntity<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_MIXTURE = "mixture";
    public static final String FIELD_MEASURE = "measure";
    public static final String FIELD_VALUE = "value";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mixture_recording_generator")
    @SequenceGenerator(name = "mixture_recording_generator", sequenceName = "mixture_recording_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id")
    private Mixture mixture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Measure measure;

    @Column(name = "measure_value", precision = 20, scale = 4)
    private BigDecimal value;

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

    public MixtureRecording(Long id) {
        this();
        setId(id);
    }

    public MixtureRecording(Long id, Mixture mixture, Measure measure, BigDecimal value, LocalDateTime recordedAt,  LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer version) {
        this(id);
        setMixture(mixture);
        setMeasure(measure);
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
    public Measure getMeasure() {
        return measure;
    }

    @Override
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void setValue(BigDecimal value) {
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

    public void setVersion(Integer version) {
        this.version = version;
    }
}
