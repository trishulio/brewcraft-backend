package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.*;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

@SuppressWarnings("unchecked")
public class InvoiceControllerTest {

   private InvoiceController controller;

   private InvoiceService mService;
   private AttributeFilter filter;

   @BeforeEach
   public void init() {
       mService = mock(InvoiceService.class);
       filter = new AttributeFilter();

       controller = new InvoiceController(mService, filter);
   }

   @Test
   public void testGetInvoices_CallsServicesWithArguments_AndReturnsPageDtoOfInvoiceDtosMappedFromInvoicesPage() {
       List<Invoice> mInvoices = List.of(
           new Invoice(
               12345L,
               "ABCDE-12345",
               "desc1",
               new PurchaseOrder(1L),
               LocalDateTime.of(1999, 1, 1, 12, 0),
               LocalDateTime.of(2000, 1, 1, 12, 0),
               LocalDateTime.of(2001, 1, 1, 12, 0),
               new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               new InvoiceStatus(99L),
               List.of(new InvoiceItem(1L, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
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
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<InvoiceDto> dto = controller.getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10,
           Set.of() // empty means no fields should be filtered out, i.e. no field should be null
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       InvoiceDto invoice = dto.getContent().get(0);
       assertEquals(12345L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrderDto(1L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto(99L), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(1L, item.getId());
       assertEquals("desc2", item.getDescription());
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
               "desc1",
               new PurchaseOrder(1L),
               LocalDateTime.of(1999, 1, 1, 12, 0),
               LocalDateTime.of(2000, 1, 1, 12, 0),
               LocalDateTime.of(2001, 1, 1, 12, 0),
               new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
               LocalDateTime.of(2002, 1, 1, 12, 0),
               LocalDateTime.of(2003, 1, 1, 12, 0),
               new InvoiceStatus(99L),
               List.of(new InvoiceItem(2L, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
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
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
           true,
           1,
           10
       );

       PageDto<InvoiceDto> dto = controller.getInvoices(
           Set.of(1L),
           Set.of(2L),
           Set.of("ABCDE-12345"),
           Set.of("invoice description"),
           Set.of("item description"),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           LocalDateTime.of(2004, 1, 1, 12, 0),
           Set.of(3L),
           new BigDecimal("1"),
           new BigDecimal("2"),
           Set.of(99L),
           Set.of(4L),
           new TreeSet<>(List.of("id")),
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
       assertNull(invoice.getDescription());
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
           "desc1",
           new PurchaseOrder(1L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(99L),
           List.of(new InvoiceItem(2L, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
           1
       );
       doReturn(mInvoice).when(mService).getInvoice(1L);
       
       InvoiceDto invoice = controller.getInvoice(1L, Set.of());
       assertEquals(12345L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrderDto(1L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))), invoice.getFreight());
       assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
       assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto(99L), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(2L, item.getId());
       assertEquals("desc2", item.getDescription());
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
           "desc1",
           new PurchaseOrder(1L),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
           LocalDateTime.of(2002, 1, 1, 12, 0),
           LocalDateTime.of(2003, 1, 1, 12, 0),
           new InvoiceStatus(99L),
           List.of(new InvoiceItem(2L, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
           1
       );
       doReturn(mInvoice).when(mService).getInvoice(1L);

       InvoiceDto invoice = controller.getInvoice(1L, Set.of("id"));

       assertEquals(12345L, invoice.getId());
       assertNull(invoice.getInvoiceNumber());
       assertNull(invoice.getDescription());
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
   public void testDeleteInvoices_ReturnsDeleteCountFromService() {
       doReturn(99).when(mService).delete(Set.of(1L, 11L, 111L));
       int count = controller.deleteInvoices(Set.of(1L, 11L, 111L));
       assertEquals(99, count);
       
       doReturn(9999).when(mService).delete(Set.of(1L, 11L, 111L));
       count = controller.deleteInvoices(Set.of(1L, 11L, 111L));
       assertEquals(9999, count);
   }

   @Test
   public void testAddInvoice_ReturnsInvoiceDtoAfterAddingToService() {
       doAnswer(i -> {
           Invoice invoice = i.getArgument(0, Invoice.class);
           invoice.setId(1L);
           return invoice;
       }).when(mService).add(any(Invoice.class));

       AddInvoiceDto payload = new AddInvoiceDto(
           "ABCDE-12345",
           2L,
           "desc1",
           new FreightDto(new MoneyDto("CAD", new BigDecimal("4"))),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           99L,
           List.of(new AddInvoiceItemDto("desc2", new QuantityDto("KG", new BigDecimal("1")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L))
       );

       InvoiceDto invoice = controller.addInvoice(payload);
       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("4.00"))), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto(99L), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(null, item.getId());
       assertEquals("desc2", item.getDescription());
       assertEquals(new QuantityDto("KG", new BigDecimal("1")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(null, item.getVersion());
   }

   @Test
   public void testUpdateInvoice_ReturnsInvoiceDtoAfterUpdatingItToService() {
       doAnswer(i -> {
           Invoice invoice = i.getArgument(1, Invoice.class);
           invoice.setId(i.getArgument(0, Long.class));
           return invoice;
       }).when(mService).put(anyLong(), any(Invoice.class));

       UpdateInvoiceDto payload = new UpdateInvoiceDto(
           "ABCDE-12345",
           2L,
           "desc1",
           new FreightDto(new MoneyDto("CAD", new BigDecimal("4"))),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           99L,
           List.of(new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("KG", new BigDecimal("1")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L, 1)),
           1
       );

       InvoiceDto invoice = controller.updateInvoice(1L, payload);

       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("4.00"))), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto(99L), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(1L, item.getId());
       assertEquals("desc2", item.getDescription());
       assertEquals(new QuantityDto("KG", new BigDecimal("1")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }

   @Test
   public void testPatchInvoice_ReturnsInvoiceDtoAfterPatchingItToService() {
       doAnswer(i -> {
           Invoice invoice = i.getArgument(1, Invoice.class);
           invoice.setId(i.getArgument(0, Long.class));
           return invoice;
       }).when(mService).patch(anyLong(), any(Invoice.class));

       UpdateInvoiceDto payload = new UpdateInvoiceDto(
           "ABCDE-12345",
           2L,
           "desc1",
           new FreightDto(new MoneyDto("CAD", new BigDecimal("4"))),
           LocalDateTime.of(1999, 1, 1, 12, 0),
           LocalDateTime.of(2000, 1, 1, 12, 0),
           LocalDateTime.of(2001, 1, 1, 12, 0),
           99L,
           List.of(new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("KG", new BigDecimal("1")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L, 1)),
           1
       );

       InvoiceDto invoice = controller.patchInvoice(1L, payload);

       assertEquals(1L, invoice.getId());
       assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
       assertEquals("desc1", invoice.getDescription());
       assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
       assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
       assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
       assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
       assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("4.00"))), invoice.getFreight());
       assertEquals(null, invoice.getCreatedAt());
       assertEquals(null, invoice.getLastUpdated());
       assertEquals(new InvoiceStatusDto(99L), invoice.getStatus());
       assertEquals(1, invoice.getItems().size());
       InvoiceItemDto item = invoice.getItems().get(0);
       assertEquals(1L, item.getId());
       assertEquals("desc2", item.getDescription());
       assertEquals(new QuantityDto("KG", new BigDecimal("1")), item.getQuantity());
       assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), item.getPrice());
       assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), item.getTax());
       assertEquals(new MaterialDto(7L), item.getMaterial());
       assertEquals(1, item.getVersion());
   }
}
