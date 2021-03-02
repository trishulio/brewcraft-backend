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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FreightEntity;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.PurchaseOrderEntity;
import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.pojo.Tax;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

@SuppressWarnings("unchecked")
public class InvoiceServiceTest {
   private InvoiceService service;

   private InvoiceRepository mRepo;
   private InvoiceItemService mItemService;

   @BeforeEach
   public void init() {
       mRepo = mock(InvoiceRepository.class);
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
   public void testPut_CreatesAndSaveNewEntityWithUpdates_WhenInvoiceWithGivenIdDoesNotExist() {
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.parse("CAD 4.00")),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           List.of(itemUpdate),
           1
       );

       doReturn(LocalDateTime.of(2100, 12, 31, 23, 59)).when(service).now();

       doAnswer(inv -> {
           Long poId = inv.getArgument(0, Long.class);
           InvoiceEntity invoice = inv.getArgument(1, InvoiceEntity.class);           
           invoice.setPurchaseOrder(new PurchaseOrderEntity(poId));           
           return invoice;

       }).when(mRepo).refreshAndAdd(any(Long.class), any(InvoiceEntity.class));
       
       doReturn(List.of(itemUpdate)).when(mItemService).mergePut(any(Validator.class), isNull(), eq(List.of(itemUpdate)));

       Invoice invoice = service.put(3L, 1L, update);

