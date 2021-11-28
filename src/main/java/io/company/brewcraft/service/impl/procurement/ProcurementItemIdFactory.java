package io.company.brewcraft.service.impl.procurement;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.ProcurementItemId;

public class ProcurementItemIdFactory {
    public static final ProcurementItemIdFactory INSTANCE = new ProcurementItemIdFactory();
    
    protected ProcurementItemIdFactory() { }

    public ProcurementItemId build(MaterialLot materialLot, InvoiceItem invoiceItem) {
        ProcurementItemId id = null;

        if (invoiceItem != null || materialLot != null) {
            id = new ProcurementItemId();
        }

        if (invoiceItem != null) {
            id.setInvoiceItemId(invoiceItem.getId());
        }

        if (materialLot != null) {
            id.setLotId(materialLot.getId());
        }

        return id;
    }
}
