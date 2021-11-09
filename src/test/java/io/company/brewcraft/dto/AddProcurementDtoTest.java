package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;

public class AddProcurementDtoTest {
    private AddProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new AddProcurementDto();
    }

    @Test
    public void testNoArgConstructor_SetsInitialValues() {
    }

    @Test
    public void testAllArgConstructor_SetsAllValues() {
        dto = new AddProcurementDto(
            "INVOICE_NUMBER", // invoiceNumber
            "SHIPMENT_NUMBER", // shipmentNumber
            new AddPurchaseOrderDto("ORDER_NUMBER", 1L),
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
                new AddProcurementItemDto(
                    "DESCRIPTION", // description
                    "LOT_NUMBER", // lotNumber
                    new QuantityDto("kg", new BigDecimal("10")), // quantity
                    new MoneyDto("CAD", new BigDecimal("20")), // price
                    new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                    1L, // materialId
                    2L // storageId
                )
            )
        );

        assertEquals("INVOICE_NUMBER", dto.getInvoiceNumber());
        assertEquals("SHIPMENT_NUMBER", dto.getShipmentNumber());
        assertEquals(new AddPurchaseOrderDto("ORDER_NUMBER", 1L), dto.getPurchaseOrder());
        assertEquals("DESCRIPTION", dto.getDescription());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), dto.getFreight());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getGeneratedOn());
        assertEquals(LocalDateTime.of(2001, 12, 12, 0, 0), dto.getReceivedOn());
        assertEquals(LocalDateTime.of(2002, 12, 12, 0, 0), dto.getPaymentDueDate());
        assertEquals(LocalDateTime.of(2003, 12, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2004, 12, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(2L, dto.getInvoiceStatusId());
        assertEquals(3L, dto.getShipmentStatusId());
        assertEquals(List.of(
            new AddProcurementItemDto(
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L // storageId
            )
        ), dto.getProcurementItems());
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
        dto.setPurchaseOrder(new AddPurchaseOrderDto("ORDER_NUMBER", 1L));
        assertEquals(new AddPurchaseOrderDto("ORDER_NUMBER", 1L), dto.getPurchaseOrder());
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
    public void testAccessProcurementItems() {
        dto.setProcurementItems(List.of(
            new AddProcurementItemDto(
            "DESCRIPTION", // description
            "LOT_NUMBER", // lotNumber
            new QuantityDto("kg", new BigDecimal("10")), // quantity
            new MoneyDto("CAD", new BigDecimal("20")), // price
            new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
            1L, // materialId
            2L // storageId
        )));
        assertEquals(List.of(
            new AddProcurementItemDto(
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L // storageId
            ))
        ,dto.getProcurementItems());
    }
}
