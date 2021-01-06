package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceStatus;

public class UpdateInvoiceDtoTest {

    UpdateInvoiceDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateInvoiceDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getDate());
        assertNull(dto.getStatus());
        assertNull(dto.getItems());
        assertNull(dto.getVersion());
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new UpdateInvoiceDto(LocalDateTime.MAX, InvoiceStatus.FINAL, List.of(new UpdateInvoiceItemDto(12345L)), 1);
        assertEquals(LocalDateTime.MAX, dto.getDate());
        assertEquals(InvoiceStatus.FINAL, dto.getStatus());
        assertEquals(List.of(new UpdateInvoiceItemDto(12345L)), dto.getItems());
    }

    @Test
    public void testAccessDate() {
        assertNull(dto.getDate());
        dto.setDate(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MAX, dto.getDate());
    }

    @Test
    public void testAccessStatus() {
        assertNull(dto.getStatus());
        dto.setStatus(InvoiceStatus.PENDING);
        assertEquals(InvoiceStatus.PENDING, dto.getStatus());
    }

    @Test
    public void testAccessItems() {
        assertNull(dto.getItems());
        dto.setItems(List.of(new UpdateInvoiceItemDto(12345L)));
        assertEquals(List.of(new UpdateInvoiceItemDto(12345L)), dto.getItems());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(123);
        assertEquals(123, dto.getVersion());
    }
}
