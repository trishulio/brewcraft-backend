package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;

public class UpdateProcurementDtoTest {
    private UpdateProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateProcurementDto();
    }

    @Test
    public void testNoArgConstructor_SetsInitialValues() {
        assertNull(dto.getInvoiceNumber());
        assertNull(dto.getPurchaseOrder());
        assertNull(dto.getDescription());
        assertNull(dto.getFreight());
        assertNull(dto.getGeneratedOn());
        assertNull(dto.getReceivedOn());
        assertNull(dto.getPaymentDueDate());
        assertNull(dto.getDeliveryDueDate());
        assertNull(dto.getDeliveredDate());
        assertNull(dto.getInvoiceStatusId());
        assertNull(dto.getShipmentStatusId());
        assertNull(dto.getInvoiceVersion());
        assertNull(dto.getVersion());
        assertNull(dto.getProcurementItems());
    }

    @Test
    public void testAllArgConstructor_SetsAllValues() {
        dto = new UpdateProcurementDto(
            new ProcurementIdDto(1L, 1L),
            "INVOICE_NUMBER",
            "SHIPMENT_NUMBER",
            new UpdatePurchaseOrderDto(1L, "ORDER_NUMBER", 2L, 30),
            "DESCRIPTION",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            LocalDateTime.of(2000, 12, 12, 0, 0), // generatedOn
            LocalDateTime.of(2001, 12, 12, 0, 0), // receivedOn
            LocalDateTime.of(2002, 12, 12, 0, 0), // paymentDueDate
            LocalDateTime.of(2003, 12, 12, 0, 0), // deliveryDueDate
            LocalDateTime.of(2004, 12, 12, 0, 0), // deliveredDate
            2L,
            3L,
            List.of(
                new UpdateProcurementItemDto(
                    new ProcurementItemIdDto(10L, 20L),
                    "DESCRIPTION", // description
                    "LOT_NUMBER", // lotNumber
                    new QuantityDto("kg", new BigDecimal("10")), // quantity
                    new MoneyDto("CAD", new BigDecimal("20")), // price
                    new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                    1L, // materialId
                    2L, // storageId
                    100, // invoiceItemVersion
                    200 // version
                )
            ),
            1, // invoiceVersion
            2 // version
        );

        assertEquals(new ProcurementIdDto(1L, 1L), dto.getId());
        assertEquals("INVOICE_NUMBER", dto.getInvoiceNumber());
        assertEquals("SHIPMENT_NUMBER", dto.getShipmentNumber());
        assertEquals(new UpdatePurchaseOrderDto(1L, "ORDER_NUMBER", 2L, 30), dto.getPurchaseOrder());
        assertEquals("DESCRIPTION", dto.getDescription());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), dto.getFreight());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getGeneratedOn());
        assertEquals(LocalDateTime.of(2001, 12, 12, 0, 0), dto.getReceivedOn());
        assertEquals(LocalDateTime.of(2002, 12, 12, 0, 0), dto.getPaymentDueDate());
        assertEquals(LocalDateTime.of(2003, 12, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(2L, dto.getInvoiceStatusId());
        assertEquals(3L, dto.getShipmentStatusId());
        assertEquals(1, dto.getInvoiceVersion());
        assertEquals(2, dto.getVersion());
        assertEquals(List.of(
            new UpdateProcurementItemDto(
                new ProcurementItemIdDto(10L, 20L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L, // storageId
                100, // invoiceVersion
                200 // version
            )
        ), dto.getProcurementItems());
    }

    @Test
    public void testIdArgConstructor() {
        dto = new UpdateProcurementDto(new ProcurementIdDto(1L, 1L));

        assertEquals(new ProcurementIdDto(1L, 1L), dto.getId());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementIdDto(10L, 10L));
        assertEquals(new ProcurementIdDto(10L, 10L), dto.getId());
    }

    @Test
    public void testAccessId_ReturnsNull_WhenShipmentOrInvoiceIsNull() {
        dto.setId(new ProcurementIdDto(null, null));
        assertNull(dto.getId());

        dto.setId(null);
        assertNull(dto.getId());
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
        dto.setPurchaseOrder(new UpdatePurchaseOrderDto(1L, "ORDER_NUMBER_1", 3L, 30));
        assertEquals(new UpdatePurchaseOrderDto(1L, "ORDER_NUMBER_1", 3L, 30), dto.getPurchaseOrder());
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
    public void testAccessInvoiceStatusId() {
        dto.setInvoiceStatusId(1L);
        assertEquals(1L, dto.getInvoiceStatusId());
    }

    @Test
    public void testAccessShipmentStatusId() {
        dto.setShipmentStatusId(2L);
        assertEquals(2L, dto.getShipmentStatusId());
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
            new UpdateProcurementItemDto(
                new ProcurementItemIdDto(5L, 15L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L, // storageId
                10, // invoiceItemVersion
                20 // version
            )
        ));
        assertEquals(List.of(
            new UpdateProcurementItemDto(
                new ProcurementItemIdDto(5L, 15L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L, // storageId
                10, // invoiceItemVersion
                20 // version
            ))
        ,dto.getProcurementItems());
    }
}
