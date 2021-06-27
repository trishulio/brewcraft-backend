package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.ShipmentDto;

public class ProcurementDto extends BaseDto {
    private PurchaseOrderDto purchaseOrder;
    private InvoiceDto invoice;
    private ShipmentDto shipment;
    
    public ProcurementDto() {
    }
    
    public ProcurementDto(PurchaseOrderDto purchaseOrder, InvoiceDto invoice, ShipmentDto shipment) {
        this();
        setPurchaseOrder(purchaseOrder);
        setInvoice(invoice);
        setShipment(shipment);
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

    public ShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentDto shipment) {
        this.shipment = shipment;
    }
}
