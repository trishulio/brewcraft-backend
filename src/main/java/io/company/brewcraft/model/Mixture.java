package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "MIXTURE")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Mixture extends BaseEntity implements UpdateMixture, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_PARENT_MIXTURE = "parentMixture";
    public static final String FIELD_QUANTITY_VALUE = "quantityValue";
    public static final String FIELD_QUANTITY_UNIT = "quantityUnit";
    public static final String FIELD_EQUIPMENT = "equipment";
    public static final String FIELD_BREW_STAGE = "brewStage";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mixture_generator")
    @SequenceGenerator(name = "mixture_generator", sequenceName = "mixture_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_mixture_id", referencedColumnName = "id")
    private Mixture parentMixture;

    @OneToMany(mappedBy = "parentMixture", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Mixture> childMixtures;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "quantity_value")) })
    @AssociationOverrides({
            @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "quantity_unit", referencedColumnName = "symbol")) })
    private QuantityEntity quantity;

    @ManyToOne()
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = true)
    private Equipment equipment;

    @OneToMany(mappedBy = "mixture", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MixtureMaterialPortion> materialPortions;

    @OneToMany(mappedBy = "mixture", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MixtureRecording> recordedMeasures;

    @ManyToOne()
    @JoinColumn(name = "brew_stage_id", referencedColumnName = "id")
    private BrewStage brewStage;

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

    public Mixture(Long id, Mixture parentMixture, List<Mixture> childMixtures, Quantity<?> quantity,
            Equipment equipment, List<MixtureMaterialPortion> materialPortions, List<MixtureRecording> recordedMeasures,
            BrewStage brewStage, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setParentMixture(parentMixture);
        setChildMixtures(childMixtures);
        setQuantity(quantity);
        setEquipment(equipment);
        setMaterialPortions(materialPortions);
        setRecordedMeasures(recordedMeasures);
        setBrewStage(brewStage);
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
        if (this.childMixtures != null) {
            this.childMixtures.stream().filter(childMixture -> !childMixtures.contains(childMixture)).collect(Collectors.toList()).forEach(this::removeChildMixture);
        }

        if (childMixtures != null) {
            if (this.childMixtures == null) {
                childMixtures.stream().collect(Collectors.toList()).forEach(this::addChildMixture);
            } else {
                childMixtures.stream().filter(childMixture -> !this.childMixtures.contains(childMixture)).collect(Collectors.toList()).forEach(this::addChildMixture);
            }
        }
    }

    @Override
    public void addChildMixture(Mixture childMixture) {
        if (childMixture == null) {
            return;
        }

        if (this.childMixtures == null) {
            this.childMixtures = new ArrayList<>();
        }

        if (childMixture.getParentMixture() != this) {
            childMixture.setParentMixture(this);
        }

        if (!this.childMixtures.contains(childMixture)) {
            this.childMixtures.add(childMixture);
        }
    }

    public boolean removeChildMixture(Mixture childMixture) {
        if (childMixture == null || this.childMixtures == null) {
            return false;
        }

        boolean removed = this.childMixtures.remove(childMixture);

        if (removed) {
            childMixture.setParentMixture(null);
        }

        return removed;
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
    public List<MixtureMaterialPortion> getMaterialPortions() {
        return materialPortions;
    }

    @Override
    public void setMaterialPortions(List<MixtureMaterialPortion> materialPortions) {
        if (this.materialPortions != null) {
            this.materialPortions.stream().filter(materialPortion -> !materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::removeMaterialPortion);
        }

        if (materialPortions != null) {
            if (this.materialPortions == null) {
                materialPortions.stream().collect(Collectors.toList()).forEach(this::addMaterialPortion);
            } else {
                materialPortions.stream().filter(materialPortion -> !this.materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::addMaterialPortion);
            }
        }
    }

    public void addMaterialPortion(MixtureMaterialPortion materialPortion) {
        if (materialPortion == null) {
            return;
        }

        if (this.materialPortions == null) {
            this.materialPortions = new ArrayList<>();
        }

        if (materialPortion.getMixture() != this) {
            materialPortion.setMixture(this);
        }

        if (!this.materialPortions.contains(materialPortion)) {
            this.materialPortions.add(materialPortion);
        }
    }

    public boolean removeMaterialPortion(MixtureMaterialPortion materialPortion) {
        if (materialPortion == null || this.materialPortions == null) {
            return false;
        }

        boolean removed = this.materialPortions.remove(materialPortion);

        if (removed) {
            materialPortion.setMixture(null);
        }

        return removed;
    }

    @Override
    public List<MixtureRecording> getRecordedMeasures() {
        return recordedMeasures;
    }

    @Override
    public void setRecordedMeasures(List<MixtureRecording> recordedMeasures) {
        if (this.recordedMeasures != null) {
            this.recordedMeasures.stream().filter(recordedMeasure -> !recordedMeasures.contains(recordedMeasure)).collect(Collectors.toList()).forEach(this::removeRecordedMeasure);
        }

        if (recordedMeasures != null) {
            if (this.recordedMeasures == null) {
                recordedMeasures.stream().collect(Collectors.toList()).forEach(this::addRecordedMeasure);
            } else {
                recordedMeasures.stream().filter(recordedMeasure -> !this.recordedMeasures.contains(recordedMeasure)).collect(Collectors.toList()).forEach(this::addRecordedMeasure);
            }
        }
    }

    public void addRecordedMeasure(MixtureRecording recording) {
        if (recording == null) {
            return;
        }

        if (this.recordedMeasures == null) {
            this.recordedMeasures = new ArrayList<>();
        }

        if (recording.getMixture() != this) {
            recording.setMixture(this);
        }

        if (!this.recordedMeasures.contains(recording)) {
            this.recordedMeasures.add(recording);
        }
    }

    public boolean removeRecordedMeasure(MixtureRecording item) {
        if (item == null || this.recordedMeasures == null) {
            return false;
        }

        boolean removed = this.recordedMeasures.remove(item);

        if (removed) {
            item.setMixture(null);
        }

        return removed;
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
