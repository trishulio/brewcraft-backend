package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixturePortion;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.repository.FinishedGoodInventoryRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;

public class FinishedGoodInventoryServiceImpl implements FinishedGoodInventoryService {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodInventoryServiceImpl.class);

    private AggregationService aggrService;

    private FinishedGoodInventoryRepository finishedGoodInventoryRepository;

    public FinishedGoodInventoryServiceImpl(AggregationService aggrService, FinishedGoodInventoryRepository finishedGoodInventoryRepository) {
        this.aggrService = aggrService;
        this.finishedGoodInventoryRepository = finishedGoodInventoryRepository;
    }

    @Override
    public Page<FinishedGoodInventoryAggregation> getAllAggregation(Set<Long> skuIds, AggregationFunction aggrFn, FinishedGoodInventoryAggregation.AggregationField[] groupBy,
            int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<FinishedGoodInventoryAggregation> spec = WhereClauseBuilder.builder()
                                                                                 .in(new String[] { FinishedGoodLot.FIELD_SKU, Sku.FIELD_ID }, skuIds)
                                                                                 .build();

        return this.aggrService.getAggregation(FinishedGoodInventoryAggregation.class, spec, aggrFn, FinishedGoodInventoryAggregation.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
    }

    @Override
    public Page<FinishedGoodInventory> getAll(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<Long> skuIds,
            Set<Long> mixtureIds,
            Set<Long> brewStageIds,
            Set<Long> brewIds,
            Set<String> brewBatchIds,
            Set<Long> productIds,
            SortedSet<String> sort,
            boolean orderAscending,
            int page,
            int size
         ) {
        final Specification<FinishedGoodInventory> spec = WhereClauseBuilder.builder()
                                                                            .in(FinishedGoodInventory.FIELD_ID, ids)
                                                                            .not().in(FinishedGoodInventory.FIELD_ID, excludeIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_SKU, Sku.FIELD_ID }, skuIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_ID }, brewStageIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID }, brewIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_BATCH_ID }, brewBatchIds)
                                                                            .in(new String[] { FinishedGoodInventory.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_PRODUCT, Product.FIELD_ID }, productIds)
                                                                            .build();

        return finishedGoodInventoryRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));
    }
}
