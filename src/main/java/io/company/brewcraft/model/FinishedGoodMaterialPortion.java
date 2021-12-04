package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "finished_good_material_portion")
@PrimaryKeyJoinColumn(name="MATERIAL_PORTION_ID")
public class FinishedGoodMaterialPortion extends MaterialPortion implements UpdateFinishedGoodMaterialPortion<FinishedGood> {

    public static final String FIELD_FINISHED_GOOD = "finishedGood";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private FinishedGood finishedGood;

    public FinishedGoodMaterialPortion() {
        super();
    }

    public FinishedGoodMaterialPortion(Long id) {
        super(id);
    }

    public FinishedGoodMaterialPortion(Long id, MaterialLot materialLot, Quantity<?> quantity, FinishedGood finishedGood,
            LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, materialLot, quantity, addedAt, createdAt, lastUpdated, version);
        setFinishedGood(finishedGood);
    }

    @Override
    public FinishedGood getFinishedGood() {
        return finishedGood;
    }

    @Override
    public void setFinishedGood(FinishedGood finishedGood) {
        this.finishedGood = finishedGood;
    }
}
