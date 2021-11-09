package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementMapperTest {

    private ProcurementMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = ProcurementMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenProcurementIsNull() {
        assertNull(mapper.toDto((Procurement) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenProcurementIsNotNull() {
        Procurement procurement = new Procurement(
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
            new Freight(Money.parse("CAD 10")),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(
                new ProcurementItem(
                    new ProcurementItemId(1L, 2L),
                    "DESCRIPTION",
                    "LOT_NUMBER",
                    Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                    new Storage(1L), // storage
                    Money.parse("CAD 10"), // price
                    new Tax(Money.parse("CAD 20")), // tax
                    new Material(1L), // material
                    LocalDateTime.of(1999, 1, 1, 0, 0), // createdAt
                    LocalDateTime.of(2000, 1, 1, 0, 0), // lastUpdated
                    1,
                    10
                )
            ),
            100, // invoiceVersion
            1 // version
        );

        ProcurementDto dto = mapper.toDto(procurement);

        ProcurementDto expected = new ProcurementDto(
            new ProcurementIdDto(1L, 2L),
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            new PurchaseOrderDto(3L),
            "DESCRIPTION",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10.00"))), // freight
            new MoneyDto("CAD", new BigDecimal("100.00")), // amount
            new TaxDto(new MoneyDto("CAD", new BigDecimal("20.00"))),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatusDto(4L),
            new ShipmentStatusDto(5L),
            List.of(
                new ProcurementItemDto(
                    new ProcurementItemIdDto(1L, 2L),
                    "DESCRIPTION", // description
                    "LOT_NUMBER", // lotNumber
                    new QuantityDto("kg", new BigDecimal("10")), // quantity
                    new MoneyDto("CAD", new BigDecimal("10.00")), // price
                    new TaxDto(new MoneyDto("CAD", new BigDecimal("20.00"))), // tax
                    new MoneyDto("CAD", new BigDecimal("100.00")), // amount
                    new MaterialDto(1L), // material
                    new StorageDto(1L), // storage
                    LocalDateTime.of(1999, 1, 1, 0, 0), // createdAt
                    LocalDateTime.of(2000, 1, 1, 0, 0), // lastUpdated
                    1, // invoiceItemVersion
                    10 // version
                )
            ),
            100, // invoiceVersion
            1 // version
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto((AddProcurementDto) null));
    }

    @Test
    public void testFromAddDto_ReturnsProcurementWithUncommonValuesIgnored() {
        AddProcurementDto dto  = new AddProcurementDto(
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

        Procurement procurement = mapper.fromAddDto(dto);

        Procurement expected = new Procurement(
            null,
            "INVOICE_NUMBER",
            "SHIPMENT_NUMBER",
            "DESCRIPTION",
            new PurchaseOrder(null, "ORDER_NUMBER", new Supplier(1L), null, null, null),
            LocalDateTime.of(2000, 12, 12, 0, 0), // generatedOn
            LocalDateTime.of(2001, 12, 12, 0, 0), // receivedOn
            LocalDateTime.of(2002, 12, 12, 0, 0), // paymentDueDate
            LocalDateTime.of(2003, 12, 12, 0, 0), // deliveryDueDate
            LocalDateTime.of(2004, 12, 12, 0, 0), // deliveredDate
            new Freight(Money.parse("CAD 10")),
            null, // createdAt
            null, // lastUpdated
            new InvoiceStatus(2L),
            new ShipmentStatus(3L),
            List.of(
                new ProcurementItem(
                    null,
                    "DESCRIPTION",
                    "LOT_NUMBER",
                    Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                    new Storage(2L), // storage
                    Money.parse("CAD 20"), // price
                    new Tax(Money.parse("CAD 5")), // tax
                    new Material(1L), // material
                    null, // createdAt
                    null, // lastUpdated
                    null, // invoiceItemVersion
                    null // version
                )
            ),
            null, // invoiceVersion
            null // version
        );

        assertEquals(expected, procurement);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto((UpdateProcurementDto) null));
    }

    @Test
    public void testFromUpdateDto_ReturnsProcurementWithUncommonValuesIgnored() {
        UpdateProcurementDto dto  = new UpdateProcurementDto(
            new ProcurementIdDto(1L, 1L), // id
            "INVOICE_NUMBER", // invoiceNumber
            "SHIPMENT_NUMBER", // shipmentNumber
            new UpdatePurchaseOrderDto(1L, "ORDER_NUMBER", 1L, 1),
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
                    new ProcurementItemIdDto(1L, 2L),
                    "DESCRIPTION", // description
                    "LOT_NUMBER", // lotNumber
                    new QuantityDto("kg", new BigDecimal("10")), // quantity
                    new MoneyDto("CAD", new BigDecimal("20")), // price
                    new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                    1L, // materialId
                    2L, // storageId
                    1, // invoiceItemVersion
                    10 // version
                )
            ),
            100,
            1
        );

        Procurement procurement = mapper.fromUpdateDto(dto);

        Procurement expected = new Procurement(
            new ProcurementId(1L, 1L), // id
            "INVOICE_NUMBER",
            "SHIPMENT_NUMBER",
            "DESCRIPTION",
            new PurchaseOrder(1L, "ORDER_NUMBER", new Supplier(1L), null, null, 1),
            LocalDateTime.of(2000, 12, 12, 0, 0), // generatedOn
            LocalDateTime.of(2001, 12, 12, 0, 0), // receivedOn
            LocalDateTime.of(2002, 12, 12, 0, 0), // paymentDueDate
            LocalDateTime.of(2003, 12, 12, 0, 0), // deliveryDueDate
            LocalDateTime.of(2004, 12, 12, 0, 0), // deliveredDate
            new Freight(Money.parse("CAD 10")),
            null, // createdAt
            null, // lastUpdated
            new InvoiceStatus(2L),
            new ShipmentStatus(3L),
            List.of(
                new ProcurementItem(
                    new ProcurementItemId(1L, 2L),
                    "DESCRIPTION",
                    "LOT_NUMBER",
                    Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
                    new Storage(2L), // storage
                    Money.parse("CAD 20"), // price
                    new Tax(Money.parse("CAD 5")), // tax
                    new Material(1L), // material
                    null, // createdAt
                    null, // lastUpdated
                    1, // invoiceItemVersion
                    10 // version
                )
            ),
            100, // invoiceVersion
            1 // version
        );

        assertEquals(expected, procurement);
    }
}
