package io.company.brewcraft.service.mapper;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.StockLotDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.Storage;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class StockLotMapperTest {
    private StockLotMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = StockLotMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(this.mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenStockIsNotNull() {
        final StockLot lot = new StockLot(
            1L,
            "LOT_1",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new Material(3L),
            new Shipment(1L),
            new InvoiceItem(4L),
            new Storage(2L)
        );

        final StockLotDto dto = this.mapper.toDto(lot);

        final StockLotDto expected = new StockLotDto(
            1L,
            "LOT_1",
            new QuantityDto("kg", new BigDecimal("10")),
            new InvoiceItemDto(4L),
            new StorageDto(2L),
            new MaterialDto(3L)
        );

        assertEquals(expected, dto);
    }
}
