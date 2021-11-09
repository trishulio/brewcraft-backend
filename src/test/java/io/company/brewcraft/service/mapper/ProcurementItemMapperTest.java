package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.service.mapper.procurement.ProcurementItemMapper;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementItemMapperTest {

    private ProcurementItemMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementItemMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto((ProcurementItem) null));
    }

    @Test
    public void testToDto_ReturnsDtoWithAllValues_WhenEntityIsNotNull() {
        ProcurementItem item = new ProcurementItem(
            new ProcurementItemId(1L, 2L),
            "DESCRIPTION",
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new Storage(1L), // storage
            Money.parse("CAD 10"), // price
            new Tax(Money.parse("CAD 20")), // tax
            new Material(1L), // material
            LocalDateTime.of(1999, 1, 1, 0, 0), // createdAt
            LocalDateTime.of(2000, 1, 1, 0, 0), // lastUpdated
            1,
            10
        );

        ProcurementItemDto dto = mapper.toDto(item);

        ProcurementItemDto expected = new ProcurementItemDto(
            new ProcurementItemIdDto(1L, 2L),
            "DESCRIPTION", // description
            "LOT_NUMBER", // lotNumber
            new QuantityDto("kg", new BigDecimal("10")), // quantity
            new MoneyDto("CAD", new BigDecimal("10.00")), // price
            new TaxDto(new MoneyDto("CAD", new BigDecimal("20.00"))), // tax
            new MoneyDto("CAD", new BigDecimal("100.00")), // amount
            new MaterialDto(1L), // material
            new StorageDto(1L), // storage
            LocalDateTime.of(1999, 1, 1, 0, 0), // createdAt
            LocalDateTime.of(2000, 1, 1, 0, 0), // lastUpdated
            1, // invoiceItemVersion
            10 // version
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((AddProcurementItemDto) null));
    }

    @Test
    public void testFromAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        AddProcurementItemDto dto = new AddProcurementItemDto(
            "DESCRIPTION", // description
            "LOT_NUMBER", // lotNumber
            new QuantityDto("kg", new BigDecimal("10")), // quantity
            new MoneyDto("CAD", new BigDecimal("20")), // price
            new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
            1L, // materialId
            2L // storageId
        );

        ProcurementItem item = mapper.fromDto(dto);

        ProcurementItem expected = new ProcurementItem(
            null,
            "DESCRIPTION",
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new Storage(2L), // storage
            Money.parse("CAD 20"), // price
            new Tax(Money.parse("CAD 5")), // tax
            new Material(1L), // material
            null, // createdAt
            null, // lastUpdated
            null, // invoiceItemVersion
            null // version
        );

        assertEquals(expected, item);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((UpdateProcurementItemDto) null));
    }

    @Test
    public void testUpdateAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        UpdateProcurementItemDto dto = new UpdateProcurementItemDto(
            new ProcurementItemIdDto(1L, 2L),
            "DESCRIPTION", // description
            "LOT_NUMBER", // lotNumber
            new QuantityDto("kg", new BigDecimal("10")), // quantity
            new MoneyDto("CAD", new BigDecimal("20")), // price
            new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
            1L, // materialId
            2L, // storageId
            1, // invoiceItemVersion
            10 // version
        );

        ProcurementItem item = mapper.fromDto(dto);

        ProcurementItem expected = new ProcurementItem(
            new ProcurementItemId(1L, 2L),
            "DESCRIPTION",
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new Storage(2L), // storage
            Money.parse("CAD 20"), // price
            new Tax(Money.parse("CAD 5")), // tax
            new Material(1L), // material
            null, // createdAt
            null, // lastUpdated
            1, // invoiceItemVersion
            10 // version
        );

        assertEquals(expected, item);
    }
}
