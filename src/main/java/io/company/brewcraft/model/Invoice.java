package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.service.AmountCalculator;
import io.company.brewcraft.service.CriteriaJoin;
import io.company.brewcraft.service.CrudEntity;

@Entity(name = "invoice")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Invoice extends BaseEntity implements UpdateInvoice<InvoiceItem>, CrudEntity<Long>, Audited, AmountSupplier {
    private static final Logger log = LoggerFactory.getLogger(Invoice.class);

    public static final String FIELD_ID = "id";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_DESCRITION = "description";
    public static final String FIELD_PURCHASE_ORDER = "purchaseOrder";
    public static final String FIELD_GENERATED_ON = "generatedOn";
    public static final String FIELD_RECEIVED_ON = "receivedOn";
    public static final String FIELD_PAYMENT_DUE_DATE = "paymentDueDate";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_FREIGHT = "freight";
    public static final String FIELD_INVOICE_STATUS = "invoiceStatus";
    public static final String FIELD_ITEMS = "invoiceItems";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "invoice_number")
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
    private Amount amount;

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
    private InvoiceStatus invoiceStatus;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy(value = InvoiceItem.FIELD_INDEX + " ASC")
    @JsonManagedReference
    @CriteriaJoin
    private List<InvoiceItem> invoiceItems;

    @Version
    private Integer version;

    public Invoice() {
        super();
        setInvoiceItems(null);
    }

    public Invoice(Long id) {
        this();
        this.setId(id);
    }

    public Invoice(Long id, String invoiceNumber, String description, PurchaseOrder purchaseOrder, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Freight freight, LocalDateTime createdAt,
            LocalDateTime lastUpdated, InvoiceStatus invoiceStatus, List<InvoiceItem> invoiceItems, Integer version) {
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
        this.setInvoiceStatus(invoiceStatus);
        this.setInvoiceItems(invoiceItems);
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
    public InvoiceStatus getInvoiceStatus() {
        return this.invoiceStatus;
    }

    @Override
    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Override
    public List<InvoiceItem> getInvoiceItems() {
        List<InvoiceItem> invoiceItems = null;
        if (this.invoiceItems != null) {
            invoiceItems = this.invoiceItems.stream().toList();
        }
        return invoiceItems;
    }

    @Override
    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        if (this.invoiceItems == null) {
            this.invoiceItems = new ArrayList<>();
        } else {
            this.invoiceItems.stream().toList().forEach(this::removeItem);
        }

        if (invoiceItems == null) {
            this.invoiceItems.clear();
        } else {
            invoiceItems.stream().toList().forEach(this::addItem);
        }
    }

    public void addItem(InvoiceItem invoiceItem) {
        if (invoiceItem == null) {
            return;
        }

        if (this.invoiceItems == null) {
            this.invoiceItems = new ArrayList<>();
        }

        Invoice prevInvoice = invoiceItem.getInvoice();
        if (prevInvoice != null && prevInvoice != this) {
            prevInvoice.removeItem(invoiceItem);
        }

        invoiceItem.setInvoice(this);
        invoiceItem.setIndex(this.invoiceItems.size());
        this.invoiceItems.add(invoiceItem);
    }

    public boolean removeItem(InvoiceItem invoiceItem) {
        if (invoiceItem == null || this.invoiceItems == null) {
            return false;
        }

        final boolean removed = this.invoiceItems.remove(invoiceItem);

        if (removed) {
            invoiceItem.setInvoice(null);
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

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Amount getAmount() {
        setAmount();
        return this.amount;
    }

    @PrePersist
    private void setAmount() {
        this.amount = AmountCalculator.INSTANCE.getTotalAmount(this.invoiceItems);
    }

    @JsonIgnore
    public int getItemCount() {
        int count = 0;
        if (this.invoiceItems != null) {
            count = this.invoiceItems.size();
        }

        return count;
    }
}
