package io.company.brewcraft.service.impl.procurement;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.ProcurementId;

public class ProcurementIdFactory {
    public static final ProcurementIdFactory INSTANCE = new ProcurementIdFactory();

    protected ProcurementIdFactory() {
    }

    public ProcurementId build(Shipment shipment, Invoice invoice) {
        ProcurementId id = null;

        if (shipment != null || invoice != null) {
            id = new ProcurementId();
        }

        if (shipment != null) {
            id.setShipmentId(shipment.getId());
        }

        if (invoice != null) {
            id.setInvoiceId(invoice.getId());
        }

        return id;
    }
}
