package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.InvoiceStatusDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.TaxDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcurementDto extends BaseDto {
    private ShipmentDto shipment;
    private InvoiceDto invoice;
    // TODO: Rename the items in Invoice to InvoiceItems
    private List<ProcurementItemDto> procurementItems;

    public ProcurementDto() {
        this.shipment = new ShipmentDto();
        this.invoice = new InvoiceDto();
    }

    public ProcurementDto(ProcurementIdDto id) {
        this();
        setId(id);
    }

    public ProcurementDto(ProcurementIdDto id, String invoiceNumber, String shipmentNumber, PurchaseOrderDto purchaseOrder, String description, FreightDto freight, MoneyDto amount, TaxDto tax, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, LocalDateTime createdAt, LocalDateTime lastUpdated, InvoiceStatusDto invoiceStatus, ShipmentStatusDto shipmentStatus, List<ProcurementItemDto> procurementItems, Integer invoiceVersion, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setShipmentNumber(shipmentNumber);
        setDescription(description);
        setPurchaseOrder(purchaseOrder);
        setFreight(freight);
        setAmount(amount);
        setTax(tax);
        setGeneratedOn(generatedOn);
        setPaymentDueDate(paymentDueDate);
        setReceivedOn(receivedOn);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setInvoiceStatus(invoiceStatus);
        setShipmentStatus(shipmentStatus);
        setProcurementItems(procurementItems);
        setInvoiceVersion(invoiceVersion);
        setVersion(version);
    }

    public ProcurementIdDto getId() {
        ProcurementIdDto id = null;
        if (this.shipment.getId() != null || this.invoice.getId() != null) {
            id = new ProcurementIdDto(this.shipment.getId(), this.invoice.getId());
        }
        return id;
    }

    public void setId(ProcurementIdDto id) {
        if (id != null) {
            this.shipment.setId(id.getShipmentId());
            this.invoice.setId(id.getInvoiceId());
        } else {
            this.shipment.setId(null);
            this.invoice.setId(null);
        }
    }

    public String getInvoiceNumber() {
        return this.invoice.getInvoiceNumber();
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoice.setInvoiceNumber(invoiceNumber);
    }

    public String getShipmentNumber() {
        return this.shipment.getShipmentNumber();
    }

    public void setShipmentNumber(String invoiceNumber) {
        this.shipment.setShipmentNumber(invoiceNumber);
    }

    public String getDescription() {
        return this.invoice.getDescription();
    }

    public void setDescription(String description) {
        this.invoice.setDescription(description);
    }

    public PurchaseOrderDto getPurchaseOrder() {
        return this.invoice.getPurchaseOrder();
    }

    public void setPurchaseOrder(PurchaseOrderDto purchaseOrder) {
        this.invoice.setPurchaseOrder(purchaseOrder);
    }

    public FreightDto getFreight() {
        return this.invoice.getFreight();
    }

    public void setFreight(FreightDto freight) {
        this.invoice.setFreight(freight);
    }

    public MoneyDto getAmount() {
        return this.invoice.getAmount();
    }

    public void setAmount(MoneyDto amount) {
        this.invoice.setAmount(amount);
    }

    public TaxDto getTax() {
        return this.invoice.getTax();
    }

    public void setTax(TaxDto tax) {
        this.invoice.setTax(tax);
    }

    public LocalDateTime getGeneratedOn() {
        return this.invoice.getGeneratedOn();
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.invoice.setGeneratedOn(generatedOn);
    }

    public LocalDateTime getReceivedOn() {
        return this.invoice.getReceivedOn();
    }

    public void setReceivedOn(LocalDateTime receivedOn) {
        this.invoice.setReceivedOn(receivedOn);
    }

    public LocalDateTime getPaymentDueDate() {
        return this.invoice.getPaymentDueDate();
    }

    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.invoice.setPaymentDueDate(paymentDueDate);
    }

    public LocalDateTime getDeliveryDueDate() {
        return this.shipment.getDeliveryDueDate();
    }

    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.shipment.setDeliveryDueDate(deliveryDueDate);
    }

    public LocalDateTime getDeliveredDate() {
        return this.shipment.getDeliveredDate();
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.shipment.setDeliveredDate(deliveredDate);
    }

    public LocalDateTime getLastUpdated() {
        return this.shipment.getLastUpdated();
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.shipment.setLastUpdated(lastUpdated);
    }

    public LocalDateTime getCreatedAt() {
        return this.shipment.getCreatedAt();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.shipment.setCreatedAt(createdAt);
    }

    public InvoiceStatusDto getInvoiceStatus() {
        return this.invoice.getInvoiceStatus();
    }

    public void setInvoiceStatus(InvoiceStatusDto invoiceStatus) {
        this.invoice.setInvoiceStatus(invoiceStatus);
    }

    public ShipmentStatusDto getShipmentStatus() {
        return this.shipment.getShipmentStatus();
    }

    public void setShipmentStatus(ShipmentStatusDto shipmentStatus) {
        this.shipment.setShipmentStatus(shipmentStatus);
    }

    public List<ProcurementItemDto> getProcurementItems() {
        return this.procurementItems;
    }

    public void setProcurementItems(List<ProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }

    public Integer getVersion() {
        return this.shipment.getVersion();
    }

    public void setVersion(Integer version) {
        this.shipment.setVersion(version);
    }

    public Integer getInvoiceVersion() {
        return this.invoice.getVersion();
    }

    public void setInvoiceVersion(Integer invoiceVersion) {
        this.invoice.setVersion(invoiceVersion);
    }
}
