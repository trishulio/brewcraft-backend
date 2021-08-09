package io.company.brewcraft.service.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;

public class ProcurementMapperTest {

    private ProcurementMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddDtoIsNull() {
        assertNull(mapper.fromDto((AddProcurementDto) null));
    }

    @Test
    public void testFromDto_ReturnsProcurementFromAddDto_WhenDtoIsNotNull() {
        AddInvoiceDto invoiceDto = new AddInvoiceDto();
        invoiceDto.setDescription("desc_1");

        AddPurchaseOrderDto poDto = new AddPurchaseOrderDto();
        poDto.setOrderNumber("ORDER_1");

        AddProcurementDto dto = new AddProcurementDto(poDto, invoiceDto);

        Procurement procurement = mapper.fromDto(dto);

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setDescription("desc_1");
        expectedInvoice.setItems(List.of());

        PurchaseOrder expectedPo = new PurchaseOrder();
        expectedPo.setOrderNumber("ORDER_1");

        Procurement expected = new Procurement(expectedPo, expectedInvoice, null);

        assertEquals(expected, procurement);
    }

    @Test
    public void testToDto_ReturnsNull_WhenProcurementIsNotNull() {
        Procurement procurement = new Procurement(
            new PurchaseOrder(1L),
            new Invoice(2L),
            new Shipment(3L)
        );

        ProcurementDto dto = mapper.toDto(procurement);

        ProcurementDto expected = new ProcurementDto(new PurchaseOrderDto(1L), new InvoiceDto(2L), new ShipmentDto(3L));

        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenProcurementIsNull() {
        assertNull(mapper.toDto(null));
    }
}
