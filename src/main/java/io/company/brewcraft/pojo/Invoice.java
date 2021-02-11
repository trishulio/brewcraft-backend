package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.Audited;
import io.company.brewcraft.dto.Identified;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;

public class Invoice extends BaseModel implements UpdateInvoice<InvoiceItem>, Identified, Audited, MoneySupplier {
    private static final Logger logger = LoggerFactory.getLogger(Invoice.class);

    private Long id;
    private String invoiceNumber;
    private PurchaseOrder purchaseOrder;
    private String description;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private InvoiceStatus status;
    private List<InvoiceItem> items;
    private Freight freight;
    private Integer version;

    public Invoice() {
    }

    public Invoice(Long id) {
        setId(id);
    }

    public Invoice(Long id, String invoiceNumber, String description, PurchaseOrder purchaseOrder, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Freight freight, LocalDateTime createdAt,
            LocalDateTime lastUpdated, InvoiceStatus status, List<InvoiceItem> items, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setDescription(description);
        setPurchaseOrder(purchaseOrder);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setFreight(freight);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setStatus(status);
        setItems(items);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @Override
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    @Override
    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    @Override
    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    @Override
    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    @Override
    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public InvoiceStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public List<InvoiceItem> getItems() {
        return items;
    }

    @Override
    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public Freight getFreight() {
        return freight;
    }

    @Override
    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Money getAmount() {
        return MoneyService.total(this.getItems());
    }
    
    public Tax getTax() {
        Tax tax = null;
        if (getItems() != null) {
            List<Tax> taxes = getItems().stream().filter(i -> i != null).map(i -> i.getTax()).collect(Collectors.toList());
            tax = Tax.total(taxes);
        }
        
        return tax;
    }
}
