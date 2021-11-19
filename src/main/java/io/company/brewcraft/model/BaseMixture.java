package io.company.brewcraft.model;

import java.util.List;

import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public interface BaseMixture extends ParentMixturesAccessor, EquipmentAccessor, BrewStageAccessor, QuantityAccessor {
    
    List<MixtureMaterialPortion> getMaterialPortions();

    void setMaterialPortions(List<MixtureMaterialPortion> materialPortions);

    List<MixtureRecording> getRecordedMeasures();

    void setRecordedMeasures(List<MixtureRecording> recordedMeasures);

}
