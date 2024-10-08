package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.AmountCalculator;
import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@Entity(name = "invoice_item")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class InvoiceItem extends BaseEntity implements UpdateInvoiceItem<Invoice>, Audited, CrudEntity<Long>, AmountSupplier, Good {
    public static final String FIELD_ID = "id";
    public static final String FIELD_INDEX = "index";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_INVOICE = "invoice";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_TAX = "tax";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_MATERIAL = "material";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_generator")
    @SequenceGenerator(name = "invoice_item_generator", sequenceName = "invoice_item_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "idx", nullable = false)
    private Integer index;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @JsonBackReference
    private Invoice invoice;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "price_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "price_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity price;

    @Embedded
    private Tax tax;

    @Embedded
    private Amount amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public InvoiceItem() {
    }

    public InvoiceItem(Long id) {
        this();
        this.setId(id);
    }

    public InvoiceItem(Long id, Integer index, String description, Quantity<?> quantity, Money price, Tax tax, Material material, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        this.setIndex(index);
        this.setDescription(description);
        this.setQuantity(quantity);
        this.setPrice(price);
        this.setTax(tax);
        this.setMaterial(material);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
        this.setVersion(version);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        IncompatibleQuantityUnitException.validateUnit(material, quantity);

        quantity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    @Override
    public Money getPrice() {
        return MoneyMapper.INSTANCE.fromEntity(this.price);
    }

    @Override
    public void setPrice(Money price) {
        this.price = MoneyMapper.INSTANCE.toEntity(price);
    }

    @Override
    public Tax getTax() {
        return this.tax;
    }

    @Override
    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public void setMaterial(Material material) {
        IncompatibleQuantityUnitException.validateUnit(material, getQuantity());
        this.material = material;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    @JsonIgnore
    public Amount getAmount() {
        setAmount();
        return this.amount;
    }

    @PrePersist
    private void setAmount() {
        this.amount = AmountCalculator.INSTANCE.getAmount(this);
    }
}
