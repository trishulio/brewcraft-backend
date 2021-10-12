package io.company.brewcraft.model.procurement;

import java.util.List;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;

public interface BaseProcurement<II extends BaseInvoiceItem<? extends BaseInvoice<II>>, ML extends BaseMaterialLot<? extends BaseShipment<ML>>, PI extends ProcurementItem> extends BaseInvoice<II>, BaseShipment<ML> {
    final String ATTR_PROCUREMENT_ITEMS = "procurementItems";
    final String ATTR_INVOICE_VERSION = "invoiceVersion";

    List<PI> getProcurementItems();

    void setProcurementItems(List<PI> procurementItems);

    Integer getInvoiceVersion();

    void setInvoiceVersion(Integer version);
}
