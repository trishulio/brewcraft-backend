package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "finished_good_mixture_portion")
@PrimaryKeyJoinColumn(name="MIXTURE_PORTION_ID")
public class FinishedGoodMixturePortion extends MixturePortion implements UpdateFinishedGoodMixturePortion<FinishedGood> {

    public static final String FIELD_MIXTURE = "mixture";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private FinishedGood finishedGood;

    public FinishedGoodMixturePortion() {
        super();
    }

    public FinishedGoodMixturePortion(Long id, Mixture mixture, Quantity<?> quantity, FinishedGood finishedGood,
            LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, mixture, quantity, addedAt, createdAt, lastUpdated, version);
        setFinishedGood(finishedGood);
    }

    public FinishedGoodMixturePortion(Long id) {
        super(id);
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
