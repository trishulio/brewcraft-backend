package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewTask;

public interface BrewTaskService {

    BrewTask getTask(Long id);

    Page<BrewTask> getTasks(Set<Long> ids, Set<String> names, int page, int size, SortedSet<String> sort, boolean orderAscending);

}
