package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AmountDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.TaxAmountDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.TaxRateDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.AddProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.ProcurementShipmentDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementMaterialLotDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementShipmentDto;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.TaxRate;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ProcurementMapperTest {
    private ProcurementMapper mapper;

    @BeforeEach
    public void init() {
        this.mapper = ProcurementMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        ProcurementDto dto = mapper.toDto(new Procurement(
            new Shipment(
                1L, // id
                "SHIPMENT", // shipmentNumber,
                "DESCRIPTION", // description
                new ShipmentStatus(1L), // shipmentStatus,
                LocalDateTime.of(2000, 1, 1, 0, 0), // deliveryDueDate
                LocalDateTime.of(2001, 1, 1, 0, 0), // deliveredDate
                LocalDateTime.of(2002, 1, 1, 0, 0), // createdAt
                LocalDateTime.of(2003, 1, 1, 0, 0), // lastUpdated
                List.of(new MaterialLot(1L, 0, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), null, new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
                1 // version
            ),
            new Invoice(
                2L,
                "ABCDE-12345",
                "desc1",
                new PurchaseOrder(
                    3L,
                    "ORDER_1",
                    new Supplier(1L),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2001, 1, 1, 0, 0),
                    1
                ),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
                LocalDateTime.of(2002, 1, 1, 12, 0),
                LocalDateTime.of(2003, 1, 1, 12, 0),
                new InvoiceStatus(99L),
                List.of(new InvoiceItem(1L, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("6"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
                1
            )
        ));

        ProcurementDto expected = new ProcurementDto(
            new ProcurementIdDto(1L, 2L),
            new ProcurementInvoiceDto(
                2L,
                "ABCDE-12345",
                "desc1",
                new ProcurementPurchaseOrderDto(
                    3L,
                    "ORDER_1",
                    new SupplierDto(1L),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2001, 1, 1, 0, 0),
                    1
                ),
                new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
                new AmountDto(new MoneyDto("CAD", new BigDecimal("140.00")), new MoneyDto("CAD", new BigDecimal("20.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("120.00")), new MoneyDto("CAD", new BigDecimal("120.00")))),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                LocalDateTime.of(2002, 1, 1, 12, 0),
                LocalDateTime.of(2003, 1, 1, 12, 0),
                new InvoiceStatusDto(99L),
                1
            ),
            new ProcurementShipmentDto(
                1L,
                "SHIPMENT",
                "DESCRIPTION",
                new ShipmentStatusDto(1L),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                LocalDateTime.of(2002, 1, 1, 0, 0),
                LocalDateTime.of(2003, 1, 1, 0, 0),
                1
            ),
            List.of(
                new ProcurementItemDto(
                    new ProcurementItemIdDto(1L, 1L),
                    new ProcurementMaterialLotDto(1L, "LOT_1", new QuantityDto("g", new BigDecimal("10")), new StorageDto(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
                    new ProcurementInvoiceItemDto(1L, "desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("6"))), new AmountDto(new MoneyDto("CAD", new BigDecimal("140.00")), new MoneyDto("CAD", new BigDecimal("20.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("120.00")), new MoneyDto("CAD", new BigDecimal("120.00")))), new MaterialDto(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)
                )
            )
        );
        assertEquals(expected, dto);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsEntity_WhenDtoIsNotNull() {
        AddProcurementDto dto = new AddProcurementDto(
            new AddProcurementInvoiceDto(
                "ABCDE-12345",
                1L,
                "desc1",
                new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                99L
            ),
            new AddProcurementShipmentDto(
                "SHIPMENT",
                "DESCRIPTION",
                1L,
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0)
            ),
            List.of(
                new AddProcurementItemDto(
                    new AddProcurementMaterialLotDto("LOT_1", new QuantityDto("g", new BigDecimal("10")), 3L),
                    new AddProcurementInvoiceItemDto("desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("6.00"))), 7L)
                )
            )
        );

        Procurement procurement = mapper.fromAddDto(dto);

        Procurement expected = new Procurement(
            new Shipment(
                null, // id
                "SHIPMENT", // shipmentNumber,
                "DESCRIPTION", // description
                new ShipmentStatus(1L), // shipmentStatus,
                LocalDateTime.of(2000, 1, 1, 0, 0), // deliveryDueDate
                LocalDateTime.of(2001, 1, 1, 0, 0), // deliveredDate
                null,
                null,
                List.of(new MaterialLot(null, 0, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), null, new Storage(3L), null, null, null)),
                null // version
            ),
            new Invoice(
                null,
                "ABCDE-12345",
                "desc1",
                new PurchaseOrder(1L),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
                null,
                null,
                new InvoiceStatus(99L),
                List.of(new InvoiceItem(null, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("6.00"))), new Material(7L), null, null, null)),
                null
            )
        );
        assertEquals(expected, procurement);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto(null));
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity_WhenDtoIsNotNull() {
        UpdateProcurementDto dto = new UpdateProcurementDto(
            new UpdateProcurementInvoiceDto(
                2L,
                "ABCDE-12345",
                1L,
                "desc1",
                new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                99L,
                1
            ),
            new UpdateProcurementShipmentDto(
                1L,
                "SHIPMENT",
                "DESCRIPTION",
                1L,
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                1
            ),
            List.of(
                new UpdateProcurementItemDto(
                    new UpdateProcurementMaterialLotDto(1L, "LOT_1", new QuantityDto("g", new BigDecimal("10")), 3L, 1),
                    new UpdateProcurementInvoiceItemDto(1L, "desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5.00")), new TaxDto(new TaxRateDto(new BigDecimal("6.00"))), 7L, 1)
                )
            )
        );

        Procurement procurement = mapper.fromUpdateDto(dto);

        Procurement expected = new Procurement(
            new Shipment(
                1L, // id
                "SHIPMENT", // shipmentNumber,
                "DESCRIPTION", // description
                new ShipmentStatus(1L), // shipmentStatus,
                LocalDateTime.of(2000, 1, 1, 0, 0), // deliveryDueDate
                LocalDateTime.of(2001, 1, 1, 0, 0), // deliveredDate
                null,
                null,
                List.of(new MaterialLot(1L, 0, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.GRAM), null, new Storage(3L), null, null, 1)),
                1 // version
            ),
            new Invoice(
                2L,
                "ABCDE-12345",
                "desc1",
                new PurchaseOrder(1L),
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
                null,
                null,
                new InvoiceStatus(99L),
                List.of(new InvoiceItem(1L, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("6.00"))), new Material(7L), null, null, 1)),
                1
            )
        );
        assertEquals(expected, procurement);
    }
}
