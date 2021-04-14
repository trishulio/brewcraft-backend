package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto extends BaseDto {
    private Long id;
    private String invoiceNumber;
    private String description;
    private PurchaseOrderDto purchaseOrder;
    private FreightDto freight;
    private MoneyDto amount;
    private TaxDto tax;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private InvoiceStatusDto status;
    private List<InvoiceItemDto> items;
    private Integer version;

    public InvoiceDto() {
    }
    
    public InvoiceDto(Long id) {
        this();
        setId(id);
    }

    public InvoiceDto(Long id, String invoiceNumber, String description, PurchaseOrderDto purchaseOrder, FreightDto freight, MoneyDto amount, TaxDto tax, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime lastUpdated, LocalDateTime createdAt, InvoiceStatusDto status, List<InvoiceItemDto> items, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setDescription(description);
        setPurchaseOrder(purchaseOrder);
        setFreight(freight);
        setAmount(amount);
        setTax(tax);
        setGeneratedOn(generatedOn);
        setPaymentDueDate(paymentDueDate);
        setReceivedOn(receivedOn);
        setLastUpdated(lastUpdated);
        setCreatedAt(createdAt);
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

    public PurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public FreightDto getFreight() {
        return freight;
    }

    public void setFreight(FreightDto freight) {
        this.freight = freight;
    }

    public MoneyDto getAmount() {
        return amount;
    }

    public void setAmount(MoneyDto amount) {
        this.amount = amount;
    }

    public TaxDto getTax() {
        return tax;
    }

    public void setTax(TaxDto tax) {
        this.tax = tax;
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

    public InvoiceStatusDto getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusDto status) {
        this.status = status;
    }

    public List<InvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDto> items) {
        this.items = items;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
