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

import io.company.brewcraft.model.Currency;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.model.TaxEntity;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.pojo.Tax;
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
       
       InvoiceItem itemUpdate = new InvoiceItem(3L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(
           9L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.parse("CAD 4.00")),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(null, "FINAL"),
           List.of(itemUpdate),
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
       assertEquals(null, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
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

       InvoiceItemEntity mExistingItem = new InvoiceItemEntity(2L, "Item description", null, new QuantityEntity(5L, new UnitEntity("kg"), new BigDecimal("10")), new MoneyEntity(6L, new Currency(124, "CAD"), new BigDecimal("20")), new TaxEntity(7L), new MaterialEntity(8L), 1);
       InvoiceEntity mExisting = new InvoiceEntity(1L);
       mExisting.setDescription("existing description");
       mExisting.setItems(List.of(mExistingItem));
       mExisting.setInvoiceNumber("ABCDE-12345");
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice patch = new Invoice();
       patch.setDescription("New description");
       patch.setGeneratedOn(LocalDateTime.of(2020, 1, 1, 12, 0));
       // These fields are ignored during patch Ignored.
       patch.setPurchaseOrder(new PurchaseOrder(99L));
       patch.setCreatedAt(LocalDateTime.of(2002, 1, 1, 12, 0));
       patch.setLastUpdated(LocalDateTime.of(2003, 1, 1, 12, 0));

       Invoice invoice = service.patch(3L, 1L, patch);

       assertEquals(1L, invoice.getId());
       assertEquals("New description", invoice.getDescription());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       assertNull(invoice.getPaymentDueDate());
       assertNull(invoice.getReceivedOn());
       assertNull(invoice.getFreight());
       assertNull(invoice.getTax());
       InvoiceItem item = invoice.getItems().get(0);
       assertNull(item.getId()); // Ignored
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 20"), item.getPrice());
       assertEquals(new Tax(), item.getTax());
       // Ignored in update
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getLastUpdated());
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
       
       InvoiceItem itemAdded = new InvoiceItem(3L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice addition = new Invoice(
           9L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.parse("CAD 4.00")),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           null,
           List.of(itemAdded),
           1
       );

       Invoice invoice = service.add(3L, addition);

       assertNull(invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(Money.parse("CAD 4.00")), invoice.getFreight());
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(4L, InvoiceStatusEntity.DEFAULT_STATUS_NAME), invoice.getStatus());
       assertNull(invoice.getVersion());
       assertEquals(1, invoice.getItems().size());
       InvoiceItem item = invoice.getItems().get(0);
       assertNull(item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertNull(item.getVersion());
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
