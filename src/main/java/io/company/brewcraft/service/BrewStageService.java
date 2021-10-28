package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewStage;

public interface BrewStageService {

    Page<BrewStage> getBrewStages(Set<Long> ids, Set<Long> brewIds, Set<Long> statusIds, Set<Long> taskIds, LocalDateTime startedAtFrom, LocalDateTime startedAtTo, LocalDateTime endedAtFrom, LocalDateTime endedAtTo, int page, int size, SortedSet<String> sort, boolean orderAscending);

    BrewStage getBrewStage(Long brewStageId);

    List<BrewStage> addBrewStages(List<BrewStage> brewStage);

    BrewStage putBrewStage(Long brewStageId, BrewStage brewStage);

    BrewStage patchBrewStage(Long brewStageId, BrewStage brewStage);

    void deleteBrewStage(Long brewStageId);

    boolean brewStageExists(Long brewStageId);

 }

