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
        InvoiceStatus status = mapper.fromDto("FINAL");
        InvoiceStatus expected = new InvoiceStatus("FINAL");

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((String) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        InvoiceStatus status = mapper.fromDto(new InvoiceStatusDto("FINAL"));
        InvoiceStatus expected = new InvoiceStatus("FINAL");

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((InvoiceStatusDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        InvoiceStatusDto status = mapper.toDto(new InvoiceStatus("FINAL"));

        InvoiceStatusDto expected = new InvoiceStatusDto("FINAL");
        assertEquals(expected, status);
    }
    
    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
