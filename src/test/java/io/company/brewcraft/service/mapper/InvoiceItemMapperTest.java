package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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
import io.company.brewcraft.model.Currency;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.model.TaxEntity;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.Tax;
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
        InvoiceItem item = new InvoiceItem(
            2L,
            "LOT-123",
            "desc2",
            Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            1
        );
        InvoiceItemDto dto = mapper.toDto(item);

        assertEquals(2L, dto.getId());
        assertEquals("LOT-123", dto.getLotNumber());
        assertEquals("desc2", dto.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5.00")), dto.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6.00"))), dto.getTax());
        assertEquals(new MaterialDto(7L), dto.getMaterial());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testFromDto_ReturnsInvoice_WhenInvoiceItemDtoIsNotNull() {

        InvoiceItemDto dto = new InvoiceItemDto(
            2L,
            "LOT-123",
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("101")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new MoneyDto("CAD", new BigDecimal("20")),
            new MaterialDto(7L),
            1
        );
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(2L, item.getId());
        assertEquals("LOT-123", item.getLotNumber());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), Units.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals(new Tax(Money.parse("CAD 10")), item.getTax());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testFromDto_ReturnsInvoice_WhenUpdateInvoiceItemIsNotNull() {
        UpdateInvoiceItemDto dto = new UpdateInvoiceItemDto(
            2L,
            "LOT-123",
            "desc2",
            new QuantityDto("KG", new BigDecimal("100")),
            new MoneyDto("CAD", new BigDecimal("101")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new MoneyDto("CAD", new BigDecimal("20")),
            new MaterialDto(7L),
            1
        );
        InvoiceItem item = mapper.fromDto(dto);

        assertEquals(2L, item.getId());
        assertEquals("LOT-123", item.getLotNumber());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), Units.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 101"), item.getPrice());
        assertEquals(new Tax(Money.parse("CAD 10")), item.getTax());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testToEntity_ReturnEntity_WhenPojoIsNotNull() {
        InvoiceItem item = new InvoiceItem(
            2L,
            "LOT-123",
            "desc2",
            Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            1
        );
        InvoiceItemEntity entity = mapper.toEntity(item);

        assertEquals(2L, entity.getId());
        assertEquals("LOT-123", entity.getLotNumber());
        assertEquals("desc2", entity.getDescription());
        assertEquals(new QuantityEntity(null, new UnitEntity("kg", null), new BigDecimal("4")), entity.getQuantity());
        assertEquals(new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("5.00")), entity.getPrice());
        assertEquals(new TaxEntity(null, new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("6.00"))), entity.getTax());
        assertEquals(new MaterialEntity(7L), entity.getMaterial());
        assertEquals(1, entity.getVersion());
        assertNull(entity.getInvoice());
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        InvoiceItemEntity entity = new InvoiceItemEntity(
            2L,
            "LOT-123",
            "desc2",
            null,
            new QuantityEntity(null, new UnitEntity("kg", null), new BigDecimal("4")),
            new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("5.00")),
            new TaxEntity(null, new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("6.00"))),
            new MaterialEntity(7L),
            1
        );
        InvoiceItem item = mapper.fromEntity(entity);

        assertEquals(2L, item.getId());
        assertEquals("LOT-123", item.getLotNumber());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("4"), Units.KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 5"), item.getPrice());
        assertEquals(new Tax(Money.parse("CAD 6")), item.getTax());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }
}
