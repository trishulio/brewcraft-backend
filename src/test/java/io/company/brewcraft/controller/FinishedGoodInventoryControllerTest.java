package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.FinishedGoodInventoryAggregationDto;
import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.FinishedGoodInventoryAggregationFieldCollection;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.uom.se.quantity.Quantities;

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
   public void testGetAllAggregations_CallsServicesWithArguments_AndReturnsPageDtoOfFinishedGoodIventoryAggregationDtosMappedFromFinishedGoodInventoryAggregationPage() {
       List<FinishedGoodInventoryAggregation> finishedGoodInventoryAggregations = List.of(
           new FinishedGoodInventoryAggregation(1L, new Sku(2L), LocalDateTime.of(1995, 1, 1, 1, 1), Quantities.getQuantity(new BigDecimal(50.0), SupportedUnits.EACH))
       );

       Page<FinishedGoodInventoryAggregation> mPage = mock(Page.class);
       doReturn(finishedGoodInventoryAggregations.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(this.finishedGoodInventoryService).getAllAggregation(
           Set.of(1L),
           AggregationFunction.SUM,
           FinishedGoodInventoryAggregationFieldCollection.ID.getFields(),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<FinishedGoodInventoryAggregationDto> dto = this.finishedGoodInventoryController.getAllAggregation(
           Set.of(1L),
           AggregationFunction.SUM,
           FinishedGoodInventoryAggregationFieldCollection.ID,
           new TreeSet<>(List.of("id")),
           true,
           1,
           10,
           Set.of()
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());

       FinishedGoodInventoryAggregationDto finishedGoodInventory = dto.getContent().get(0);
       assertEquals(1L, finishedGoodInventory.getId());
       assertEquals(new SkuDto(2L), finishedGoodInventory.getSku());
       assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50.0)), finishedGoodInventory.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
   }

   @Test
   public void testGetAll_CallsServicesWithArguments_AndReturnsPageDtoOfFinishedGoodIventoryDtosMappedFromFinishedGoodInventoryPage() {
       List<FinishedGoodInventory> finishedGoodInventoryList = List.of(
           new FinishedGoodInventory(
               10L,
               new Sku(5L),
               List.of(new FinishedGoodLotMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotFinishedGoodLotPortion(8L, new FinishedGoodLot(9L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
               Quantities.getQuantity(100.0, SupportedUnits.EACH),
               LocalDateTime.of(1995, 1, 1, 1, 1)
           )
       );

       Page<FinishedGoodInventory> mPage = mock(Page.class);
       doReturn(finishedGoodInventoryList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(this.finishedGoodInventoryService).getAll(
           Set.of(1L),
           Set.of(2L),
           Set.of(99L),
           Set.of(199L),
           Set.of(299L),
           Set.of(399L),
           Set.of("a123"),
           Set.of(599L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<FinishedGoodInventoryDto> dto = this.finishedGoodInventoryController.getAll(
           Set.of(1L),
           Set.of(2L),
           Set.of(99L),
           Set.of(199L),
           Set.of(299L),
           Set.of(399L),
           Set.of("a123"),
           Set.of(599L),
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
       assertEquals(10L, finishedGoodInventory.getId());
       assertEquals(new SkuDto(5L), finishedGoodInventory.getSku());
       assertEquals(List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGoodInventory.getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGoodInventory.getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(8L, new FinishedGoodLotDto(9L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGoodInventory.getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodInventory.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
   }
}
