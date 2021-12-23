package io.company.brewcraft.dto;


public class AddPurchaseOrderDto extends BaseDto {
    private String orderNumber;
    private Long supplierId;

    public AddPurchaseOrderDto() {
    }

    public AddPurchaseOrderDto(String orderNumber, Long supplierId) {
        setOrderNumber(orderNumber);
        setSupplierId(supplierId);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
