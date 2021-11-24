package io.company.brewcraft.service.impl.procurement;

import java.util.List;
import java.util.stream.Collectors;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;

public class ProcurementFactory {
    private ProcurementItemFactory procurementItemFactory;

    public ProcurementFactory(ProcurementItemFactory procurementItemFactory) {
        this.procurementItemFactory = procurementItemFactory;
    }

    public Procurement buildFromShipment(Shipment shipment) {
        Procurement procurement = null;
        Invoice invoice = null;
        PurchaseOrder purchaseOrder = null;
        List<ProcurementItem> procurementItems = null;

        if (shipment != null) {
            invoice = shipment.getFirstInvoice();

            if (shipment.getLotCount() > 0) {
                procurementItems = shipment.getLots().stream().map(s -> procurementItemFactory.buildFromLot(s)).collect(Collectors.toList());
            }
        }

        if (invoice != null) {
            purchaseOrder = invoice.getPurchaseOrder();
        }

        if (shipment != null) {
            // Ideally, if shipment != null, then invoice and purchaseOrder != null as well
            // in case of procurement. But handling null values here regardless.
            procurement = new Procurement(shipment, invoice, purchaseOrder, procurementItems);
        }

        return procurement;
    }

    public boolean isEligibleShipmentForProcurement(ProcurementId procurementId, Shipment shipment) {
        boolean isEligible = false;
        Invoice invoice = null;
        PurchaseOrder purchaseOrder = null;

        if (shipment != null && shipment.getId().equals(procurementId.getShipmentId())) {
            // Note: Procurement service assumes 1-1 relationship between an Invoice
            // and a Shipment. Therefore firstInvoice == the only invoice a shipment has.
            invoice = shipment.getFirstInvoice();
        }

        if (invoice != null && invoice.getId().equals(procurementId.getInvoiceId())) {
            purchaseOrder = invoice.getPurchaseOrder();
        }

        if (purchaseOrder != null && purchaseOrder.getId().equals(procurementId.getPurchaseOrderId())) {
            isEligible = true;
        }

        return isEligible;
    }

    public Procurement buildFromShipmentIfIdMatches(ProcurementId procurementId, Shipment shipment) {
        Procurement procurement = null;
        boolean isEligible = this.isEligibleShipmentForProcurement(procurementId, shipment);

        if (isEligible) {
            procurement = this.buildFromShipment(shipment);
        }

        return procurement;
    }
}
