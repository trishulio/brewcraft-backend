package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BrewLogType;
import io.company.brewcraft.model.BrewMeasureValue;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.service.BrewLogTypeAccessor;
import io.company.brewcraft.service.BrewStageAccessor;

public interface BaseBrewLog extends BrewStageAccessor, BrewLogTypeAccessor {
    public Long getId();

    public void setId(Long id);

    public BrewStage getBrewStage();

    public void setBrewStage(BrewStage brewStage);
    
    public BrewLogType getType();

    public void setType(BrewLogType type);

    public LocalDateTime getRecordedAt();

    public void setRecordedAt(LocalDateTime recordedAt);

    public String getComment();
    
    public void setComment(String comment);

    public List<BrewMeasureValue> getRecordedMeasure();

    public void setRecordedMeasure(List<BrewMeasureValue> recordedMeasure);
}
