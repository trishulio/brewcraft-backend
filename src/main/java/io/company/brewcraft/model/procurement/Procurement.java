package io.company.brewcraft.model.procurement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseEntity;
import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Tax;

public class Procurement extends BaseEntity implements UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>, Audited {
    private static final Logger log = LoggerFactory.getLogger(Procurement.class);

    private Shipment shipment;
    private Invoice invoice;

    public Procurement(Shipment shipment, Invoice invoice) {
        this();
        updateProperties(shipment);
        updateProperties(invoice);
    }

    public Procurement(Shipment shipment) {
        this();
        Invoice invoice = null;
        if (shipment.getLotCount() > 0) {
            InvoiceItem invoiceItem = shipment.getLots().get(0).getInvoiceItem();
            if (invoiceItem != null) {
                invoice = invoiceItem.getInvoice();
            }
        }

        updateProperties(invoice);
        updateProperties(shipment);
    }

    public Procurement() {
        super();
        this.shipment = new Shipment();
        this.invoice = new Invoice();
    }

    public Procurement(ProcurementId id) {
        this();
        this.setId(id);
    }

    public Procurement(ProcurementId id, String invoiceNumber, String shipmentNumber, String description, PurchaseOrder purchaseOrder, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, LocalDateTime deliveryDueDate, LocalDateTime deliveredDate, Freight freight, LocalDateTime createdAt,
            LocalDateTime lastUpdated, InvoiceStatus invoiceStatus, ShipmentStatus shipmentStatus, List<ProcurementItem> procurementItems, Integer invoiceVersion, Integer shipmentVersion) {
        this(id);
        this.setInvoiceNumber(invoiceNumber);
        this.setShipmentNumber(shipmentNumber);
        this.setDescription(description);
        this.setPurchaseOrder(purchaseOrder);
        this.setGeneratedOn(generatedOn);
        this.setReceivedOn(receivedOn);
        this.setPaymentDueDate(paymentDueDate);
        this.setDeliveryDueDate(deliveryDueDate);
        this.setDeliveredDate(deliveredDate);
        this.setFreight(freight);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
        this.setInvoiceStatus(invoiceStatus);
        this.setShipmentStatus(shipmentStatus);
        this.setProcurementItems(procurementItems);
        this.setInvoiceVersion(invoiceVersion);
        this.setVersion(shipmentVersion);
    }

    @Override
    public ProcurementId getId() {
        ProcurementId id = null;
        if (this.shipment.getId() != null || this.invoice.getId() != null) {
            id = new ProcurementId(this.shipment.getId(), this.invoice.getId());
        }
        return id;
    }

    @Override
    public void setId(ProcurementId id) {
        if (id != null) {
            this.invoice.setId(id.getInvoiceId());
            this.shipment.setId(id.getShipmentId());
        } else {
            this.invoice.setId(null);
            this.shipment.setId(null);
        }
    }
    @Override
    public String getInvoiceNumber() {
        return this.invoice.getInvoiceNumber();
    }

