package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceServiceTest {
   private InvoiceService service;

   private InvoiceRepository mRepo;
   private InvoiceItemService mItemService;

   @BeforeEach
   public void init() {
       mRepo = mock(InvoiceRepository.class);
       doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mRepo).saveAndFlush(any(Invoice.class));
       doAnswer(inv -> {
           Long poId = inv.getArgument(0, Long.class);
           Invoice invoice = inv.getArgument(1, Invoice.class);           
           invoice.setPurchaseOrder(new PurchaseOrder(poId));
           return null;
       }).when(mRepo).refresh(anyLong(), any(Invoice.class));

       mItemService = mock(InvoiceItemService.class);

       this.service = spy(new InvoiceService(mRepo, mItemService));
   }
   
   @Test
   @Disabled(value = "TODO: Need to figure out a way to assert the spec behaviour based on the inputs")
   public void testGetInvoices_CallsRepositoryWithACustomSpec_AndReturnsPageOfEntities() {
       fail("TODO: Need to figure out a way to assert the spec behaviour based on the inputs");
   }

   @Test
   public void testGetInvoice_ReturnsInvoicePojo_WhenRepositoryReturnsOptionalWithEntity() {
       Optional<Invoice> mOptional = Optional.of(new Invoice(1L));
       doReturn(mOptional).when(mRepo).findById(1L);
       
       Invoice invoice = service.getInvoice(1L);
       
       assertEquals(new Invoice(1L), invoice);
   }
   
   @Test
   public void testGetInvoice_ReturnsNull_WhenRepositoryReturnsEmptyOptional() {
       Optional<Invoice> mOptional = Optional.empty();
       doReturn(mOptional).when(mRepo).findById(1L);
       
       Invoice invoice = service.getInvoice(1L);
       
       assertNull(invoice);
   }

   @Test
   public void testExists_ReturnsTrue_WhenRepoReturnsTrue() {
       doReturn(true).when(mRepo).existsById(12345L);

       assertTrue(service.exists(12345L));
   }

   @Test
   public void testExists_ReturnsFalse_WhenRepoReturnsFalse() {
       doReturn(false).when(mRepo).existsById(12345L);

       assertFalse(service.exists(12345L));
   }

   @Test
   public void testDelete_CallsRepoDeleteById_WhenInvoiceExists() {
       doReturn(true).when(mRepo).existsById(12345L);
       service.delete(12345L);

       verify(mRepo, times(1)).deleteById(12345L);
   }

   @Test
   public void testDelete_ThrowsEntityNotFoundException_WhenInvoiceDoesNotExist() {
       doReturn(false).when(mRepo).existsById(12345L);

       assertThrows(EntityNotFoundException.class, () -> service.delete(12345L));
   }

   @Test
   public void testPut_CreatesAndSaveNewEntityWithUpdates_WhenInvoiceWithGivenIdDoesNotExist() {
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(1L),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           List.of(itemUpdate),
           1
       );

       doReturn(List.of(itemUpdate)).when(mItemService).getPutItems(null, List.of(itemUpdate));

       Invoice invoice = service.put(3L, 1L, update);

       verify(mRepo, times(1)).saveAndFlush(any(Invoice.class));
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(1L), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(null, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       Iterator<InvoiceItem> it = invoice.getItems().iterator();
       InvoiceItem item = it.next();
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPut_UpdatesTheExistingEntityAndSavesIt_WhenEntityExist() {
       Invoice mExisting = new Invoice(1L);
       mExisting.setCreatedAt(LocalDateTime.of(2100, 1, 1, 12, 0));
       mExisting.setVersion(1);
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(1L),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           List.of(itemUpdate),
           1
       );

       doReturn(List.of(itemUpdate)).when(mItemService).getPutItems(null, List.of(itemUpdate));
       
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice invoice = service.put(3L, 1L, update);

       verify(mRepo, times(1)).refresh(eq(3L), any(Invoice.class));
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(1L), invoice.getFreight());
       assertEquals(LocalDateTime.of(2100, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       Iterator<InvoiceItem> it = invoice.getItems().iterator();
       InvoiceItem item = it.next();
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPut_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
       Invoice existing = new Invoice(1L);
       existing.setVersion(1);
       doReturn(Optional.of(existing)).when(mRepo).findById(1L);

       Invoice update = new Invoice(1L);
       existing.setVersion(2);

       assertThrows(OptimisticLockException.class, () -> service.put(2L, 1L, update));
   }

   @Test
   public void testPatch_ApplyUpdatesOnExistingEntityAndSavesIt_WhenInvoiceExists() {
       Invoice mExisting = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(1L),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           null,
           1  
       );

       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(1L);
       update.setDescription("New description value");
       update.setCreatedAt(LocalDateTime.of(9999, 12, 31, 12, 0));
       update.setLastUpdated(LocalDateTime.of(9999, 12, 31, 12, 0));
       update.setItems(List.of(itemUpdate));
       update.setVersion(1);

       doReturn(List.of(itemUpdate)).when(mItemService).getPatchItems(Collections.emptyList(), List.of(itemUpdate));

       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice invoice = service.patch(3L, 1L, update);

       verify(mRepo, times(1)).refresh(eq(3L), any(Invoice.class));
       verify(mRepo, times(1)).saveAndFlush(invoice);
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("New description value", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(1L), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       Iterator<InvoiceItem> it = invoice.getItems().iterator();
       InvoiceItem item = it.next();
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPatch_SavesWithExistingInvoiceId_WhenInvoiceIdArgIsNull() {
       doNothing().when(mRepo).refresh(anyLong(), any(Invoice.class));

       Invoice existing = new Invoice(1L);

       existing.setPurchaseOrder(new PurchaseOrder(2L));
       doReturn(Optional.of(existing)).when(mRepo).findById(1L);

       Invoice update = new Invoice(1L);
       Invoice invoice = service.patch(null, 1L, update);        
       verify(mRepo, times(1)).refresh(2L, invoice);
   }

   @Test
   public void testPatch_SavesWithNullPurchaseOrder_WhenPurchaseOrderIdIsNullAndExistingPurchaseOrderIsNull() {
       doNothing().when(mRepo).refresh(isNull(), any(Invoice.class));

       Invoice existing = new Invoice(1L);
       doReturn(Optional.of(existing)).when(mRepo).findById(1L);

       Invoice update = new Invoice(1L);
       Invoice invoice = service.patch(null, 1L, update);

       verify(mRepo, times(1)).refresh(null, invoice);
       verify(mRepo, times(1)).saveAndFlush(invoice);
   }

   @Test
   public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
       Invoice existing = new Invoice(1L);
       existing.setVersion(1);
       doReturn(Optional.of(existing)).when(mRepo).findById(1L);

       Invoice update = new Invoice(1L);
       existing.setVersion(2);

       assertThrows(OptimisticLockException.class, () -> service.patch(2L, 1L, update));
   }

   @Test
   public void testAdd_SavesTheNewEntity() {
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice addition = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           List.of(itemUpdate),
           1
       );

       doReturn(List.of(itemUpdate)).when(mItemService).getAddItems(eq(List.of(itemUpdate)));

       Invoice invoice = service.add(3L, addition);

       verify(mRepo, times(1)).refresh(eq(3L), any(Invoice.class));
       verify(mRepo, times(1)).saveAndFlush(invoice);
       assertEquals(null, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(null, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       Iterator<InvoiceItem> it = invoice.getItems().iterator();
       InvoiceItem item = it.next();
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }
}
