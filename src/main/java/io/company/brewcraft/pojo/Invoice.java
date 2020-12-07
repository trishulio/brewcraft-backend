package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;

public class Invoice extends BaseModel {
    private static final Logger logger = LoggerFactory.getLogger(Invoice.class);
    private Long id;
    private Supplier supplier;
    private LocalDateTime date;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private InvoiceStatus status;
    private List<InvoiceItem> items;
    private Integer version;

    public Invoice() {
        this(null);
    }

    public Invoice(Long id) {
        this(id, null, null, null, null, null, null, null);
    }

    public Invoice(Long id, Supplier supplier, LocalDateTime date, LocalDateTime createdAt, LocalDateTime lastUpdated, InvoiceStatus status, List<InvoiceItem> items, Integer version) {
        setId(id);
        setSupplier(supplier);
        setDate(date);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setStatus(status);
        setItems(items);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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

    public static Logger getLogger() {
        return logger;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Money getAmount() {
        Money amount = null;

        if (items != null) {
            List<Money> monies = items.stream().map(i -> i.getAmount()).collect(Collectors.toList());
            amount = Money.total(monies);
        }

        return amount;
    }
}
