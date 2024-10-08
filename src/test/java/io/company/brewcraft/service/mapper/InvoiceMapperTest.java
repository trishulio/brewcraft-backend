package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddInvoiceItemDto;
import io.company.brewcraft.dto.AmountDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxAmountDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.TaxRateDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.TaxRate;
import io.company.brewcraft.util.SupportedUnits;
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
            List.of(new InvoiceItem(1L, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("10"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            1
        );

        InvoiceDto dto = mapper.toDto(invoice);

        InvoiceDto expected = new InvoiceDto(
            12345L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrderDto(1L),
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            new AmountDto(new MoneyDto("CAD", new BigDecimal("220.00")), new MoneyDto("CAD", new BigDecimal("20.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("200.00")), new MoneyDto("CAD", new BigDecimal("200.00")))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusDto(99L),
            List.of(new InvoiceItemDto(1L, "desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("10"))), new AmountDto(new MoneyDto("CAD", new BigDecimal("220.00")), new MoneyDto("CAD", new BigDecimal("20.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("200.00")), new MoneyDto("CAD", new BigDecimal("200.00")))), new MaterialDto(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
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
            1L,
            "ABCDE-12345",
            1L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            List.of(new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("10"))), 7L, 1)),
            1
        );

        Invoice invoice = mapper.fromUpdateDto(dto);

        Invoice expected = new Invoice(
            1L,
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
            List.of(new InvoiceItem(1L, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("10"))), new Material(7L), null, null, 1)),
            1
        );

        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenUpdateInvoiceDtoIsNull() {
        assertNull(mapper.fromUpdateDto((UpdateInvoiceDto) null));
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
            List.of(new AddInvoiceItemDto("desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("10"))), 7L))
        );

        Invoice invoice = mapper.fromAddDto(dto);

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
            List.of(new InvoiceItem(null, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("10"))), new Material(7L), null, null, null)),
            null
        );

        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddInvoiceDtoIsNull() {
        assertNull(mapper.fromAddDto((AddInvoiceDto) null));
    }
}
