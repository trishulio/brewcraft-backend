package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "procurement_lot")
@Table
public class ProcurementLot extends Lot {
    public ProcurementLot(Long id) {
        this.setId(id);
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
