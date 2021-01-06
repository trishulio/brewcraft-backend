package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.InvoiceStatus;

public class AddInvoiceDto extends BaseDto {
    private LocalDateTime date;
    private InvoiceStatus status;
    private List<UpdateInvoiceItemDto> items;

    public AddInvoiceDto() {
        this(null, null, null);
    }

    public AddInvoiceDto(LocalDateTime date, InvoiceStatus status, List<UpdateInvoiceItemDto> items) {
        setDate(date);
        setStatus(status);
        setItems(items);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<UpdateInvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(List<UpdateInvoiceItemDto> items) {
        this.items = items;
    }
}
