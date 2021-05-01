package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AddInvoiceDto extends BaseDto {
    private String invoiceNumber;
    private Long purchaseOrderId;
    private String description;
    private FreightDto freight;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private Long statusId;
    private List<AddInvoiceItemDto> items;

    public AddInvoiceDto() {
    }

    public AddInvoiceDto(String invoiceNumber, Long purchaseOrderId, String description, FreightDto freight, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Long statusId, List<AddInvoiceItemDto> items) {
        setInvoiceNumber(invoiceNumber);
        setPurchaseOrderId(purchaseOrderId);
        setDescription(description);
        setFreight(freight);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setStatusId(statusId);
        setItems(items);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String desription) {
        this.description = desription;
    }

    public FreightDto getFreight() {
        return freight;
    }

    public void setFreight(FreightDto freight) {
        this.freight = freight;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public List<AddInvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(List<AddInvoiceItemDto> items) {
        this.items = items;
    }
}
