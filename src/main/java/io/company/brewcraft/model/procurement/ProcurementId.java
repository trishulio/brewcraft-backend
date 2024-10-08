package io.company.brewcraft.model.procurement;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.company.brewcraft.model.BaseEntity;

@Embeddable
public class ProcurementId extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "invoice_id")
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