package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;

@Entity(name = "INVOICE")
@Table
public class Invoice extends BaseModel implements UpdateInvoice<InvoiceItem>, Identified, Audited, MoneySupplier {
    private static final Logger logger = LoggerFactory.getLogger(Invoice.class);

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

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "freight_id", referencedColumnName = "id")
    private Freight freight;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "invoice_status_id", referencedColumnName = "id")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<InvoiceItem> items;

    @Version
    private Integer version;
    
    public Invoice() {
    }

    public Invoice(Long id) {
        setId(id);
    }

    public Invoice(Long id, String invoiceNumber, String description, PurchaseOrder purchaseOrder, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, Freight freight, LocalDateTime createdAt,
            LocalDateTime lastUpdated, InvoiceStatus status, Collection<InvoiceItem> items, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setDescription(description);
        setPurchaseOrder(purchaseOrder);
        setGeneratedOn(generatedOn);
        setReceivedOn(receivedOn);
        setPaymentDueDate(paymentDueDate);
        setFreight(freight);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setStatus(status);
        setItems(items);
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
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @Override
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    @Override
    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    @Override
    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    @Override
    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    @Override
    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public InvoiceStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public Collection<InvoiceItem> getItems() {
        return items;
    }

    @Override
    public void setItems(Collection<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public Freight getFreight() {
        return freight;
    }

    @Override
    public void setFreight(Freight freight) {
        this.freight = freight;
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
        return MoneyService.total(this.getItems());
    }
    
    public Tax getTax() {
        Tax tax = null;
        if (getItems() != null) {
            Collection<Tax> taxes = getItems().stream().filter(i -> i != null).map(i -> i.getTax()).collect(Collectors.toSet());
            tax = Tax.total(taxes);
        }
        
        return tax;
    }
}
