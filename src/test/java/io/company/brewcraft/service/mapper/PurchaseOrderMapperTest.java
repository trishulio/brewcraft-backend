package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;

public class PurchaseOrderMapperTest {

    private PurchaseOrderMapper mapper;

    @BeforeEach
    public void init() {
        mapper = PurchaseOrderMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
        PurchaseOrder order = mapper.fromDto(1L);

        PurchaseOrder expected = new PurchaseOrder(1L);

        assertEquals(expected, order);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        PurchaseOrder order = mapper.fromDto(new PurchaseOrderDto(1L));

        PurchaseOrder expected = new PurchaseOrder(1L);

        assertEquals(expected, order);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((PurchaseOrderDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        PurchaseOrder order = new PurchaseOrder(1L, "ORDER_1", new Supplier(1L));

        PurchaseOrderDto dto = mapper.toDto(order);

        PurchaseOrderDto expected = new PurchaseOrderDto(1L, "ORDER_1", new SupplierDto(1L));
        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
