package io.company.brewcraft.model.procurement;


import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;


public class Procurement {

    private Invoice invoice;

    private Shipment shipment;

    public Procurement(final Invoice invoice, final Shipment shipment) {
        setInvoice(invoice);
        setShipment(shipment);
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
