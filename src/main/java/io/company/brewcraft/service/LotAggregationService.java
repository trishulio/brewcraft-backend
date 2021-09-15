package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Lot;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.SpecificationBuilder;

/**
 * Gotcha: The service doesn't support sorting the results by Material Name. By the nature of the API call,
 * the group_by parameters translates to the SQL's SELECT and GROUP BY clauses. Since material's name is not
 * part of the SELECT clause, a sort cannot be performed on it. However, hibernate takes care of fetching the
 * Material object so it's included in the return object. To solve this issue, I have set a default sort by
 * Material name on the VIEW itself. However, a sort in the QUERY will override it. So, currently, the sort on
 * Material name cannot be combined with sort on other columns.
 * 
 * An alternative to support multi-column sort that can be performed on the property attribute values is doing
 * it outside the SQL query, programmatically. Collections API provides library methods to accomplish this easily.
 * However, a major challenge associated with that would be parsing the sort-set parameter String values and
 * converting them to the Comparators for the Collections API:
 * https://stackoverflow.com/questions/4258700/collections-sort-with-multiple-fields
 */
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

            groupBy = addToArray(groupBy, ProcurementLot.AggregationField.QUANTITY_UNIT);

            return this.aggrService.getAggregation(ProcurementLot.class, spec, aggrFn, ProcurementLot.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
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

        groupBy = addToArray(groupBy, StockLot.AggregationField.QUANTITY_UNIT);

        return this.aggrService.getAggregation(StockLot.class, spec, aggrFn, StockLot.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
    }

    private <T> T[] addToArray(T[] source, T... extra) {
        int lenOriginal = source.length;
        source = Arrays.copyOf(source, source.length + extra.length);

        for (int i = 0; i < extra.length; i++) {
            source[lenOriginal + i] = extra[i];
        }

        return source;
    }
}
