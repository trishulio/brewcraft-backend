package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UpdatePurchaseOrderDto extends BaseDto {
    private String orderNumber;
    private Long supplierId;
    private Integer version;

    public UpdatePurchaseOrderDto() {
    }

    public UpdatePurchaseOrderDto(String orderNumber, Long supplierId, Integer version) {
        setOrderNumber(orderNumber);
        setSupplierId(supplierId);
        setVersion(version);
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
