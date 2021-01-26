package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "INVOICE")
@Table
public class InvoiceEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_GENERATED_ON = "generatedOn";
    public static final String FIELD_RECEIVED_ON = "receivedOn";
    public static final String FIELD_PAYMENT_DUE_DATE = "paymentDueDate";
    public static final String FIELD_FREIGHT = "freight";
    public static final String FIELD_PURCHASE_ORDER = "purchaseOrder";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_ITEMS = "items";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "generated_on")
    private LocalDateTime generatedOn;

    @Column(name = "received_on")
    private LocalDateTime receivedOn;

    @Column(name = "payment_due_date")
    private LocalDateTime paymentDueDate;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "freight_id", referencedColumnName = "id")
    private FreightEntity freight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    private PurchaseOrderEntity purchaseOrder;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "invoice_status_id", referencedColumnName = "id")
    private InvoiceStatusEntity status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItemEntity> items;

    @Version
    private Integer version;

    public InvoiceEntity() {
    }

    public InvoiceEntity(Long id) {
        setId(id);
    }

    public InvoiceEntity(Long id, String invoiceNumber, LocalDateTime generatedOn, LocalDateTime receivedOn, LocalDateTime paymentDueDate, FreightEntity freight, PurchaseOrderEntity purchaseOrder,
            LocalDateTime lastUpdated, LocalDateTime createdAt, InvoiceStatusEntity status, List<InvoiceItemEntity> items, Integer version) {
        this(id);
        setInvoiceNumber(invoiceNumber);
        setGeneratedOn(generatedOn);
        setPaymentDueDate(paymentDueDate);
        setFreight(freight);
        setPurchaseOrder(purchaseOrder);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public FreightEntity getFreight() {
        return freight;
    }

    public void setFreight(FreightEntity freight) {
        this.freight = freight;
    }

    public PurchaseOrderEntity getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderEntity purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public InvoiceStatusEntity getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusEntity status) {
        this.status = status;
    }

    public List<InvoiceItemEntity> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemEntity> items) {
        if (this.getItems() != null) {
            this.getItems().clear();
            this.getItems().addAll(items);
        } else {
            this.items = items;
        }

        if (this.getItems() != null) {
            this.getItems().forEach(item -> item.setInvoice(this));
        }
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
