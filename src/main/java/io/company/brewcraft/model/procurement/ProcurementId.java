package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseModel;

public class ProcurementId extends BaseModel {
    private Long shipmentId;
    private Long invoiceId;

    public ProcurementId() {
        super();
    }

    public ProcurementId(Long shipmentId, Long invoiceId) {
        setShipmentId(shipmentId);
        setInvoiceId(invoiceId);
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
}
