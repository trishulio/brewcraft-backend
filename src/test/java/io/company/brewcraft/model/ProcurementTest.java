package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementTest {

    private Procurement procurement;

    @BeforeEach
    public void init() {
        procurement = new Procurement();
    }


    @Test
    public void testAllArgConstructor_SetsAllFieldsUsingSetters() {
        procurement = new Procurement(
            new ProcurementId(1L, 2L),
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(3L),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(new ProcurementItemId(10L, 20L))),
            100, // invoiceVersion
            1 // version
        );

        assertEquals(new ProcurementId(1L, 2L), procurement.getId());
        assertEquals("INVOICE_NO_1", procurement.getInvoiceNumber());
        assertEquals("SHIPMENT_NO_1", procurement.getShipmentNumber());
        assertEquals("DESCRIPTION", procurement.getDescription());
        assertEquals(new PurchaseOrder(3L), procurement.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), procurement.getGeneratedOn());
        assertEquals(LocalDateTime.of(1999, 2, 1, 0, 0), procurement.getReceivedOn());
        assertEquals(LocalDateTime.of(1999, 3, 1, 0, 0), procurement.getPaymentDueDate());
        assertEquals(LocalDateTime.of(1999, 4, 1, 0, 0), procurement.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(1999, 5, 1, 0, 0), procurement.getDeliveredDate());
        assertEquals(new Freight(), procurement.getFreight());
        assertEquals(LocalDateTime.of(1999, 6, 1, 0, 0), procurement.getCreatedAt());
        assertEquals(LocalDateTime.of(1999, 7, 1, 0, 0), procurement.getLastUpdated());
        assertEquals( new InvoiceStatus(4L), procurement.getInvoiceStatus());
        assertEquals(new ShipmentStatus(5L), procurement.getShipmentStatus());
        assertEquals(List.of(new ProcurementItem(new ProcurementItemId(10L, 20L))), procurement.getProcurementItems());
        assertEquals(100, procurement.getInvoiceVersion());
        assertEquals(1, procurement.getVersion());
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        procurement = new Procurement(new ProcurementId(1L, 2L));
        assertEquals(new ProcurementId(1L, 2L), procurement.getId());
    }

    @Test
    public void testShipmentConstructor_SetsShipmentValuesOnly_WhenInvoiceItemIsNull() {
        Shipment shipment = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(
                new MaterialLot(
                    1L,
                    "LOT_1",
                    Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
                    null,
                    new Storage(300L),
                    LocalDateTime.of(1999, 1, 1, 0, 0),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    1
                )
            ),
            1
        );

        procurement = new Procurement(shipment);
        ProcurementItem procurementItem = new ProcurementItem();
        procurementItem.setId(new ProcurementItemId(1L, null));
        procurementItem.setLotNumber("LOT_1");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        procurementItem.setStorage(new Storage(300L));
        procurementItem.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        procurementItem.setVersion(1);

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getShipmentId());
        assertEquals("SHIPMENT_1", procurement.getShipmentNumber());
        assertEquals("DESCRIPTION_1", procurement.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), procurement.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), procurement.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), procurement.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), procurement.getLastUpdated());
        assertEquals(new ShipmentStatus(99L), procurement.getShipmentStatus());
        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1, procurement.getVersion());
    }

    @Test
    public void testShipmentConstructor_SetsShipmentValuesOnly_WhenInvoiceIsNull() {
        Shipment shipment = new Shipment(1L);
        MaterialLot lot = new MaterialLot(10L);
        lot.setInvoiceItem(new InvoiceItem(20L));
        shipment.setLots(List.of(lot));

        procurement = new Procurement(shipment);
        ProcurementItem procurementItem = new ProcurementItem(new ProcurementItemId(10L, null));

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getShipmentId());
    }

    @Test
    public void testShipmentConstructor_SetsInvoiceValues_WhenInvoiceIsNotNull() {
        InvoiceItem invoiceItem = new InvoiceItem(
            2L,
            "DESCRIPTION_ITEM",
            Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            1
        );
        new Invoice(
            1L,
            "INVOICE_1",
            "DESCRIPTION",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(99L),
            List.of(invoiceItem),
            1
        );

        MaterialLot lot = new MaterialLot(10L);
        lot.setInvoiceItem(invoiceItem);
        Shipment shipment = new Shipment(1L);
        shipment.setLots(List.of(lot));

        procurement = new Procurement(shipment);

        ProcurementItem procurementItem = new ProcurementItem();
        procurementItem.setId(new ProcurementItemId(10L, 2L));
        procurementItem.setDescription("DESCRIPTION_ITEM");
        procurementItem.setPrice(Money.of(CurrencyUnit.CAD, new BigDecimal("5")));
        procurementItem.setTax(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))));
        procurementItem.setMaterial(new Material(7L));
        procurementItem.setInvoiceItemVersion(1);

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getInvoiceId());
        assertEquals("INVOICE_1", procurement.getInvoiceNumber());
        assertEquals(new PurchaseOrder(1L), procurement.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), procurement.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), procurement.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), procurement.getPaymentDueDate());
        assertEquals(new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))), procurement.getFreight());
        assertEquals( new InvoiceStatus(99L), procurement.getInvoiceStatus());
        assertEquals(1, procurement.getInvoiceVersion());
    }

    @Test
    public void testShipmentConstructor_SetsShipmentAndInvoiceValues() {
        Shipment shipment = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(
                new MaterialLot(
                    1L,
                    "LOT_1",
                    Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
                    new InvoiceItem(1L),
                    new Storage(300L),
                    LocalDateTime.of(1999, 1, 1, 0, 0),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    1
                )
            ),
            1
        );

        procurement = new Procurement(shipment);
        ProcurementItem procurementItem = new ProcurementItem();
        procurementItem.setId(new ProcurementItemId(1L, null));
        procurementItem.setLotNumber("LOT_1");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        procurementItem.setStorage(new Storage(300L));
        procurementItem.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        procurementItem.setVersion(1);

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getShipmentId());
        assertEquals("SHIPMENT_1", procurement.getShipmentNumber());
        assertEquals("DESCRIPTION_1", procurement.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), procurement.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), procurement.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), procurement.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), procurement.getLastUpdated());
        assertEquals(new ShipmentStatus(99L), procurement.getShipmentStatus());
        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1, procurement.getVersion());
    }

    @Test
    public void testNoArgConstructor_SetsNullValues() {
        assertNull(procurement.getId());
        assertNull(procurement.getInvoiceNumber());
        assertNull(procurement.getShipmentNumber());
        assertNull(procurement.getDescription());
        assertNull(procurement.getPurchaseOrder());
        assertNull(procurement.getGeneratedOn());
        assertNull(procurement.getReceivedOn());
        assertNull(procurement.getPaymentDueDate());
        assertNull(procurement.getDeliveryDueDate());
        assertNull(procurement.getDeliveredDate());
        assertNull(procurement.getFreight());
        assertNull(procurement.getCreatedAt());
        assertNull(procurement.getLastUpdated());
        assertNull(procurement.getInvoiceStatus());
        assertNull(procurement.getShipmentStatus());
        assertNull(procurement.getProcurementItems());
        assertNull(procurement.getInvoiceVersion());
        assertNull(procurement.getVersion());
    }

    @Test
    public void testAccessId() {
        procurement.setId(new ProcurementId(1L, 2L));
        assertEquals(new ProcurementId(1L, 2L), procurement.getId());
    }

    @Test
    public void testAccessInvoiceNumber() {
        procurement.setInvoiceNumber("INVOICE_1");
        assertEquals("INVOICE_1", procurement.getInvoiceNumber());
    }

    @Test
    public void testAccessDescription() {
        procurement.setDescription("DESCRIPTION");
        assertEquals("DESCRIPTION", procurement.getDescription());
    }

    @Test
    public void testAccessGeneratedOn() {
        procurement.setGeneratedOn(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getGeneratedOn());
    }

    @Test
    public void testAccessReceivedOn() {
        procurement.setReceivedOn(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        procurement.setPaymentDueDate(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getPaymentDueDate());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        procurement.setDeliveryDueDate(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getDeliveryDueDate());
    }

    @Test
    public void testAccessDeliveredDate() {
        procurement.setDeliveredDate(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getDeliveredDate());
    }

    @Test
    public void testAccessFreight() {
        procurement.setFreight(new Freight(Money.parse("CAD 10")));
        assertEquals(new Freight(Money.parse("CAD 10")), procurement.getFreight());
    }

    @Test
    public void testAccessInvoiceStatus() {
        procurement.setInvoiceStatus(new InvoiceStatus(1L));
        assertEquals(new InvoiceStatus(1L), procurement.getInvoiceStatus());
    }

    @Test
    public void testAccessPurchaseOrder() {
        procurement.setPurchaseOrder(new PurchaseOrder(1L));
        assertEquals(new PurchaseOrder(1L), procurement.getPurchaseOrder());
    }

    @Test
    public void testAccessShipmentNumber() {
        procurement.setShipmentNumber("SHIPMENT_1");
        assertEquals("SHIPMENT_1", procurement.getShipmentNumber());
    }

    @Test
    public void testAccessShipmentStatus() {
        procurement.setShipmentStatus(new ShipmentStatus(1L));
        assertEquals(new ShipmentStatus(1L), procurement.getShipmentStatus());
    }

    @Test
    public void testAccessCreatedAt() {
        procurement.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        procurement.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurement.getLastUpdated());
    }

    @Test
    public void testAccessProcurementItems() {
        procurement.setProcurementItems(List.of(new ProcurementItem()));
        assertEquals(List.of(new ProcurementItem()), procurement.getProcurementItems());
    }

    @Test
    public void testAccessAmount() {
        ProcurementItem item = new ProcurementItem();
        item.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        item.setPrice(Money.parse("CAD 10"));
        procurement.setProcurementItems(List.of(item));
        assertEquals(Money.parse("CAD 100"), procurement.getAmount());
    }

    @Test
    public void testAccessTax() {
        ProcurementItem item = new ProcurementItem();
        item.setTax(new Tax(Money.parse("CAD 10")));
        procurement.setProcurementItems(List.of(item));

        assertEquals(new Tax(Money.parse("CAD 10")), procurement.getTax());
    }

    @Test
    public void testAccessVersion() {
        procurement.setVersion(1);
        assertEquals(1, procurement.getVersion());
    }

    @Test
    public void testAccessInvoiceVersion() {
        procurement.setInvoiceVersion(1);
        assertEquals(1, procurement.getInvoiceVersion());
    }

    @Test
    public void testBuildInvoice_ReturnsAnInvoiceBuiltFromProcurementProperties() {
        ProcurementItem item = new ProcurementItem();
        item.setId(new ProcurementItemId(null, 2L));
        item.setDescription("DESCRIPTION_ITEM");
        item.setQuantity(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM));
        item.setPrice(Money.of(CurrencyUnit.CAD, new BigDecimal("5")));
        item.setMaterial(new Material(7L));
        item.setTax(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))));
        item.setCreatedAt(LocalDateTime.of(1999, 1, 1, 1, 1));
        item.setLastUpdated(LocalDateTime.of(1999, 1, 1, 1, 1));
        item.setInvoiceItemVersion(1);

        procurement.setId(new ProcurementId(null, 1L));
        procurement.setInvoiceNumber("INVOICE_1");
        procurement.setDescription("DESCRIPTION");
        procurement.setPurchaseOrder(new PurchaseOrder(1L));
        procurement.setGeneratedOn(LocalDateTime.of(1999, 1, 1, 12, 0));
        procurement.setReceivedOn(LocalDateTime.of(2000, 1, 1, 12, 0));
        procurement.setPaymentDueDate(LocalDateTime.of(2001, 1, 1, 12, 0));
        procurement.setFreight(new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))));
        procurement.setCreatedAt(LocalDateTime.of(2002, 1, 1, 12, 0));
        procurement.setLastUpdated(LocalDateTime.of(2003, 1, 1, 12, 0));
        procurement.setInvoiceStatus(new InvoiceStatus(99L));
        procurement.setInvoiceVersion(1);
        procurement.setProcurementItems(List.of(item));

        Invoice invoice = procurement.buildInvoice();

        Invoice expected = new Invoice(
            1L,
            "INVOICE_1",
            "DESCRIPTION",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(99L),
            List.of(
                new InvoiceItem(
                    2L,
                    "DESCRIPTION_ITEM",
                    Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
                    Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
                    new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
                    new Material(7L),
                    LocalDateTime.of(1999, 1, 1, 1, 1),
                    LocalDateTime.of(1999, 1, 1, 1, 1),
                    1
                )
            ),
            1
        );

        assertEquals(expected, invoice);
    }

    @Test
    public void testBuildShipment_ReturnsAShipmentBuiltFromProcurementProperties() {
        ProcurementItem item = new ProcurementItem();
        item.setId(new ProcurementItemId(1L, null));
        item.setLotNumber("LOT_1");
        item.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        item.setStorage(new Storage(300L));
        item.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        item.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        item.setVersion(1);

        procurement.setId(new ProcurementId(1L, null));
        procurement.setShipmentNumber("SHIPMENT_1");
        procurement.setDescription("DESCRIPTION_1");
        procurement.setShipmentStatus(new ShipmentStatus(99L));
        procurement.setDeliveryDueDate(LocalDateTime.of(1999, 1, 1, 12, 0));
        procurement.setDeliveredDate(LocalDateTime.of(2000, 1, 1, 12, 0));
        procurement.setCreatedAt(LocalDateTime.of(2001, 1, 1, 12, 0));
        procurement.setLastUpdated(LocalDateTime.of(2002, 1, 1, 12, 0));
        procurement.setVersion(1);
        procurement.setProcurementItems(List.of(item));

        Shipment shipment = procurement.buildShipmentWithoutInvoice();

        Shipment expected = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(
                new MaterialLot(
                    1L,
                    "LOT_1",
                    Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                    null,
                    new Storage(300L),
                    LocalDateTime.of(1999, 1, 1, 0, 0),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    1
                )
            ),
            1
        );

        assertEquals(expected, shipment);
    }

    @Test
    public void testUpdateShipment_ResetsShipmentFields_WhenShipmentIsNull() {
        procurement.updateProperties((Shipment) null);

        assertNull(procurement.getId());
        assertNull(procurement.getShipmentNumber());
        assertNull(procurement.getDescription());
        assertNull(procurement.getDeliveryDueDate());
        assertNull(procurement.getDeliveredDate());
        assertNull(procurement.getCreatedAt());
        assertNull(procurement.getLastUpdated());
        assertNull(procurement.getShipmentStatus());
        assertNull(procurement.getProcurementItems());
        assertNull(procurement.getVersion());
    }

    @Test
    public void testUpdateShipment_SetsShipmentValues_WhenShipmentIsNotNull() {
        Shipment shipment = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            List.of(
                new MaterialLot(
                    1L,
                    "LOT_1",
                    Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
                    null,
                    new Storage(300L),
                    LocalDateTime.of(1999, 1, 1, 0, 0),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    1
                )
            ),
            1
        );

        procurement.updateProperties(shipment);

        ProcurementItem procurementItem = new ProcurementItem();
        procurementItem.setId(new ProcurementItemId(1L, null));
        procurementItem.setLotNumber("LOT_1");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        procurementItem.setStorage(new Storage(300L));
        procurementItem.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        procurementItem.setVersion(1);

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getShipmentId());
        assertEquals("SHIPMENT_1", procurement.getShipmentNumber());
        assertEquals("DESCRIPTION_1", procurement.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), procurement.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), procurement.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), procurement.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), procurement.getLastUpdated());
        assertEquals(new ShipmentStatus(99L), procurement.getShipmentStatus());
        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1, procurement.getVersion());
    }

    @Test
    public void testUpdateInvoice_ResetsInvoiceFields_WhenInvoiceIsNull() {
        procurement.updateProperties((Invoice) null);

        assertNull(procurement.getInvoiceNumber());
        assertNull(procurement.getDescription());
        assertNull(procurement.getPurchaseOrder());
        assertNull(procurement.getGeneratedOn());
        assertNull(procurement.getReceivedOn());
        assertNull(procurement.getPaymentDueDate());
        assertNull(procurement.getFreight());
        assertNull(procurement.getCreatedAt());
        assertNull(procurement.getLastUpdated());
        assertNull(procurement.getInvoiceStatus());
        assertNull(procurement.getProcurementItems());
        assertNull(procurement.getInvoiceVersion());
    }

    @Test
    public void testUpdateInvoice_SetsAllInvoiceProperties_WhenInvoiceIsNotNull() {
        Invoice invoice = new Invoice(
            1L,
            "INVOICE_1",
            "DESCRIPTION",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(99L),
            List.of(
                new InvoiceItem(
                    2L,
                    "DESCRIPTION_ITEM",
                    Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
                    Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
                    new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
                    new Material(7L),
                    LocalDateTime.of(2000, 1, 1, 1, 1),
                    LocalDateTime.of(1999, 1, 1, 1, 1),
                    1
                )
            ),
            1
        );

        procurement.updateProperties(invoice);

        ProcurementItem procurementItem = new ProcurementItem();
        procurementItem.setId(new ProcurementItemId(null, 2L));
        procurementItem.setDescription("DESCRIPTION_ITEM");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM));
        procurementItem.setPrice(Money.of(CurrencyUnit.CAD, new BigDecimal("5")));
        procurementItem.setTax(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))));
        procurementItem.setMaterial(new Material(7L));
        procurementItem.setCreatedAt(LocalDateTime.of(2000, 1, 1, 1, 1));
        procurementItem.setLastUpdated(LocalDateTime.of(1999, 1, 1, 1, 1));
        procurementItem.setInvoiceItemVersion(1);

        assertEquals(List.of(procurementItem), procurement.getProcurementItems());
        assertEquals(1L, procurement.getId().getInvoiceId());
        assertEquals("INVOICE_1", procurement.getInvoiceNumber());
        assertEquals("DESCRIPTION", procurement.getDescription());
        assertEquals(new PurchaseOrder(1L), procurement.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), procurement.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), procurement.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), procurement.getPaymentDueDate());
        assertEquals(new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))), procurement.getFreight());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), procurement.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), procurement.getLastUpdated());
        assertEquals( new InvoiceStatus(99L), procurement.getInvoiceStatus());
        assertEquals(1, procurement.getInvoiceVersion());
    }

    @Test
    public void testGetItemCount_ReturnsInvoiceItemCount_WhenInvoiceItemCountIsMax() {
        Invoice invoice = new Invoice();
        invoice.setItems(List.of(new InvoiceItem(1L), new InvoiceItem(2L)));
        Shipment shipment = new Shipment();
        shipment.setLots(List.of(new MaterialLot(1L)));

        Procurement procurement = new Procurement(shipment, invoice);

        assertEquals(2, procurement.getItemCount());
    }

    @Test
    public void testGetItemCount_ReturnsShipmentItemCount_WhenShipmentItemCountIsMax() {
        Invoice invoice = new Invoice();
        invoice.setItems(List.of(new InvoiceItem(1L)));
        Shipment shipment = new Shipment();
        shipment.setLots(List.of(new MaterialLot(1L), new MaterialLot(2L)));

        Procurement procurement = new Procurement(shipment, invoice);

        assertEquals(2, procurement.getItemCount());
    }
}