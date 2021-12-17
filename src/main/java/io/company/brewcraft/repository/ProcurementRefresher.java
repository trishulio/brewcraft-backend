package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;

public class ProcurementRefresher implements Refresher<Procurement, ProcurementAccessor> {
    private AccessorRefresher<ProcurementId, ProcurementAccessor, Procurement> accessorRefresher;
    private Refresher<Invoice, InvoiceAccessor> invoiceRefresher;
    private Refresher<Shipment, ShipmentAccessor> shipmentRefresher;

    public ProcurementRefresher(AccessorRefresher<ProcurementId, ProcurementAccessor, Procurement> accessorRefresher, Refresher<Invoice, InvoiceAccessor> invoiceRefresher, Refresher<Shipment, ShipmentAccessor> shipmentRefresher) {
        this.accessorRefresher = accessorRefresher;
        this.invoiceRefresher = invoiceRefresher;
        this.shipmentRefresher = shipmentRefresher;
    }

    @Override
    public void refresh(Collection<Procurement> entities) {
        this.invoiceRefresher.refreshAccessors(entities);
        this.shipmentRefresher.refreshAccessors(entities);
    }

    @Override
    public void refreshAccessors(Collection<? extends ProcurementAccessor> accessors) {
        this.accessorRefresher.refreshAccessors(accessors);
    }
}
