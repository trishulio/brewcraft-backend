package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BaseModel;

public class PurchaseOrder extends BaseModel {
    private Long id;
    private String orderNumber;
    private Supplier supplier;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id) {
        setId(id);
    }

    public PurchaseOrder(Long id, String orderNumber) {
        this(id);
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
