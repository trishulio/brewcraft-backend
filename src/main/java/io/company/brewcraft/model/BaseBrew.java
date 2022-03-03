package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.service.AssignedToAccessor;
import io.company.brewcraft.service.OwnedByAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ProductAccessor;

public interface BaseBrew extends ProductAccessor, ParentBrewAccessor, AssignedToAccessor, OwnedByAccessor {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getBatchId();

    void setBatchId(String batchId);

    List<Brew> getChildBrews();

    void setChildBrews(List<Brew> childBrews);

    List<BrewStage> getBrewStages();

    void setBrewStages(List<BrewStage> brewStages);

    LocalDateTime getStartedAt();

    void setStartedAt(LocalDateTime startedAt);

    LocalDateTime getEndedAt();

    void setEndedAt(LocalDateTime endedAt);

}
