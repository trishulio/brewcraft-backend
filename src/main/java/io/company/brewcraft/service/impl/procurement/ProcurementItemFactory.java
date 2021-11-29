package io.company.brewcraft.service.impl.procurement;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.ProcurementItem;

public class ProcurementItemFactory {
    public ProcurementItem buildFromLot(MaterialLot lot) {
        ProcurementItem procurementItem = null;

        if (lot != null) {
            procurementItem = new ProcurementItem(lot, lot.getInvoiceItem());
        }

        return procurementItem;
    }
}
