package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "BREW_STAGE")
public class BrewStage extends BaseEntity implements BaseBrewStage, UpdateBrewStage, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_BREW = "brew";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_TASK = "task";
    public static final String FIELD_BREW_LOGS = "brewLogs";
    public static final String FIELD_STARTED_AT = "startedAt";
    public static final String FIELD_ENDED_AT = "endedAt";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_stage_generator")
    @SequenceGenerator(name = "brew_stage_generator", sequenceName = "brew_stage_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_id", referencedColumnName = "id", nullable = false)
    private Brew brew;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_stage_status_id", referencedColumnName = "id")
    private BrewStageStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brew_task_id", referencedColumnName = "id")
    private BrewTask task;
    
    @OneToMany(mappedBy = "brewStage", orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("recordedAt ASC, id ASC")
    private List<BrewLog> brewLogs;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public BrewStage() {
    }
    
    public BrewStage(Long id) {
        this();
        setId(id);
    }

    public BrewStage(Long id, Brew brew, BrewStageStatus status, BrewTask task, List<BrewLog> brewLogs,
            LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer version) {
    	this(id);
        setBrew(brew);
        setStatus(status);
        setTask(task);
        setBrewLogs(brewLogs);
        setStartedAt(startedAt);
        setEndedAt(endedAt);
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
    public Brew getBrew() {
        return brew;
    }

    @Override
    public void setBrew(Brew brew) {
        this.brew = brew;
    }

    @Override
    public BrewStageStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(BrewStageStatus status) {
        this.status = status;
    }

    @Override
    public BrewTask getTask() {
        return task;
    }

    @Override
    public void setTask(BrewTask task) {
        this.task = task;
    }

    @Override
    public List<BrewLog> getBrewLogs() {
        //TODO: should we sort before returning brew logs? is it neccessary since brewLogs has @OrderBy annotation?
        if (brewLogs != null) {
            brewLogs.sort(new Comparator<BrewLog>() {
                public int compare(BrewLog o1, BrewLog o2) {
                    return new CompareToBuilder().append(o1.getRecordedAt(), o2.getRecordedAt()).append(o1.getId(), o2.getId()).toComparison(); 
                }});
        }
        return brewLogs;
    }

    @Override
    public void setBrewLogs(List<BrewLog> brewLogs) {
        if (brewLogs != null) {
            brewLogs.stream().forEach(log -> log.setBrewStage(this));
        }
        
        if (this.getBrewLogs() != null) {
            this.getBrewLogs().clear();
            this.getBrewLogs().addAll(brewLogs);
        } else {
            this.brewLogs = brewLogs;
        }    
    }

    @Override
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    @Override
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    @Override
    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    @Override
    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
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
    
    public BrewLog getLatestBrewLog() {
        List<BrewLog> logs = this.getBrewLogs();
        BrewLog log = null;
        
        if (logs != null && !logs.isEmpty()) {
            log = logs.get(logs.size() - 1);
        }
        
        return log;
    }
    
    public Mixture getMixture() {
        Mixture mixture = null;
        BrewLog log = this.getLatestBrewLog();
        
        if (log != null) {
            mixture = log.getMixture();
        }
        
       return mixture;
    }
    
}
