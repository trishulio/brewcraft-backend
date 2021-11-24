package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;

public class ProcurementItem extends BaseEntity implements UpdateProcurementItem {
    private InvoiceItem invoiceItem;
    private MaterialLot materialLot;

    public ProcurementItem() {
        super();
    }

    public ProcurementItem(MaterialLot materialLot, InvoiceItem invoiceItem) {
        this();
        setMaterialLot(materialLot);
        setInvoiceItem(invoiceItem);
    }

    public ProcurementItem(ProcurementItemId id) {
        super();
        this.materialLot = new MaterialLot(id.getLotId());
        this.invoiceItem = new InvoiceItem(id.getInvoiceItemId());
    }

    @Override
    public ProcurementItemId getId() {
        ProcurementItemId id = null;

        if (this.invoiceItem != null || this.materialLot != null) {
            id = new ProcurementItemId();
        }

        if (this.invoiceItem != null) {
            id.setInvoiceItemId(this.invoiceItem.getId());
        }

        if (this.materialLot != null) {
            id.setLotId(this.materialLot.getId());
        }

        return id;
    }

    @Override
    public MaterialLot getMaterialLot() {
        return this.materialLot;
    }

    @Override
    public void setMaterialLot(MaterialLot materialLot) {
        if (materialLot != null) {
            this.materialLot = materialLot.deepClone();
            this.materialLot.setInvoiceItem(null);
        } else {
            materialLot = new MaterialLot();
        }
    }

    @Override
    public InvoiceItem getInvoiceItem() {
        return this.invoiceItem;
    }

    @Override
    public void setInvoiceItem(InvoiceItem invoiceItem) {
        if (invoiceItem != null) {
            this.invoiceItem = invoiceItem.deepClone();
        } else {
            this.invoiceItem = new InvoiceItem();
        }
    }
}
