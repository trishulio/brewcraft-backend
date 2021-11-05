package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class FinishedGoodInventoryControllerTest {

   private FinishedGoodInventoryController finishedGoodInventoryController;

   private FinishedGoodInventoryService finishedGoodInventoryService;
   
   private AttributeFilter filter;

   @BeforeEach
   public void init() {
       this.finishedGoodInventoryService = mock(FinishedGoodInventoryService.class);
       this.filter = new AttributeFilter();

       this.finishedGoodInventoryController = new FinishedGoodInventoryController(this.finishedGoodInventoryService, this.filter);
   }

   @Test
   public void testGetFinishedGoodInventory_CallsServicesWithArguments_AndReturnsPageDtoOfFinishedGoodIventoryDtosMappedFromFinishedGoodInventoryPage() {
       List<FinishedGoodInventory> skus = List.of(
               new FinishedGoodInventory(1L, new Sku(2L), 50L)
       );
       
       Page<FinishedGoodInventory> mPage = mock(Page.class);
       doReturn(skus.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(this.finishedGoodInventoryService).getAll(
           Set.of(1L),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<FinishedGoodInventoryDto> dto = this.finishedGoodInventoryController.getFinishedGoodInventory(
           Set.of(1L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10,
           Set.of()
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       
       FinishedGoodInventoryDto finishedGoodInventory = dto.getContent().get(0);
       assertEquals(new SkuDto(2L), finishedGoodInventory.getSku());
       assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50L)), finishedGoodInventory.getQuantity());
   }
}
