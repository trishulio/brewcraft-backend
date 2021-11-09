package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.AddShipmentDto;
import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;

public class AddProcurementDto extends BaseDto {
    private AddInvoiceDto invoice;
    private AddPurchaseOrderDto purchaseOrder;
    private AddShipmentDto shipment;

    private List<AddProcurementItemDto> procurementItems;

    public AddProcurementDto() {
        super();
        this.invoice = new AddInvoiceDto();
        this.shipment = new AddShipmentDto();
    }

    public AddProcurementDto(String invoiceNumber, String shipmentNumber, AddPurchaseOrderDto purchaseOrder, String description, FreightDto freight, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Long invoiceStatusId, Long shipmentStatusId, List<AddProcurementItemDto> procurementProcurementItems) {
        this();
        setInvoiceNumber(invoiceNumber);
        setShipmentNumber(shipmentNumber);
        setPurchaseOrder(purchaseOrder);
        setDescription(description);
        setFreight(freight);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setDeliveryDueDate(deliveryDueDate);
        setDeliveredDate(deliveredDate);
        setInvoiceStatusId(invoiceStatusId);
        setShipmentStatusId(shipmentStatusId);
        setProcurementItems(procurementProcurementItems);
    }

    public String getInvoiceNumber() {
        return this.invoice.getInvoiceNumber();
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoice.setInvoiceNumber(invoiceNumber);
    }

    public AddPurchaseOrderDto getPurchaseOrder() {
        return this.purchaseOrder;
    }

    public void setPurchaseOrder(AddPurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDescription() {
        return this.invoice.getDescription();
    }

    public void setDescription(String desription) {
        this.invoice.setDescription(desription);
    }

    public String getShipmentNumber() {
        return this.shipment.getShipmentNumber();
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipment.setShipmentNumber(shipmentNumber);
    }

    public FreightDto getFreight() {
        return this.invoice.getFreight();
    }

    public void setFreight(FreightDto freight) {
        this.invoice.setFreight(freight);
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

    public Long getInvoiceStatusId() {
        return this.invoice.getInvoiceStatusId();
    }

    public void setInvoiceStatusId(Long invoiceStatusId) {
        this.invoice.setInvoiceStatusId(invoiceStatusId);
    }

    public Long getShipmentStatusId() {
        return this.shipment.getShipmentStatusId();
    }

    public void setShipmentStatusId(Long shipmentStatusId) {
        this.shipment.setShipmentStatusId(shipmentStatusId);
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

    public List<AddProcurementItemDto> getProcurementItems() {
        return this.procurementItems;
    }

    public void setProcurementItems(List<AddProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }
}
