package io.company.brewcraft.dto.procurement;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.dto.BaseDto;

public class AddProcurementItemDto extends BaseDto {
    @NotNull
    private AddProcurementMaterialLotDto materialLot;

    @NotNull
    private AddProcurementInvoiceItemDto invoiceItem;

    public AddProcurementItemDto() {
        super();
    }

    public AddProcurementItemDto(AddProcurementMaterialLotDto materialLot, AddProcurementInvoiceItemDto invoiceItem) {
        this();
        setMaterialLot(materialLot);
        setInvoiceItem(invoiceItem);
    }

    public AddProcurementMaterialLotDto getMaterialLot() {
        return materialLot;
    }

    public void setMaterialLot(AddProcurementMaterialLotDto materialLot) {
        this.materialLot = materialLot;
    }

    public AddProcurementInvoiceItemDto getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(AddProcurementInvoiceItemDto invoiceItem) {
        this.invoiceItem = invoiceItem;
    }
}
