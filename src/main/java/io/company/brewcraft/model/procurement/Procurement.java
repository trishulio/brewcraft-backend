package io.company.brewcraft.model.procurement;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;

public class Procurement extends BaseEntity implements UpdateProcurement<ProcurementItem> {
    private PurchaseOrder purchaseOrder;
    private Invoice invoice;
    private Shipment shipment;

    private List<ProcurementItem> procurementItems;

    public Procurement() {
        super();
        setShipment(null);
        setInvoice(null);
        setPurchaseOrder(null);
    }

    public Procurement(ProcurementId id) {
        super();
        this.shipment = new Shipment(id.getShipmentId());
        this.invoice = new Invoice(id.getInvoiceId());
        this.purchaseOrder = new PurchaseOrder(id.getPurchaseOrderId());
    }

    public Procurement(Shipment shipment, Invoice invoice, PurchaseOrder purchaseOrder, List<ProcurementItem> procurementItems) {
        super();
        setShipment(shipment);
        setInvoice(invoice);
        setPurchaseOrder(purchaseOrder);
        setProcurementItems(procurementItems);
    }

    @Override
    public ProcurementId getId() {
        ProcurementId id = null;

        if (shipment != null || invoice != null || purchaseOrder != null) {
            id = new ProcurementId();
        }

        if (shipment != null) {
            id.setShipmentId(shipment.getId());
        }

        if (invoice != null) {
            id.setInvoiceId(invoice.getId());
        }

        if (purchaseOrder != null) {
            id.setPurchaseOrderId(purchaseOrder.getId());
        }

        return id;
    }

    @Override
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    @Override
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder != null) {
            this.purchaseOrder = purchaseOrder.deepClone();
        } else {
            this.purchaseOrder = new PurchaseOrder();
        }
    }

    @Override
    public Invoice getInvoice() {
        Invoice invoice = null;
        if (this.invoice != null) {
            invoice = this.invoice.deepClone();
        }

        List<InvoiceItem> invoiceItems = null;
        if (this.procurementItems != null) {
            invoiceItems = this.procurementItems.stream().map(ProcurementItem::getInvoiceItem).collect(Collectors.toList());
        }

        if (invoice == null && invoiceItems != null) {
            invoice = new Invoice();
        }

        if (invoice != null) {
            invoice.setInvoiceItems(invoiceItems);
        }

        return invoice;
    }

    @Override
    public void setInvoice(Invoice invoice) {
        if (invoice != null) {
            this.invoice = invoice.deepClone();
            this.invoice.setInvoiceItems(null);
            this.invoice.setPurchaseOrder(null);
        } else {
            this.invoice = new Invoice();
        }
    }

    @Override
    public Shipment getShipment() {
        Shipment shipment = null;
        if (this.shipment != null) {
            shipment = this.shipment.deepClone();
        }

        List<MaterialLot> lots = null;
        if (this.procurementItems != null) {
            lots = this.procurementItems.stream().map(ProcurementItem::getMaterialLot).collect(Collectors.toList());
        }

        if (shipment == null && lots != null) {
            shipment = new Shipment();
            shipment.setLots(lots);
        }

        if (shipment != null) {
            shipment.setLots(lots);
        }

        return shipment;
    }

    @Override
    public void setShipment(Shipment shipment) {
        if (shipment != null) {
            this.shipment = shipment.deepClone();
            this.shipment.setLots(null);
        } else {
            this.shipment = new Shipment();
        }
    }

    @Override
    public List<ProcurementItem> getProcurementItems() {
        return procurementItems;
    }

    @Override
    public void setProcurementItems(List<ProcurementItem> procurementItems) {
        this.procurementItems = procurementItems;

    }

    @JsonIgnore
    public int getProcurementCount() {
        int count = 0;

        if (this.invoice != null) {
            count = this.invoice.getItemCount();
        }

        if (this.shipment != null) {
            count = Math.max(count, this.shipment.getLotCount());
        }

        return count;
    }
}
