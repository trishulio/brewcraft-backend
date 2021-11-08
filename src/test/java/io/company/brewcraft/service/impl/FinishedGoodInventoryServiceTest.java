package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.repository.FinishedGoodInventoryRepository;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.FinishedGoodInventoryServiceImpl;

public class FinishedGoodInventoryServiceTest {
   private FinishedGoodInventoryService finishedGoodInventoryService;

   private FinishedGoodInventoryRepository finishedGoodInventoryRepository;

   @BeforeEach
   public void init() {
       this.finishedGoodInventoryRepository = mock(FinishedGoodInventoryRepository.class);

       this.finishedGoodInventoryService = new FinishedGoodInventoryServiceImpl(this.finishedGoodInventoryRepository);
   }

   @Test
   public void testGetSkus_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       FinishedGoodInventory finishedGoodInventoryEntity = new FinishedGoodInventory(1L, new Sku(2L), 50L);

       List<FinishedGoodInventory> finishedGoodInventoryEntities = Arrays.asList(finishedGoodInventoryEntity);

       Page<FinishedGoodInventory> expectedPage = new PageImpl<>(finishedGoodInventoryEntities);

       ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

       when(finishedGoodInventoryRepository.findAll(ArgumentMatchers.<Specification<FinishedGoodInventory>>any(), pageableArgument.capture())).thenReturn(expectedPage);

       Page<FinishedGoodInventory> actualPage = finishedGoodInventoryService.getAll(null, 0, 100, new TreeSet<>(List.of("id")), true);

       assertEquals(0, pageableArgument.getValue().getPageNumber());
       assertEquals(100, pageableArgument.getValue().getPageSize());
       assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
       assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
       assertEquals(1, actualPage.getNumberOfElements());

       FinishedGoodInventory actualFinishedGoodInventory = actualPage.get().findFirst().get();

       assertEquals(finishedGoodInventoryEntity.getId(), actualFinishedGoodInventory.getId());
       assertEquals(finishedGoodInventoryEntity.getSku(), actualFinishedGoodInventory.getSku());
       assertEquals(finishedGoodInventoryEntity.getQuantity(), actualFinishedGoodInventory.getQuantity());
   }

}
