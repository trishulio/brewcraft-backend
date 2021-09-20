package io.company.brewcraft.service.mapper;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;

public class ProcurementMapperTest {

    private ProcurementMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = ProcurementMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddDtoIsNull() {
        assertNull(this.mapper.fromDto((AddProcurementDto) null));
    }

    @Test
    public void testFromDto_ReturnsProcurementFromAddDto_WhenDtoIsNotNull() {
        final AddInvoiceDto invoiceDto = new AddInvoiceDto();
        invoiceDto.setDescription("desc_1");

        final AddPurchaseOrderDto poDto = new AddPurchaseOrderDto();
        poDto.setOrderNumber("ORDER_1");

        final AddProcurementDto dto = new AddProcurementDto(poDto, invoiceDto);

        final Procurement procurement = this.mapper.fromDto(dto);

        final Invoice expectedInvoice = new Invoice();
        expectedInvoice.setDescription("desc_1");

        final PurchaseOrder expectedPo = new PurchaseOrder();
        expectedPo.setOrderNumber("ORDER_1");

        final Procurement expected = new Procurement(expectedPo, expectedInvoice);

        assertEquals(expected, procurement);
    }

    @Test
    public void testToDto_ReturnsNull_WhenProcurementIsNotNull() {
        final Procurement procurement = new Procurement(
            new PurchaseOrder(1L),
            new Invoice(2L)
        );

        final ProcurementDto dto = this.mapper.toDto(procurement);

        final ProcurementDto expected = new ProcurementDto(new PurchaseOrderDto(1L), new InvoiceDto(2L));

        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenProcurementIsNull() {
        assertNull(this.mapper.toDto(null));
    }
}
