package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.WhereClauseBuilder;

public class LotAggregationService {

    private AggregationService aggrService;

    public LotAggregationService(AggregationService aggrService) {
        this.aggrService = aggrService;
    }

    public Page<ProcurementLot> getAggregatedProcurementQuantity(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> lotNumbers,
        Set<Long> materialIds,
        Set<Long> materialCategoryIds,
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
        final Specification<ProcurementLot> spec = WhereClauseBuilder.builder()
                                                                       .in(ProcurementLot.FIELD_ID, ids)
                                                                       .not().in(ProcurementLot.FIELD_ID, excludeIds)
                                                                       .in(ProcurementLot.FIELD_LOT_NUMBER, lotNumbers)
                                                                       .in(new String[] { ProcurementLot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_MATERIAL, Material.FIELD_CATEGORY, MaterialCategory.FIELD_ID }, materialCategoryIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                       .between(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                       .build();

        return this.aggrService.getAggregation(ProcurementLot.class, spec, aggrFn, ProcurementLot.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
    }

    public Page<StockLot> getAggregatedStockQuantity(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> lotNumbers,
        Set<Long> materialIds,
        Set<Long> materialCategoryIds,
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
        final Specification<StockLot> spec = WhereClauseBuilder.builder()
                                                                 .in(StockLot.FIELD_ID, ids)
                                                                 .not().in(StockLot.FIELD_ID, excludeIds)
                                                                 .in(StockLot.FIELD_LOT_NUMBER, lotNumbers)
                                                                 .in(new String[] { StockLot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                                 .in(new String[] { StockLot.FIELD_MATERIAL, Material.FIELD_CATEGORY, MaterialCategory.FIELD_ID }, materialCategoryIds)
                                                                 .in(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                 .in(new String[] { StockLot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                 .in(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                 .between(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                 .build();

        return this.aggrService.getAggregation(StockLot.class, spec, aggrFn, StockLot.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
    }
}
