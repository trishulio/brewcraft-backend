package io.company.brewcraft.pojo;

import javax.measure.Quantity;
import javax.persistence.*;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.service.mapper.MoneyMapper;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "INVOICE_ITEM")
@Table
public class InvoiceItem extends BaseModel implements MoneySupplier, UpdateInvoiceItem, Identified {
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

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "qty_id", referencedColumnName = "id")
    private QuantityEntity quantity;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private MoneyEntity price;

    @OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private Tax tax;

    @OneToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private MaterialEntity material;

    @Version
    private Integer version;

    public InvoiceItem() {
    }

    public InvoiceItem(Long id) {
        this();
        setId(id);
    }

    public InvoiceItem(Long id, String description, Quantity<?> quantity, Money price, Tax tax, Material material, Integer version) {
        this(id);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterial(material);
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
        return MaterialMapper.INSTANCE.fromEntity(material, new CycleAvoidingMappingContext());
    }

    @Override
    public void setMaterial(Material material) {
        this.material = MaterialMapper.INSTANCE.toEntity(material, new CycleAvoidingMappingContext());
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
}
