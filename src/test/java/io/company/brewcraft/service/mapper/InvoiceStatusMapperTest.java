package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.model.InvoiceStatus;

public class InvoiceStatusMapperTest {
    private InvoiceStatusMapper mapper;

    @BeforeEach
    public void init() {
        mapper = InvoiceStatusMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnPojo_WhenIdIsNotNull() {
        InvoiceStatus invoiceStatus = mapper.fromDto(99l);
        InvoiceStatus expected = new InvoiceStatus(99l);

        assertEquals(expected, invoiceStatus);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        InvoiceStatus invoiceStatus = mapper.fromDto(new InvoiceStatusDto(99l));
        InvoiceStatus expected = new InvoiceStatus(99l);

        assertEquals(expected, invoiceStatus);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((InvoiceStatusDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        InvoiceStatusDto invoiceStatus = mapper.toDto(new InvoiceStatus(99l));

        InvoiceStatusDto expected = new InvoiceStatusDto(99l);
        assertEquals(expected, invoiceStatus);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
