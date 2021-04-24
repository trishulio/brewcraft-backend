package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.measure.Quantity;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "invoice_item")
@Table
public class InvoiceItem extends BaseEntity implements MoneySupplier, UpdateInvoiceItem, Identified<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_TAX = "tax";
    public static final String FIELD_MATERIAL = "material";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_generator")
    @SequenceGenerator(name = "invoice_item_generator", sequenceName = "invoice_item_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Embedded
    private QuantityEntity quantity;

    @Embedded
    private MoneyEntity price;

    @Embedded
    private Tax tax;

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
        setId(id);
    }

    public InvoiceItem(Long id, String description, Quantity<?> quantity, Money price, Tax tax, Material material, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterial(material);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return description;
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
        if (this.invoice != null) {
            this.invoice.getItems().remove(this);
        }

        if (invoice != null) {
            if (invoice.getItems() == null) {
                invoice.setItems(new ArrayList<>(0));
            }
            invoice.getItems().add(this);
        }

        this.invoice = invoice;
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(quantity);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
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
        return tax;
    }

    @Override
    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Money getAmount() {
        Money amount = null;

        Number qty = this.getQuantity() != null ? this.getQuantity().getValue() : null;
        Money price = this.getPrice() != null ? this.getPrice() : null;

        if (qty != null && price != null) {
            amount = price.multipliedBy(qty.longValue());
        }

        return amount;
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
}
