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
        this.mInvoiceService = mock(InvoiceService.class);
        this.mPoService = mock(PurchaseOrderService.class);
        this.mShipmentService = mock(ShipmentService.class);
        this.service = new ProcurementServiceImpl(this.mInvoiceService, this.mPoService, this.mShipmentService);
    }

    @Test
    public void testAdd_ThrowsIllegalArgumentException_WhenMultiplePurchaseOrdersArePassedIn() {
        final Invoice additionInvoice = new Invoice();
        additionInvoice.setPurchaseOrder(new PurchaseOrder(1L));

        final PurchaseOrder additionPo = new PurchaseOrder(2L);

        final Procurement addition = new Procurement(additionPo, additionInvoice, null);

        assertThrows(IllegalArgumentException.class, () -> this.service.add(addition), "PurchaseOrder specified in invoice object and also provided as a separate payload to add.");
    }

    @Test
    public void testAdd_ReturnsNewlyCreatedPoInvoiceShipment_WhenInputIsNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mInvoiceService).add(anyList());
        doAnswer(inv -> inv.getArgument(0)).when(this.mPoService).add(anyList());
        doAnswer(inv -> inv.getArgument(0)).when(this.mShipmentService).add(anyList());

        final PurchaseOrder additionPo = new PurchaseOrder();
        additionPo.setOrderNumber("ORDER_1");

        final InvoiceItem additionItem = new InvoiceItem();
        additionItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        final Invoice additionInvoice = new Invoice();
        additionInvoice.setInvoiceNumber("INVOICE_1");
        additionInvoice.addItem(additionItem);

        final Procurement addition = new Procurement(additionPo, additionInvoice, null);

        final Procurement procurement = this.service.add(addition);

        final PurchaseOrder expectedPo = new PurchaseOrder();
        expectedPo.setOrderNumber("ORDER_1");

        final Invoice expectedInvoice = new Invoice();
        expectedInvoice.setInvoiceNumber("INVOICE_1");
        final InvoiceItem expectedItem = new InvoiceItem();
        expectedItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedInvoice.addItem(expectedItem);

        expectedInvoice.setPurchaseOrder(expectedPo);

        final Shipment expectedShipment = new Shipment();
        expectedShipment.setShipmentNumber("INVOICE_1");
        final MaterialLot expectedLot = new MaterialLot();
        expectedLot.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedLot.setInvoiceItem(expectedItem);
        expectedShipment.addLot(expectedLot);

        final Procurement expected = new Procurement(expectedPo, expectedInvoice, expectedShipment);

        assertEquals(expected, procurement);
    }

    @Test
    public void testAdd_SkipsAddingPurchaseOrder_WhenNullPurchaseOrderIsPassed() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mInvoiceService).add(anyList());
        doAnswer(inv -> inv.getArgument(0, PurchaseOrder.class)).when(this.mPoService).add(anyList());
        doAnswer(inv -> inv.getArgument(0)).when(this.mShipmentService).add(anyList());

        final InvoiceItem additionItem = new InvoiceItem();
        additionItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        final Invoice additionInvoice = new Invoice();
        additionInvoice.setInvoiceNumber("INVOICE_1");
        additionInvoice.addItem(additionItem);
        additionInvoice.setPurchaseOrder(new PurchaseOrder(1L));

        final Procurement addition = new Procurement(null, additionInvoice, null);

        final Procurement procurement = this.service.add(addition);

        final PurchaseOrder expectedPo = new PurchaseOrder(1L);

        final Invoice expectedInvoice = new Invoice();
        expectedInvoice.setInvoiceNumber("INVOICE_1");
        final InvoiceItem expectedItem = new InvoiceItem();
        expectedItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedInvoice.addItem(expectedItem);

        expectedInvoice.setPurchaseOrder(expectedPo);

        final Shipment expectedShipment = new Shipment();
        expectedShipment.setShipmentNumber("INVOICE_1");
        final MaterialLot expectedLot = new MaterialLot();
        expectedLot.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        expectedLot.setInvoiceItem(expectedItem);
        expectedShipment.addLot(expectedLot);

        final Procurement expected = new Procurement(expectedPo, expectedInvoice, expectedShipment);

        assertEquals(expected, procurement);
    }
}
