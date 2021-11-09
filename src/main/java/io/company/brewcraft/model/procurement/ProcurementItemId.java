package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseModel;

public class ProcurementItemId extends BaseModel {
    private Long lotId;
    private Long invoiceItemId;

    public ProcurementItemId() {
        super();
    }

    public ProcurementItemId(Long lotId, Long invoiceItemId) {
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
