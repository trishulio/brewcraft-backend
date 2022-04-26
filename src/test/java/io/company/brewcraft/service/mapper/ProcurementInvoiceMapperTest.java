package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.TaxAmountDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceDto;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.TaxRate;
import io.company.brewcraft.service.mapper.procurement.ProcurementInvoiceMapper;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ProcurementInvoiceMapperTest {
    private ProcurementInvoiceMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ProcurementInvoiceMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto_WhenArgIsNotNull() {
        Invoice invoice = new Invoice(
            12345L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(99L),
            List.of(new InvoiceItem(1L, 0, "desc2", Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), Money.of(CurrencyUnit.CAD, new BigDecimal("5")), new Tax(new TaxRate(new BigDecimal("1"))), new Material(7L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            1
        );

        ProcurementInvoiceDto dto = mapper.toDto(invoice);

        ProcurementInvoiceDto expected = new ProcurementInvoiceDto(
            12345L,
            "ABCDE-12345",
            "desc1",
            new ProcurementPurchaseOrderDto(1L),
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            new AmountDto(new MoneyDto("CAD", new BigDecimal("40.00")), new MoneyDto("CAD", new BigDecimal("20.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("20.00")), new MoneyDto("CAD", new BigDecimal("20.00")))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusDto(99L),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testFromDto_ReturnsProcurementInvoiceWithUpdateProperties_WhenUpdateProcurementInvoiceDtoIsNotNull() {
        UpdateProcurementInvoiceDto dto = new UpdateProcurementInvoiceDto(
            1L,
            "ABCDE-12345",
            2L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            1
        );

        Invoice invoice = mapper.fromUpdateDto(dto);

        Invoice expected = new Invoice(
            1L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(2L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            null,
            null,
            new InvoiceStatus(99L),
            null,
            1
        );

        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenUpdateProcurementInvoiceDtoIsNull() {
        assertNull(mapper.fromUpdateDto((UpdateProcurementInvoiceDto) null));
    }

    @Test
    public void testFromDto_ReturnsProcurementInvoiceWithAddProperties_WhenAddProcurementInvoiceDtoIsNotNull() {
        AddProcurementInvoiceDto dto = new AddProcurementInvoiceDto(
            "ABCDE-12345",
            1L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("3.00"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L
        );

        Invoice invoice = mapper.fromAddDto(dto);

        Invoice expected = new Invoice(
            null,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1l),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            null,
            null,
            new InvoiceStatus(99L),
            null,
            null
        );

        assertEquals(expected, invoice);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenAddProcurementInvoiceDtoIsNull() {
        assertNull(mapper.fromAddDto((AddProcurementInvoiceDto) null));
    }
}
