package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.InvoiceStatusService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.validator.ValidationException;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

@SuppressWarnings("unchecked")
public class InvoiceServiceTest {
   private InvoiceService service;

   private InvoiceRepository mRepo;
   private InvoiceStatusService mStatusService;
   private PurchaseOrderService mPoService;

   @BeforeEach
   public void init() {
       mRepo = mock(InvoiceRepository.class);
       mStatusService = mock(InvoiceStatusService.class);
       mPoService = mock(PurchaseOrderService.class);
       this.service = new InvoiceService(mRepo, mStatusService, mPoService);
   }
   
   public void testGetInvoices_CallsRepositoryWithACustomSpec_AndReturnsPageOfEntities() {
       fail();
   }
   @Test
   public void testGetInvoice_ReturnsInvoicePojo_WhenRepositoryReturnsOptionalWithEntity() {
       Optional<InvoiceEntity> mOptional = Optional.of(new InvoiceEntity(1L));
       doReturn(mOptional).when(mRepo).findById(1L);
       
       Invoice invoice = service.getInvoice(1L);
       
       assertEquals(new Invoice(1L), invoice);
   }
   
   @Test
   public void testGetInvoice_ReturnsNull_WhenRepositoryReturnsEmptyOptional() {
       Optional<InvoiceEntity> mOptional = Optional.empty();
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
   public void testPut_SavesNewEntityWithAtGivenId() {
       doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));

       PurchaseOrder mPo = new PurchaseOrder(3L);
       doReturn(mPo).when(mPoService).getPurchaseOrder(3L);
       
       InvoiceStatus mStatus = new InvoiceStatus(2L, "FINAL");
       doReturn(mStatus).when(mStatusService).getInvoiceStatus("FINAL");
       
