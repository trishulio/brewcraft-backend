package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseModel;

public class ProcurementId extends BaseModel {
    private Long shipmentId;
    private Long invoiceId;
    private Long purchaseOrderId;

    public ProcurementId() {
        super();
    }

    public ProcurementId(Long shipmentId, Long invoiceId, Long purchaseOrderId) {
        setShipmentId(shipmentId);
        setInvoiceId(invoiceId);
        setPurchaseOrderId(purchaseOrderId);
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}