package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.procurement.ProcurementService;
import io.company.brewcraft.util.controller.AttributeFilter;

public class ProcurementControllerTest {

    private ProcurementController controller;
    private ProcurementService mService;

    @BeforeEach
    public void init() {
        this.mService = mock(ProcurementService.class);
        this.controller = new ProcurementController(new AttributeFilter(), this.mService);
    }

    @Test
    public void testAdd_ReturnsDtoFromServicePojo_WhenAddDtoIsNotNull() {
        doAnswer(inv -> inv.getArgument(0, Procurement.class)).when(this.mService).add(any(Procurement.class));

        final AddInvoiceDto invoiceAdditionDto = new AddInvoiceDto();
        invoiceAdditionDto.setPurchaseOrderId(2L);
        final AddPurchaseOrderDto poAdditionDto = new AddPurchaseOrderDto("ORDER_1", 1L);
        final AddProcurementDto addition = new AddProcurementDto(poAdditionDto, invoiceAdditionDto);

        final ProcurementDto dto = this.controller.add(addition);

        final InvoiceDto expectedInvoice = new InvoiceDto();
        expectedInvoice.setPurchaseOrder(new PurchaseOrderDto(2L));
        final PurchaseOrderDto expectedPo = new PurchaseOrderDto(null, "ORDER_1", new SupplierDto(1L), null, null, null);
        final ProcurementDto expected = new ProcurementDto(expectedPo, expectedInvoice);

        assertEquals(expected, dto);
    }
}
