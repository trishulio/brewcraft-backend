package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;

public class AddProcurementInvoiceDto extends BaseDto {
    private String invoiceNumber;
    private String description;
    private FreightDto freight;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private Long invoiceStatusId;

    public AddProcurementInvoiceDto() {
    }

    public AddProcurementInvoiceDto(String invoiceNumber, String description, FreightDto freight, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Long invoiceStatusId) {
        setInvoiceNumber(invoiceNumber);
        setDescription(description);
        setFreight(freight);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setInvoiceStatusId(invoiceStatusId);
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

    public Long getInvoiceStatusId() {
        return invoiceStatusId;
    }

    public void setInvoiceStatusId(Long invoiceStatusId) {
        this.invoiceStatusId = invoiceStatusId;
    }
}
