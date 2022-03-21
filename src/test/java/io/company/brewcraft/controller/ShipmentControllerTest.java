
package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddShipmentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.service.impl.ShipmentService;

public class ShipmentControllerTest {

   private ShipmentController controller;

   private CrudControllerService<
               Long,
               Shipment,
               BaseShipment<? extends BaseMaterialLot<?>>,
               UpdateShipment<? extends UpdateMaterialLot<?>>,
               ShipmentDto,
               AddShipmentDto,
               UpdateShipmentDto
           > mCrudController;

   private ShipmentService mService;

   @BeforeEach
   public void init() {
       this.mCrudController = mock(CrudControllerService.class);
       this.mService = mock(ShipmentService.class);
       this.controller = new ShipmentController(mCrudController, mService);
   }

   @Test
   public void testGetAllShipment_ReturnsDtosFromController() {
       doReturn(new PageImpl<>(List.of(new Shipment(1L)))).when(mService).getShipments(
           Set.of(1L), // ids,
           Set.of(2L), // excludeIds,
           Set.of("SHIPMENT_NUMBER"), // shipmentNumbers,
           Set.of("S_DESCRIPTION"), // descriptions,
           Set.of(3L), // statusIds,
           LocalDateTime.of(2000, 1, 1, 0, 1), // deliveryDueDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 2), // deliveryDueDateTo,
           LocalDateTime.of(2000, 1, 1, 0, 3), // deliveredDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 4), // deliveredDateTo,
           // invoice filters
           Set.of(10L), // invoiceIds,
           Set.of(20L), // invoiceExcludeIds,
           Set.of("INVOICE_NUMBER"), // invoiceNumbers,
           Set.of("I_DESCRIPTION"), // invoiceDescriptions,
           Set.of("II_DESCRIPTION"), // lotDescriptions,
           LocalDateTime.of(2000, 1, 1, 0, 5), // generatedOnFrom,
           LocalDateTime.of(2000, 1, 1, 0, 6), // generatedOnTo,
           LocalDateTime.of(2000, 1, 1, 0, 7), // receivedOnFrom,
           LocalDateTime.of(2000, 1, 1, 0, 8), // receivedOnTo,
           LocalDateTime.of(2000, 1, 1, 0, 9), // paymentDueDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 10), // paymentDueDateTo,
           Set.of(100L), // purchaseOrderIds,
           Set.of(1000L), // materialIds,
           new BigDecimal("1"), // amtFrom,
           new BigDecimal("2"), // amtTo,
           new BigDecimal("3"), // freightAmtFrom,
           new BigDecimal("4"), // freightAmtTo,
           Set.of(30L), // invoiceStatusIds,
           Set.of(200L), // supplierIds,
           // misc
           new TreeSet<>(Set.of("col_1", "col_2")), // sort,
           true, // orderAscending,
           1, // page,
           10 //size
       );
       doReturn(new PageDto<>(List.of(new ShipmentDto(1L)), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new Shipment(1L))), Set.of(""));

       PageDto<ShipmentDto> page = this.controller.getShipments(
           Set.of(1L), // ids,
           Set.of(2L), // excludeIds,
           Set.of("SHIPMENT_NUMBER"), // shipmentNumbers,
           Set.of("S_DESCRIPTION"), // descriptions,
           Set.of(3L), // statusIds,
           LocalDateTime.of(2000, 1, 1, 0, 1), // deliveryDueDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 2), // deliveryDueDateTo,
           LocalDateTime.of(2000, 1, 1, 0, 3), // deliveredDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 4), // deliveredDateTo,
           // invoice filters
           Set.of(10L), // invoiceIds,
           Set.of(20L), // invoiceExcludeIds,
           Set.of("INVOICE_NUMBER"), // invoiceNumbers,
           Set.of("I_DESCRIPTION"), // invoiceDescriptions,
           Set.of("II_DESCRIPTION"), // lotDescriptions,
           LocalDateTime.of(2000, 1, 1, 0, 5), // generatedOnFrom,
           LocalDateTime.of(2000, 1, 1, 0, 6), // generatedOnTo,
           LocalDateTime.of(2000, 1, 1, 0, 7), // receivedOnFrom,
           LocalDateTime.of(2000, 1, 1, 0, 8), // receivedOnTo,
           LocalDateTime.of(2000, 1, 1, 0, 9), // paymentDueDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 10), // paymentDueDateTo,
           Set.of(100L), // purchaseOrderIds,
           Set.of(1000L), // materialIds,
           new BigDecimal("1"), // amtFrom,
           new BigDecimal("2"), // amtTo,
           new BigDecimal("3"), // freightAmtFrom,
           new BigDecimal("4"), // freightAmtTo,
           Set.of(30L), // invoiceStatusIds,
           Set.of(200L), // supplierIds,
           // misc
           new TreeSet<>(Set.of("col_1", "col_2")), // sort,
           true, // orderAscending,
           1, // page,
           10, //size
           Set.of("")
       );

       PageDto<ShipmentDto> expected = new PageDto<>(List.of(new ShipmentDto(1L)), 1, 1);
       assertEquals(expected, page);
   }

   @Test
   public void testGetShipment_ReturnsDtoFromController() {
       doReturn(new ShipmentDto(1L)).when(mCrudController).get(1L, Set.of(""));

       ShipmentDto dto = this.controller.getShipment(1L, Set.of(""));

       ShipmentDto expected = new ShipmentDto(1L);
       assertEquals(expected, dto);
   }

   @Test
   public void testDeleteShipments_ReturnsDeleteCountFromController() {
       doReturn(1L).when(mCrudController).delete(Set.of(1L));

       assertEquals(1L, this.controller.deleteShipments(Set.of(1L)));
   }

   @Test
   public void testAddShipments_AddsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new ShipmentDto(1L))).when(mCrudController).add(List.of(new AddShipmentDto()));

       List<ShipmentDto> dtos = this.controller.addShipment(List.of(new AddShipmentDto()));

       assertEquals(List.of(new ShipmentDto(1L)), dtos);
   }

   @Test
   public void testUpdateShipments_PutsToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new ShipmentDto(1L))).when(mCrudController).put(List.of(new UpdateShipmentDto(1L)));

       List<ShipmentDto> dtos = this.controller.putShipment(List.of(new UpdateShipmentDto(1L)));

       assertEquals(List.of(new ShipmentDto(1L)), dtos);
   }

   @Test
   public void testPatchShipments_PatchToControllerAndReturnsListOfDtos() {
       doReturn(List.of(new ShipmentDto(1L))).when(mCrudController).patch(List.of(new UpdateShipmentDto(1L)));

       List<ShipmentDto> dtos = this.controller.patchShipment(List.of(new UpdateShipmentDto(1L)));

       assertEquals(List.of(new ShipmentDto(1L)), dtos);
   }
}
