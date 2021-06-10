package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "BREW_LOG")
public class BrewLog extends BaseEntity implements BaseBrewLog, UpdateBrewLog, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_BREWSTAGE = "brewStage";
    public static final String FIELD_RECORDED_AT = "recordedAt";
    public static final String FIELD_COMMENT = "comment";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_RECORDED_MEASURES = "recordedMeasures";
    public static final String FIELD_MIXTURE = "mixture";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_log_generator")
    @SequenceGenerator(name = "brew_log_generator", sequenceName = "brew_log_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_stage_id", referencedColumnName = "id", nullable = false)
    private BrewStage brewStage;
    
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;
    
    private String comment;
    
    //private User processor; TODO
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_log_type_id", referencedColumnName = "id")
    private BrewLogType type;
    
    @OneToMany(mappedBy = "brewLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BrewMeasureValue> recordedMeasures;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id", nullable = false)
    private Mixture mixture;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public BrewLog() {
    }
    
    public BrewLog(Long id) {
        this();
        setId(id);
    }

    public BrewLog(Long id, BrewStage brewStage, LocalDateTime recordedAt, String comment, BrewLogType type,
            List<BrewMeasureValue> recordedMeasures, Mixture mixture, LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer version) {
    	this(id);
        this.brewStage = brewStage;
        this.recordedAt = recordedAt;
        this.comment = comment;
        this.type = type;
        this.recordedMeasures = recordedMeasures;
        this.mixture = mixture;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.version = version;
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
    public BrewStage getBrewStage() {
        return brewStage;
    }

    @Override
    public void setBrewStage(BrewStage brewStage) {
        this.brewStage = brewStage;
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
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public BrewLogType getType() {
        return type;
    }

    @Override
    public void setType(BrewLogType type) {
        this.type = type;
    }

    @Override
    public List<BrewMeasureValue> getRecordedMeasures() {
        return recordedMeasures;
    }

    @Override
    public void setRecordedMeasures(List<BrewMeasureValue> recordedMeasures) {
        if (recordedMeasures != null) {
            recordedMeasures.stream().forEach(recordedMeasure -> recordedMeasure.setBrewLog(this));
        }
        
        if (this.getRecordedMeasures() != null) {
            this.getRecordedMeasures().clear();
            this.getRecordedMeasures().addAll(recordedMeasures);
        } else {
            this.recordedMeasures = recordedMeasures;
        }    
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
    
    public BrewLog getPreviousLog() {
        List<BrewLog> logs = this.getBrewStage().getBrewLogs();
        int currentIndex = logs.indexOf(this);
        BrewLog previousLog = null;
        
        if (currentIndex == 0) {
            Brew parentBrew = this.getBrewStage().getBrew().getParentBrew();
            
            if (parentBrew != null && parentBrew.getLatestStage() != null) {
                previousLog = parentBrew.getLatestStage().getLatestBrewLog();
            }
        } else {
            previousLog = logs.get(currentIndex - 1);
        }
        
        return previousLog;
    }
}
