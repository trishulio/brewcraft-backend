package io.company.brewcraft.dto.procurement;


import io.company.brewcraft.dto.BaseDto;

public class ProcurementItemDto extends BaseDto {
    private ProcurementItemIdDto id;
    private ProcurementInvoiceItemDto invoiceItem;
    private ProcurementMaterialLotDto materialLot;

    public ProcurementItemDto() {
        super();
    }

    public ProcurementItemDto(ProcurementItemIdDto id) {
        this();
        setId(id);
    }

    public ProcurementItemDto(ProcurementItemIdDto id, ProcurementMaterialLotDto materialLot, ProcurementInvoiceItemDto invoiceItem) {
        this(id);
        setMaterialLot(materialLot);
        setInvoiceItem(invoiceItem);
    }

    public ProcurementItemIdDto getId() {
        return id;
    }

    public void setId(ProcurementItemIdDto id) {
        this.id = id;
    }

    public ProcurementInvoiceItemDto getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(ProcurementInvoiceItemDto invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public ProcurementMaterialLotDto getMaterialLot() {
        return materialLot;
    }

    public void setMaterialLot(ProcurementMaterialLotDto materialLot) {
        this.materialLot = materialLot;
    }
}