       verify(mRepo, times(1)).refreshAndAdd(eq(3L), any(InvoiceEntity.class));
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(Money.parse("CAD 4.00")), invoice.getFreight());
       assertEquals(LocalDateTime.of(2100, 12, 31, 23, 59), invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       InvoiceItem item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPut_UpdatesTheExistingEntityAndSavesIt_WhenEntityExist() {
       InvoiceEntity mExisting = new InvoiceEntity(1L);
       mExisting.setCreatedAt(LocalDateTime.of(2100, 1, 1, 12, 0));
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.parse("CAD 4.00")),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(2L, "FINAL"),
           List.of(itemUpdate),
           1
       );

       doAnswer(inv -> {
           Long poId = inv.getArgument(0, Long.class);
           InvoiceEntity invoice = inv.getArgument(1, InvoiceEntity.class);           
           invoice.setPurchaseOrder(new PurchaseOrderEntity(poId));           
           return invoice;

       }).when(mRepo).refreshAndAdd(any(Long.class), any(InvoiceEntity.class));

       doReturn(List.of(itemUpdate)).when(mItemService).mergePut(any(Validator.class), isNull(), eq(List.of(itemUpdate)));
       
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice invoice = service.put(3L, 1L, update);

       verify(mRepo, times(1)).refreshAndAdd(eq(3L), any(InvoiceEntity.class));
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(Money.parse("CAD 4.00")), invoice.getFreight());
       assertEquals(LocalDateTime.of(2100, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       InvoiceItem item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPut_RaisesErrors_WhenValidatorHasFailures() {
       Validator mValidator = new Validator();
       mValidator.rule(false, "Fake Failure: %s", "TEST");

       InvoiceEntity mExisting = new InvoiceEntity(1L);
       mExisting.setCreatedAt(LocalDateTime.of(2100, 1, 1, 12, 0));
       InvoiceItem itemUpdate = new InvoiceItem(2L);
       Invoice update = new Invoice(1L);

       doReturn(List.of(itemUpdate)).when(mItemService).mergePut(any(Validator.class), isNull(), eq(List.of(itemUpdate)));
       
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       doReturn(mValidator).when(service).validator();
       
       assertThrows(ValidationException.class, () -> service.put(3L, 1L, update), "1. Fake Failure: TEST");
   }

   @Test
   public void testPatch_ApplyUpdatesOnExistingEntityAndSavesIt_WhenInvoiceEntityExists() {
       InvoiceEntity mExisting = new InvoiceEntity(
           1L,
           "ABCDE-12345",
           "desc1",
           new PurchaseOrderEntity(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new FreightEntity(1L),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatusEntity(2L, "FINAL"),
           null,
           1  
       );

       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
       Invoice update = new Invoice(1L);
       update.setDescription("New description value");
       update.setCreatedAt(LocalDateTime.of(9999, 12, 31, 12, 0));
       update.setLastUpdated(LocalDateTime.of(9999, 12, 31, 12, 0));
       update.setItems(List.of(itemUpdate));
       
       doAnswer(inv -> {
           Long poId = inv.getArgument(0, Long.class);
           InvoiceEntity invoice = inv.getArgument(1, InvoiceEntity.class);           
           invoice.setPurchaseOrder(new PurchaseOrderEntity(poId));           
           return invoice;

       }).when(mRepo).refreshAndAdd(any(Long.class), any(InvoiceEntity.class));

       doReturn(List.of(itemUpdate)).when(mItemService).mergePatch(any(Validator.class), isNull(), eq(List.of(itemUpdate)));

       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       Invoice invoice = service.patch(3L, 1L, update);

       verify(mRepo, times(1)).refreshAndAdd(eq(3L), any(InvoiceEntity.class));
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("New description value", invoice.getDescription());
       assertEquals(new PurchaseOrder(3L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new Freight(), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatus(2L, "FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getVersion());
       assertEquals(1, invoice.getItems().size());

       InvoiceItem item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPatch_RaisesErrors_WhenValidatorHasFailures() {
       Validator mValidator = new Validator();
       mValidator.rule(false, "Fake Failure: %s", "TEST");

       InvoiceEntity mExisting = new InvoiceEntity(1L);
       mExisting.setCreatedAt(LocalDateTime.of(2100, 1, 1, 12, 0));
       InvoiceItem itemUpdate = new InvoiceItem(2L);
       Invoice update = new Invoice(1L);

       doReturn(List.of(itemUpdate)).when(mItemService).mergePatch(any(Validator.class), isNull(), eq(List.of(itemUpdate)));

       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       doReturn(mValidator).when(service).validator();

       assertThrows(ValidationException.class, () -> service.patch(3L, 1L, update), "1. Fake Failure: TEST");
   }

   @Test
   public void testAdd_SavesTheNewEntity() {
       InvoiceItem itemUpdate = new InvoiceItem(2L, "Item description", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), Money.parse("CAD 10"), new Tax(Money.parse("CAD 20")), new Material(7L), 1);
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

       doAnswer(inv -> {
           Long poId = inv.getArgument(0, Long.class);
           InvoiceEntity invoice = inv.getArgument(1, InvoiceEntity.class);           
           invoice.setPurchaseOrder(new PurchaseOrderEntity(poId));           
           return invoice;

       }).when(mRepo).refreshAndAdd(any(Long.class), any(InvoiceEntity.class));

       doReturn(List.of(itemUpdate)).when(mItemService).addList(any(Validator.class), eq(List.of(itemUpdate)));

       Invoice invoice = service.add(3L, addition);

       verify(mRepo, times(1)).refreshAndAdd(eq(3L), any(InvoiceEntity.class));
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

       InvoiceItem item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("Item description", item.getDescription());
       assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), item.getQuantity());
       assertEquals(Money.parse("CAD 10"), item.getPrice());
       assertEquals(new Tax(Money.parse("CAD 20")), item.getTax());
       assertEquals(new Material(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testAdd_RaisesErrors_WhenValidatorHasFailures() {
       Validator mValidator = new Validator();
       mValidator.rule(false, "Fake Failure: %s", "TEST");

       InvoiceEntity mExisting = new InvoiceEntity(1L);
       mExisting.setCreatedAt(LocalDateTime.of(2100, 1, 1, 12, 0));
       InvoiceItem itemUpdate = new InvoiceItem(2L);
       Invoice update = new Invoice(1L);

       doReturn(List.of(itemUpdate)).when(mItemService).mergePatch(any(Validator.class), isNull(), eq(List.of(itemUpdate)));
       
       doReturn(Optional.of(mExisting)).when(mRepo).findById(1L);

       doReturn(mValidator).when(service).validator();

       assertThrows(ValidationException.class, () -> service.add(3L, update), "1. Fake Failure: TEST");
   }
}
