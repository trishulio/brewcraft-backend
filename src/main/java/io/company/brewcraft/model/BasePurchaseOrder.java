package io.company.brewcraft.model;

import io.company.brewcraft.service.SupplierAccessor;

public interface BasePurchaseOrder extends SupplierAccessor {
    final String ATTR_ORDER_NUMBER = "orderNumber";

    String getOrderNumber();

    void setOrderNumber(String orderNumber);
}
