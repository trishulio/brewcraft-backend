package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "MIXTURE")
public class Mixture extends BaseEntity implements BaseMixture, UpdateMixture, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_PARENT_MIXTURE = "parentMixture";
    public static final String FIELD_QUANTITY_VALUE = "quantityValue";
    public static final String FIELD_QUANTITY_UNIT = "quantityUnit";
    public static final String FIELD_EQUIPMENT = "equipment";
    public static final String FIELD_BREW_LOGS = "brewLogs";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mixture_generator")
    @SequenceGenerator(name = "mixture_generator", sequenceName = "mixture_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_mixture_id", referencedColumnName = "id")
    private Mixture parentMixture;

    @OneToMany(mappedBy = "parentMixture")
    private List<Mixture> childMixtures;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "quantity_value"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "quantity_unit", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;
    
    @OneToOne()
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;
    
    @OneToMany(mappedBy = "mixture", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MaterialPortion> materialPortions;
    
    @OneToMany(mappedBy = "mixture", orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("recordedAt ASC, id ASC")
    private List<BrewLog> brewLogs;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public Mixture() {
    }
    
    public Mixture(Long id) {
        this();
        setId(id);
    }

    public Mixture(Long id, Mixture parentMixture, List<Mixture> childMixtures, Quantity<?> quantity, Equipment equipment, List<MaterialPortion> materialPortions, List<BrewLog> brewLogs) {
        this(id);
        setParentMixture(parentMixture);
        setChildMixtures(childMixtures);
        setQuantity(quantity);
        setEquipment(equipment);
        setMaterialPortions(materialPortions);
        setBrewLogs(brewLogs);
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
    public Mixture getParentMixture() {
        return parentMixture;
    }

    @Override
    public void setParentMixture(Mixture parentMixture) {
        this.parentMixture = parentMixture;
        
        if (parentMixture != null) {
        	parentMixture.addChildMixture(this);
        }
    }

    @Override
    public List<Mixture> getChildMixtures() {
        return childMixtures;
    }

    @Override
    public void setChildMixtures(List<Mixture> childMixtures) {
        if (childMixtures != null) {
        	childMixtures.stream().forEach(childMixture -> childMixture.setParentMixture(this));
        }
        
        if (this.getChildMixtures() != null) {
            this.getChildMixtures().clear();
            this.getChildMixtures().addAll(childMixtures);
        } else {
            this.childMixtures = childMixtures;
        }
    }
    
    public void addChildMixture(Mixture childMixture) {
        if (childMixtures == null) {
            childMixtures = new ArrayList<>();
        }
        
        if (childMixture.getParentMixture() != this) {
            childMixture.setParentMixture(this);
        }

        if (!childMixtures.contains(childMixture)) {
            this.childMixtures.add(childMixture);
        }
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.quantity);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    @Override
    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    
    @Override
    public List<MaterialPortion> getMaterialPortions() {
        return materialPortions;
    }

    @Override
    public void setMaterialPortions(List<MaterialPortion> materialPortions) {
        if (materialPortions != null) {
        	materialPortions.stream().forEach(materialPortion -> materialPortion.setMixture(this));
        }
        
        if (this.getMaterialPortions() != null) {
            this.getMaterialPortions().clear();
            this.getMaterialPortions().addAll(materialPortions);
        } else {
            this.materialPortions = materialPortions;
        }
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
            brewLogs.stream().forEach(log -> log.setMixture(this));
        }
        
        if (this.getBrewLogs() != null) {
            this.getBrewLogs().clear();
            this.getBrewLogs().addAll(brewLogs);
        } else {
            this.brewLogs = brewLogs;
        }   
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
    
//    public BrewLog getLatestBrewLogByType(String type) {
//        List<BrewLog> logs = this.getBrewLogs();     
//        BrewLog log = null;
//        
//        if (logs != null && !logs.isEmpty()) {
//            for (int i = logs.size(); i > 0; i--) {
//                if (logs.get(i).getType().getName() == BrewTransferEvent.EVENT_NAME) {
//                    log = logs.get(i);
//                }
//            }
//        }
//        
//        return log;
//    }   
}
