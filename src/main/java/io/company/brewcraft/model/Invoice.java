package io.company.brewcraft.model;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity(name = "INVOICE")
public class Invoice extends BaseEntity {
    private static final Logger logger = LoggerFactory.getLogger(Invoice.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Column(name = "date")
    private Date date;
    
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items;
    
    @Version
    private Integer version;

    public Invoice() {
        this(null);
    }

    public Invoice(Long id) {
        this(id, null, null, null, null, null);
    }

    public Invoice(Long id, Supplier supplier, Date date, InvoiceStatus status, List<InvoiceItem> items, Integer version) {
        setId(id);
        setSupplier(supplier);
        setDate(date);
        setStatus(status);
        setItems(items);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
        if (this.items != null) {
            this.items.forEach(item -> item.setInvoice(this));
        }
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Money getAmount() {
        Money amount = null;

        if (items != null) {
            List<Money> monies = items.stream().map(i -> i.getAmount()).collect(Collectors.toList());
            amount = Money.total(monies);
        }

        return amount;
    }
}
