package io.company.brewcraft.dto.procurement;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;

public class AddProcurementDto extends BaseDto {
    @NotNull
    private AddProcurementInvoiceDto invoice;

    @NotNull
    private AddProcurementShipmentDto shipment;

    @NotNull
    private List<AddProcurementItemDto> procurementItems;

    public AddProcurementDto() {
        super();
    }

    public AddProcurementDto(AddProcurementInvoiceDto invoice, AddProcurementShipmentDto shipment, List<AddProcurementItemDto> procurementItems) {
        this();
        setInvoice(invoice);
        setShipment(shipment);
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

    public List<AddProcurementItemDto> getProcurementItems() {
        return procurementItems;
    }

    public void setProcurementItems(List<AddProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }
}
