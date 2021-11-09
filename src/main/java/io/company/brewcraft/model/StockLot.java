package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Immutable;

@Entity(name = "stock_lot")
@Table
@Immutable
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class StockLot extends Lot {
    public StockLot() {
        super();
    }

    public StockLot(Long id) {
        super(id);
    }

    public StockLot(Long id, String lotNumber, Quantity<?> quantity, Material material, Shipment shipment, InvoiceItem invoiceItem, Storage storage) {
        super(id, lotNumber, quantity, material, shipment, invoiceItem, storage);
    }

    public StockLot(Long id, String lotNumber, Material material, String materialName, InvoiceItem invoiceItem, Shipment shipment, Storage storage, UnitEntity unit, BigDecimal value) {
        super(id, lotNumber, material, materialName, invoiceItem, shipment, storage, unit, value);
    }

    public StockLot(String lotNumber, UnitEntity unit, BigDecimal value) {
        super(lotNumber, unit, value);
    }

    public StockLot(String lotNumber, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, invoiceItem, unit, value);
    }

    public StockLot(Shipment shipment, UnitEntity unit, BigDecimal value) {
        super(shipment, unit, value);
    }

    public StockLot(Shipment shipment, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(shipment, invoiceItem, unit, value);
    }

    public StockLot(Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(material, materialName, unit, value);
    }

    public StockLot(Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(material, invoiceItem, materialName, unit, value);
    }

    public StockLot(Storage storage, UnitEntity unit, BigDecimal value) {
        super(storage, unit, value);
    }

    public StockLot(Storage storage, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(storage, invoiceItem, unit, value);
    }

    public StockLot(InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(invoiceItem, unit, value);
    }

    public StockLot(String lotNumber, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, material, materialName, unit, value);
    }

    public StockLot(String lotNumber, Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, material, invoiceItem, materialName, unit, value);
    }

    public StockLot(Shipment shipment, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(shipment, material, materialName, unit, value);
    }

    public StockLot(Shipment shipment, Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(shipment, material, invoiceItem, materialName, unit, value);
    }

    public StockLot(Storage storage, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(storage, material, materialName, unit, value);
    }

    public StockLot(Storage storage, Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(storage, material, invoiceItem, materialName, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, material, materialName, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, invoiceItem, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, material, invoiceItem, materialName, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, Storage storage, Material material, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, material, materialName, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, invoiceItem, unit, value);
    }

    public StockLot(String lotNumber, Shipment shipment, Storage storage, Material material, InvoiceItem invoiceItem, String materialName, UnitEntity unit, BigDecimal value) {
        super(lotNumber, shipment, storage, material, invoiceItem, materialName, unit, value);
    }
}
