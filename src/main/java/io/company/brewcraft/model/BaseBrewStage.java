package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;

public interface BaseBrewStage extends BrewStageStatusAccessor, BrewTaskAccessor, BrewAccessor {
    public List<Mixture> getMixtures();

    public void setMixtures(List<Mixture> mixtures);

    public LocalDateTime getStartedAt();

    public void setStartedAt(LocalDateTime startDate);

    public LocalDateTime getEndedAt();

    public void setEndedAt(LocalDateTime endDate);
}
