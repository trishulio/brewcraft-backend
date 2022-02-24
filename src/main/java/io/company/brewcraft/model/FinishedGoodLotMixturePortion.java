package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "finished_good_lot_mixture_portion")
@PrimaryKeyJoinColumn(name="MIXTURE_PORTION_ID")
public class FinishedGoodLotMixturePortion extends MixturePortion implements UpdateFinishedGoodLotMixturePortion<FinishedGoodLot> {

    public static final String FIELD_MIXTURE = "mixture";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_lot_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private FinishedGoodLot finishedGoodLot;

    public FinishedGoodLotMixturePortion() {
        super();
    }

    public FinishedGoodLotMixturePortion(Long id, Mixture mixture, Quantity<?> quantity, FinishedGoodLot finishedGoodLot,
            LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, mixture, quantity, addedAt, createdAt, lastUpdated, version);
        setFinishedGoodLot(finishedGoodLot);
    }

    public FinishedGoodLotMixturePortion(Long id) {
        super(id);
    }

    @Override
    public FinishedGoodLot getFinishedGoodLot() {
        return finishedGoodLot;
    }

    @Override
    public void setFinishedGoodLot(FinishedGoodLot finishedGoodLot) {
        this.finishedGoodLot = finishedGoodLot;
    }
}
