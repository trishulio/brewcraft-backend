package io.company.brewcraft.model;

import java.util.List;

import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public interface BaseMixture extends ParentMixtureAccessor, EquipmentAccessor, BrewStageAccessor, QuantityAccessor {

    List<Mixture> getChildMixtures();

    void setChildMixtures(List<Mixture> childMixtures);

    void addChildMixture(Mixture childMixture);

    List<MixtureMaterialPortion> getMaterialPortions();

    void setMaterialPortions(List<MixtureMaterialPortion> materialPortions);

    List<MixtureRecording> getRecordedMeasures();

    void setRecordedMeasures(List<MixtureRecording> recordedMeasures);

}
