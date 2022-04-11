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

    public ProcurementLot(Long id, String lotNumber, Material material, String materialName, InvoiceItem invoiceItem, Shipment shipment, Storage storage, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(id, lotNumber, material, materialName, invoiceItem, shipment, storage, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Shipment shipment, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(shipment, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Shipment shipment, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(shipment, invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Storage storage, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(storage, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Storage storage, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(storage, invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Shipment shipment, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(shipment, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(shipment, invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Storage storage, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(storage, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(storage, invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, storage, material, materialName, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, storage, invoiceItem, displayUnit, qtyValueInSysUnit);
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        super(lotNumber, shipment, storage, invoiceItem, material, materialName, displayUnit, qtyValueInSysUnit);
    }
}
