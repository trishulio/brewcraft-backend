package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddFinishedGoodLotDto;
import io.company.brewcraft.dto.AddFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.AddMixturePortionDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.dto.UpdateMixturePortionDto;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.service.FinishedGoodLotService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.uom.se.quantity.Quantities;

@SuppressWarnings("unchecked")
public class FinishedGoodLotControllerTest {

   private FinishedGoodLotController controller;

   private FinishedGoodLotService mService;
   private AttributeFilter filter;

   @BeforeEach
   public void init() {
       this.mService = mock(FinishedGoodLotService.class);
       this.filter = new AttributeFilter();

       this.controller = new FinishedGoodLotController(this.mService, this.filter);
   }

   @Test
   public void testGetFinishedGoods_CallsServicesWithArguments_AndReturnsPageDtoOfInvoiceDtosMappedFromFinishedGoodsPage() {
       final List<FinishedGoodLot> finishedGoods = List.of(
           new FinishedGoodLot(
               10L,
               new Sku(5L),
               List.of(new FinishedGoodLotMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotFinishedGoodLotPortion(8L, new FinishedGoodLot(9L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
               Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
               LocalDateTime.of(1995, 1, 1, 1, 1),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               1
           )
       );
       final Page<FinishedGoodLot> mPage = mock(Page.class);
       doReturn(finishedGoods.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(this.mService).getFinishedGoodLots(
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

       final PageDto<FinishedGoodLotDto> dto = this.controller.getFinishedGoods(
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
           Set.of() // empty means no fields should be filtered out, i.e. no field should be null
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());

       final FinishedGoodLotDto finishedGood = dto.getContent().get(0);
       assertEquals(10L, finishedGood.getId());
       assertEquals(new SkuDto(5L), finishedGood.getSku());
       assertEquals(List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGood.getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGood.getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(8L, new FinishedGoodLotDto(9L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGood.getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGood.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
       assertEquals(1, finishedGood.getVersion());
   }

   @Test
   public void testGetFinishedGood_ReturnsFinishedGoodDtoMappedFromServiceFinishedGood() {
       final FinishedGoodLot finishedGood = new FinishedGoodLot(
               10L,
               new Sku(5L),
               List.of(new FinishedGoodLotMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
               List.of(new FinishedGoodLotFinishedGoodLotPortion(8L, new FinishedGoodLot(8L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
               Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
               LocalDateTime.of(1995, 1, 1, 1, 1),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               1
       );
       doReturn(finishedGood).when(this.mService).get(10L);

       final FinishedGoodLotDto finishedGoodDto = this.controller.getFinishedGood(10L, Set.of());
       assertEquals(10L, finishedGoodDto.getId());
       assertEquals(new SkuDto(5L), finishedGoodDto.getSku());
       assertEquals(List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGoodDto.getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGoodDto.getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(8L, new FinishedGoodLotDto(8L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGoodDto.getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGoodDto.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodDto.getPackagedOn());
       assertEquals(1, finishedGoodDto.getVersion());
   }

   @Test
   public void testGetFinishedGood_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       doReturn(null).when(this.mService).get(1L);
       assertThrows(EntityNotFoundException.class, () -> this.controller.getFinishedGood(1L, Set.of()), "Finished Good not found with id: 1");
   }

   @Test
   public void testDeleteFinishedGoods_ReturnsDeleteCountFromService() {
       doReturn(99).when(this.mService).delete(Set.of(1L, 11L, 111L));
       int count = this.controller.deleteFinishedGoods(Set.of(1L, 11L, 111L));
       assertEquals(99, count);

       doReturn(9999).when(this.mService).delete(Set.of(1L, 11L, 111L));
       count = this.controller.deleteFinishedGoods(Set.of(1L, 11L, 111L));
       assertEquals(9999, count);
   }

   @Test
   public void testAddFinishedGood_ReturnsFinishedGoodDtoAfterAddingToService() {
       doAnswer(i -> i.getArgument(0)).when(this.mService).add(anyList());

       final AddFinishedGoodLotDto payload = new AddFinishedGoodLotDto(
               5L,
               List.of(new AddMixturePortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(4)))),
               List.of(new AddMaterialPortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1))),
               List.of(new AddFinishedGoodLotPortionDto(10L, new QuantityDto("each", BigDecimal.valueOf(100)))),
               new QuantityDto("each", BigDecimal.valueOf(100)),
               LocalDateTime.of(1995, 1, 1, 1, 1)
       );

       final List<FinishedGoodLotDto> finishedGoods = this.controller.addFinishedGood(List.of(payload));
       assertEquals(1, finishedGoods.size());
       assertEquals(null, finishedGoods.get(0).getId());
       assertEquals(new SkuDto(5L), finishedGoods.get(0).getSku());
       assertEquals(List.of(new MixturePortionDto(null, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), null)), finishedGoods.get(0).getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(null, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), null)), finishedGoods.get(0).getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(null, new FinishedGoodLotDto(10L), new QuantityDto("each", BigDecimal.valueOf(100)), null)), finishedGoods.get(0).getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGoods.get(0).getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoods.get(0).getPackagedOn());
       assertEquals(null, finishedGoods.get(0).getVersion());
   }

   @Test
   public void testUpdateFinishedGood_ReturnsFinishedGoodDtoAfterUpdatingItToService() {
       doAnswer(i -> i.getArgument(0)).when(this.mService).put(anyList());

       final UpdateFinishedGoodLotDto payload = new UpdateFinishedGoodLotDto(
               null,
               5L,
               List.of(new UpdateMixturePortionDto(1L, 8L, new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
               List.of(new UpdateMaterialPortionDto(2L, 8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
               List.of(new UpdateFinishedGoodLotPortionDto(3L, 10L, new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
               new QuantityDto("each", BigDecimal.valueOf(100)),
               LocalDateTime.of(1995, 1, 1, 1, 1),
               1
       );

       final FinishedGoodLotDto finishedGood = this.controller.updateFinishedGood(1L, payload);

       assertEquals(1L, finishedGood.getId());
       assertEquals(new SkuDto(5L), finishedGood.getSku());
       assertEquals(List.of(new MixturePortionDto(1L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGood.getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(2L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGood.getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(3L, new FinishedGoodLotDto(10L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGood.getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGood.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
       assertEquals(1, finishedGood.getVersion());
   }

   @Test
   public void testUpdateFinishedGoods_ReturnsListOfFinishedGoodDtosAfterUpdatingItToService() {
       doAnswer(i -> i.getArgument(0)).when(this.mService).put(anyList());

       final List<UpdateFinishedGoodLotDto> payload = List.of(new UpdateFinishedGoodLotDto(
               1L,
               5L,
               List.of(new UpdateMixturePortionDto(1L, 8L, new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
               List.of(new UpdateMaterialPortionDto(2L, 8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
               List.of(new UpdateFinishedGoodLotPortionDto(3L, 10L, new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
               new QuantityDto("each", BigDecimal.valueOf(100)),
               LocalDateTime.of(1995, 1, 1, 1, 1),
               1
       ));

       final List<FinishedGoodLotDto> finishedGoods = this.controller.updateFinishedGoods(payload);

       assertEquals(1, finishedGoods.size());
       assertEquals(1L, finishedGoods.get(0).getId());
       assertEquals(new SkuDto(5L), finishedGoods.get(0).getSku());
       assertEquals(List.of(new MixturePortionDto(1L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGoods.get(0).getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(2L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGoods.get(0).getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(3L, new FinishedGoodLotDto(10L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGoods.get(0).getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGoods.get(0).getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoods.get(0).getPackagedOn());
       assertEquals(1, finishedGoods.get(0).getVersion());
   }

   @Test
   public void testPatchFinishedGood_ReturnsFinishedGoodDtoAfterPatchingItToService() {
       doAnswer(i -> i.getArgument(0)).when(this.mService).patch(anyList());

       final UpdateFinishedGoodLotDto payload = new UpdateFinishedGoodLotDto(
               null,
               5L,
               List.of(new UpdateMixturePortionDto(1L, 8L, new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
               List.of(new UpdateMaterialPortionDto(2L, 8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
               List.of(new UpdateFinishedGoodLotPortionDto(3L, 10L, new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
               new QuantityDto("each", BigDecimal.valueOf(100)),
               LocalDateTime.of(1995, 1, 1, 1, 1),
               1
       );

       final FinishedGoodLotDto finishedGood = this.controller.patchFinishedGood(1L, payload);

       assertEquals(1L, finishedGood.getId());
       assertEquals(new SkuDto(5L), finishedGood.getSku());
       assertEquals(List.of(new MixturePortionDto(1L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)), finishedGood.getMixturePortions());
       assertEquals(List.of(new MaterialPortionDto(2L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)), finishedGood.getMaterialPortions());
       assertEquals(List.of(new FinishedGoodLotPortionDto(3L, new FinishedGoodLotDto(10L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)), finishedGood.getFinishedGoodLotPortions());
       assertEquals(new QuantityDto("each", BigDecimal.valueOf(100)), finishedGood.getQuantity());
       assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
       assertEquals(1, finishedGood.getVersion());
   }
}
