package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.repository.FinishedGoodInventoryRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;

public class FinishedGoodInventoryServiceImpl implements FinishedGoodInventoryService {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodInventoryServiceImpl.class);

    private FinishedGoodInventoryRepository finishedGoodInventoryRepository;

    public FinishedGoodInventoryServiceImpl(FinishedGoodInventoryRepository finishedGoodInventoryRepository) {
        this.finishedGoodInventoryRepository = finishedGoodInventoryRepository;
    }

    public Page<FinishedGoodInventory> getAll(Set<Long> skuIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<FinishedGoodInventory> spec = WhereClauseBuilder.builder()
                                                                        .in(new String[] { FinishedGood.FIELD_SKU, Sku.FIELD_ID }, skuIds)
                                                                        .build();

        Page<FinishedGoodInventory> finishedGoodInventory = finishedGoodInventoryRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return finishedGoodInventory;
    }

}
