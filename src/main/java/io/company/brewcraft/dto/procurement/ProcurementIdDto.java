package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;

public class ProcurementIdDto extends BaseDto {
    private Long shipmentId;
    private Long invoiceId;

    public ProcurementIdDto() {
        super();
    }

    public ProcurementIdDto(Long shipmentId, Long invoiceId) {
        setShipmentId(shipmentId);
        setInvoiceId(invoiceId);
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
}
