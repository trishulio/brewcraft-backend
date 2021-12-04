package io.company.brewcraft.model.procurement;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.service.impl.procurement.ProcurementIdFactory;
import io.company.brewcraft.service.impl.procurement.ProcurementItemFactory;

@Entity(name = "procurement")
@Table
public class Procurement extends BaseEntity implements UpdateProcurement<ProcurementItem> {
    private static final long serialVersionUID = 1L;

    public static final String FIELD_SHIPMENT = "shipment";
    public static final String FIELD_INVOICE = "invoice";

    private static final ProcurementIdFactory idFactory = ProcurementIdFactory.INSTANCE;

    @EmbeddedId
    private ProcurementId id;

    @MapsId(ProcurementId.FIELD_INVOICE_ID)
    @ManyToOne
    @JoinColumn(name = ProcurementId.FIELD_INVOICE_ID, referencedColumnName = "id")
    private Invoice invoice;

    @MapsId(ProcurementId.FIELD_SHIPMENT_ID)
    @ManyToOne
    @JoinColumn(name = ProcurementId.FIELD_SHIPMENT_ID, referencedColumnName = "id")
    private Shipment shipment;

    public Procurement() {
        super();
    }

    public Procurement(ProcurementId id) {
        this();
        this.shipment = new Shipment(id.getShipmentId());
        this.invoice = new Invoice(id.getInvoiceId());
    }

    public Procurement(Shipment shipment, Invoice invoice) {
        super();
        setShipment(shipment);
        setInvoice(invoice);
    }
    
    @PrePersist
    private void setId() {
        this.id = new ProcurementId(null, null);
        if (this.shipment != null) {
            this.id.setShipmentId(this.shipment.getId());
        }

        if (this.invoice != null) {
            this.id.setInvoiceId(this.invoice.getId());
        }
    }

    @Override
    public ProcurementId getId() {
        return idFactory.build(shipment, invoice);
    }

    @Override
    public Invoice getInvoice() {
        return this.invoice;
    }

    @Override
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public Shipment getShipment() {
        return this.shipment;
    }

    @Override
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public List<ProcurementItem> getProcurementItems() {
        List<InvoiceItem> invoiceItems = null;
        List<MaterialLot> lots = null;

        if (invoice != null) {
            invoiceItems = invoice.getInvoiceItems();
        }

        if (shipment != null) {
            lots = shipment.getLots();
        }

        return ProcurementItemFactory.INSTANCE.buildFromLotsAndItems(lots, invoiceItems);
    }

    @Override
    public void setProcurementItems(List<ProcurementItem> procurementItems) {
        if (procurementItems == null) {
            if (this.invoice != null) {
                this.invoice.setInvoiceItems(null);
            }

            if (this.shipment != null) {
                this.shipment.setLots(null);
            }
        } else {
            if (this.invoice == null) {
                this.invoice = new Invoice();
            }

            if (this.shipment == null) {
                this.shipment = new Shipment();
            }

            procurementItems.forEach(procurementItem -> {
                InvoiceItem invoiceItem = procurementItem.getInvoiceItem();
                MaterialLot lot = procurementItem.getMaterialLot();

                this.invoice.addItem(invoiceItem);
                this.shipment.addLot(lot);
            });
        }
    }

    @JsonIgnore
    public int getProcurementItemsCount() {
        int invoiceItemCount = 0;
        int lotsCount = 0;

        if (invoice != null) {
            invoiceItemCount = this.invoice.getItemCount();
        }

        if (shipment != null) {
            lotsCount = this.shipment.getLotCount();
        }

        return Math.max(invoiceItemCount, lotsCount);
    }
}
