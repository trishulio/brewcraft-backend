package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.CriteriaJoin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "BREW_STAGE")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class BrewStage extends BaseEntity implements UpdateBrewStage, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_BREW = "brew";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_TASK = "task";
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

    @OneToMany(mappedBy = "brewStage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    @JsonIgnore
    @CriteriaJoin
    private List<Mixture> mixtures;

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

    public BrewStage(Long id, Brew brew, BrewStageStatus status, BrewTask task, List<Mixture> mixtures,
            LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer version) {
        this(id);
        setBrew(brew);
        setStatus(status);
        setTask(task);
        setMixtures(mixtures);
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
    public List<Mixture> getMixtures() {
        return mixtures;
    }

    @Override
    public void setMixtures(List<Mixture> mixtures) {
        if (this.mixtures != null) {
            if (mixtures == null) {
                this.mixtures.clear();
            } else {
                this.mixtures.stream().filter(mixture -> !mixtures.contains(mixture)).toList().forEach(this::removeMixture);
            }
        }

        if (mixtures != null) {
            if (this.mixtures == null) {
                this.mixtures = new ArrayList<>();
                mixtures.stream().toList().forEach(this::addMixture);
            } else {
                mixtures.stream().filter(mixture -> !this.mixtures.contains(mixture)).toList().forEach(this::addMixture);
            }
        }
    }

    public void addMixture(Mixture mixture) {
        if (mixture == null) {
            return;
        }

        if (this.mixtures == null) {
            this.mixtures = new ArrayList<>();
        }

        if (mixture.getBrewStage() != this) {
            mixture.setBrewStage(this);
        }

        if (!this.mixtures.contains(mixture)) {
            this.mixtures.add(mixture);
        }
    }

    public boolean removeMixture(Mixture mixture) {
        if (mixture == null || this.mixtures == null) {
            return false;
        }

        boolean removed = this.mixtures.remove(mixture);

        if (removed) {
            mixture.setBrewStage(null);
        }

        return removed;
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

    public void setVersion(Integer version) {
        this.version = version;
    }
}
