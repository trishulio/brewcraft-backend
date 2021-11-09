package io.company.brewcraft.model.procurement;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;

public interface BaseProcurementItem<I extends BaseInvoice<? extends BaseInvoiceItem<I>>, S extends BaseShipment<? extends BaseMaterialLot<S>>, P extends UpdateProcurement<? extends BaseInvoiceItem<I>, ? extends BaseMaterialLot<S>, ? extends BaseProcurementItem<I, S, P>>> extends BaseInvoiceItem<I>, BaseMaterialLot<S> {
    final String ATTR_INVOICE_ITEM_ID = "invoiceItemId";
    final String ATTR_PROCUREMENT = "procurement";
    final String ATTR_INVOICE_ITEM_VERSION = "invoiceItemVersion";

    P getProcurement();

    void setProcurement(P procurement);

    Integer getInvoiceItemVersion();

    void setInvoiceItemVersion(Integer version);
}
