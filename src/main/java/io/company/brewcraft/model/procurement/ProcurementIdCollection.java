package io.company.brewcraft.model.procurement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProcurementIdCollection {
    private Set<Long> invoiceIds, shipmentIds;

    public ProcurementIdCollection(Collection<ProcurementId> procurementIds) {
        setProcurementIds(procurementIds);
    }

    public void setProcurementIds(Collection<ProcurementId> procurementIds) {
        Set<Long> invoiceIds = new HashSet<>(procurementIds.size());
        Set<Long> shipmentIds = new HashSet<>(procurementIds.size());

        procurementIds.forEach(id -> {
            invoiceIds.add(id.getInvoiceId());
            shipmentIds.add(id.getShipmentId());
        });

        this.invoiceIds = invoiceIds;
        this.shipmentIds = shipmentIds;
    }

    public Set<Long> getInvoiceIds() {
        return this.invoiceIds;
    }

    public Set<Long> getShipmentIds() {
        return this.shipmentIds;
    }
}