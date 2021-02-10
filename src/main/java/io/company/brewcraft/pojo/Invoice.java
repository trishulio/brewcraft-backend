package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.MoneyService;

public class Invoice extends BaseModel {
    private static final Logger logger = LoggerFactory.getLogger(Invoice.class);

    public static final String FIELD_CREATED_AT = "createdAt";
    
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
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

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public Freight getFreight() {
        return freight;
    }

    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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
