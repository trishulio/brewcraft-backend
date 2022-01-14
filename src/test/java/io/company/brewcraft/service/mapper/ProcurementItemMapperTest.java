package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementMaterialLotDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.service.mapper.procurement.ProcurementItemMapper;
import io.company.brewcraft.util.SupportedUnits;
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
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        ProcurementItem procurementItem = new ProcurementItem(
            new MaterialLot(
                1L,
                0,
                "LOT_NUMBER",
                Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                new InvoiceItem(10L),
                new Storage(100L),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                1
            ),
            new InvoiceItem(
                2L,
                0,
                "desc2",
                Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM),
                Money.of(CurrencyUnit.CAD, new BigDecimal("200.00")),
                new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("10.00"))),
                new Material(7L),
                LocalDateTime.of(2002, 1, 1, 0, 0),
                LocalDateTime.of(2003, 1, 1, 0, 0),
                1
            )
        );

        ProcurementItemDto dto = mapper.toDto(procurementItem);

        ProcurementItemDto expected = new ProcurementItemDto(
            new ProcurementItemIdDto(1L, 2L),
            new ProcurementMaterialLotDto(
                1L,
                "LOT_NUMBER",
                new QuantityDto("kg", new BigDecimal("10")),
                new StorageDto(100L),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                1
            ),
            new ProcurementInvoiceItemDto(
                2L,
                "desc2",
                new QuantityDto("KG", new BigDecimal("100")),
                new MoneyDto("CAD", new BigDecimal("200.00")),
                new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))),
                new MoneyDto("CAD", new BigDecimal("20000.00")),
                new MaterialDto(7L),
                LocalDateTime.of(2002, 1, 1, 0, 0),
                LocalDateTime.of(2003, 1, 1, 0, 0),
                1
            )
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        AddProcurementItemDto dto = new AddProcurementItemDto(
            new AddProcurementMaterialLotDto(
                "LOT_NUMBER",
                new QuantityDto("kg", new BigDecimal("10")),
                100L
            ),
            new AddProcurementInvoiceItemDto(
                "desc2",
                new QuantityDto("KG", new BigDecimal("100")),
                new MoneyDto("CAD", new BigDecimal("200.00")),
                new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))),
                7L
            )
        );

        ProcurementItem procurementItem = mapper.fromAddDto(dto);

        ProcurementItem expected = new ProcurementItem(
            new MaterialLot(
                null,
                null,
                "LOT_NUMBER",
                Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                null,
                new Storage(100L),
                null,
                null,
                null
            ),
            new InvoiceItem(
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
            )
        );

        assertEquals(expected, procurementItem);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto(null));
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity_WhenDtoIsNotNull() {
        UpdateProcurementItemDto dto = new UpdateProcurementItemDto(
            new UpdateProcurementMaterialLotDto(
                1L,
                "LOT_NUMBER",
                new QuantityDto("kg", new BigDecimal("10")),
                100L,
                1
            ),
            new UpdateProcurementInvoiceItemDto(
                2L,
                "desc2",
                new QuantityDto("KG", new BigDecimal("100")),
                new MoneyDto("CAD", new BigDecimal("200.00")),
                new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))),
                7L,
                1
            )
        );

        ProcurementItem procurementItem = mapper.fromUpdateDto(dto);

        ProcurementItem expected = new ProcurementItem(
            new MaterialLot(
                1L,
                null,
                "LOT_NUMBER",
                Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                null,
                new Storage(100L),
                null,
                null,
                1
            ),
            new InvoiceItem(
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
            )
        );

        assertEquals(expected, procurementItem);
    }
}
