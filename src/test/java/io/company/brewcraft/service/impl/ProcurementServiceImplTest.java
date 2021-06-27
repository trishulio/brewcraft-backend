package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.impl.procurement.ProcurementServiceImpl;
import io.company.brewcraft.service.procurement.ProcurementService;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementServiceImplTest {
    private ProcurementService service;

    private InvoiceService mInvoiceService;
    private PurchaseOrderService mPoService;
    private ShipmentService mShipmentService;

    @BeforeEach
    public void init() {
        mInvoiceService = mock(InvoiceService.class);
        mPoService = mock(PurchaseOrderService.class);
        mShipmentService = mock(ShipmentService.class);
        service = new ProcurementServiceImpl(mInvoiceService, mPoService, mShipmentService);
    }

    @Test
    public void testAdd_ThrowsIllegalArgumentException_WhenMultiplePurchaseOrdersArePassedIn() {
        Invoice additionInvoice = new Invoice();
        additionInvoice.setPurchaseOrder(new PurchaseOrder(1L));

        PurchaseOrder additionPo = new PurchaseOrder(2L);

        Procurement addition = new Procurement(additionPo, additionInvoice, null);

        assertThrows(IllegalArgumentException.class, () -> service.add(addition), "PurchaseOrder specified in invoice object and also provided as a separate payload to add.");
    }

    @Test
    public void testAdd_ReturnsNewlyCreatedPoInvoiceShipment_WhenInputIsNotNull() {
        doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mInvoiceService).add(any(Invoice.class));
        doAnswer(inv -> inv.getArgument(0, PurchaseOrder.class)).when(mPoService).add(any(PurchaseOrder.class));
        doAnswer(inv -> inv.getArgument(0, Shipment.class)).when(mShipmentService).add(any(Shipment.class));

        PurchaseOrder additionPo = new PurchaseOrder();
        additionPo.setOrderNumber("ORDER_1");

        InvoiceItem additionItem = new InvoiceItem();
        additionItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        Invoice additionInvoice = new Invoice();
        additionInvoice.setInvoiceNumber("INVOICE_1");
        additionInvoice.addItem(additionItem);

        Procurement addition = new Procurement(additionPo, additionInvoice, null);

        Procurement procurement = service.add(addition);

        PurchaseOrder expectedPo = new PurchaseOrder();
        expectedPo.setOrderNumber("ORDER_1");

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setInvoiceNumber("INVOICE_1");
        InvoiceItem expectedItem = new InvoiceItem();
        expectedItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedInvoice.addItem(expectedItem);

        expectedInvoice.setPurchaseOrder(expectedPo);

        Shipment expectedShipment = new Shipment();
        expectedShipment.setShipmentNumber("INVOICE_1");
        MaterialLot expectedLot = new MaterialLot();
        expectedLot.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedLot.setInvoiceItem(expectedItem);
        expectedShipment.addLot(expectedLot);

        Procurement expected = new Procurement(expectedPo, expectedInvoice, expectedShipment);

        assertEquals(expected, procurement);
    }

    @Test
    public void testAdd_SkipsAddingPurchaseOrder_WhenNullPurchaseOrderIsPassed() {
        doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mInvoiceService).add(any(Invoice.class));
        doAnswer(inv -> inv.getArgument(0, PurchaseOrder.class)).when(mPoService).add(any(PurchaseOrder.class));
        doAnswer(inv -> inv.getArgument(0, Shipment.class)).when(mShipmentService).add(any(Shipment.class));

        InvoiceItem additionItem = new InvoiceItem();
        additionItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        Invoice additionInvoice = new Invoice();
        additionInvoice.setInvoiceNumber("INVOICE_1");
        additionInvoice.addItem(additionItem);
        additionInvoice.setPurchaseOrder(new PurchaseOrder(1L));

        Procurement addition = new Procurement(null, additionInvoice, null);

        Procurement procurement = service.add(addition);

        PurchaseOrder expectedPo = new PurchaseOrder(1L);

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setInvoiceNumber("INVOICE_1");
        InvoiceItem expectedItem = new InvoiceItem();
        expectedItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedInvoice.addItem(expectedItem);

        expectedInvoice.setPurchaseOrder(expectedPo);

        Shipment expectedShipment = new Shipment();
        expectedShipment.setShipmentNumber("INVOICE_1");
        MaterialLot expectedLot = new MaterialLot();
        expectedLot.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedLot.setInvoiceItem(expectedItem);
        expectedShipment.addLot(expectedLot);

        Procurement expected = new Procurement(expectedPo, expectedInvoice, expectedShipment);

        assertEquals(expected, procurement);
    }
}
