package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusService {

    Page<BrewStageStatus> getStatuses(Set<Long> ids, int page, int size, SortedSet<String> sort, boolean orderAscending);

    BrewStageStatus getStatus(String name);

}
