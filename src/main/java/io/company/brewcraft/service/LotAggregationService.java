package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Material;
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
                                                                       .in(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                       .in(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                       .between(new String[] { ProcurementLot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                       .build();

        /***
         * Since the select and the group_by clause are made to always be the same, to
         * get all the properties of a lot, the client needs to specify all column_names
         * like group_by=ID,MATERIAL,QUANTITY,SHIPMENT,STORAGE otherwise all those
         * attributes would be null by default. But when group_by clause contains the
         * attribute ID, it is guaranteed that all individual tuples will be returned
         * without any aggregation on them because IDs are unique. This means, we can do
         * one better. When group_by=ID is present in the request, the service adds all
         * the remaining fields to the select and group_by clause. This makes it
         * convenient for the client to get all the results and not have to specify an
         * entire list of attributes.
         */
        if (ArrayUtils.contains(groupBy, ProcurementLot.AggregationField.ID)) {
            ProcurementLot.AggregationField[] fields = ProcurementLot.AggregationField.values();
            groupBy = ArrayUtils.remove(fields, fields.length - 1);
        } else if (ArrayUtils.contains(groupBy, ProcurementLot.AggregationField.MATERIAL)) {
            groupBy = ArrayUtils.addAll(groupBy, ProcurementLot.AggregationField.MATERIAL_NAME, ProcurementLot.AggregationField.QUANTITY_UNIT);
        } else {
            groupBy = ArrayUtils.addAll(groupBy, ProcurementLot.AggregationField.QUANTITY_UNIT);
        }
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
        final Specification<StockLot> spec = WhereClauseBuilder.builder()
                                                                 .in(StockLot.FIELD_ID, ids)
                                                                 .not().in(StockLot.FIELD_ID, excludeIds)
                                                                 .in(StockLot.FIELD_LOT_NUMBER, lotNumbers)
                                                                 .in(new String[] { StockLot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                                 .in(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                                 .in(new String[] { StockLot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                                 .in(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                                 .between(new String[] { StockLot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                                 .build();

        /***
         * Since the select and the group_by clause are made to always be the same, to
         * get all the properties of a lot, the client needs to specify all column_names
         * like group_by=ID,MATERIAL,QUANTITY,SHIPMENT,STORAGE otherwise all those
         * attributes would be null by default. But when group_by clause contains the
         * attribute ID, it is guaranteed that all individual tuples will be returned
         * without any aggregation on them because IDs are unique. This means, we can do
         * one better. When group_by=ID is present in the request, the service adds all
         * the remaining fields to the select and group_by clause. This makes it
         * convenient for the client to get all the results and not have to specify an
         * entire list of attributes.
         */
        if (ArrayUtils.contains(groupBy, StockLot.AggregationField.ID)) {
            StockLot.AggregationField[] fields = StockLot.AggregationField.values();
            groupBy = ArrayUtils.remove(fields, fields.length - 1);
        } else if (ArrayUtils.contains(groupBy, StockLot.AggregationField.MATERIAL)) {
            groupBy = ArrayUtils.addAll(groupBy, StockLot.AggregationField.MATERIAL_NAME, StockLot.AggregationField.QUANTITY_UNIT);
        } else {
            groupBy = ArrayUtils.addAll(groupBy, StockLot.AggregationField.QUANTITY_UNIT);
        }

        return this.aggrService.getAggregation(StockLot.class, spec, aggrFn, StockLot.AggregationField.QUANTITY_VALUE, groupBy, sort, orderAscending, page, size);
    }
}
