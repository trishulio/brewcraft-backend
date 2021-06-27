package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;

public class Procurement extends BaseEntity {
    private PurchaseOrder purchaseOrder;

    private Invoice invoice;

    private Shipment shipment;

    public Procurement() {
    }

    public Procurement(final PurchaseOrder purchaseOrder, final Invoice invoice, final Shipment shipment) {
        this();
        setPurchaseOrder(purchaseOrder);
        setInvoice(invoice);
        setShipment(shipment);
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
