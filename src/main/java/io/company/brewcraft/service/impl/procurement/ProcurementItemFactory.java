package io.company.brewcraft.service.impl.procurement;

import java.util.ArrayList;
import java.util.List;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.ProcurementItem;

public class ProcurementItemFactory {
    public static final ProcurementItemFactory INSTANCE = new ProcurementItemFactory();

    public List<ProcurementItem> buildFromLotsAndItems(List<MaterialLot> lots, List<InvoiceItem> invoiceItems) {
        List<ProcurementItem> procurementItems = null;

        int lotsSize = -1;
        int invoiceItemsSize = -1;

        if (lots != null) {
            lotsSize = lots.size();
        }

        if (invoiceItems != null) {
            invoiceItemsSize = invoiceItems.size();
        }

        if (lotsSize != -1 || invoiceItemsSize != -1) {
            procurementItems = new ArrayList<>(Math.max(lotsSize, invoiceItemsSize));
        }

        int lotIdx = 0, invoiceItemIdx = 0;

        while (lotIdx < lotsSize && invoiceItemIdx < invoiceItemsSize) {
            ProcurementItem procurementItem = new ProcurementItem(lots.get(lotIdx++), invoiceItems.get(invoiceItemIdx++));

            procurementItems.add(procurementItem);
        }

        while (lotIdx < lotsSize) {
            ProcurementItem procurementItem = new ProcurementItem(lots.get(lotIdx++), null);

            procurementItems.add(procurementItem);
        }

        while (invoiceItemIdx < invoiceItemsSize) {
            ProcurementItem procurementItem = new ProcurementItem(null, invoiceItems.get(invoiceItemIdx++));

            procurementItems.add(procurementItem);
        }

        return procurementItems;
    }
}
