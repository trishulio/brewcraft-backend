package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "MEASURE")
public class Measure extends BaseEntity implements Identified<Long> {
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measure_generator")
    @SequenceGenerator(name = "measure_generator", sequenceName = "measure_sequence", allocationSize = 1)
    private Long id;

    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public Measure() {
        super();
    }

    public Measure(Long id) {
        this();
        setId(id);
    }

    public Measure(Long id, String name, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
