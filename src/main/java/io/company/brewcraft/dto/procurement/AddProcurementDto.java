package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.BaseDto;

public class AddProcurementDto extends BaseDto {
    private AddInvoiceDto invoice;
    private AddPurchaseOrderDto purchaseOrder;

    public AddProcurementDto() {
    }

    public AddProcurementDto(AddPurchaseOrderDto purchaseOrder, AddInvoiceDto invoice) {
        this();
        setPurchaseOrder(purchaseOrder);
        setInvoice(invoice);
    }

    public AddInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(AddInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public AddPurchaseOrderDto getPurchaseOrder() {
        return this.purchaseOrder;
    }

    public void setPurchaseOrder(AddPurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
