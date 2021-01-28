package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateInvoiceDto {
    private String invoiceNumber;
    private String description;
    private FreightDto freight;
    private MoneyDto amount;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private InvoiceStatusDto status;
    private List<UpdateInvoiceItemDto> items;
    private Integer version;

    public UpdateInvoiceDto() {
    }

    public UpdateInvoiceDto(String invoiceNumber, String description, FreightDto freight, MoneyDto amount, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, InvoiceStatusDto status, List<UpdateInvoiceItemDto> items,
            Integer version) {
        setInvoiceNumber(invoiceNumber);
        setDescription(description);
        setFreight(freight);
        setAmount(amount);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setStatus(status);
        setItems(items);
        setVersion(version);
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
