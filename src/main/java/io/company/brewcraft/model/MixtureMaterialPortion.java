package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "MIXTURE_MATERIAL_PORTION")
@PrimaryKeyJoinColumn(name="MATERIAL_PORTION_ID")
public class MixtureMaterialPortion extends MaterialPortion implements UpdateMixtureMaterialPortion {
    
    public static final String FIELD_MIXTURE = "mixture";
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id", nullable = true)
    private Mixture mixture;

    public MixtureMaterialPortion() {
        super();
    }

    public MixtureMaterialPortion(Long id, MaterialLot materialLot, Quantity<?> quantity, Mixture mixture,
            LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super(id, materialLot, quantity, addedAt, createdAt, lastUpdated, version);
        setMixture(mixture);
    }

    public MixtureMaterialPortion(Long id) {
        super(id);
    }
    
    @Override
    public Mixture getMixture() {
        return mixture;
    }

    @Override
    public void setMixture(Mixture mixture) {
        this.mixture = mixture;
    }

}
