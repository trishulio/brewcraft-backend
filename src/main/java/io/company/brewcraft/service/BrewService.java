package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Brew;

public interface BrewService {

    public Page<Brew> getBrews(Set<Long> ids, Set<String> batchIds, Set<String> names, Set<Long> productIds, Set<Long> stageTaskIds, Set<Long> excludeStageTaskIds, LocalDateTime startedAtFrom, LocalDateTime startedAtTo,  LocalDateTime endedAtFrom, LocalDateTime endedAtTo, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Brew getBrew(Long brewId);

    public Brew addBrew(Brew brew);

    public Brew putBrew(Long brewId, Brew brew);

    public Brew patchBrew(Long brewId, Brew brew);

    public void deleteBrew(Long brewId);

    public boolean brewExists(Long brewId);

 }

