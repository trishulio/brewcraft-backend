package io.company.brewcraft.dto;

public class PurchaseOrderDto extends BaseDto {
    private Long id;
    private String orderNumber;
    private SupplierDto supplier;
    private Integer version;

    public PurchaseOrderDto() {
    }

    public PurchaseOrderDto(Long id) {
        setId(id);
    }

    public PurchaseOrderDto(Long id, String orderNumber, SupplierDto supplier, Integer version) {
        this(id);
        setOrderNumber(orderNumber);
        setSupplier(supplier);
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

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
