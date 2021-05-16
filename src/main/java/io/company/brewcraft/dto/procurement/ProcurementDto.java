package io.company.brewcraft.dto.procurement;

import io.company.brewcraft.dto.BaseDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.model.Shipment;

public class ProcurementDto extends BaseDto {

    private InvoiceDto invoice;

    private ShipmentDto shipment;

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
