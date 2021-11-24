package io.company.brewcraft.dto.procurement;

import java.util.List;

import io.company.brewcraft.dto.BaseDto;

public class UpdateProcurementDto extends BaseDto {
    private UpdateProcurementInvoiceDto invoice;
    private UpdateProcurementShipmentDto shipment;
    private UpdateProcurementPurchaseOrderDto purchaseOrder;

    private List<UpdateProcurementItemDto> procurementItems;

    public UpdateProcurementDto() {
        super();
    }

    public UpdateProcurementDto(UpdateProcurementInvoiceDto invoice, UpdateProcurementShipmentDto shipment, UpdateProcurementPurchaseOrderDto purchaseOrder, List<UpdateProcurementItemDto> procurementItems) {
        this();
        setInvoice(invoice);
        setShipment(shipment);
        setPurchaseOrder(purchaseOrder);
        setProcurementItems(procurementItems);
    }

    public UpdateProcurementInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(UpdateProcurementInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public UpdateProcurementShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(UpdateProcurementShipmentDto shipment) {
        this.shipment = shipment;
    }

    public UpdateProcurementPurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(UpdateProcurementPurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<UpdateProcurementItemDto> getProcurementItems() {
        return procurementItems;
    }

    public void setProcurementItems(List<UpdateProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }

}
