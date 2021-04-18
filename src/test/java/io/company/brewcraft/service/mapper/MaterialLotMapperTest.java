package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialLotMapperTest {

    private MaterialLotMapper mapper;

    @BeforeEach
    public void init() {
        mapper = MaterialLotMapper.INSTANCE;
    }

    @Test
    public void toDto_ReturnsDtoFromMaterialLot_WhenMaterialLotIsNotNull() {
        MaterialLot lot = new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        MaterialLotDto dto = mapper.toDto(lot);

        MaterialLotDto expected = new MaterialLotDto(1L, new QuantityDto("kg", new BigDecimal("1")), new MaterialDto(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        assertEquals(expected, dto);
    }

    @Test
    public void toDto_ReturnsNull_WhenShipmentIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void fromDto_ReturnMaterialLot_WhenDtoIsNotNull() {
        UpdateMaterialLotDto dto = new UpdateMaterialLotDto(1L, new QuantityDto("kg", new BigDecimal("1")), 1L, 1);

        MaterialLot lot = mapper.fromDto(dto);

        MaterialLot expected = new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), null, null, 1);
        assertEquals(expected, lot);
    }

    @Test
    public void fromDto_ReturnsNull_WhenMaterialLotDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

}
