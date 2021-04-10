package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.ShipmentItemDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentItemDto;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.pojo.Material;
import tec.uom.se.quantity.Quantities;
import io.company.brewcraft.utils.SupportedUnits;

public class ShipmentMapperTest {

    private ShipmentMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ShipmentMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        UpdateShipmentDto dto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            2L,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            List.of(new UpdateShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("10")), 1L, 1)),
            1
        );

        Shipment shipment = mapper.fromDto(dto);

        Shipment expected = new Shipment(
            null,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(null, "RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            null,
            null,
            List.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), null, null, 1)),
            1
        );

        assertEquals(expected, shipment);
    }
    
    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(1L, "RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            1
        );

        ShipmentDto expected = new ShipmentDto(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(1L, "RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(new ShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("10")), new MaterialDto(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            1
        );
        
        assertEquals(expected, mapper.toDto(shipment));
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
