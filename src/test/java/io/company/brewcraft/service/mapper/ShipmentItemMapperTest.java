package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentItemDto;
import io.company.brewcraft.dto.UpdateShipmentItemDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.ShipmentItem;
import tec.uom.se.quantity.Quantities;
import io.company.brewcraft.utils.SupportedUnits;

public class ShipmentItemMapperTest {

    private ShipmentItemMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ShipmentItemMapper.INSTANCE;
    }

    @Test
    public void toDto_ReturnsDtoFromShipmentItem_WhenShipmentItemIsNotNull() {
        ShipmentItem item = new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        ShipmentItemDto dto = mapper.toDto(item);

        ShipmentItemDto expected = new ShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("1")), new MaterialDto(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1);

        assertEquals(expected, dto);
    }

    @Test
    public void toDto_ReturnsNull_WhenShipmentIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void fromDto_ReturnShipmentItem_WhenDtoIsNotNull() {
        UpdateShipmentItemDto dto = new UpdateShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("1")), 1L, 1);

        ShipmentItem item = mapper.fromDto(dto);

        ShipmentItem expected = new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), null, null, 1);
        assertEquals(expected, item);
    }

    @Test
    public void fromDto_ReturnsNull_WhenShipmentItemDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

}
