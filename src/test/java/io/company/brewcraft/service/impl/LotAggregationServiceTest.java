package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Lot;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.service.Selector;
import io.company.brewcraft.service.SumAggregation;

@SuppressWarnings("unchecked")
public class LotAggregationServiceTest {
    private LotAggregationService service;

    private AggregationRepository aggrRepo;

    @BeforeEach
    public void init() {
        this.aggrRepo = mock(AggregationRepository.class);

        this.service = new LotAggregationService(this.aggrRepo);
    }

    @Test
    public void testGetAggregatedProcurementQuantity_CallsRepoWithSelectorAndGroupByAttributes_AndReturnsPageFroaggrRepo() {
        final Selector expSelector = new Selector();
        final Selector expGroupBy = new Selector();

        expSelector.select(Lot.AggregationField.MATERIAL)
                   .select(Lot.AggregationField.QUANTITY_UNIT)
                   .select(new SumAggregation(Lot.AggregationField.QUANTITY_VALUE));

        expGroupBy.select(Lot.AggregationField.MATERIAL)
                  .select(Lot.AggregationField.QUANTITY_UNIT);

        final Page<ProcurementLot> mPage = new PageImpl<>(List.of(new ProcurementLot(1L)));
        doReturn(mPage).when(this.aggrRepo).getAggregation(
            eq(ProcurementLot.class),
            eq(expSelector),
            eq(expGroupBy),
            any(Specification.class), // TODO: Specifications are currently not tested.
            eq(PageRequest.of(1,  10, Sort.by("id")))
        );

        final Page<ProcurementLot> lots = this.service.getAggregatedProcurementQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // shipmentIds
            Set.of(5L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new Lot.AggregationField[] { Lot.AggregationField.MATERIAL }, // groupBy
            new TreeSet<>(List.of("id")), // sortBy
            true, // orderAscending
            1, // page
            10 // size
        );

        final Page<ProcurementLot> expected = new PageImpl<>(
            List.of(new ProcurementLot(1L))
        );

        assertEquals(expected, lots);
    }

    @Test
    public void testGetAggregatedStockQuantity_CallsRepoWithSelectorAndGroupByAttributes_AndReturnsPageFroaggrRepo() {
        final Selector expSelector = new Selector();
        final Selector expGroupBy = new Selector();

        expSelector.select(Lot.AggregationField.MATERIAL)
                   .select(Lot.AggregationField.QUANTITY_UNIT)
                   .select(new SumAggregation(Lot.AggregationField.QUANTITY_VALUE));

        expGroupBy.select(Lot.AggregationField.MATERIAL)
                  .select(Lot.AggregationField.QUANTITY_UNIT);

        final Page<StockLot> mPage = new PageImpl<>(List.of(new StockLot(1L)));
        doReturn(mPage).when(this.aggrRepo).getAggregation(
            eq(StockLot.class),
            eq(expSelector),
            eq(expGroupBy),
            any(Specification.class), // TODO: Specifications are currently not tested.
            eq(PageRequest.of(1,  10, Sort.by("id")))
        );

        final Page<StockLot> lots = this.service.getAggregatedStockQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // shipmentIds
            Set.of(5L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new Lot.AggregationField[] { Lot.AggregationField.MATERIAL }, // groupBy
            new TreeSet<>(List.of("id")), // sortBy
            true, // orderAscending
            1, // page
            10 // size
        );

        final Page<StockLot> expected = new PageImpl<>(
            List.of(new StockLot(1L))
        );

        assertEquals(expected, lots);
    }
}
