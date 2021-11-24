package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.procurement.AddProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.ProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementShipmentDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.mapper.procurement.ProcurementShipmentMapper;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ProcurementShipmentMapperTest {

    private ProcurementShipmentMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = ProcurementShipmentMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        final UpdateProcurementShipmentDto dto = new UpdateProcurementShipmentDto(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            100L,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            1
        );

        final Shipment shipment = this.mapper.fromUpdateDto(dto);

        final Shipment expected = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            null,
            null,
            null,
            1
        );

        assertEquals(expected, shipment);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(this.mapper.fromUpdateDto((UpdateProcurementShipmentDto) null));
        assertNull(this.mapper.fromAddDto((AddProcurementShipmentDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        final Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            1
        );

        final ProcurementShipmentDto expected = new ProcurementShipmentDto(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            1
        );

        assertEquals(expected, this.mapper.toDto(shipment));
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(this.mapper.toDto(null));
    }
}
