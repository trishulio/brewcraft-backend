package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.FinishedGoodInventoryServiceImpl;

public class FinishedGoodInventoryServiceTest {
   private FinishedGoodInventoryService finishedGoodInventoryService;

   private AggregationService mAggrService;

   @BeforeEach
   public void init() {
       this.mAggrService = mock(AggregationService.class);

       this.finishedGoodInventoryService = new FinishedGoodInventoryServiceImpl(this.mAggrService);
   }

   @Test
   public void testGetSkus_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       ArgumentCaptor<Specification<FinishedGoodInventory>> captor = ArgumentCaptor.forClass(Specification.class);

       doReturn(new PageImpl<>(List.of(new FinishedGoodInventory(1L)))).when(mAggrService).getAggregation(
           eq(FinishedGoodInventory.class),
           captor.capture(),
           eq(AggregationFunction.SUM),
           eq(FinishedGoodInventory.AggregationField.QUANTITY),
           eq(new FinishedGoodInventory.AggregationField[] { FinishedGoodInventory.AggregationField.ID }),
           eq(new TreeSet<>(List.of("id"))),
           eq(true),
           eq(1),
           eq(10)
       );

       Page<FinishedGoodInventory> page = this.finishedGoodInventoryService.getAll(
           Set.of(1L), // ski_ids
           AggregationFunction.SUM, // aggrFn
           new FinishedGoodInventory.AggregationField[] { FinishedGoodInventory.AggregationField.ID }, // groupBy
           1, // page
           10, // size
           new TreeSet<>(List.of("id")), // sort
           true // orderAscending
       );

       Page<FinishedGoodInventory> expected = new PageImpl<>(List.of(new FinishedGoodInventory(1L)));

       assertEquals(expected, page);

       captor.getValue();
       // TODO: Validate the specification.
   }
}
