package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.repository.MaterialLotAggregationRepository;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.MaterialLotInventoryService;
import io.company.brewcraft.service.Selector;
import io.company.brewcraft.service.SumAggregation;

public class MaterialLotInventoryServiceTest {

    private MaterialLotInventoryService service;
    
    private MaterialLotAggregationRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(MaterialLotAggregationRepository.class);
        service = new MaterialLotInventoryService(mRepo);
    }
    
    @Test
    public void testGetAggregatedProcurementQuantity_CallsRepoWithSelectorAndGroupByAttributes_AndReturnsPageFromRepo() {
        Selector expSelector = new Selector();
        Selector expGroupBy = new Selector();
        
        expSelector.select(MaterialLot.AggregationField.MATERIAL)
                   .select(MaterialLot.AggregationField.QUANTITY_UNIT)
                   .select(new SumAggregation(MaterialLot.AggregationField.QUANTITY_VALUE));

        expGroupBy.select(MaterialLot.AggregationField.MATERIAL)
                  .select(MaterialLot.AggregationField.QUANTITY_UNIT);

        Page<MaterialLot> mPage = new PageImpl<MaterialLot>(List.of(new MaterialLot(1L)));
        doReturn(mPage).when(mRepo).getAggregation(
            eq(expSelector),
            eq(expGroupBy),
            any(Specification.class), // TODO: Specs are currently not tested.
            eq(PageRequest.of(1,  10, Sort.by("id")))
        );

        Page<MaterialLot> lots = service.getAggregatedProcurementQuantity(
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
            new MaterialLot.AggregationField[] { MaterialLot.AggregationField.MATERIAL }, // groupBy
            Set.of("id"), // sortBy
            true, // orderAscending
            1, // page
            10 // size
        );
        
        Page<MaterialLot> expected = new PageImpl<>(
            List.of(new MaterialLot(1L))
        );
        
        assertEquals(expected, lots);
    }
}
