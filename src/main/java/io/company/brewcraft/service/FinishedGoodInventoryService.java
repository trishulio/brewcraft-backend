package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.FinishedGoodInventory;

public interface FinishedGoodInventoryService {

    Page<FinishedGoodInventory> getAll(Set<Long> skuIds, AggregationFunction aggrFn, FinishedGoodInventory.AggregationField[] groupBy, int page, int size, SortedSet<String> sort, boolean orderAscending);

}
