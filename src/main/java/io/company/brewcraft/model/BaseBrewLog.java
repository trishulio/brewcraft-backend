package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.BrewLogTypeAccessor;
import io.company.brewcraft.service.BrewStageAccessor;

public interface BaseBrewLog extends BrewLogTypeAccessor, BrewStageAccessor  {

	BrewStage getBrewStage();

	void setBrewStage(BrewStage brewStage);

	LocalDateTime getRecordedAt();

	void setRecordedAt(LocalDateTime recordedAt);

	String getComment();

	void setComment(String comment);

	BrewLogType getType();

	void setType(BrewLogType type);

	List<BrewMeasureValue> getRecordedMeasures();

	void setRecordedMeasures(List<BrewMeasureValue> recordedMeasures);

	Mixture getMixture();

	void setMixture(Mixture mixture);

}
