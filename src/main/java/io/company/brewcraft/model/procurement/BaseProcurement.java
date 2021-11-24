package io.company.brewcraft.model.procurement;

import java.util.List;

import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.ShipmentAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface BaseProcurement<BPI extends BaseProcurementItem> extends PurchaseOrderAccessor, InvoiceAccessor, ShipmentAccessor {
    List<BPI> getProcurementItems();

    void setProcurementItems(List<BPI> procurementItems);
}
