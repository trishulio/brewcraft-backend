package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
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
    public void testFromDto_ReturnsPurchaseOrder_WhenAddDtoIsNotNull() {
        AddPurchaseOrderDto dto = new AddPurchaseOrderDto("ORDER_1", 1L);

        PurchaseOrder po = mapper.fromDto(dto);

        PurchaseOrder expected = new PurchaseOrder(null, "ORDER_1", new Supplier(1L), null, null, null);

        assertEquals(expected, po);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddDtoIsNull() {
        assertNull(mapper.fromDto((AddPurchaseOrderDto) null));
    }

    @Test
    public void testFromDto_ReturnsPurchaseOrder_WhenUpdateDtoIsNotNull() {
        UpdatePurchaseOrderDto dto = new UpdatePurchaseOrderDto("ORDER_1", 2L, 3);

        PurchaseOrder po = mapper.fromDto(dto);

        PurchaseOrder expected = new PurchaseOrder(null, "ORDER_1", new Supplier(2L), null, null, 3);

        assertEquals(expected, po);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        PurchaseOrder order = new PurchaseOrder(1L, "ORDER_1", new Supplier(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);

        PurchaseOrderDto dto = mapper.toDto(order);

        PurchaseOrderDto expected = new PurchaseOrderDto(1L, "ORDER_1", new SupplierDto(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);
        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
