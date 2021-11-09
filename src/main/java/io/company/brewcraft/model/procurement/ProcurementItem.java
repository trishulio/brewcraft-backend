package io.company.brewcraft.model.procurement;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.Tax;

public class ProcurementItem extends BaseEntity implements UpdateProcurementItem<Invoice, Shipment, Procurement>, Audited {

    private Procurement procurement;

    private MaterialLot lot;
    private InvoiceItem invoiceItem;

    public ProcurementItem() {
        this.lot = new MaterialLot();;
        this.invoiceItem = new InvoiceItem();
    }

    public ProcurementItem(ProcurementItemId id) {
        this();
        this.setId(id);
    }

    public ProcurementItem(MaterialLot lot, InvoiceItem invoiceItem) {
        this();

        Quantity<?> lotQty = null;
        Quantity<?> iQty = null;

        if (lot != null || invoiceItem != null) {
            lotQty = lot.getQuantity();
            iQty = invoiceItem.getQuantity();
        }

        if ((lotQty != null) && !lotQty.equals(iQty)) {
            throw new IllegalArgumentException("Expected the Lot and InvoiceItem to have equal quantities.");
        }

        updateProperties(lot);
        updateProperties(invoiceItem);
    }

    public ProcurementItem(ProcurementItemId id, String description, String lotNumber, Quantity<?> quantity, Storage storage, Money price, Tax tax, Material material, LocalDateTime createdAt, LocalDateTime lastUpdated,
            Integer invoiceItemVersion, Integer lotVersion) {
        this(id);
        this.setDescription(description);
        this.setLotNumber(lotNumber);
        this.setQuantity(quantity);
        this.setStorage(storage);
        this.setPrice(price);
        this.setTax(tax);
        this.setMaterial(material);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
        this.setInvoiceItemVersion(invoiceItemVersion);
        this.setVersion(lotVersion);
    }

    @Override
    public ProcurementItemId getId() {
        ProcurementItemId id = null;
        if (this.lot.getId() != null || this.invoiceItem.getId() != null) {
            id = new ProcurementItemId(this.lot.getId(), this.invoiceItem.getId());
        }
        return id;
    }

    @Override
    public void setId(ProcurementItemId id) {
        if (id != null) {
            this.lot.setId(id.getLotId());
            this.invoiceItem.setId(id.getInvoiceItemId());
        } else {
            this.lot.setId(null);
            this.invoiceItem.setId(null);
        }
    }

    @Override
    public String getDescription() {
        return this.invoiceItem.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.invoiceItem.setDescription(description);
    }

    @Override
    public String getLotNumber() {
        return this.lot.getLotNumber();
    }

    @Override
    public void setLotNumber(String lotNumber) {
        this.lot.setLotNumber(lotNumber);
    }

    @Override
    public Quantity<?> getQuantity() {
        return this.lot.getQuantity();
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.lot.setQuantity(quantity);
        this.invoiceItem.setQuantity(quantity);
    }

    @Override
    public Storage getStorage() {
        return this.lot.getStorage();
    }

    @Override
    public void setStorage(Storage storage) {
        this.lot.setStorage(storage);
    }

    @Override
    public Money getPrice() {
        return this.invoiceItem.getPrice();
    }

    @Override
    public void setPrice(Money price) {
        this.invoiceItem.setPrice(price);
    }

    @Override
    public Tax getTax() {
        return this.invoiceItem.getTax();
    }

    @Override
    public void setTax(Tax tax) {
        this.invoiceItem.setTax(tax);
    }

    @Override
    public Material getMaterial() {
        return this.invoiceItem.getMaterial();
    }

    @Override
    public void setMaterial(Material material) {
        this.invoiceItem.setMaterial(material);
    }

    @Override
    public Integer getInvoiceItemVersion() {
        return this.invoiceItem.getVersion();
    }

    @Override
    public void setInvoiceItemVersion(Integer invoiceItemVersion) {
        this.invoiceItem.setVersion(invoiceItemVersion);
    }

    @Override
    public Money getAmount() {
        return this.invoiceItem.getAmount();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.lot.getCreatedAt();
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.lot.setCreatedAt(createdAt);
        this.invoiceItem.setCreatedAt(createdAt);
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lot.getLastUpdated();
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lot.setLastUpdated(lastUpdated);
        this.invoiceItem.setLastUpdated(lastUpdated);
    }

    @Deprecated
    @Override
    @JsonIgnore
    public Invoice getInvoice() {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use buildInvoice() instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public void setInvoice(Invoice invoice) {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use updateProperties(Invoice invoice) instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public Shipment getShipment() {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use buildShipment() instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public void setShipment(Shipment shipment) {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use updateProperties(Shipment shipment) instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public InvoiceItem getInvoiceItem() {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use getProcurementItems() instead");
    }

    @Override
    @JsonIgnore
    @Deprecated
    public void setInvoiceItem(InvoiceItem item) {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use getProcurementItems() instead");
    }

    @Override
    public Integer getVersion() {
        return this.lot.getVersion();
    }

    @Override
    public void setVersion(Integer version) {
        this.lot.setVersion(version);
    }

    @Override
    @JsonBackReference
    public Procurement getProcurement() {
        return this.procurement;
    }

    @Override
    @JsonBackReference
    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public InvoiceItem buildInvoiceItem() {
        return this.invoiceItem.deepClone();
    }

    public MaterialLot buildMaterialLot() {
        return this.lot.deepClone();
    }

    public void updateProperties(MaterialLot lot) {
        if (lot != null) {
            this.lot = lot.deepClone();
            this.setQuantity(this.lot.getQuantity());
            this.setCreatedAt(this.lot.getCreatedAt());
            this.setLastUpdated(this.lot.getLastUpdated());
        } else {
            this.lot = new MaterialLot();
            this.invoiceItem.setQuantity(null);
            this.setCreatedAt(null);
            this.setLastUpdated(null);
        }
    }

    public void updateProperties(InvoiceItem invoiceItem) {
        if (invoiceItem != null) {
            this.invoiceItem = invoiceItem.deepClone();
            this.setQuantity(this.invoiceItem.getQuantity());
            this.setCreatedAt(this.invoiceItem.getCreatedAt());
            this.setLastUpdated(this.invoiceItem.getLastUpdated());
        } else {
            this.invoiceItem = new InvoiceItem();
            this.lot.setQuantity(null);
            this.setCreatedAt(null);
            this.setLastUpdated(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        // Note: Converting to string might not be the efficient way to compare
        if (o == null || !(o instanceof ProcurementItem)) {
            return false;
        }

        return this.toString().equals(o.toString());
    }
}
