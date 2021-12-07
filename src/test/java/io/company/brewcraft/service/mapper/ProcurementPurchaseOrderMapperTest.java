package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.mapper.procurement.ProcurementPurchaseOrderMapper;

public class ProcurementPurchaseOrderMapperTest {

    private ProcurementPurchaseOrderMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementPurchaseOrderMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        PurchaseOrder order = new PurchaseOrder(1L, "ORDER_1", new Supplier(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);

        ProcurementPurchaseOrderDto dto = mapper.toDto(order);

        ProcurementPurchaseOrderDto expected = new ProcurementPurchaseOrderDto(1L, "ORDER_1", new SupplierDto(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);
        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
