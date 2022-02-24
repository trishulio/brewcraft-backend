package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CriteriaJoin;

@Entity(name = "finished_good_inventory")
@Table(name = "finished_good_inventory")
@Immutable
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class FinishedGoodInventory extends BaseFinishedGoodInventory {

    public static final String FIELD_MIXTURE_PORTIONS = "mixturePortions";
    public static final String FIELD_MATERIAL_PORTIONS = "materialPortions";
    public static final String FIELD_FINISHED_GOOD_LOT_PORTIONS = "finishedGoodLotPortions";

    @OneToMany(mappedBy = "finishedGoodLot")
    @CriteriaJoin(type = JoinType.LEFT)
    private List<FinishedGoodLotMixturePortion> mixturePortions;

    @OneToMany(mappedBy = "finishedGoodLot")
    @CriteriaJoin(type = JoinType.LEFT)
    private List<FinishedGoodLotMaterialPortion> materialPortions;

    @OneToMany(mappedBy = "finishedGoodLotTarget")
    @CriteriaJoin
    private List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions;

    public FinishedGoodInventory() {
        super();
    }

    public FinishedGoodInventory(Long id) {
        super(id);
    }

    public FinishedGoodInventory(Long id, Sku sku, List<FinishedGoodLotMixturePortion> mixturePortions, List<FinishedGoodLotMaterialPortion> materialPortions, List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions, Quantity<?> quantity, LocalDateTime packagedOn) {
        super(id, sku, quantity, packagedOn);
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.finishedGoodLotPortions = finishedGoodLotPortions;
    }

    public List<FinishedGoodLotMixturePortion> getMixturePortions() {
        return mixturePortions;
    }

    public void setMixturePortions(List<FinishedGoodLotMixturePortion> mixturePortions) {
        this.mixturePortions = mixturePortions;
    }

    public List<FinishedGoodLotFinishedGoodLotPortion> getFinishedGoodLotPortions() {
        return finishedGoodLotPortions;
    }

    public void setFinishedGoodLotPortions(List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions) {
        this.finishedGoodLotPortions = finishedGoodLotPortions;
    }

    public List<FinishedGoodLotMaterialPortion> getMaterialPortions() {
        return materialPortions;
    }

    public void setMaterialPortions(List<FinishedGoodLotMaterialPortion> materialPortions) {
        this.materialPortions = materialPortions;
    }
}