    @Override
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoice.setInvoiceNumber(invoiceNumber);
    }

    @Override
    public String getDescription() {
        return this.invoice.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.invoice.setDescription(description);
        this.shipment.setDescription(description);
    }

    @Override
    public LocalDateTime getGeneratedOn() {
        return this.invoice.getGeneratedOn();
    }

    @Override
    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.invoice.setGeneratedOn(generatedOn);
    }

    @Override
    public LocalDateTime getReceivedOn() {
        return this.invoice.getReceivedOn();
    }

    @Override
    public void setReceivedOn(LocalDateTime receivedOn) {
        this.invoice.setReceivedOn(receivedOn);
    }

    @Override
    public LocalDateTime getPaymentDueDate() {
        return this.invoice.getPaymentDueDate();
    }

    @Override
    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.invoice.setPaymentDueDate(paymentDueDate);
    }

    @Override
    public Freight getFreight() {
        return this.invoice.getFreight();
    }

    @Override
    public void setFreight(Freight freight) {
        this.invoice.setFreight(freight);
    }

    @Override
    public InvoiceStatus getInvoiceStatus() {
        return this.invoice.getInvoiceStatus();
    }

    @Override
    public void setInvoiceStatus(InvoiceStatus status) {
        this.invoice.setInvoiceStatus(status);
    }

    @Override
    public PurchaseOrder getPurchaseOrder() {
        return this.invoice.getPurchaseOrder();
    }

    @Override
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.invoice.setPurchaseOrder(purchaseOrder);
    }

    @Override
    public String getShipmentNumber() {
        return this.shipment.getShipmentNumber();
    }

    @Override
    public void setShipmentNumber(String shipmentNumber) {
        this.shipment.setShipmentNumber(shipmentNumber);
    }

    @Override
    public LocalDateTime getDeliveryDueDate() {
        return this.shipment.getDeliveryDueDate();
    }

    @Override
    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.shipment.setDeliveryDueDate(deliveryDueDate);
    }

    @Override
    public LocalDateTime getDeliveredDate() {
        return this.shipment.getDeliveredDate();
    }

    @Override
    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.shipment.setDeliveredDate(deliveredDate);
    }

    @Override
    public ShipmentStatus getShipmentStatus() {
        return this.shipment.getShipmentStatus();
    }

    @Override
    public void setShipmentStatus(ShipmentStatus status) {
        this.shipment.setShipmentStatus(status);
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.shipment.getCreatedAt();
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.shipment.setCreatedAt(createdAt);
        this.invoice.setCreatedAt(createdAt);
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.shipment.getLastUpdated();
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.shipment.setLastUpdated(lastUpdated);
        this.invoice.setLastUpdated(lastUpdated);
    }

    @Deprecated
    @Override
    @JsonIgnore
    public List<InvoiceItem> getItems() {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use getProcurementItems() instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public void setItems(List<InvoiceItem> items) {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use setProcurementItems(List<ProcurementItems> procurementItems) instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public List<MaterialLot> getLots() {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use buildMaterialLot() instead");
    }

    @Deprecated
    @Override
    @JsonIgnore
    public void setLots(List<MaterialLot> items) {
        throw new NoSuchMethodError("This method is not implemented for not being required. Use setMaterialLot(List<MaterialLot> materialLots) instead");
    }

    @Override
    @JsonManagedReference
    public List<ProcurementItem> getProcurementItems() {
        List<ProcurementItem> procurementItems = null;
        List<InvoiceItem> invoiceItems = invoice.getItems();
        List<MaterialLot> lots = shipment.getLots();

        if (invoiceItems != null || lots != null) {
            procurementItems = new ArrayList<>();
        }

        if (invoiceItems != null) {
            for (int i= 0; i < invoiceItems.size(); i++) {
                ProcurementItem procurementItem = new ProcurementItem();
                procurementItem.updateProperties(invoiceItems.get(i));
                procurementItems.add(procurementItem);
            }
        }

        if (lots != null) {
            int min = Math.min(procurementItems.size(), lots.size());
            for (int i = 0; i < min; i++) {
                procurementItems.get(i).updateProperties(lots.get(i));
            }

            for(int i = min; i < lots.size(); i++) {
                ProcurementItem procurementItem = new ProcurementItem();
                procurementItem.updateProperties(lots.get(i));
                procurementItems.add(procurementItem);
            }
        }

        return procurementItems;
    }

    @Override
    @JsonManagedReference
    public void setProcurementItems(List<ProcurementItem> procurementItems) {
        final List<InvoiceItem> invoiceItems = new ArrayList<>();
        final List<MaterialLot> lots = new ArrayList<>();

        procurementItems.forEach(procurementItem -> {
            invoiceItems.add(procurementItem.buildInvoiceItem());
            lots.add(procurementItem.buildMaterialLot());
        });

        this.invoice.setItems(invoiceItems);
        this.shipment.setLots(lots);
    }

    @Override
    @JsonIgnore
    public Money getAmount() {
        return this.invoice.getAmount();
    }

    @Override
    @JsonIgnore
    public Tax getTax() {
        return this.invoice.getTax();
    }

    @Override
    public Integer getVersion() {
        return this.shipment.getVersion();
    }

    @Override
    public void setVersion(Integer version) {
        this.shipment.setVersion(version);
    }

    @Override
    public Integer getInvoiceVersion() {
        return this.invoice.getVersion();
    }

    @Override
    public void setInvoiceVersion(Integer version) {
        this.invoice.setVersion(version);
    }


    public Invoice buildInvoice() {
        return this.invoice.deepClone();
    }

    public Shipment buildShipmentWithoutInvoice() {
        return this.shipment.deepClone();
    }

    public void updateProperties(Shipment shipment) {
        if (shipment != null) {
            this.shipment = shipment.deepClone();
            this.setDescription(this.shipment.getDescription());
            this.setCreatedAt(this.shipment.getCreatedAt());
            this.setLastUpdated(this.shipment.getLastUpdated());
        } else {
            this.shipment = new Shipment();
            this.setDescription(null);
            this.setCreatedAt(null);
            this.setLastUpdated(null);
        }
    }

    public void updateProperties(Invoice invoice) {
        if (invoice != null) {
            this.invoice = invoice.deepClone();
            this.setDescription(this.invoice.getDescription());
            this.setCreatedAt(this.invoice.getCreatedAt());
            this.setLastUpdated(this.invoice.getLastUpdated());
        } else {
            this.invoice = new Invoice();
            this.setDescription(null);
            this.setCreatedAt(null);
            this.setLastUpdated(null);
        }
    }

    public int itemCount() {
        return Math.max(this.invoice.getItemCount(), this.shipment.getLotCount());
    }
}
