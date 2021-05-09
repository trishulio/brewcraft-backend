package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.*;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceMapperTest {
    private InvoiceMapper mapper;
    
    @BeforeEach
    public void init() {
        mapper = InvoiceMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto_WhenArgIsNotNull() {
        Invoice invoice = new Invoice(
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
        );

        InvoiceDto dto = mapper.toDto(invoice);

        InvoiceDto expected = new InvoiceDto(
            12345L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrderDto(1L),
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            new MoneyDto("CAD", new BigDecimal("20.00")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusDto(99L),
            List.of(new InvoiceItemDto(1L, "desc2", new QuantityDto("kg", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), new MoneyDto("CAD", new BigDecimal("20.00")), new MaterialDto(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testFromDto_ReturnsInvoiceWithUpdateProperties_WhenUpdateInvoiceDtoIsNotNull() {
        UpdateInvoiceDto dto = new UpdateInvoiceDto(
            "ABCDE-12345",
            1L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            List.of(new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("kg", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), 7L, 1)),
            1
        );
        
        Invoice invoice = mapper.fromDto(dto);
        
        Invoice expected = new Invoice(
            null,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            null,
            null,
            new InvoiceStatus(99L),
            List.of(new InvoiceItem(1L, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), null, null, 1)),
            1
        );
        
        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenUpdateInvoiceDtoIsNull() {
        assertNull(mapper.fromDto((UpdateInvoiceDto) null));
    }

    @Test
    public void testFromDto_ReturnsInvoiceWithAddProperties_WhenAddInvoiceDtoIsNotNull() {
        AddInvoiceDto dto = new AddInvoiceDto(
            "ABCDE-12345",
            1L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            List.of(new AddInvoiceItemDto("desc2", new QuantityDto("kg", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), 7L))
        );
        
        Invoice invoice = mapper.fromDto(dto);
        
        Invoice expected = new Invoice(
            null,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            null,
            null,
            new InvoiceStatus(99L),
            List.of(new InvoiceItem(null, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), new Material(7L), null, null, null)),
            null
        );
        
        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddInvoiceDtoIsNull() {
        assertNull(mapper.fromDto((AddInvoiceDto) null));
    }
}
