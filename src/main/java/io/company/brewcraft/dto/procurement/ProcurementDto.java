package io.company.brewcraft.dto.procurement;

import java.util.List;


import io.company.brewcraft.dto.BaseDto;

public class ProcurementDto extends BaseDto {
    private ProcurementIdDto id;
    private ProcurementInvoiceDto invoice;
    private ProcurementShipmentDto shipment;
    private List<ProcurementItemDto> procurementItems;

    public ProcurementDto() {
        super();
    }

    public ProcurementDto(ProcurementIdDto id) {
        this();
        setId(id);
    }

    public ProcurementDto(ProcurementIdDto id, ProcurementInvoiceDto invoice, ProcurementShipmentDto shipment, List<ProcurementItemDto> procurementItems) {
        this(id);
        setInvoice(invoice);
        setShipment(shipment);
        setProcurementItems(procurementItems);
    }

    public ProcurementIdDto getId() {
        return this.id;
    }

    public void setId(ProcurementIdDto id) {
        this.id = id;
    }

    public ProcurementShipmentDto getShipment() {
        return shipment;
    }

    public void setShipment(ProcurementShipmentDto shipment) {
        this.shipment = shipment;
    }

    public ProcurementInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(ProcurementInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public List<ProcurementItemDto> getProcurementItems() {
        return procurementItems;
    }

    public void setProcurementItems(List<ProcurementItemDto> procurementItems) {
        this.procurementItems = procurementItems;
    }
}
