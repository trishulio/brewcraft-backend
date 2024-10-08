package io.company.brewcraft.dto.procurement;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;

public class UpdateProcurementDto extends BaseDto {
    @NotNull
    private UpdateProcurementInvoiceDto invoice;

    @NotNull
    private UpdateProcurementShipmentDto shipment;

    @NotNull
    private List<UpdateProcurementItemDto> procurementItems;

    public UpdateProcurementDto() {
        super();
    }

    public UpdateProcurementDto(UpdateProcurementInvoiceDto invoice, UpdateProcurementShipmentDto shipment, List<UpdateProcurementItemDto> procurementItems) {
        this();
        setInvoice(invoice);
        setShipment(shipment);
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

    public List<UpdateProcurementItemDto> getProcurementItems() {
        return procurementItems;
    }

    public void setProcurementItems(List<UpdateProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }
}
