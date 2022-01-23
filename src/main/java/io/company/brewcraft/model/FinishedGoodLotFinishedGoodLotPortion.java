package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.company.brewcraft.service.CrudEntity;

@Entity(name = "finished_good_lot_finished_good_lot_portion")
@PrimaryKeyJoinColumn(name="FINISHED_GOOD_LOT_PORTION_ID")
public class FinishedGoodLotFinishedGoodLotPortion extends FinishedGoodLotPortion implements UpdateFinishedGoodLotFinishedGoodLotPortion, CrudEntity<Long>, Audited {

    public static final String FIELD_FINISHED_GOOD_LOT = "finishedGoodLot";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_lot_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    //finishedGoodLotTarget represents the finishedGoodLot the portion is being added to.
    private FinishedGoodLot finishedGoodLotTarget;

    public FinishedGoodLotFinishedGoodLotPortion() {
        super();
    }

    public FinishedGoodLotFinishedGoodLotPortion(Long id) {
        super(id);
    }

    public FinishedGoodLotFinishedGoodLotPortion(Long id, FinishedGoodLot finishedGoodLot, Quantity<?> quantity, FinishedGoodLot finishedGoodLotTarget,
            LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, finishedGoodLot, quantity, createdAt, lastUpdated, version);
        setFinishedGoodLotTarget(finishedGoodLotTarget);
    }

    @Override
    public FinishedGoodLot getFinishedGoodLotTarget() {
        return finishedGoodLotTarget;
    }

    @Override
    public void setFinishedGoodLotTarget(FinishedGoodLot finishedGoodLotTarget) {
        this.finishedGoodLotTarget = finishedGoodLotTarget;
    }
}
