package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProcurementLotDto;
import io.company.brewcraft.dto.StockLotDto;
import io.company.brewcraft.model.Lot;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.util.controller.AttributeFilter;

public class LotAggregationControllerTest {
    private LotAggregationController controller;

    private LotAggregationService mService;

    @BeforeEach
    public void init() {
        this.mService = mock(LotAggregationService.class);
        this.controller = new LotAggregationController(this.mService, new AttributeFilter());
    }

    @Test
    public void testGetAggregatedProcurementQuantity_ReturnsLotPageDto_WhenServiceReturnsPage() {
        final Page<ProcurementLot> mPage = new PageImpl<>(List.of(new ProcurementLot(1L)));
        doReturn(mPage).when(this.mService).getAggregatedProcurementQuantity(
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

        final PageDto<ProcurementLotDto> dto = this.controller.getAggregatedProcurementQuantity(
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
            10, // size
            Set.of("")
        );

        final PageDto<ProcurementLotDto> expected = new PageDto<>(
            List.of(new ProcurementLotDto(1L)),
            1,
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testGetAggregatedStockQuantity_ReturnsLotPageDto_WhenServiceReturnsPage() {
        final Page<StockLot> mPage = new PageImpl<>(List.of(new StockLot(1L)));
        doReturn(mPage).when(this.mService).getAggregatedStockQuantity(
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

        final PageDto<StockLotDto> dto = this.controller.getAggregatedStockQuantity(
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
            10, // size
            Set.of("")
        );

        final PageDto<StockLotDto> expected = new PageDto<>(
            List.of(new StockLotDto(1L)),
            1,
            1
        );

        assertEquals(expected, dto);
    }
}
