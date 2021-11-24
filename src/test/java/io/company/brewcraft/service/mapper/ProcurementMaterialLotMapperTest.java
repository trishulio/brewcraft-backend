package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementMaterialLotDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.mapper.procurement.ProcurementMaterialLotMapper;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementMaterialLotMapperTest {

    private ProcurementMaterialLotMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementMaterialLotMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        ProcurementMaterialLotDto dto = mapper.toDto(new MaterialLot(
            1L,
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new InvoiceItem(10L),
            new Storage(100L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        ));

        ProcurementMaterialLotDto expected = new ProcurementMaterialLotDto(
            1L,
            "LOT_NUMBER",
            new QuantityDto("kg", new BigDecimal("10")),
            new StorageDto(100L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
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
        MaterialLot lot = mapper.fromUpdateDto(new UpdateProcurementMaterialLotDto(
            1L,
            "LOT_NUMBER",
            new QuantityDto("kg", new BigDecimal("10")),
            100L,
            1
        ));

        MaterialLot expected = new MaterialLot(
            1L,
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            null,
            new Storage(100L),
            null, // createdAt
            null, // lastUpdated
            1
        );
        assertEquals(expected, lot);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        MaterialLot lot = mapper.fromAddDto(new AddProcurementMaterialLotDto(
            "LOT_NUMBER",
            new QuantityDto("kg", new BigDecimal("10")),
            100L
        ));

        MaterialLot expected = new MaterialLot(
            null, // id
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            null,
            new Storage(100L),
            null, // createdAt
            null, // lastUpdated
            null // version
        );
        assertEquals(expected, lot);
    }
}
