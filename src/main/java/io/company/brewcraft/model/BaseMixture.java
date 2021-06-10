package io.company.brewcraft.model;

import java.util.List;

import javax.measure.Quantity;

import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public interface BaseMixture extends ParentMixtureAccessor, EquipmentAccessor {

	Mixture getParentMixture();

	void setParentMixture(Mixture parentMixture);

	List<Mixture> getChildMixtures();

	void setChildMixtures(List<Mixture> childMixtures);

	void addChildMixture(Mixture childMixture);

	Quantity<?> getQuantity();

	void setQuantity(Quantity<?> quantity);

	Equipment getEquipment();

	void setEquipment(Equipment equipment);

	List<MaterialPortion> getMaterialPortions();

	void setMaterialPortions(List<MaterialPortion> materialPortions);

	List<BrewLog> getBrewLogs();

	void setBrewLogs(List<BrewLog> brewLogs);
}
