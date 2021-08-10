package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity(name = "procurement_lot")
@Table
@Immutable
public class ProcurementLot extends Lot {
    public ProcurementLot() {
    }

    public ProcurementLot(Long id) {
        this();
        this.setId(id);
    }

    public ProcurementLot(Long id, String lotNumber, Quantity<?> quantity, Material material, Shipment shipment, InvoiceItem invoiceItem, Storage storage) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setQuantity(quantity);
        this.setMaterial(material);
        this.setShipment(shipment);
        this.setInvoiceItem(invoiceItem);
        this.setStorage(storage);
    }

    public ProcurementLot(String lotNumber, UnitEntity unit, BigDecimal value) {
        this.setLotNumber(lotNumber);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(Shipment shipment, UnitEntity unit, BigDecimal value) {
        this.setShipment(shipment);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(Material material, UnitEntity unit, BigDecimal value) {
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(Storage storage, UnitEntity unit, BigDecimal value) {
        this.setStorage(storage);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(String lotNumber, Material material, UnitEntity unit, BigDecimal value) {
        this.setLotNumber(lotNumber);
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(Shipment shipment, Material material, UnitEntity unit, BigDecimal value) {
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(Storage storage, Material material, UnitEntity unit, BigDecimal value) {
        this.setStorage(storage);
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Material material, UnitEntity unit, BigDecimal value) {
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }

    public ProcurementLot(String lotNumber, Shipment shipment, Storage storage, Material material, UnitEntity unit, BigDecimal value) {
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setStorage(storage);
        this.setMaterial(material);
        this.setQuantity(new QuantityEntity(unit, value));
    }
}
