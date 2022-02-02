package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.JoinType;

import io.company.brewcraft.service.CriteriaJoin;
import io.company.brewcraft.service.mapper.QuantityMapper;

@MappedSuperclass
public abstract class BaseFinishedGoodInventory extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU = "sku";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PACKAGED_ON = "packagedOn";

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    @CriteriaJoin(type = JoinType.LEFT)
    private Sku sku;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "qty_used_in_sys_unit")) })
    @AssociationOverrides({ @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol")) })
    private QuantityEntity quantity;

    @Column(name = "packaged_on")
    private LocalDateTime packagedOn;

    public BaseFinishedGoodInventory() {
        super();
    }

    public BaseFinishedGoodInventory(Long id) {
        this();
        this.setId(id);
    }

    public BaseFinishedGoodInventory(Long id, Sku sku, UnitEntity unit, BigDecimal value, LocalDateTime packagedOn) {
        this(id);
        this.setSku(sku);
        this.setQuantity(unit, value);
        this.setPackagedOn(packagedOn);
    }

    public BaseFinishedGoodInventory(Long id, Sku sku, Quantity<?> quantity, LocalDateTime packagedOn) {
        this(id);
        this.setSku(sku);
        this.setQuantity(quantity);
        this.setPackagedOn(packagedOn);
    }

    public BaseFinishedGoodInventory(Sku sku) {
        this.setSku(sku);
    }

    public BaseFinishedGoodInventory(Sku sku, UnitEntity unit, BigDecimal value) {
        this.setSku(sku);
        this.setQuantity(unit, value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.quantity);
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    public void setQuantity(QuantityEntity quantityEntity) {
        this.quantity = quantityEntity;
    }

    private void setQuantity(UnitEntity unit, BigDecimal value) {
        setQuantity(new QuantityEntity(unit, value));
    }

    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }
}
