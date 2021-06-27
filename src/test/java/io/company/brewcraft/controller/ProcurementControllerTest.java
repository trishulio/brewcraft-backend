package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

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
        mService = mock(ProcurementService.class);
        controller = new ProcurementController(new AttributeFilter(), mService);
    }

    @Test
    public void testAdd_ReturnsDtoFromServicePojo_WhenAddDtoIsNotNull() {
        doAnswer(inv -> inv.getArgument(0, Procurement.class)).when(mService).add(any(Procurement.class));

        AddInvoiceDto invoiceAdditionDto = new AddInvoiceDto();
        invoiceAdditionDto.setPurchaseOrderId(2L);
        AddPurchaseOrderDto poAdditionDto = new AddPurchaseOrderDto("ORDER_1", 1L);
        AddProcurementDto addition = new AddProcurementDto(poAdditionDto, invoiceAdditionDto);

        ProcurementDto dto = controller.add(addition);

        InvoiceDto expectedInvoice = new InvoiceDto();
        expectedInvoice.setPurchaseOrder(new PurchaseOrderDto(2L));
        expectedInvoice.setItems(List.of());
        PurchaseOrderDto expectedPo = new PurchaseOrderDto(null, "ORDER_1", new SupplierDto(1L));
        ProcurementDto expected = new ProcurementDto(expectedPo, expectedInvoice, null);

        assertEquals(expected, dto);
    }
}
