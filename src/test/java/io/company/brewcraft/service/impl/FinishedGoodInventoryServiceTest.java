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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.repository.FinishedGoodInventoryRepository;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.FinishedGoodInventoryServiceImpl;

public class FinishedGoodInventoryServiceTest {
   private FinishedGoodInventoryService finishedGoodInventoryService;

   private AggregationService mAggrService;

   private FinishedGoodInventoryRepository mFinishedGoodInventoryRepo;

   @BeforeEach
   public void init() {
       this.mAggrService = mock(AggregationService.class);
       this.mFinishedGoodInventoryRepo = mock(FinishedGoodInventoryRepository.class);

       this.finishedGoodInventoryService = new FinishedGoodInventoryServiceImpl(this.mAggrService, mFinishedGoodInventoryRepo);
   }

   @Test
   public void testGetSkus_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       ArgumentCaptor<Specification<FinishedGoodInventoryAggregation>> captor = ArgumentCaptor.forClass(Specification.class);

       doReturn(new PageImpl<>(List.of(new FinishedGoodInventoryAggregation(1L)))).when(mAggrService).getAggregation(
           eq(FinishedGoodInventoryAggregation.class),
           captor.capture(),
           eq(AggregationFunction.SUM),
           eq(FinishedGoodInventoryAggregation.AggregationField.QUANTITY_VALUE),
           eq(new FinishedGoodInventoryAggregation.AggregationField[] { FinishedGoodInventoryAggregation.AggregationField.ID }),
           eq(new TreeSet<>(List.of("id"))),
           eq(true),
           eq(1),
           eq(10)
       );

       Page<FinishedGoodInventoryAggregation> page = this.finishedGoodInventoryService.getAllAggregation(
           Set.of(1L), // ski_ids
           AggregationFunction.SUM, // aggrFn
           new FinishedGoodInventoryAggregation.AggregationField[] { FinishedGoodInventoryAggregation.AggregationField.ID }, // groupBy
           1, // page
           10, // size
           new TreeSet<>(List.of("id")), // sort
           true // orderAscending
       );

       Page<FinishedGoodInventoryAggregation> expected = new PageImpl<>(List.of(new FinishedGoodInventoryAggregation(1L)));

       assertEquals(expected, page);

       captor.getValue();
       // TODO: Validate the specification.
   }

   @Test
   public void testGetFinishedGoodInventory_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       @SuppressWarnings("unchecked")
       final ArgumentCaptor<Specification<FinishedGoodInventory>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<FinishedGoodInventory> mPage = new PageImpl<>(List.of(new FinishedGoodInventory(1L)));
       doReturn(mPage).when(this.mFinishedGoodInventoryRepo).findAll(captor.capture(), any(Pageable.class));

       final Page<FinishedGoodInventory> page = this.finishedGoodInventoryService.getAll(
           Set.of(1L),
           Set.of(2L),
           Set.of(3L),
           Set.of(4L),
           Set.of(5L),
           Set.of(6L),
           Set.of("a123"),
           Set.of(8L),
           new TreeSet<>(List.of("id")),
           true,
           10,
           20
       );

       final Page<FinishedGoodInventory> expected = new PageImpl<>(List.of(new FinishedGoodInventory(1L)));
       assertEquals(expected, page);

       // TODO: Pending testing for the specification
       captor.getValue();
   }
}
