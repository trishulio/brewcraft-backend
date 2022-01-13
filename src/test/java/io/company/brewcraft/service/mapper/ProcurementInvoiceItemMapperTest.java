package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.service.mapper.procurement.ProcurementInvoiceItemMapper;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ProcurementInvoiceItemMapperTest {

    ProcurementInvoiceItemMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementInvoiceItemMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        InvoiceItem invoiceItem = new InvoiceItem(
            2L,
            0,
            "desc2",
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("200")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("10"))),
            new Material(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            1
        );

        ProcurementInvoiceItemDto dto = mapper.toDto(invoiceItem);

        ProcurementInvoiceItemDto expected = new ProcurementInvoiceItemDto(
            2L,
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("200.00")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))),
            new MoneyDto("CAD", new BigDecimal("20000.00")),
            new MaterialDto(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto(null));
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity_WhenDtoIsNotNull() {
        UpdateProcurementInvoiceItemDto dto = new UpdateProcurementInvoiceItemDto(
            2L,
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("200")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            7L,
            1
        );

        InvoiceItem invoiceItem = mapper.fromUpdateDto(dto);

        InvoiceItem expected = new InvoiceItem(
            2L,
            null,
            "desc2",
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("200.00")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("10.00"))),
            new Material(7L),
            null,
            null,
            1
        );

        assertEquals(expected, invoiceItem);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        AddProcurementInvoiceItemDto dto = new AddProcurementInvoiceItemDto(
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("200")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            7L
        );

        InvoiceItem invoiceItem = mapper.fromAddDto(dto);

        InvoiceItem expected = new InvoiceItem(
            null,
            null,
            "desc2",
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("200.00")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("10.00"))),
            new Material(7L),
            null,
            null,
            null
        );

        assertEquals(expected, invoiceItem);
    }
}