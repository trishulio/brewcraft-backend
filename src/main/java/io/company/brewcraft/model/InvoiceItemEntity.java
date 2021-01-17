package io.company.brewcraft.model;

import javax.persistence.*;

@Entity(name = "INVOICE_ITEM")
public class InvoiceItemEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_TAX = "tax";
    public static final String FIELD_MATERIAL = "material";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_generator")
    @SequenceGenerator(name = "invoice_item_generator", sequenceName = "invoice_item_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private InvoiceEntity invoice;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "qty_id", referencedColumnName = "id")
    private QuantityEntity quantity;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private MoneyEntity price;

    @OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private TaxEntity tax;

    @OneToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private MaterialEntity material;

    @Version
    private Integer version;

    public InvoiceItemEntity() {
    }

    public InvoiceItemEntity(Long id) {
        this();
        setId(id);
    }

    public InvoiceItemEntity(Long id, InvoiceEntity invoice, QuantityEntity quantity, MoneyEntity price, MaterialEntity material, Integer version) {
        this(id);
        setInvoice(invoice);
        setQuantity(quantity);
        setPrice(price);
        setMaterial(material);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public QuantityEntity getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityEntity quantity) {
        this.quantity = quantity;
    }

    public MoneyEntity getPrice() {
        return price;
    }

    public void setPrice(MoneyEntity price) {
        this.price = price;
    }

    public TaxEntity getTax() {
        return tax;
    }

    public void setTax(TaxEntity tax) {
        this.tax = tax;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}