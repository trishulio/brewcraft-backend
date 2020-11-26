package io.company.brewcraft.model;

import javax.measure.Quantity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.joda.money.Money;

@Entity(name = "INVOICE_ITEM")
public class InvoiceItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_item_generator")
    @SequenceGenerator(name = "invoice_item_generator", sequenceName = "invoice_item_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    // TODO: How to persiste this?
    @Transient
    private Quantity<?> quantity;
    // TODO: How to persiste this?
    @Transient
    private Money price;

    @Column(name = "lot")
    private String lot;

//    @JoinColumn(name = "material_id")
    @Transient
    private Object material; // TODO: Change when materials are supported

    @Version
    private Integer version;

    public InvoiceItem() {
    }

    public InvoiceItem(Long id) {
        this(id, null, null, null, null, null, null);
    }

    public InvoiceItem(Long id, Invoice invoice, Quantity<?> quantity, Money price, String lot, Object material, Integer version) {
        setId(id);
        setInvoice(invoice);
        setQuantity(quantity);
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

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Object getMaterial() {
        return material;
    }

    public void setMaterial(Object material) {
        this.material = material;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Money getAmount() {
        Money amount = null;

        Number qty = this.getQuantity() != null ? this.getQuantity().getValue() : null;
        Money price = this.getPrice() != null ? this.getPrice() : null;

        if (qty != null && price != null) {
            amount = this.getPrice().multipliedBy(qty.longValue());
        }

        return amount;
    }
}