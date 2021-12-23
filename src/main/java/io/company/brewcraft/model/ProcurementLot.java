package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "procurement_lot")
@Table
@Immutable
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class ProcurementLot extends Lot {
    public ProcurementLot() {
        super();
    }

    public ProcurementLot(Long id) {
        super(id);
    }

    public ProcurementLot(Long id, String lotNumber, Quantity<?> quantity, Material material, Shipment shipment, InvoiceItem invoiceItem, Storage storage) {
        super(id, lotNumber, quantity, material, shipment, invoiceItem, storage);
    }

    public ProcurementLot(Long id, String lotNumber, Material material, String materialName, InvoiceItem invoiceItem, Shipment shipment, Storage storage, UnitEntity unit, BigDecimal value) {
        super(id, lotNumber, material, materialName, invoiceItem, shipment, storage, unit, value);
    }

    public ProcurementLot(String lotNumber, UnitEntity unit, BigDecimal value) {
        super(lotNumber, unit, value);
    }

    public ProcurementLot(String lotNumber, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, invoiceItem, unit, value);
    }

    public ProcurementLot(Shipment shipment, UnitEntity unit, BigDecimal value) {
        super(shipment, unit, value);
    }

    public ProcurementLot(Shipment shipment, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(shipment, invoiceItem, unit, value);
    }

    public ProcurementLot(Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(material, materialName, unit, value);
    }

    public ProcurementLot(InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(invoiceItem, material, materialName, unit, value);
    }

    public ProcurementLot(Storage storage, UnitEntity unit, BigDecimal value) {
        super(storage, unit, value);
    }

    public ProcurementLot(Storage storage, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(storage, invoiceItem, unit, value);
    }

    public ProcurementLot(InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(invoiceItem, unit, value);
    }

    public ProcurementLot(String lotNumber, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, material, materialName, unit, value);
    }

    public ProcurementLot(String lotNumber, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, invoiceItem, material, materialName, unit, value);
    }

    public ProcurementLot(Shipment shipment, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(shipment, material, materialName, unit, value);
    }

    public ProcurementLot(Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(shipment, invoiceItem, material, materialName, unit, value);
    }

    public ProcurementLot(Storage storage, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(storage, material, materialName, unit, value);
    }

    public ProcurementLot(Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(storage, invoiceItem, material, materialName, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, material, materialName, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, invoiceItem, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, invoiceItem, material, materialName, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, material, materialName, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, invoiceItem, unit, value);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, invoiceItem, material, materialName, unit, value);
    }
}
