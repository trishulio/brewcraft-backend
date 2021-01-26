package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AddInvoiceDto extends BaseDto {
    @NotNull
    private String invoiceNumber;

    @NotNull
    private FreightDto freight;

    @NotNull
    private MoneyDto amount;

    @NotNull
    private LocalDateTime generatedOn;

    @NotNull
    private LocalDateTime receivedOn;

    @NotNull
    private LocalDateTime paymentDueDate;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime lastUpdated;

    @NotNull
    private InvoiceStatusDto status;

    @NotNull
    private List<UpdateInvoiceItemDto> items;

    public AddInvoiceDto() {
    }

    public AddInvoiceDto(@NotNull String invoiceNumber, @NotNull FreightDto freight, @NotNull MoneyDto amount, @NotNull LocalDateTime generatedOn, @NotNull LocalDateTime receivedOn,
            @NotNull LocalDateTime paymentDueDate, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime lastUpdated, @NotNull InvoiceStatusDto status, @NotNull List<UpdateInvoiceItemDto> items) {
        setInvoiceNumber(invoiceNumber);
        setFreight(freight);
        setAmount(amount);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setStatus(status);
        setItems(items);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
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
