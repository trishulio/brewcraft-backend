package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "finished_good_lot_material_portion")
@PrimaryKeyJoinColumn(name="MATERIAL_PORTION_ID")
public class FinishedGoodLotMaterialPortion extends MaterialPortion implements UpdateFinishedGoodLotMaterialPortion<FinishedGoodLot> {
    public static final String FIELD_FINISHED_GOOD_LOT = "finishedGoodLot";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_lot_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private FinishedGoodLot finishedGoodLot;

    public FinishedGoodLotMaterialPortion() {
        super();
    }

    public FinishedGoodLotMaterialPortion(Long id) {
        super(id);
    }

    public FinishedGoodLotMaterialPortion(Long id, MaterialLot materialLot, Quantity<?> quantity, FinishedGoodLot finishedGoodLot,
            LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, materialLot, quantity, addedAt, createdAt, lastUpdated, version);
        setFinishedGoodLot(finishedGoodLot);
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
