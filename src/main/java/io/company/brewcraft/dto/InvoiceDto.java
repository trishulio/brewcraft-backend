package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.company.brewcraft.model.InvoiceStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto extends BaseDto {

    private Long id;
    private SupplierDto supplier;
    private LocalDateTime date;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private InvoiceStatus status;
    private List<InvoiceItemDto> items;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<InvoiceItemDto> getItems() {
        return items;
    }
    
    public void setItems(List<InvoiceItemDto> items) {
        this.items = items;
    }
    
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
}