       Invoice update = new Invoice(
           null,
           "ABCDE-12345",
           "desc1",
           null,
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.parse("CAD 4.00")),
           null,
           null,
           new InvoiceStatus(null, "FINAL"),
           List.of(new InvoiceItem(3L)),
           1
       );
       Invoice invoice = service.put(3L, 1L, update);

       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(Money.parse("CAD 4.00")), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());
       InvoiceItem item = invoice.getItems().get(0);
       assertEquals(3L, item.getId());
       assertEquals(null, item.getVersion());
   }

   @Test
   public void testPut_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
       doReturn(null).when(mPoService).getPurchaseOrder(3L);

       Invoice update = new Invoice();
       assertThrows(EntityNotFoundException.class, () -> service.put(3L, 1L, update), "PurchaseOrder not found with id: 3");
   }

   @Test
   public void testPut_ThrowsValidationException_WhenNonExistentInvoiceStatusNameIsUsed() {
       doReturn(new PurchaseOrder(3L)).when(mPoService).getPurchaseOrder(3L);
       doReturn(null).when(mStatusService).getInvoiceStatus("FINAL");

       Invoice update = new Invoice();
       update.setStatus(new InvoiceStatus(null, "FINAL"));
       assertThrows(ValidationException.class, () -> service.put(3L, 1L, update), "Invalid Status Name: FINAL");
   }

   @Test
   public void testPatch_MergesNewAndExistingEntity_AndReplacesExistingWithIt() {
       doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));

       doReturn(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME)).when(mStatusService).getInvoiceStatus(InvoiceStatusEntity.DEFAULT_STATUS_NAME);
       doReturn(new PurchaseOrder(3L)).when(mPoService).getPurchaseOrder(3L);
       
       InvoiceEntity mExisting = new InvoiceEntity();
       mExisting.setDescription("existing description");
       mExisting.setItems(List.of(new InvoiceItemEntity(2L)));
       mExisting.setInvoiceNumber("ABCDE-12345");
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice patch = new Invoice();
       patch.setDescription("New description");
       patch.setGeneratedOn(LocalDateTime.of(2020, 1, 1, 12, 0));

       Invoice invoice = service.patch(3L, 1L, patch);

       assertEquals(1L, invoice.getId());
       assertEquals("New description", invoice.getDescription());
       assertEquals(new InvoiceItem(2L), invoice.getItems().get(0));
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME), invoice.getStatus());
       assertNull(invoice.getAmount());
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getFreight());
       assertNull(invoice.getPaymentDueDate());
       assertNull(invoice.getReceivedOn());
       assertNull(invoice.getTax());
   }

   @Test
   public void testPatch_ThrowsEntityNotFoundException_WhenNoExistingEntityExists() {
       doReturn(Optional.empty()).when(mRepo).findById(1L);

       Invoice patch = new Invoice();
       assertThrows(EntityNotFoundException.class, () -> service.patch(3L, 1L, patch), "InvoiceEntity not found with id: 1");
   }

   @Test
   public void testPatch_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
       doReturn(null).when(mPoService).getPurchaseOrder(3L);

       Invoice update = new Invoice();
       assertThrows(EntityNotFoundException.class, () -> service.patch(3L, 1L, update), "PurchaseOrder not found with id: 3");
   }

   @Test
   public void testPatch_ThrowsValidationException_WhenNonExistentInvoiceStatusNameIsUsed() {
       doReturn(Optional.of(new InvoiceEntity(1L))).when(mRepo).findById(1L);

       doReturn(new PurchaseOrder(3L)).when(mPoService).getPurchaseOrder(3L);
       doReturn(null).when(mStatusService).getInvoiceStatus("FINAL");

       Invoice update = new Invoice();
       update.setStatus(new InvoiceStatus(null, "FINAL"));
       assertThrows(ValidationException.class, () -> service.patch(3L, 1L, update), "Invalid Status Name: FINAL");
   }

   @Test
   public void testAdd_AssignsAPurchaseOrderAndDefaultStatus_AndSavesTheEntity() {
       doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));

       doReturn(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME)).when(mStatusService).getInvoiceStatus(InvoiceStatusEntity.DEFAULT_STATUS_NAME);
       doReturn(new PurchaseOrder(3L)).when(mPoService).getPurchaseOrder(3L);
       
       Invoice addition = new Invoice();
       addition.setDescription("description");
       addition.setGeneratedOn(LocalDateTime.of(2020, 1, 1, 12, 0));
       InvoiceItem item = new InvoiceItem(5L);
       item.setPrice(Money.parse("CAD 10"));
       item.setQuantity(Quantities.getQuantity(new BigDecimal("5"), Units.KILOGRAM));
       addition.setItems(List.of(item));

       Invoice invoice = service.add(3L, addition);

       assertEquals(null, invoice.getId());
       assertEquals("description", invoice.getDescription());
       assertEquals(item, invoice.getItems().get(0));
       assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME), invoice.getStatus());
       assertEquals(Money.parse("CAD 50.00"), invoice.getAmount());
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getFreight());
       assertNull(invoice.getPaymentDueDate());
       assertNull(invoice.getReceivedOn());
       assertNull(invoice.getTax());
   }

   @Test
   public void testAdd_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
       doReturn(null).when(mPoService).getPurchaseOrder(3L);

       Invoice update = new Invoice();
       assertThrows(EntityNotFoundException.class, () -> service.add(3L, update), "PurchaseOrder not found with id: 3");
   }

   @Test
   public void testAdd_ThrowsValidationException_WhenNonExistentInvoiceStatusNameIsUsed() {
       doReturn(new PurchaseOrder(3L)).when(mPoService).getPurchaseOrder(3L);
       doReturn(null).when(mStatusService).getInvoiceStatus("FINAL");

       Invoice update = new Invoice();
       update.setStatus(new InvoiceStatus(null, "FINAL"));
       assertThrows(ValidationException.class, () -> service.add(3L, update), "Invalid Status Name: FINAL");
   }
}
