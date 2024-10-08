package io.company.brewcraft.model;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.JoinType;

import io.company.brewcraft.service.CriteriaJoin;
import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@MappedSuperclass
public abstract class Lot extends BaseEntity {
    public enum AggregationField implements PathProvider {
        ID (FIELD_ID),
        LOT_NUMBER (FIELD_LOT_NUMBER),
        MATERIAL (FIELD_MATERIAL),
        MATERIAL_NAME (FIELD_MATERIAL, Material.FIELD_NAME),
        INVOICE_ITEM (FIELD_INVOICE_ITEM),
        SHIPMENT (FIELD_SHIPMENT),
        STORAGE (FIELD_STORAGE),
        QUANTITY_UNIT (FIELD_QTY, QuantityEntity.FIELD_UNIT),
        QUANTITY_VALUE (FIELD_QTY, QuantityEntity.FIELD_VALUE);

        private final String[] path;

        private AggregationField(String... path) {
            this.path = path;
        }

        @Override
        public String[] getPath() {
            return this.path;
        }
    }

    public static final String FIELD_ID = "id";
    public static final String FIELD_LOT_NUMBER = "lotNumber";
    public static final String FIELD_QTY = "quantity";
    public static final String FIELD_MATERIAL = "material";
    public static final String FIELD_SHIPMENT = "shipment";
    public static final String FIELD_INVOICE_ITEM = "invoiceItem";
    public static final String FIELD_STORAGE = "storage";

    public Lot() {
        super();
    }

    public Lot(Long id) {
        this();
        this.setId(id);
    }

    public Lot(Long id, String lotNumber, Quantity<?> quantity, Material material, Shipment shipment, InvoiceItem invoiceItem, Storage storage) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setQuantity(quantity);
        this.setMaterial(material);
        this.setShipment(shipment);
        this.setInvoiceItem(invoiceItem);
        this.setStorage(storage);
    }

    // AllArgJpaConstructor
    public Lot(Long id, String lotNumber, Material material, String materialName, InvoiceItem invoiceItem, Shipment shipment, Storage storage, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this(id);
        this.setLotNumber(lotNumber);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setShipment(shipment);
        this.setStorage(storage);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setLotNumber(lotNumber);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setLotNumber(lotNumber);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Shipment shipment, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setShipment(shipment);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Shipment shipment, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setShipment(shipment);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Storage storage, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setStorage(storage);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Storage storage, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setStorage(storage);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, InvoiceItem invoiceitem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceitem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Shipment shipment, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Storage storage, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setStorage(storage);
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setStorage(storage);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, Storage storage, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setStorage(storage);
        this.setMaterial(material);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setStorage(storage);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    public Lot(String lotNumber, Shipment shipment, Storage storage, InvoiceItem invoiceItem, Material material, String materialName, UnitEntity displayUnit, BigDecimal qtyValueInSysUnit) {
        /**
         * MaterialName is an unused field. It's included in the constructor since it's
         * required by JPA for doing sort by Material Name field. Do not remove it.
         */
        this.setLotNumber(lotNumber);
        this.setShipment(shipment);
        this.setStorage(storage);
        this.setMaterial(material);
        this.setInvoiceItem(invoiceItem);
        this.setQuantity(displayUnit, qtyValueInSysUnit);
    }

    @Id
    private Long id;

    @Column(name = "lot_number")
    private String lotNumber;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit")) })
    @AssociationOverrides({ @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol")) })
    private QuantityEntity quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @CriteriaJoin(type = JoinType.LEFT)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_item_id", referencedColumnName = "id")
    @CriteriaJoin(type = JoinType.LEFT)
    private InvoiceItem invoiceItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    @CriteriaJoin(type = JoinType.LEFT)
    private Storage storage;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    public void setQuantity(Quantity<?> quantity) {
        Quantity<?> systemQtyValueWithDisplayUnit = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);

        this.quantity = QuantityMapper.INSTANCE.toEntity(systemQtyValueWithDisplayUnit);
    }

    private void setQuantity(UnitEntity displayUnit, BigDecimal qtyValueInSystemUnit) {
        this.quantity = new QuantityEntity(displayUnit, qtyValueInSystemUnit);
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Shipment getShipment() {
        return this.shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public InvoiceItem getInvoiceItem() {
        return this.invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
