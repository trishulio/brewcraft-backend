package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
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
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

       final Page<Shipment> page = this.service.getShipments(
           Set.of(1L), //ids
           Set.of(2L), //excludeIds
           Set.of("SHIPMENT_1"), //shipmentNumbers
           Set.of("DESC"), //descriptions,
           Set.of(4L), //statusIds,
           LocalDateTime.of(2000, 1, 1, 0, 0), //deliveryDueDateFrom,
           LocalDateTime.of(2001, 1, 1, 0, 0), //deliveryDueDateTo,
           LocalDateTime.of(2002, 1, 1, 0, 0), //deliveredDateFrom,
           LocalDateTime.of(2003, 1, 1, 0, 0), //deliveredDateTo,
           new TreeSet<>(List.of("id")), //sortBy,
           true, //ascending,
           10, //page,
           20 //size
       );

       final Page<Shipment> expected = new PageImpl<>(List.of(new Shipment(1L)));
       assertEquals(expected, page);

       captor.getValue();
   }

   @Test
   public void testGetShipment_ReturnsShipmentPojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new Shipment(1L)).when(this.mRepoService).get(1L);

       final Shipment invoice = this.service.get(1L);

       assertEquals(new Shipment(1L), invoice);
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
       doReturn(123).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

       final int count = this.service.delete(Set.of(1L, 2L, 3L));
       assertEquals(123, count);
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
