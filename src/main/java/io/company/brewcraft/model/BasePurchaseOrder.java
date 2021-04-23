package io.company.brewcraft.model;

public interface BasePurchaseOrder {
    final String ATTR_ORDER_NUMBER = "orderNumber";
    final String ATTR_SUPPLIER = "supplier";

    public String getOrderNumber();

    public void setOrderNumber(String orderNumber);

    public Supplier getSupplier();

    public void setSupplier(Supplier supplier);
}
