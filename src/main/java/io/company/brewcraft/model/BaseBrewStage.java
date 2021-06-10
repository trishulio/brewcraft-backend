package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;

public interface BaseBrewStage extends BrewStageStatusAccessor, BrewTaskAccessor {

    public Brew getBrew();

    public void setBrew(Brew brew);

    public BrewStageStatus getStatus();

    public void setStatus(BrewStageStatus status);

    public BrewTask getTask();

    public void setTask(BrewTask task);

    public List<BrewLog> getBrewLogs();

    public void setBrewLogs(List<BrewLog> brewLogs);

    public LocalDateTime getStartedAt();

    public void setStartedAt(LocalDateTime startDate);

    public LocalDateTime getEndedAt();

    public void setEndedAt(LocalDateTime endDate);
}
