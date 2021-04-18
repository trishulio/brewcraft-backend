package io.company.brewcraft.service;

import io.company.brewcraft.model.PurchaseOrder;

public interface PurchaseOrderAccessor {
    PurchaseOrder getPurchaseOrder();

    void setPurchaseOrder(PurchaseOrder order);
}
