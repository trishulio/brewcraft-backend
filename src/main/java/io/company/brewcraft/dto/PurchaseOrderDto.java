package io.company.brewcraft.dto;

public class PurchaseOrderDto extends BaseDto {
    private Long id;
    private String orderNumber;
    private SupplierDto supplier;

    public PurchaseOrderDto() {
    }

    public PurchaseOrderDto(Long id) {
        setId(id);
    }

    public PurchaseOrderDto(Long id, String orderNumber) {
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

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }
}
