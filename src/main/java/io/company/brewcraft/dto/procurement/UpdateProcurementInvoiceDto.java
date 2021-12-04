package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;

public class UpdateProcurementInvoiceDto extends BaseDto {
    private Long id;
    private String invoiceNumber;
    private Long purchaseOrderId;
    private String description;
    private FreightDto freight;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private Long invoiceStatusId;
    @NotNull
    private Integer version;

    public UpdateProcurementInvoiceDto() {
        super();
    }

    public UpdateProcurementInvoiceDto(Long id) {
        this();
        setId(id);
    }

    public UpdateProcurementInvoiceDto(Long id, String invoiceNumber, Long purchaseOrderId, String description, FreightDto freight, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Long invoiceStatusId, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setPurchaseOrderId(purchaseOrderId);
        setDescription(description);
        setFreight(freight);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setInvoiceStatusId(invoiceStatusId);
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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
