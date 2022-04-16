package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.AmountDto;
import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.dto.TaxDto;

public class ProcurementInvoiceDto extends BaseDto {
    private Long id;
    private String invoiceNumber;
    private String description;
    private ProcurementPurchaseOrderDto purchaseOrder;
    private FreightDto freight;
    private AmountDto amount;
    private TaxDto tax;
    private LocalDateTime generatedOn;
    private LocalDateTime receivedOn;
    private LocalDateTime paymentDueDate;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private InvoiceStatusDto invoiceStatus;
    private Integer version;

    public ProcurementInvoiceDto() {
    }

    public ProcurementInvoiceDto(Long id) {
        this();
        setId(id);
    }

    public ProcurementInvoiceDto(Long id, String invoiceNumber, String description, ProcurementPurchaseOrderDto purchaseOrder, FreightDto freight, AmountDto amount, TaxDto tax, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime createdAt, LocalDateTime lastUpdated, InvoiceStatusDto invoiceStatus, Integer version) {
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
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setInvoiceStatus(invoiceStatus);
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

    public ProcurementPurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(ProcurementPurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public FreightDto getFreight() {
        return freight;
    }

    public void setFreight(FreightDto freight) {
        this.freight = freight;
    }

    public AmountDto getAmount() {
        return amount;
    }

    public void setAmount(AmountDto amount) {
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

    public InvoiceStatusDto getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatusDto invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
