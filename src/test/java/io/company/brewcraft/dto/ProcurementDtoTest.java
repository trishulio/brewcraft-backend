package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;

public class ProcurementDtoTest {
    private ProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementDto();
    }

    @Test
    public void testNoArgConstructor_SetsInitialValues() {
        assertNull(dto.getId());
        assertNull(dto.getInvoiceNumber());
        assertNull(dto.getPurchaseOrder());
        assertNull(dto.getDescription());
        assertNull(dto.getFreight());
        assertNull(dto.getGeneratedOn());
        assertNull(dto.getReceivedOn());
        assertNull(dto.getPaymentDueDate());
        assertNull(dto.getDeliveryDueDate());
        assertNull(dto.getDeliveredDate());
        assertNull(dto.getInvoiceStatus());
        assertNull(dto.getShipmentStatus());
        assertNull(dto.getInvoiceVersion());
        assertNull(dto.getVersion());
        assertNull(dto.getProcurementItems());
    }

    @Test
    public void testAllArgConstructor_SetsAllValues() {
        dto = new ProcurementDto(
            new ProcurementIdDto(1L, 10L),
            "INVOICE_NUMBER",
            "SHIPMENT_NUMBER",
            new PurchaseOrderDto(1L, "ORDER_NUMBER", new SupplierDto(2L), LocalDateTime.of(2007, 12, 12, 0, 0), LocalDateTime.of(2008, 12, 12, 0, 0), 30),
            "DESCRIPTION",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new MoneyDto("CAD", new BigDecimal("50")), // amount
            new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
            LocalDateTime.of(2000, 12, 12, 0, 0), // generatedOn
            LocalDateTime.of(2001, 12, 12, 0, 0), // receivedOn
            LocalDateTime.of(2002, 12, 12, 0, 0), // paymentDueDate
            LocalDateTime.of(2003, 12, 12, 0, 0), // deliveryDueDate
            LocalDateTime.of(2004, 12, 12, 0, 0), // deliveredDate
            LocalDateTime.of(2005, 12, 12, 0, 0), // createdAt
            LocalDateTime.of(2006, 12, 12, 0, 0), // lastUpdated
            new InvoiceStatusDto(2L),
            new ShipmentStatusDto(3L),
            List.of(
                new ProcurementItemDto(
                    new ProcurementItemIdDto(10L, 20L),
                    "DESCRIPTION", // description
                    "LOT_NUMBER", // lotNumber
                    new QuantityDto("kg", new BigDecimal("10")), // quantity
                    new MoneyDto("CAD", new BigDecimal("20")), // price
                    new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                    new MoneyDto("CAD", new BigDecimal("50")), // amount
                    new MaterialDto(1L), // materialId
                    new StorageDto(2L), // storageId
                    LocalDateTime.of(2000, 12, 12, 0, 0), // createdAt
                    LocalDateTime.of(2000, 12, 12, 0, 0), // lastUpdated
                    100, // invoiceItemVersion
                    200 // version
                )
            ),
            1, // invoiceVersion
            2 // version
        );

        assertEquals(new ProcurementIdDto(1L, 10L), dto.getId());
        assertEquals("INVOICE_NUMBER", dto.getInvoiceNumber());
        assertEquals("SHIPMENT_NUMBER", dto.getShipmentNumber());
        assertEquals(new PurchaseOrderDto(1L, "ORDER_NUMBER", new SupplierDto(2L), LocalDateTime.of(2007, 12, 12, 0, 0), LocalDateTime.of(2008, 12, 12, 0, 0), 30), dto.getPurchaseOrder());
        assertEquals("DESCRIPTION", dto.getDescription());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), dto.getFreight());
        assertEquals(new MoneyDto("CAD", new BigDecimal("50")), dto.getAmount());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), dto.getTax());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getGeneratedOn());
        assertEquals(LocalDateTime.of(2001, 12, 12, 0, 0), dto.getReceivedOn());
        assertEquals(LocalDateTime.of(2002, 12, 12, 0, 0), dto.getPaymentDueDate());
        assertEquals(LocalDateTime.of(2003, 12, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(LocalDateTime.of(2005, 12, 12, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2006, 12, 12, 0, 0), dto.getLastUpdated());
        assertEquals(new InvoiceStatusDto(2L), dto.getInvoiceStatus());
        assertEquals(new ShipmentStatusDto(3L), dto.getShipmentStatus());
        assertEquals(1, dto.getInvoiceVersion());
        assertEquals(2, dto.getVersion());
        assertEquals(List.of(
            new ProcurementItemDto(
                new ProcurementItemIdDto(10L, 20L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                new MoneyDto("CAD", new BigDecimal("50")), // amount
                new MaterialDto(1L), // materialId
                new StorageDto(2L), // storageId
                LocalDateTime.of(2000, 12, 12, 0, 0), // createdAt
                LocalDateTime.of(2000, 12, 12, 0, 0), // lastUpdated
                100, // invoiceItemVersion
                200 // version
            )
        ), dto.getProcurementItems());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementIdDto(10L, 10L));
        assertEquals(new ProcurementIdDto(10L, 10L), dto.getId());
    }

    @Test
    public void testAccessInvoiceNumber() {
        dto.setInvoiceNumber("INVOICE_NUMBER_1");
        assertEquals("INVOICE_NUMBER_1", dto.getInvoiceNumber());
    }

    @Test
    public void testAccessShipmentNumber() {
        dto.setShipmentNumber("SHIPMENT_NUMBER_1");
        assertEquals("SHIPMENT_NUMBER_1", dto.getShipmentNumber());
    }

    @Test
    public void testAccessPurchaseOrder() {
        dto.setPurchaseOrder(new PurchaseOrderDto(1L, "ORDER_NUMBER", new SupplierDto(2L), LocalDateTime.of(2007, 12, 12, 0, 0), LocalDateTime.of(2008, 12, 12, 0, 0), 30));
        assertEquals(new PurchaseOrderDto(1L, "ORDER_NUMBER", new SupplierDto(2L), LocalDateTime.of(2007, 12, 12, 0, 0), LocalDateTime.of(2008, 12, 12, 0, 0), 30), dto.getPurchaseOrder());
    }

    @Test
    public void testAccessDescription() {
        dto.setDescription("DESCRIPTION_1");
        assertEquals("DESCRIPTION_1", dto.getDescription());
    }

    @Test
    public void testAccessFreight() {
        dto.setFreight(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))));
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), dto.getFreight());
    }


    @Test
    public void testAccessTax() {
        dto.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), dto.getTax());
    }


    @Test
    public void testAccessAmount() {
        dto.setAmount(new MoneyDto("CAD", new BigDecimal("50")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("50")), dto.getAmount());
    }
    @Test
    public void testAccessGeneratedOn() {
        dto.setGeneratedOn(LocalDateTime.of(2000, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getGeneratedOn());
    }

    @Test
    public void testAccessReceivedOn() {
        dto.setReceivedOn(LocalDateTime.of(2001, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2001, 12, 12, 0, 0), dto.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        dto.setPaymentDueDate(LocalDateTime.of(2002, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2002, 12, 12, 0, 0), dto.getPaymentDueDate());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        dto.setDeliveryDueDate(LocalDateTime.of(2003, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2003, 12, 12, 0, 0), dto.getDeliveryDueDate());
    }

    @Test
    public void testAccessDeliveredDate() {
        dto.setDeliveredDate(LocalDateTime.of(2004, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getDeliveredDate());
    }

    @Test
    public void testAccessCreatedAt() {
        dto.setCreatedAt(LocalDateTime.of(2004, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        dto.setLastUpdated(LocalDateTime.of(2004, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getLastUpdated());
    }

    @Test
    public void testAccessInvoiceStatus() {
        dto.setInvoiceStatus(new InvoiceStatusDto(1L));
        assertEquals(new InvoiceStatusDto(1L), dto.getInvoiceStatus());
    }

    @Test
    public void testAccessShipmentStatus() {
        dto.setShipmentStatus(new ShipmentStatusDto(2L));
        assertEquals(new ShipmentStatusDto(2L), dto.getShipmentStatus());
    }

    @Test
    public void testAccessInvoiceVersion() {
        dto.setInvoiceVersion(1);
        assertEquals(1, dto.getInvoiceVersion());
    }

    @Test
    public void testAccessVersion() {
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessProcurementItems() {
        dto.setProcurementItems(List.of(
            new ProcurementItemDto(
                new ProcurementItemIdDto(1L, 2L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                new MoneyDto("CAD", new BigDecimal("50")), // amount
                new MaterialDto(1L), // materialId
                new StorageDto(2L), // storageId
                LocalDateTime.of(2000, 12, 12, 0, 0), // createdAt
                LocalDateTime.of(2000, 12, 12, 0, 0), // lastUpdated
                100, // invoiceItemVersion
                200 // version
            )
        ));
        assertEquals(List.of(
            new ProcurementItemDto(
                new ProcurementItemIdDto(1L, 2L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                new MoneyDto("CAD", new BigDecimal("50")), // amount
                new MaterialDto(1L), // materialId
                new StorageDto(2L), // storageId
                LocalDateTime.of(2000, 12, 12, 0, 0), // createdAt
                LocalDateTime.of(2000, 12, 12, 0, 0), // lastUpdated
                100, // invoiceItemVersion
                200 // version
            )
        ), dto.getProcurementItems());
    }
}
