package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.pojo.InvoiceItem;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

public class InvoiceItemMapperTest {

    InvoiceItemMapper mapper;

    @BeforeEach
    public void init() {
        mapper = InvoiceItemMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsInvoiceItemDto_WhenInvoiceItemIsNotNull() {
        InvoiceItem item = new InvoiceItem(12345L, Quantities.getQuantity(100, Units.KILOGRAM), Money.parse("CAD 99"), "LOT", new Material(111L), 1);
        InvoiceItemDto dto = mapper.toDto(item);

        assertEquals(12345L, dto.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal(100)), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("99.00")), dto.getPrice());
        assertEquals(new MoneyDto("CAD", new BigDecimal("9900.00")), dto.getAmount());
        assertEquals("LOT", dto.getLot());
        assertEquals(new MaterialEntity(111L), dto.getMaterial());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testFromDto_ReturnsInvoice_WhenInvoiceItemDtoIsNotNull() {
        InvoiceItemDto dto = new InvoiceItemDto(12345L, new QuantityDto("kg", new BigDecimal(100)), new MoneyDto("CAD", new BigDecimal(101)), new MoneyDto("CAD", new BigDecimal(103)), "LOT", new MaterialDto(), 1);
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(12345L, item.getId());
        assertEquals(Quantities.getQuantity(100, Units.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals("LOT", item.getLot());
        assertEquals(new Material(), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testFromDto_ReturnsInvoice_WhenUpdateInvoiceItemIsNotNull() {
        UpdateInvoiceItemDto dto = new UpdateInvoiceItemDto(12345L, new QuantityDto("kg", new BigDecimal(100)), new MoneyDto("CAD", new BigDecimal(101)), "LOT", new MaterialDto(), 1);
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(12345L, item.getId());
        assertEquals(Quantities.getQuantity(100, Units.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals("LOT", item.getLot());
        assertEquals(new Material(), item.getMaterial());
        assertEquals(1, item.getVersion());
    }
}
