package io.company.brewcraft.model;

import java.util.List;

import javax.measure.Quantity;

import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public interface BaseMixture extends ParentMixtureAccessor, EquipmentAccessor, BrewStageAccessor {

	List<Mixture> getChildMixtures();

	void setChildMixtures(List<Mixture> childMixtures);

	void addChildMixture(Mixture childMixture);

	Quantity<?> getQuantity();

	void setQuantity(Quantity<?> quantity);

	List<MaterialPortion> getMaterialPortions();

	void setMaterialPortions(List<MaterialPortion> materialPortions);

	List<MixtureRecording> getRecordedMeasures();

	void setRecordedMeasures(List<MixtureRecording> recordedMeasures);
	
}
