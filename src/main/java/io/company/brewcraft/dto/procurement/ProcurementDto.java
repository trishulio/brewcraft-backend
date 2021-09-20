package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PurchaseOrderDto;

public class ProcurementDto extends BaseDto {
    private PurchaseOrderDto purchaseOrder;
    private InvoiceDto invoice;

    public ProcurementDto() {
    }

    public ProcurementDto(PurchaseOrderDto purchaseOrder, InvoiceDto invoice) {
        this();
        setPurchaseOrder(purchaseOrder);
        setInvoice(invoice);
    }

    public PurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }
}
