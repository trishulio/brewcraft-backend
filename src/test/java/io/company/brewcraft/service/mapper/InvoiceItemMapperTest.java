package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceItemMapperTest {

    InvoiceItemMapper mapper;

    @BeforeEach
    public void init() {
        mapper = InvoiceItemMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsInvoiceItemDto_WhenInvoiceItemIsNotNull() {
        InvoiceItem item = new InvoiceItem(
            2L,
            "desc2",
            Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(1999, 1, 1, 1, 1), 
            1
        );
        InvoiceItemDto dto = mapper.toDto(item);

        assertEquals(2L, dto.getId());
        assertEquals("desc2", dto.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), dto.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), dto.getTax());
        assertEquals(new MaterialDto(7L), dto.getMaterial());
        assertEquals(1, dto.getVersion());
    }
    
    @Test
    public void testToDto_ReturnsNull_WhenInvoiceItemIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testFromDto_ReturnsInvoice_WhenInvoiceItemDtoIsNotNull() {
        InvoiceItemDto dto = new InvoiceItemDto(
            2L,
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("101")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new MoneyDto("CAD", new BigDecimal("20")),
            new MaterialDto(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(2000, 1, 1, 1, 1), 
            1
        );
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(2L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals(new Tax(Money.parse("CAD 10")), item.getTax());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 1, 1), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), item.getLastUpdated());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testFromDto_ReturnsNull_WhenInvoiceItemDtoIsNull() {
        assertNull(mapper.fromDto((InvoiceItemDto) null));
    }
    
    @Test
    public void testFromDto_ReturnsInvoice_WhenUpdateInvoiceItemIsNotNull() {
        UpdateInvoiceItemDto dto = new UpdateInvoiceItemDto(
            1L,
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("101")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            7L,
            1
        );
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(1L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals(new Tax(Money.parse("CAD 10")), item.getTax());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testFromDto_ReturnsNull_WhenUpdateInvoiceItemIsNull() {
        assertNull(mapper.fromDto((UpdateInvoiceItemDto) null));
    }
}