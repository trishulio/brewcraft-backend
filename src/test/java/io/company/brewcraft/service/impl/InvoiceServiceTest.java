package io.company.brewcraft.service.impl;

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
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class InvoiceServiceTest {
   private InvoiceService service;

   private InvoiceItemService mItemService;
   private UpdateService<Long, Invoice, BaseInvoice<? extends BaseInvoiceItem<?>>, UpdateInvoice<? extends UpdateInvoiceItem<?>>> mUpdateService;
   private RepoService<Long, Invoice, InvoiceAccessor> mRepoService;

   @BeforeEach
   public void init() {
       this.mItemService = mock(InvoiceItemService.class);
       this.mUpdateService = mock(UpdateService.class);
       this.mRepoService = mock(RepoService.class);

       doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

       this.service = new InvoiceService(this.mUpdateService, this.mItemService, this.mRepoService);
   }

   @Test
   public void testGetInvoices_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       final ArgumentCaptor<Specification<Invoice>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<Invoice> mPage = new PageImpl<>(List.of(new Invoice(1L)));
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

       final Page<Invoice> page = this.service.getInvoices(
           Set.of(1L), //ids
           Set.of(2L), //excludeIds
           Set.of("INVOICE_1"), //invoiceNumbers
           Set.of("DESC"), //invoiceDescriptions,
           Set.of("ITEM_DESC"), //invoiceItemDescriptions
           LocalDateTime.of(2000, 1, 1, 0, 0), //generatedOnFrom,
           LocalDateTime.of(2001, 1, 1, 0, 0), //generatedOnTo,
           LocalDateTime.of(2002, 1, 1, 0, 0), //receivedOnFrom,
           LocalDateTime.of(2003, 1, 1, 0, 0), //receivedOnTo,
           LocalDateTime.of(2004, 1, 1, 0, 0), //paymentDueDateFrom,
           LocalDateTime.of(2005, 1, 1, 0, 0), //paymentDueDateTo,
           Set.of(3L), //purchaseOrderIds,
           new BigDecimal("10"), //freightAmtFrom,
           new BigDecimal("20"), //freightAmtTo,
           Set.of(4L), //statusIds,
           Set.of(5L), //supplierIds,
           new TreeSet<>(List.of("id")), //sortBy,
           true, //ascending,
           10, //page,
           20 //size
       );

       final Page<Invoice> expected = new PageImpl<>(List.of(new Invoice(1L)));
       assertEquals(expected, page);

       captor.getValue();
   }

   @Test
   public void testGetInvoice_ReturnsInvoicePojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new Invoice(1L)).when(this.mRepoService).get(1L);

       final Invoice invoice = this.service.get(1L);

       assertEquals(new Invoice(1L), invoice);
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
   public void testDelete_CallsRepoServiceDeleteBulk_WhenInvoiceExists() {
       doReturn(123).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

       final int count = this.service.delete(Set.of(1L, 2L, 3L));
       assertEquals(123, count);
   }

   @Test
   public void testDelete_CallsRepoServiceDelete_WhenInvoiceExists() {
       this.service.delete(1L);
       verify(this.mRepoService).delete(1L);
   }

   @Test
   public void testAdd_AddsInvoiceAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
       doAnswer(inv -> inv.getArgument(0)).when(this.mItemService).getAddEntities(any());
       doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

       final BaseInvoice<InvoiceItem> invoice1 = new Invoice(1L);
       invoice1.setItems(List.of(new InvoiceItem(10L)));
       final BaseInvoice<InvoiceItem> invoice2 = new Invoice();
       invoice2.setItems(List.of(new InvoiceItem(20L)));

       final List<Invoice> added = this.service.add(List.of(invoice1, invoice2));

       final List<Invoice> expected = List.of(
           new Invoice(1L), new Invoice()
       );
       expected.get(0).setItems(List.of(new InvoiceItem(10L)));
       expected.get(1).setItems(List.of(new InvoiceItem(20L)));

       assertEquals(expected, added);
       verify(this.mRepoService, times(1)).saveAll(added);
   }

   @Test
   public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
       assertNull(this.service.add(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPut_UpdatesInvoiceAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mItemService).getPutEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

       final UpdateInvoice<InvoiceItem> invoice1 = new Invoice(1L);
       invoice1.setItems(List.of(new InvoiceItem(10L)));
       final UpdateInvoice<InvoiceItem> invoice2 = new Invoice(2L);
       invoice2.setItems(List.of(new InvoiceItem(20L)));

       doReturn(List.of(new Invoice(1L), new Invoice(2L))).when(this.mRepoService).getByIds(List.of(invoice1, invoice2));

       final List<Invoice> updated = this.service.put(List.of(invoice1, invoice2, new Invoice()));

       final List<Invoice> expected = List.of(
           new Invoice(1L), new Invoice(2L), new Invoice()
       );
       expected.get(0).setItems(List.of(new InvoiceItem(10L)));
       expected.get(1).setItems(List.of(new InvoiceItem(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
       assertNull(this.service.put(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_PatchesInvoiceAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mItemService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final UpdateInvoice<InvoiceItem> invoice1 = new Invoice(1L);
       invoice1.setItems(List.of(new InvoiceItem(10L)));
       final UpdateInvoice<InvoiceItem> invoice2 = new Invoice(2L);
       invoice2.setItems(List.of(new InvoiceItem(20L)));

       doReturn(List.of(new Invoice(1L), new Invoice(2L))).when(this.mRepoService).getByIds(List.of(invoice1, invoice2));

       final List<Invoice> updated = this.service.patch(List.of(invoice1, invoice2));

       final List<Invoice> expected = List.of(
           new Invoice(1L), new Invoice(2L)
       );
       expected.get(0).setItems(List.of(new InvoiceItem(10L)));
       expected.get(1).setItems(List.of(new InvoiceItem(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
       assertNull(this.service.patch(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_ThrowsNotFoundException_WhenAllInvoicesDontExist() {
       doAnswer(inv -> inv.getArgument(1)).when(this.mItemService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final List<UpdateInvoice<? extends UpdateInvoiceItem<?>>> updates = List.of(
           new Invoice(1L), new Invoice(2L), new Invoice(3L), new Invoice(4L)
       );
       doReturn(List.of(new Invoice(1L), new Invoice(2L))).when(this.mRepoService).getByIds(updates);

       assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find invoices with Ids: [3, 4]");
   }
}
