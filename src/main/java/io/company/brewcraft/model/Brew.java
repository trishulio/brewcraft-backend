package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

@Entity(name = "BREW")
public class Brew extends BaseEntity implements BaseBrew, UpdateBrew, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BATCH_ID = "batchId";
    public static final String FIELD_PRODUCT = "product";
    public static final String FIELD_PARENT_BREW = "parentBrew";
    public static final String FIELD_CHILD_BREWS = "childBrews";
    public static final String FIELD_BREW_STAGES = "brewStages";
    public static final String FIELD_STARTED_AT = "startedAt";
    public static final String FIELD_ENDED_AT = "endedAt";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_generator")
    @SequenceGenerator(name = "brew_generator", sequenceName = "brew_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    private String description;
    
    @Column(name = "batch_id", nullable = false)
    private Long batchId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_brew_id", referencedColumnName = "id")
    private Brew parentBrew;

    @OneToMany(mappedBy = "parentBrew")
    private List<Brew> childBrews;
    
    @OneToMany(mappedBy="brew", orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("startedAt ASC")
    private List<BrewStage> brewStages;
    
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

    public Brew() {
    }
    
    public Brew(Long id) {
        this();
        setId(id);
    }

    public Brew(Long id, String name, String description, Long batchId, Product product, Brew parentBrew, List<Brew> childBrews, List<BrewStage> brewStages, LocalDateTime startedAt, 
            LocalDateTime endedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
    	this(id);
        setName(name);
        setDescription(description);
        setBatchId(batchId);
        setProduct(product);
        setParentBrew(parentBrew);
        setChildBrews(childBrews);
        setBrewStages(brewStages);
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getBatchId() {
        return batchId;
    }

    @Override
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Brew getParentBrew() {
        return parentBrew;
    }

    @Override
    public void setParentBrew(Brew parentBrew) {
        this.parentBrew = parentBrew;
        
        if (parentBrew != null) {
            parentBrew.AddChildBrew(this);
        }
    }

    @Override
    public List<Brew> getChildBrews() {
        return childBrews;
    }

    @Override
    public void setChildBrews(List<Brew> childBrews) {
        if (childBrews != null) {
        	childBrews.stream().forEach(child -> child.setParentBrew(this));
        }
        
        if (this.getChildBrews() != null) {
            this.getChildBrews().clear();
            this.getChildBrews().addAll(childBrews);
        } else {
            this.childBrews = childBrews;
        }    
    }
    
    public void AddChildBrew(Brew childBrew) {
        if (childBrews == null) {
            childBrews = new ArrayList<>();
        }
        
        if (childBrew.getParentBrew() != this) {
            childBrew.setParentBrew(this);
        }

        if (!childBrews.contains(childBrew)) {
            this.childBrews.add(childBrew);
        }
    }

    @Override
    public List<BrewStage> getBrewStages() {
        //TODO: should we sort before returning brew stages? is it neccessary since brewStages has @OrderBy annotation?
//        if (brewStages != null && !brewStages.isEmpty()) {
//            brewStages.sort(new Comparator<BrewStage>() {
//                public int compare(BrewStage o1, BrewStage o2) {
//                    return new CompareToBuilder().append(o1.getStartedAt(), o2.getStartedAt()).toComparison(); 
//                }});
//        }
        return brewStages;
    }

    @Override
    public void setBrewStages(List<BrewStage> brewStages) {
        if (brewStages != null) {
        	brewStages.stream().forEach(stage -> stage.setBrew(this));
        }
        
        if (this.getBrewStages() != null) {
            this.getBrewStages().clear();
            this.getBrewStages().addAll(brewStages);
        } else {
            this.brewStages = brewStages;
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
