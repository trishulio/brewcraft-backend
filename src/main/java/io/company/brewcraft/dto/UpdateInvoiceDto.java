package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.InvoiceStatus;

public class UpdateInvoiceDto {

    private LocalDateTime date;
    private InvoiceStatus status;
    private List<UpdateInvoiceItemDto> items;
    private Integer version;

    public UpdateInvoiceDto() {
        this(null, null, null, null);
    }

    public UpdateInvoiceDto(LocalDateTime date, InvoiceStatus status, List<UpdateInvoiceItemDto> items, Integer version) {
        setDate(date);
        setStatus(status);
        setItems(items);
        setVersion(version);
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

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
