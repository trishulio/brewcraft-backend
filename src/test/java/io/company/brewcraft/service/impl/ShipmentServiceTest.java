package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class ShipmentServiceTest {
   private ShipmentService service;

   private MaterialLotService mLotService;
   private UpdateService<Long, Shipment, BaseShipment<? extends BaseMaterialLot<?>>, UpdateShipment<? extends UpdateMaterialLot<?>>> mUpdateService;
   private RepoService<Long, Shipment, ShipmentAccessor> mRepoService;

   @BeforeEach
   public void init() {
       this.mLotService = mock(MaterialLotService.class);
       this.mUpdateService = mock(UpdateService.class);
       this.mRepoService = mock(RepoService.class);

       doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

       this.service = new ShipmentService(this.mUpdateService, this.mLotService, this.mRepoService);
   }

   @Test
   public void testGetShipments_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       final ArgumentCaptor<Specification<Shipment>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<Shipment> mPage = new PageImpl<>(List.of(new Shipment(1L)));
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("col_1", "col_2"))), eq(true), eq(1), eq(10));

       final Page<Shipment> page = this.service.getShipments(
           // shipment filters
           Set.of(1L), // ids,
           Set.of(2L), // excludeIds,
           Set.of("SHIPMENT_NUMBER"), // shipmentNumbers,
           Set.of("S_DESCRIPTION"), // descriptions,
           Set.of(3L), // shipmentStatusIds,
           LocalDateTime.of(2000, 1, 1, 0, 1), // deliveryDueDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 2), // deliveryDueDateTo,
           LocalDateTime.of(2000, 1, 1, 0, 3), // deliveredDateFrom,
           LocalDateTime.of(2000, 1, 1, 0, 4), // deliveredDateTo,
           // invoice filters
           Set.of(10L), // invoiceIds,
           Set.of(20L), // invoiceExcludeIds,
           Set.of("INVOICE_NUMBER"), // invoiceNumbers,
           Set.of("I_DESCRIPTION"), // invoiceDescriptions,
           Set.of("II_DESCRIPTION"), // invoiceItemDescriptions,
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

       final Page<Shipment> expected = new PageImpl<>(List.of(new Shipment(1L)));
       assertEquals(expected, page);

       // TODO: Pending testing for the specification
       captor.getValue();
   }

   @Test
   public void testGetShipment_ReturnsShipmentPojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new Shipment(1L)).when(this.mRepoService).get(1L);

       final Shipment invoice = this.service.get(1L);

       assertEquals(new Shipment(1L), invoice);
   }

   @Test
   public void testGetByIds_CallsRepoService() {
       ArgumentCaptor<List<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(List.class);

       doReturn(List.of(new Shipment(1L))).when(mRepoService).getByIds(captor.capture());

       assertEquals(List.of(new Shipment(1L)), service.getByIds(List.of(() -> 1L)));
       assertEquals(1L, captor.getValue().get(0).getId());
   }

   @Test
   public void testGetByAccessorIds_CallsRepoService() {
       ArgumentCaptor<Function<ShipmentAccessor, Shipment>> captor = ArgumentCaptor.forClass(Function.class);

       List<? extends ShipmentAccessor> accessors = List.of(new ShipmentAccessor() {
            @Override
            public void setShipment(Shipment Shipment) {}
            @Override
            public Shipment getShipment() {
                return new Shipment(1L);
            }
        });

       doReturn(List.of(new Shipment(1L))).when(mRepoService).getByAccessorIds(eq(accessors), captor.capture());

       assertEquals(List.of(new Shipment(1L)), service.getByAccessorIds(accessors));
       assertEquals(new Shipment(1L), captor.getValue().apply(accessors.get(0)));
   }

   @Test
   public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
       doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

       assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
   }

   @Test
   public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
       doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

       assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
   }

   @Test
   public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
       doReturn(true).when(this.mRepoService).exists(1L);

       assertTrue(this.service.exist(1L));
   }

   @Test
   public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
       doReturn(true).when(this.mRepoService).exists(1L);

       assertTrue(this.service.exist(1L));
   }

   @Test
   public void testDelete_CallsRepoServiceDeleteBulk_WhenShipmentExists() {
       doReturn(123L).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

       final long count = this.service.delete(Set.of(1L, 2L, 3L));
       assertEquals(123L, count);
   }

   @Test
   public void testDelete_CallsRepoServiceDelete_WhenShipmentExists() {
       this.service.delete(1L);
       verify(this.mRepoService).delete(1L);
   }

   @Test
   public void testAdd_AddsShipmentAndLotsAndSavesToRepo_WhenAdditionsAreNotNull() {
       doAnswer(inv -> inv.getArgument(0)).when(this.mLotService).getAddEntities(any());
       doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

       final BaseShipment<MaterialLot> invoice1 = new Shipment(1L);
       invoice1.setLots(List.of(new MaterialLot(10L)));
       final BaseShipment<MaterialLot> invoice2 = new Shipment();
       invoice2.setLots(List.of(new MaterialLot(20L)));

       final List<Shipment> added = this.service.add(List.of(invoice1, invoice2));

       final List<Shipment> expected = List.of(
           new Shipment(1L), new Shipment()
       );
       expected.get(0).setLots(List.of(new MaterialLot(10L)));
       expected.get(1).setLots(List.of(new MaterialLot(20L)));

       assertEquals(expected, added);
       verify(this.mRepoService, times(1)).saveAll(added);
   }

   @Test
   public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
       assertNull(this.service.add(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPut_UpdatesShipmentAndLotsAndSavesToRepo_WhenUpdatesAreNotNull() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mLotService).getPutEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

       final UpdateShipment<MaterialLot> invoice1 = new Shipment(1L);
       invoice1.setLots(List.of(new MaterialLot(10L)));
       final UpdateShipment<MaterialLot> invoice2 = new Shipment(2L);
       invoice2.setLots(List.of(new MaterialLot(20L)));

       doReturn(List.of(new Shipment(1L), new Shipment(2L))).when(this.mRepoService).getByIds(List.of(invoice1, invoice2));

       final List<Shipment> updated = this.service.put(List.of(invoice1, invoice2, new Shipment()));

       final List<Shipment> expected = List.of(
           new Shipment(1L), new Shipment(2L), new Shipment()
       );
       expected.get(0).setLots(List.of(new MaterialLot(10L)));
       expected.get(1).setLots(List.of(new MaterialLot(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
       assertNull(this.service.put(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_PatchesShipmentAndLotsAndSavesToRepo_WhenPatchesAreNotNull() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mLotService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final UpdateShipment<MaterialLot> invoice1 = new Shipment(1L);
       invoice1.setLots(List.of(new MaterialLot(10L)));
       final UpdateShipment<MaterialLot> invoice2 = new Shipment(2L);
       invoice2.setLots(List.of(new MaterialLot(20L)));

       doReturn(List.of(new Shipment(1L), new Shipment(2L))).when(this.mRepoService).getByIds(List.of(invoice1, invoice2));

       final List<Shipment> updated = this.service.patch(List.of(invoice1, invoice2));

       final List<Shipment> expected = List.of(
           new Shipment(1L), new Shipment(2L)
       );
       expected.get(0).setLots(List.of(new MaterialLot(10L)));
       expected.get(1).setLots(List.of(new MaterialLot(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
       assertNull(this.service.patch(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_ThrowsNotFoundException_WhenAllShipmentsDontExist() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mLotService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final List<UpdateShipment<? extends UpdateMaterialLot<?>>> updates = List.of(
           new Shipment(1L), new Shipment(2L), new Shipment(3L), new Shipment(4L)
       );
       doReturn(List.of(new Shipment(1L), new Shipment(2L))).when(this.mRepoService).getByIds(updates);

       assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find invoices with Ids: [3, 4]");
   }
}
