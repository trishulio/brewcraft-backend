package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;

public class AddProcurementPurchaseOrderDto extends BaseDto {
    private String orderNumber;
    private Long supplierId;

    public AddProcurementPurchaseOrderDto() {
    }

    public AddProcurementPurchaseOrderDto(String orderNumber, Long supplierId) {
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
