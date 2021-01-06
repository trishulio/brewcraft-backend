package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "INVOICE")
@Table
public class InvoiceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "date")
    private LocalDateTime date;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItemEntity> items;

    @Version
    private Integer version;

    public InvoiceEntity() {
        this(null);
    }

    public InvoiceEntity(Long id) {
        this(id, null, null, null, null, null, null, null);
    }

    public InvoiceEntity(Long id, Supplier supplier, LocalDateTime date, LocalDateTime lastUpdated, LocalDateTime createdAt, InvoiceStatus status, List<InvoiceItemEntity> items, Integer version) {
        setId(id);
        setSupplier(supplier);
        setDate(date);
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
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
