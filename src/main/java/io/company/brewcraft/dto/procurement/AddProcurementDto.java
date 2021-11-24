package io.company.brewcraft.dto.procurement;

import java.util.List;

import io.company.brewcraft.dto.BaseDto;

public class AddProcurementDto extends BaseDto {
    private AddProcurementInvoiceDto invoice;
    private AddProcurementShipmentDto shipment;
    private AddProcurementPurchaseOrderDto purchaseOrder;
    private List<AddProcurementItemDto> procurementItems;

    public AddProcurementDto() {
        super();
    }

    public AddProcurementDto(AddProcurementInvoiceDto invoice, AddProcurementShipmentDto shipment, AddProcurementPurchaseOrderDto purchaseOrder, List<AddProcurementItemDto> procurementItems) {
        this();
        setInvoice(invoice);
        setShipment(shipment);
        setPurchaseOrder(purchaseOrder);
        setProcurementItems(procurementItems);
    }

    public AddProcurementInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(AddProcurementInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public AddProcurementShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(AddProcurementShipmentDto shipment) {
        this.shipment = shipment;
    }

    public AddProcurementPurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(AddProcurementPurchaseOrderDto purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<AddProcurementItemDto> getProcurementItems() {
        return procurementItems;
    }

    public void setProcurementItems(List<AddProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }
}
