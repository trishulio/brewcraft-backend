package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceStatus;

public class AddInvoiceDtoTest {

    AddInvoiceDto dto;

    @BeforeEach
    public void init() {
        dto = new AddInvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new AddInvoiceDto(LocalDateTime.MAX, InvoiceStatus.PENDING, List.of(new UpdateInvoiceItemDto(567L)));
        assertEquals(LocalDateTime.MAX, dto.getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getStatus());
        assertEquals(1, dto.getItems().size());
        assertEquals(new UpdateInvoiceItemDto(567L), dto.getItems().get(0));
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
}
