package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.LotAggregationService;

@SuppressWarnings("unchecked")
public class LotAggregationServiceTest {
    private LotAggregationService service;

    private AggregationService mAggrService;

    @BeforeEach
    public void init() {
        mAggrService = mock(AggregationService.class);

        this.service = new LotAggregationService(mAggrService);
    }

    @Test
    public void testGetAggregatedProcurementQuantity_BuildsSpecAndReturnsPageFromAggregationService() {
        ArgumentCaptor<Specification<ProcurementLot>> captor = ArgumentCaptor.forClass(Specification.class);

        doReturn(new PageImpl<>(List.of(new ProcurementLot(1L)))).when(mAggrService).getAggregation(
            eq(ProcurementLot.class),
            captor.capture(),
            eq(AggregationFunction.SUM),
            eq(ProcurementLot.AggregationField.QUANTITY_VALUE),
            eq(new ProcurementLot.AggregationField[] { ProcurementLot.AggregationField.ID }),
            eq(new TreeSet<>(List.of("id"))),
            eq(true),
            eq(1),
            eq(10)
        );

        Page<ProcurementLot> page = this.service.getAggregatedProcurementQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // materialCategoryIds
            Set.of(5L), // shipmentIds
            Set.of(6L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new ProcurementLot.AggregationField[] { ProcurementLot.AggregationField.ID }, // groupBy
            new TreeSet<>(List.of("id")), // sort
            true, // orderAscending
            1, // page
            10 // size
        );

        Page<ProcurementLot> expected = new PageImpl<>(List.of(new ProcurementLot(1L)));

        assertEquals(expected, page);

        captor.getValue();
        // TODO: Validate the specification.
    }

    @Test
    public void testGetAggregatedStockQuantity_BuildsSpecAndReturnsPageFromAggregationService() {
        ArgumentCaptor<Specification<StockLot>> captor = ArgumentCaptor.forClass(Specification.class);

        doReturn(new PageImpl<>(List.of(new StockLot(1L)))).when(mAggrService).getAggregation(
            eq(StockLot.class),
            captor.capture(),
            eq(AggregationFunction.SUM),
            eq(StockLot.AggregationField.QUANTITY_VALUE),
            eq(new StockLot.AggregationField[] { StockLot.AggregationField.ID }),
            eq(new TreeSet<>(List.of("id"))),
            eq(true),
            eq(1),
            eq(10)
        );

        Page<StockLot> page = this.service.getAggregatedStockQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // materialCategoryIds
            Set.of(5L), // shipmentIds
            Set.of(6L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new ProcurementLot.AggregationField[] { ProcurementLot.AggregationField.ID }, // groupBy
            new TreeSet<>(List.of("id")), // sort
            true, // orderAscending
            1, // page
            10 // size
        );

        Page<StockLot> expected = new PageImpl<>(List.of(new StockLot(1L)));

        assertEquals(expected, page);

        captor.getValue();
        // TODO: Validate the specification.
    }
}
