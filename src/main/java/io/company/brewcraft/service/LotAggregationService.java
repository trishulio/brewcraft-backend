package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Lot;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.SpecificationBuilder;

public class LotAggregationService {

    private final AggregationRepository aggrRepo;

    public LotAggregationService(AggregationRepository aggrRepo) {
        this.aggrRepo = aggrRepo;
    }

    public Page<ProcurementLot> getAggregatedProcurementQuantity(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<String> lotNumbers,
            Set<Long> materialIds,
            Set<Long> shipmentIds,
            Set<Long> storageIds,
            Set<String> shipmentNumbers,
            LocalDateTime deliveredDateFrom,
            LocalDateTime deliveredDateTo,
            AggregationFunction aggrFn,
            ProcurementLot.AggregationField[] groupBy,
            SortedSet<String> sort,
            boolean orderAscending,
            int page,
            int size
        ) {
            final Specification<ProcurementLot> spec = SpecificationBuilder.builder()
                                                                           .in(Lot.FIELD_ID, ids)
                                                                           .not().in(Lot.FIELD_ID, excludeIds)
                                                                           .in(Lot.FIELD_LOT_NUMBER, lotNumbers)
                                                                           .in(new String[] { Lot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                                           .in(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                           .in(new String[] { Lot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                           .in(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                           .between(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                           .build();

        final PageRequest pageable = pageRequest(sort, orderAscending, page, size);

        final Selector selectAttr = new Selector();
        final Selector groupByAttr = new Selector();

        Arrays.stream(groupBy).forEach(col -> {
            selectAttr.select(col);
            groupByAttr.select(col);
        });

        selectAttr.select(ProcurementLot.AggregationField.QUANTITY_UNIT);
        groupByAttr.select(ProcurementLot.AggregationField.QUANTITY_UNIT);

        selectAttr.select(aggrFn.getAggregation(ProcurementLot.AggregationField.QUANTITY_VALUE));

        return this.aggrRepo.getAggregation(ProcurementLot.class, selectAttr, groupByAttr, spec, pageable);
    }

    public Page<StockLot> getAggregatedStockQuantity(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<String> lotNumbers,
            Set<Long> materialIds,
            Set<Long> shipmentIds,
            Set<Long> storageIds,
            Set<String> shipmentNumbers,
            LocalDateTime deliveredDateFrom,
            LocalDateTime deliveredDateTo,
            AggregationFunction aggrFn,
            StockLot.AggregationField[] groupBy,
            SortedSet<String> sort,
            boolean orderAscending,
            int page,
            int size
        ) {
            final Specification<StockLot> spec = SpecificationBuilder.builder()
                                                                     .in(Lot.FIELD_ID, ids)
                                                                     .not().in(Lot.FIELD_ID, excludeIds)
                                                                     .in(Lot.FIELD_LOT_NUMBER, lotNumbers)
                                                                     .in(new String[] { Lot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                                     .in(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                     .in(new String[] { Lot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                     .in(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                     .between(new String[] { Lot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                     .build();

        final PageRequest pageable = pageRequest(sort, orderAscending, page, size);

        final Selector selectAttr = new Selector();
        final Selector groupByAttr = new Selector();

        Arrays.stream(groupBy).forEach(col -> {
            selectAttr.select(col);
            groupByAttr.select(col);
        });

        selectAttr.select(StockLot.AggregationField.QUANTITY_UNIT);
        groupByAttr.select(StockLot.AggregationField.QUANTITY_UNIT);

        selectAttr.select(aggrFn.getAggregation(StockLot.AggregationField.QUANTITY_VALUE));

        return this.aggrRepo.getAggregation(StockLot.class, selectAttr, groupByAttr, spec, pageable);
    }
}
