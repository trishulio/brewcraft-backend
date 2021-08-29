package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;

@Entity(name = "invoice")
@Table
public class Invoice extends BaseEntity implements UpdateInvoice<InvoiceItem>, CrudEntity<Long>, Audited, MoneySupplier {
    private static final Logger log = LoggerFactory.getLogger(Invoice.class);

    public static final String FIELD_ID = "id";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_DESCRITION = "description";
    public static final String FIELD_PURCHASE_ORDER = "purchaseOrder";
    public static final String FIELD_GENERATED_ON = "generatedOn";
    public static final String FIELD_RECEIVED_ON = "receivedOn";
    public static final String FIELD_PAYMENT_DUE_DATE = "paymentDueDate";
    public static final String FIELD_FREIGHT = "freight";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_ITEMS = "items";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "generated_on")
    private LocalDateTime generatedOn;

    @Column(name = "received_on")
    private LocalDateTime receivedOn;

    @Column(name = "payment_due_date")
    private LocalDateTime paymentDueDate;

    @Embedded
    private Freight freight;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_status_id", referencedColumnName = "id")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InvoiceItem> items;

    @Version
    private Integer version;

    public Invoice() {
    }

    public Invoice(Long id) {
        this.setId(id);
    }

    public Invoice(Long id, String invoiceNumber, String description, PurchaseOrder purchaseOrder, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Freight freight, LocalDateTime createdAt,
            LocalDateTime lastUpdated, InvoiceStatus status, List<InvoiceItem> items, Integer version) {
        this(id);
        this.setInvoiceNumber(invoiceNumber);
        this.setDescription(description);
        this.setPurchaseOrder(purchaseOrder);
        this.setGeneratedOn(generatedOn);
        this.setReceivedOn(receivedOn);
        this.setPaymentDueDate(paymentDueDate);
        this.setFreight(freight);
        this.setCreatedAt(createdAt);
        this.setLastUpdated(lastUpdated);
        this.setStatus(status);
        this.setItems(items);
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
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    @Override
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
    public PurchaseOrder getPurchaseOrder() {
        return this.purchaseOrder;
    }

    @Override
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public LocalDateTime getGeneratedOn() {
        return this.generatedOn;
    }

    @Override
    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public LocalDateTime getReceivedOn() {
        return this.receivedOn;
    }

    @Override
    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    @Override
    public LocalDateTime getPaymentDueDate() {
        return this.paymentDueDate;
    }

    @Override
    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
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
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public InvoiceStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public List<InvoiceItem> getItems() {
        List<InvoiceItem> items = null;
        if (this.items != null) {
            items = this.items.stream().collect(Collectors.toList());
        }
        return items;
    }

    @Override
    public void setItems(List<InvoiceItem> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items.stream().collect(Collectors.toList()).forEach(this::removeItem);
        }

        if (items == null) {
            this.items = null;
        } else {
            items.stream().collect(Collectors.toList()).forEach(this::addItem);
        }
    }

    public void addItem(InvoiceItem item) {
        if (item == null) {
            return;
        }

        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        if (item.getInvoice() != this) {
            item.setInvoice(this);
        }

        if (!this.items.contains(item)) {
            this.items.add(item);
        }
    }

    public boolean removeItem(InvoiceItem item) {
        if (item == null || this.items == null) {
            return false;
        }

        final boolean removed = this.items.remove(item);

        if (removed) {
            item.setInvoice(null);
        }

        return removed;
    }

    @Override
    public Freight getFreight() {
        return this.freight;
    }

    @Override
    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Money getAmount() {
        return MoneyService.total(this.getItems());
    }

    public Tax getTax() {
        Tax tax = null;
        if (this.getItems() != null) {
            final Collection<Tax> taxes = this.getItems().stream().filter(i -> i != null).map(i -> i.getTax()).collect(Collectors.toSet());
            tax = Tax.total(taxes);
        }

        return tax;
    }
}
