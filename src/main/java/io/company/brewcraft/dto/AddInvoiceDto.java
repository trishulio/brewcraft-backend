package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AddInvoiceDto extends BaseDto {
    @NotNull
    private String invoiceNumber;

    @NotNull
    private Long purchaseOrderId;

    @NotNull
    private FreightDto freight;

    @NotNull
    private MoneyDto amount;

    @NotNull
    private TaxDto tax;

    @NotNull
    private LocalDateTime generatedOn;

    @NotNull
    private LocalDateTime receivedOn;

    @NotNull
    private LocalDateTime paymentDueDate;

    @NotNull
    private LocalDateTime lastUpdated;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private InvoiceStatusDto status;

    @NotNull
    private List<UpdateInvoiceItemDto> items;

    public AddInvoiceDto() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public List<UpdateInvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(List<UpdateInvoiceItemDto> items) {
        this.items = items;
    }
}
