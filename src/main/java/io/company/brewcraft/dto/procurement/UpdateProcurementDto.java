package io.company.brewcraft.dto.procurement;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.dto.UpdateShipmentDto;

public class UpdateProcurementDto extends BaseDto {
    private UpdateInvoiceDto invoice;

    private UpdatePurchaseOrderDto purchaseOrder;

    private UpdateShipmentDto shipment;

    private List<UpdateProcurementItemDto> procurementItems;

    @NotNull
    private Integer version;

    public UpdateProcurementDto() {
        super();
        this.invoice = new UpdateInvoiceDto();
        this.shipment = new UpdateShipmentDto();
    }

    public UpdateProcurementDto(ProcurementIdDto id) {
        this();
        setId(id);
    }

    public UpdateProcurementDto(ProcurementIdDto id, String invoiceNumber, String shipmentNumber, UpdatePurchaseOrderDto purchaseOrder, String description, FreightDto freight, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Long invoiceStatusId, Long shipmentStatusId, List<UpdateProcurementItemDto> procurementItems, Integer invoiceVersion, Integer version) {
        this(id);
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

    public UpdatePurchaseOrderDto getPurchaseOrder() {
        return this.purchaseOrder;
    }

    public void setPurchaseOrder(UpdatePurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getShipmentNumber() {
        return this.shipment.getShipmentNumber();
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipment.setShipmentNumber(shipmentNumber);
    }

    public String getDescription() {
        return this.invoice.getDescription();
    }

    public void setDescription(String desription) {
        this.invoice.setDescription(desription);
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

    public List<UpdateProcurementItemDto> getProcurementItems() {
        return this.procurementItems;
    }

    public void setProcurementItems(List<UpdateProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getInvoiceVersion() {
        return this.invoice.getVersion();
    }

    public void setInvoiceVersion(Integer invoiceVersion) {
        this.invoice.setVersion(invoiceVersion);
    }
}
