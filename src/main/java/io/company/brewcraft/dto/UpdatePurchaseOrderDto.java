package io.company.brewcraft.dto;

public class UpdatePurchaseOrderDto extends BaseDto {
    private Long id;
    private String orderNumber;
    private Long supplierId;
    private Integer version;

    public UpdatePurchaseOrderDto() {
        super();
    }

    public UpdatePurchaseOrderDto(Long id) {
        this();
        setId(id);
    }

    public UpdatePurchaseOrderDto(Long id, String orderNumber, Long supplierId, Integer version) {
        this(id);
        setOrderNumber(orderNumber);
        setSupplierId(supplierId);
        setVersion(version);
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
