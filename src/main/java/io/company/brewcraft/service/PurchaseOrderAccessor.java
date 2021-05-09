package io.company.brewcraft.service;

import io.company.brewcraft.model.PurchaseOrder;

public interface PurchaseOrderAccessor {
    final String ATTR_PURCHASE_ORDER = "purchaseOrder";

    PurchaseOrder getPurchaseOrder();

    void setPurchaseOrder(PurchaseOrder order);
}
