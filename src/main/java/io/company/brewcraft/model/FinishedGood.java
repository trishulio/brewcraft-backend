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
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.service.CriteriaJoin;
import io.company.brewcraft.service.CrudEntity;

@Entity(name = "finished_good")
public class FinishedGood extends BaseEntity implements UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion>, CrudEntity<Long>, Audited {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU = "sku";
    public static final String FIELD_MIXTURE_PORTIONS = "mixturePortions";
    public static final String FIELD_MATERIAL_PORTIONS = "materialPortions";
    public static final String FIELD_PARENT_FINISHED_GOOD = "parentFinishedGood";
    public static final String FIELD_CHILD_FINISHED_GOODS = "childFinishedGoods";
    public static final String FIELD_PACKAGED_ON = "packagedOn";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finished_good_generator")
    @SequenceGenerator(name = "finished_good_generator", sequenceName = "finished_good_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    private Sku sku;

    @OneToMany(mappedBy = "finishedGood", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @CriteriaJoin
    private List<FinishedGoodMixturePortion> mixturePortions;

    @OneToMany(mappedBy = "finishedGood", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @CriteriaJoin
    private List<FinishedGoodMaterialPortion> materialPortions;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "parent_finished_good_id", referencedColumnName = "id")
    private FinishedGood parentFinishedGood;

    @OneToMany(mappedBy = "parentFinishedGood", fetch = FetchType.LAZY)
    @JsonBackReference
    @CriteriaJoin
    private List<FinishedGood> childFinishedGoods;

    @Column(name = "packaged_on")
    private LocalDateTime packagedOn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public FinishedGood() {
    }

    public FinishedGood(Long id) {
        this();
        setId(id);
    }

    public FinishedGood(Long id, Sku sku, List<FinishedGoodMixturePortion> mixturePortions, List<FinishedGoodMaterialPortion> materialPortions, FinishedGood parentFinishedGood, List<FinishedGood> childFinishedGoods, LocalDateTime packagedOn, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setSku(sku);
        setMixturePortions(mixturePortions);
        setMaterialPortions(materialPortions);
        setParentFinishedGood(parentFinishedGood);
        setChildFinishedGoods(childFinishedGoods);
        setPackagedOn(packagedOn);
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
    public Sku getSku() {
        return sku;
    }

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Override
    public List<FinishedGoodMixturePortion> getMixturePortions() {
        return mixturePortions;
    }

    @Override
    public void setMixturePortions(List<FinishedGoodMixturePortion> mixturePortions) {
        if (this.mixturePortions != null) {
            if (mixturePortions == null) {
                this.mixturePortions.clear();
            } else {
                this.mixturePortions.stream().filter(mixturePortion -> !mixturePortions.contains(mixturePortion)).collect(Collectors.toList()).forEach(this::removeMixturePortion);
            }
        }

        if (mixturePortions != null) {
            if (this.mixturePortions == null) {
                this.mixturePortions = new ArrayList<>();
                mixturePortions.stream().collect(Collectors.toList()).forEach(this::addMixturePortion);
            } else {
                mixturePortions.stream().filter(mixturePortion -> !this.mixturePortions.contains(mixturePortion)).collect(Collectors.toList()).forEach(this::addMixturePortion);
            }
        }
    }

    public void addMixturePortion(FinishedGoodMixturePortion mixturePortion) {
        if (mixturePortion == null) {
            return;
        }

        if (this.mixturePortions == null) {
            this.mixturePortions = new ArrayList<>();
        }

        if (mixturePortion.getFinishedGood() != this) {
            mixturePortion.setFinishedGood(this);
        }

        if (!this.mixturePortions.contains(mixturePortion)) {
            this.mixturePortions.add(mixturePortion);
        }
    }

    public boolean removeMixturePortion(FinishedGoodMixturePortion mixturePortion) {
        if (mixturePortion == null || this.mixturePortions == null) {
            return false;
        }

        boolean removed = this.mixturePortions.remove(mixturePortion);

        if (removed) {
            mixturePortion.setFinishedGood(null);
        }

        return removed;
    }

    @Override
    public List<FinishedGoodMaterialPortion> getMaterialPortions() {
        return materialPortions;
    }

    @Override
    public void setMaterialPortions(List<FinishedGoodMaterialPortion> materialPortions) {
        if (this.materialPortions != null) {
            if (materialPortions == null) {
                this.materialPortions.clear();
            } else {
                this.materialPortions.stream().filter(materialPortion -> !materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::removeMaterialPortion);
            }
        }

        if (materialPortions != null) {
            if (this.materialPortions == null) {
                this.materialPortions = new ArrayList<>();
                materialPortions.stream().collect(Collectors.toList()).forEach(this::addMaterialPortion);
            } else {
                materialPortions.stream().filter(materialPortion -> !this.materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::addMaterialPortion);
            }
        }
    }

    public void addMaterialPortion(FinishedGoodMaterialPortion materialPortion) {
        if (materialPortion == null) {
            return;
        }

        if (this.materialPortions == null) {
            this.materialPortions = new ArrayList<>();
        }

        if (materialPortion.getFinishedGood() != this) {
            materialPortion.setFinishedGood(this);
        }

        if (!this.materialPortions.contains(materialPortion)) {
            this.materialPortions.add(materialPortion);
        }
    }

    public boolean removeMaterialPortion(FinishedGoodMaterialPortion materialPortion) {
        if (materialPortion == null || this.materialPortions == null) {
            return false;
        }

        boolean removed = this.materialPortions.remove(materialPortion);

        if (removed) {
            materialPortion.setFinishedGood(null);
        }

        return removed;
    }

    @Override
    public FinishedGood getParentFinishedGood() {
        return parentFinishedGood;
    }

    @Override
    public void setParentFinishedGood(FinishedGood finishedGood) {
        this.parentFinishedGood = finishedGood;

        if (finishedGood != null) {
            finishedGood.addChildFinishedGood(this);
        }
    }

    @Override
    public List<FinishedGood> getChildFinishedGoods() {
        return childFinishedGoods;
    }

    @Override
    public void setChildFinishedGoods(List<FinishedGood> childFinishedGoods) {
        if (this.childFinishedGoods != null) {
            if (childFinishedGoods == null) {
                this.childFinishedGoods.clear();
            } else {
                this.childFinishedGoods.stream().filter(childFinishedGood -> !childFinishedGoods.contains(childFinishedGood)).collect(Collectors.toList()).forEach(this::removeChildFinishedGood);
            }
        }

        if (childFinishedGoods != null) {
            if (this.childFinishedGoods == null) {
                this.childFinishedGoods = new ArrayList<>();
                childFinishedGoods.stream().collect(Collectors.toList()).forEach(this::addChildFinishedGood);
            } else {
                childFinishedGoods.stream().filter(childFinishedGood -> !this.childFinishedGoods.contains(childFinishedGood)).collect(Collectors.toList()).forEach(this::addChildFinishedGood);
            }
        }
    }

    public void addChildFinishedGood(FinishedGood finishedGood) {
        if (finishedGood == null) {
            return;
        }

        if (this.childFinishedGoods == null) {
            this.childFinishedGoods = new ArrayList<>();
        }

        if (finishedGood.getParentFinishedGood() != this) {
            finishedGood.setParentFinishedGood(this);
        }

        if (!this.childFinishedGoods.contains(finishedGood)) {
            this.childFinishedGoods.add(finishedGood);
        }
    }

    public boolean removeChildFinishedGood(FinishedGood finishedGood) {
        if (finishedGood == null || this.childFinishedGoods == null) {
            return false;
        }

        boolean removed = this.childFinishedGoods.remove(finishedGood);

        if (removed) {
            finishedGood.setParentFinishedGood(null);
        }

        return removed;
    }

    @Override
    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    @Override
    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}