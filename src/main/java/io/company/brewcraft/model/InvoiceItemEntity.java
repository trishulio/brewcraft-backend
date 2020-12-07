package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

@Entity(name = "INVOICE_ITEM")
public class InvoiceItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_generator")
    @SequenceGenerator(name = "invoice_item_generator", sequenceName = "invoice_item_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private InvoiceEntity invoice;

    @Column(name = "qty_id")
    private QuantityEntity qty;

    @Column(name = "money_id")
    private MoneyEntity price;

    @Column(name = "lot")
    private String lot;

    @JoinColumn(name = "material_id")
    private MaterialEntity material; // TODO: Create an Entity

    @Version
    private Integer version;

    public InvoiceItemEntity() {
    }

    public InvoiceItemEntity(Long id) {
        this(id, null, null, null, null, null, null);
    }

    public InvoiceItemEntity(Long id, InvoiceEntity invoice, QuantityEntity qty, MoneyEntity price, String lot, MaterialEntity material, Integer version) {
        setId(id);
        setInvoice(invoice);
        setQuantity(qty);
        setPrice(price);
        setLot(lot);
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
        return this.invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public QuantityEntity getQuantity() {
        return qty;
    }

    public void setQuantity(QuantityEntity quantity) {
        this.qty = quantity;
    }

    public MoneyEntity getPrice() {
        return price;
    }

    public void setPrice(MoneyEntity price) {
        this.price = price;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}