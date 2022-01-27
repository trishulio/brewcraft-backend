package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.repository.WhereClauseBuilder;

public class FinishedGoodInventoryServiceImpl implements FinishedGoodInventoryService {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodInventoryServiceImpl.class);

    private AggregationService aggrService;

    public FinishedGoodInventoryServiceImpl(AggregationService aggrService) {
        this.aggrService = aggrService;
    }

    @Override
    public Page<FinishedGoodInventory> getAll(Set<Long> skuIds, AggregationFunction aggrFn, FinishedGoodInventory.AggregationField[] groupBy,
            int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<FinishedGoodInventory> spec = WhereClauseBuilder.builder()
                                                                      .in(new String[] { FinishedGoodLot.FIELD_SKU, Sku.FIELD_ID }, skuIds)
                                                                      .build();

        return this.aggrService.getAggregation(FinishedGoodInventory.class, spec, aggrFn, FinishedGoodInventory.AggregationField.QUANTITY, groupBy, sort, orderAscending, page, size);
    }
}
