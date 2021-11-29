package io.company.brewcraft.model.procurement;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.impl.procurement.ProcurementItemIdFactory;

public class ProcurementItem extends BaseEntity implements UpdateProcurementItem {
    private static final ProcurementItemIdFactory idFactory = ProcurementItemIdFactory.INSTANCE;
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
        return idFactory.build(this.materialLot, this.invoiceItem);
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
