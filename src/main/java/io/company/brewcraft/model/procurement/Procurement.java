package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;

public class Procurement extends BaseEntity {
    private PurchaseOrder purchaseOrder;
    private Invoice invoice;

    public Procurement() {
    }

    public Procurement(final PurchaseOrder purchaseOrder, final Invoice invoice) {
        this();
        setPurchaseOrder(purchaseOrder);
        setInvoice(invoice);
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
}
