package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.*;
import io.company.brewcraft.pojo.Freight;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.PurchaseOrder;
import io.company.brewcraft.pojo.Tax;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

@SuppressWarnings("unchecked")
public class InvoiceControllerTest {

   private InvoiceController controller;

   private InvoiceService mService;
   private AttributeFilter mFilter;

   @BeforeEach
   public void init() {
       mService = mock(InvoiceService.class);
       mFilter = new AttributeFilter();

       controller = new InvoiceController(mService, mFilter);
   }

   @Test
   public void testGetInvoices_CallsServicesWithArguments_AndReturnsPageDtoOfInvoiceDtosMappedFromInvoicesPage() {
       List<Invoice> mInvoices = List.of(
           new Invoice(
               12345L,
               "ABCDE-12345",
               new PurchaseOrder(1L),
               LocalDateTime.of(1999, 1, 1, 12, 0),
               LocalDateTime.of(2000, 1, 1, 12, 0),
               LocalDateTime.of(2001, 1, 1, 12, 0),
               new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               new InvoiceStatus(4L, "FINAL"),
               List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
               1
           )
       );
       Page<Invoice> mPage = mock(Page.class);
       doReturn(mInvoices.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(mService).getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of("FINAL"),
           Set.of(4L),
           Set.of("id"),
           true,
           1,
           10
       );

       PageDto<InvoiceDto> dto = controller.getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of("FINAL"),
           Set.of(4L),
           Set.of("id"),
           true,
           1,
           10,
           Set.of() // empty means no fields should be filtered out, a.k.a none should be null
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       InvoiceDto invoice = dto.getContent().get(0);
       assertEquals(12345L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(new PurchaseOrderDto(1L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("LOT-123", item.getLotNumber());
       assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testGetInvoices_CallsServicesWithArguments_AndReturnsPageDtoWithAllFieldsFilteredOut() {
       List<Invoice> mInvoices = List.of(
           new Invoice(
               12345L,
               "ABCDE-12345",
               new PurchaseOrder(1L),
               LocalDateTime.of(1999, 1, 1, 12, 0),
               LocalDateTime.of(2000, 1, 1, 12, 0),
               LocalDateTime.of(2001, 1, 1, 12, 0),
               new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               new InvoiceStatus(4L, "FINAL"),
               List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
               1
           )
       );
       Page<Invoice> mPage = mock(Page.class);
       doReturn(mInvoices.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(mService).getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of("FINAL"),
           Set.of(4L),
           Set.of("id"),
           true,
           1,
           10
       );

       PageDto<InvoiceDto> dto = controller.getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of("FINAL"),
           Set.of(4L),
           Set.of("id"),
           true,
           1,
           10,
           Set.of("id")
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       InvoiceDto invoice = dto.getContent().get(0);
       assertEquals(12345L, invoice.getId());
       assertNull(invoice.getInvoiceNumber());
       assertNull(invoice.getPurchaseOrder());
       assertNull(invoice.getGeneratedOn());
       assertNull(invoice.getReceivedOn());
       assertNull(invoice.getPaymentDueDate());
       assertNull(invoice.getFreight());
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getLastUpdated());
       assertNull(invoice.getStatus());
       assertNull(invoice.getItems());
   }
   
   @Test
   public void testGetInvoice_ReturnsInvoiceDtoMappedFromServiceInvoice() {
       Invoice mInvoice = new Invoice(
           12345L,
           "ABCDE-12345",
           new PurchaseOrder(1L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(4L, "FINAL"),
           List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
           1
       );
       doReturn(mInvoice).when(mService).getInvoice(1L);
       
       InvoiceDto invoice = controller.getInvoice(1L, Set.of());
       assertEquals(12345L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(new PurchaseOrderDto(1L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("LOT-123", item.getLotNumber());
       assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }
   
   @Test
   public void testGetInvoice_FiltersOutAllAttributesExceptId_WhenAttributeSetOnlyContainsId() {
       Invoice mInvoice = new Invoice(
           12345L,
           "ABCDE-12345",
           new PurchaseOrder(1L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(4L, "FINAL"),
           List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
           1
       );
       doReturn(mInvoice).when(mService).getInvoice(1L);

       InvoiceDto invoice = controller.getInvoice(1L, Set.of("id"));

       assertEquals(12345L, invoice.getId());
       assertNull(invoice.getInvoiceNumber());
       assertNull(invoice.getPurchaseOrder());
       assertNull(invoice.getGeneratedOn());
       assertNull(invoice.getReceivedOn());
       assertNull(invoice.getPaymentDueDate());
       assertNull(invoice.getFreight());
       assertNull(invoice.getCreatedAt());
       assertNull(invoice.getLastUpdated());
       assertNull(invoice.getStatus());
       assertNull(invoice.getItems());
   }
   
   @Test
   public void testGetInvoice_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       doReturn(null).when(mService).getInvoice(1L);
       assertThrows(EntityNotFoundException.class, () -> controller.getInvoice(1L, Set.of()), "Invoice not found with id: 1");
   }

   @Test
   public void testDeleteInvoice_CallsServiceWithId() {
       doNothing().when(mService).delete(1L);
       controller.deleteInvoice(1L);

       verify(mService, times(1)).delete(1L);
   }

   @Test
   public void testDeleteInvoices_ThrowsEntityNotFoundException_WhenSupplierThrowsEntityNotFoundException() {
       doThrow(new EntityNotFoundException("Invoice", "1")).when(mService).delete(1L);
       assertThrows(EntityNotFoundException.class, () -> controller.deleteInvoice(1L), "Invoice not found with id: 1");
   }

   @Test
   public void testAddInvoice_ReturnsInvoiceDtoAfterAddingToService() {
       AddInvoiceDto payload = new AddInvoiceDto(
           "ABCDE-12345",
           new FreightDto(new MoneyDto("CAD", new BigDecimal("4.00"))),
           new MoneyDto("CAD", new BigDecimal("10.00")),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatusDto("FINAL"),
           List.of(new UpdateInvoiceItemDto(3L, "LOT-123", new QuantityDto("KG", new BigDecimal("1.00")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), new MoneyDto("CAD", new BigDecimal("7.00")), new MaterialDto(7L), 1))
       );
       Invoice mAddInvoice = new Invoice(
           null,
           "ABCDE-12345",
           null,
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("4.00"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(null, "FINAL"),
           List.of(new InvoiceItem(3L, "LOT-123", Quantities.getQuantity(new BigDecimal("1.00"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5.00")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6.00"))), new Material(7L), 1)),
           null
       );
       Invoice mRetInvoice = new Invoice(
           1L,
           "ABCDE-12345",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(4L, "FINAL"),
           List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
           1
       );       
       doReturn(mRetInvoice).when(mService).add(2L, mAddInvoice);

       InvoiceDto invoice = controller.addInvoice(2L, payload);
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("LOT-123", item.getLotNumber());
       assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testUpdateInvoice_ReturnsInvoiceDtoAfterUpdatingItToService() {
       UpdateInvoiceDto payload = new UpdateInvoiceDto(
           "ABCDE-12345",
           new FreightDto(new MoneyDto("CAD", new BigDecimal("4.00"))),
           new MoneyDto("CAD", new BigDecimal("10.00")),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatusDto("FINAL"),
           List.of(new UpdateInvoiceItemDto(3L, "LOT-123", new QuantityDto("KG", new BigDecimal("1.00")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), new MoneyDto("CAD", new BigDecimal("7.00")), new MaterialDto(7L), 1)),
           1
       );
       Invoice mUpdateInvoice = new Invoice(
           null,
           "ABCDE-12345",
           null,
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("4.00"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(null, "FINAL"),
           List.of(new InvoiceItem(3L, "LOT-123", Quantities.getQuantity(new BigDecimal("1.00"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5.00")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6.00"))), new Material(7L), 1)),
           1
       );
       Invoice mRetInvoice = new Invoice(
           1L,
           "ABCDE-12345",
           new PurchaseOrder(2L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(4L, "FINAL"),
           List.of(new InvoiceItem(2L, "LOT-123", Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), 1)),
           1
       );
       doReturn(mRetInvoice).when(mService).update(2L, 1L, mUpdateInvoice);

       InvoiceDto invoice = controller.updateInvoice(2L, 1L, payload);

       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("LOT-123", item.getLotNumber());
       assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }
}
