package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;

public class ProcurementItemIdDto extends BaseDto {
    private Long lotId;
    private Long invoiceItemId;

    public ProcurementItemIdDto() {
        super();
    }

    public ProcurementItemIdDto(Long lotId, Long invoiceItemId) {
        setLotId(lotId);
        setInvoiceItemId(invoiceItemId);
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Long invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }
}
