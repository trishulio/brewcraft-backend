package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderService;

public class PurchaseOrderControllerTest {
   private PurchaseOrderController controller;

   private CrudControllerService<
               Long,
               PurchaseOrder,
               BasePurchaseOrder,
               UpdatePurchaseOrder,
               PurchaseOrderDto,
               AddPurchaseOrderDto,
               UpdatePurchaseOrderDto
           > mCrudController;

   private PurchaseOrderService mService;

   @BeforeEach
   public void init() {
       this.mCrudController = mock(CrudControllerService.class);
       this.mService = mock(PurchaseOrderService.class);
       this.controller = new PurchaseOrderController(mCrudController, mService);
   }

   @Test
   public void testGetAllPurchaseOrder_ReturnsDtosFromController() {
       doReturn(new PageImpl<>(List.of(new PurchaseOrder(1L)))).when(mService).getPurchaseOrders(
           Set.of(1L), //ids
           Set.of(2L), //excludeIds
           Set.of("ORDER_1"), //orderNumbers
           Set.of(3L), //supplierIds
           new TreeSet<>(List.of("id")), //sort
           true, //orderAscending
           10, //page
           20 //size
       );
       doReturn(new PageDto<>(List.of(new PurchaseOrderDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new PurchaseOrder(1L))), Set.of(""));

       PageDto<PurchaseOrderDto> page = this.controller.getAllPurchaseOrders(
           Set.of(1L), //ids
           Set.of(2L), //excludeIds
           Set.of("ORDER_1"), //orderNumbers
           Set.of(3L), //supplierIds
           new TreeSet<>(List.of("id")), //sort
           true, //orderAscending
           10, //page
           20, //size
           Set.of("")
       );

       PageDto<PurchaseOrderDto> expected = new PageDto<>(List.of(new PurchaseOrderDto(1L)), 1, 1);
       assertEquals(expected, page);
   }

   @Test
   public void testGetPurchaseOrder_ReturnsDtoFromController() {
       doReturn(new PurchaseOrderDto(1L)).when(mCrudController).get(1L, Set.of(""));

       PurchaseOrderDto dto = this.controller.getPurchaseOrder(1L, Set.of(""));

       PurchaseOrderDto expected = new PurchaseOrderDto(1L);
       assertEquals(expected, dto);
   }

   @Test
   public void testDeletePurchaseOrders_ReturnsDeleteCountFromController() {
       doReturn(1L).when(mCrudController).delete(Set.of(1L));

       assertEquals(1L, this.controller.deletePurchaseOrder(Set.of(1L)));
   }

   @Test
   public void testAddPurchaseOrders_AddsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new PurchaseOrderDto(1L))).when(mCrudController).add(List.of(new AddPurchaseOrderDto()));

       List<PurchaseOrderDto> dtos = this.controller.postPurchaseOrder(List.of(new AddPurchaseOrderDto()));

       assertEquals(List.of(new PurchaseOrderDto(1L)), dtos);
   }

   @Test
   public void testUpdatePurchaseOrders_PutsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new PurchaseOrderDto(1L))).when(mCrudController).put(List.of(new UpdatePurchaseOrderDto(1L)));

       List<PurchaseOrderDto> dtos = this.controller.putPurchaseOrder(List.of(new UpdatePurchaseOrderDto(1L)));

       assertEquals(List.of(new PurchaseOrderDto(1L)), dtos);
   }

   @Test
   public void testPatchPurchaseOrders_PatchToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new PurchaseOrderDto(1L))).when(mCrudController).patch(List.of(new UpdatePurchaseOrderDto(1L)));

       List<PurchaseOrderDto> dtos = this.controller.patchPurchaseOrder(List.of(new UpdatePurchaseOrderDto(1L)));

       assertEquals(List.of(new PurchaseOrderDto(1L)), dtos);
   }
}
