package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.ShipmentEntity;
import io.company.brewcraft.pojo.Shipment;

public class ShipmentMapperTest {

    private ShipmentMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ShipmentMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        ShipmentDto dto = new ShipmentDto(1L, "ABCD-123");
        Shipment shipment = mapper.fromDto(dto);

        assertEquals(new Shipment(1L, "ABCD-123"), shipment);
    }

    @Test
    public void testFromEntity_ReturnPojo_WhenEntityIsNotNull() {
        ShipmentEntity entity = new ShipmentEntity(1L, "ABCD-123", new InvoiceEntity(2L));
        Shipment shipment = mapper.fromEntity(entity);

        assertEquals(new Shipment(1L, "ABCD-123"), shipment);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Shipment shipment = new Shipment(1L, "ABCD-123");
        ShipmentDto dto = mapper.toDto(shipment);

        assertEquals(new ShipmentDto(1L, "ABCD-123"), dto);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Shipment shipment = new Shipment(1L, "ABCD-123");
        ShipmentEntity entity = mapper.toEntity(shipment);

        assertEquals(new ShipmentEntity(1L, "ABCD-123", null), entity);
    }

}
