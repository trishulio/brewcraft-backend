package io.company.brewcraft.dto.procurement;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;

public class UpdateProcurementItemDto extends BaseDto {
    @NotNull
    private UpdateProcurementInvoiceItemDto invoiceItem;

    @NotNull
    private UpdateProcurementMaterialLotDto materialLot;

    public UpdateProcurementItemDto() {
        super();
    }

    public UpdateProcurementItemDto(UpdateProcurementMaterialLotDto materialLot, UpdateProcurementInvoiceItemDto invoiceItem) {
        this();
        setMaterialLot(materialLot);
        setInvoiceItem(invoiceItem);
    }

    public UpdateProcurementInvoiceItemDto getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(UpdateProcurementInvoiceItemDto invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public UpdateProcurementMaterialLotDto getMaterialLot() {
        return materialLot;
    }

    public void setMaterialLot(UpdateProcurementMaterialLotDto materialLot) {
        this.materialLot = materialLot;
    }
}
