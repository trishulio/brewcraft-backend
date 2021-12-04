package io.company.brewcraft.model.procurement;

import java.util.List;

import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.ShipmentAccessor;

public interface BaseProcurement<BPI extends BaseProcurementItem> extends InvoiceAccessor, ShipmentAccessor {
    List<BPI> getProcurementItems();

    void setProcurementItems(List<BPI> procurementItems);
}
