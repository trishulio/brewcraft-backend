package io.company.brewcraft.dto;

import java.util.Date;
import java.util.List;

import io.company.brewcraft.model.InvoiceStatus;

public class UpdateInvoiceDto {

    private SupplierDto supplier;
    private Date date;
    private InvoiceStatus status;
    private List<UpdateInvoiceItemDto> items;
    private Integer version;

    public UpdateInvoiceDto() {
        this(null, null, null, null, null);
    }

    public UpdateInvoiceDto(SupplierDto supplier, Date date, InvoiceStatus status, List<UpdateInvoiceItemDto> items, Integer version) {
        setSupplier(supplier);
        setDate(date);
        setStatus(status);
        setItems(items);
        setVersion(version);
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
