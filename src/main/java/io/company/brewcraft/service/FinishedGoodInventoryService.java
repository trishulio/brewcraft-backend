package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;

public interface FinishedGoodInventoryService {

    Page<FinishedGoodInventoryAggregation> getAllAggregation(Set<Long> skuIds, AggregationFunction aggrFn, FinishedGoodInventoryAggregation.AggregationField[] groupBy, int page, int size, SortedSet<String> sort, boolean orderAscending);

    Page<FinishedGoodInventory> getAll(Set<Long> ids, Set<Long> excludeIds, Set<Long> skuIds,
            Set<Long> mixtureIds, Set<Long> brewStageIds, Set<Long> brewIds, Set<String> brewBatchIds,
            Set<Long> productIds, SortedSet<String> sort, boolean orderAscending, int page, int size);

}

