package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CrudEntity;
@Entity(name = "purchase_order")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class PurchaseOrder extends BaseEntity implements BasePurchaseOrder, UpdatePurchaseOrder, Audited, CrudEntity<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ORDER_NUMBER = "orderNumber";
    public static final String FIELD_SUPPLIER = "supplier";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_generator")
    @SequenceGenerator(name = "purchase_order_generator", sequenceName = "purchase_order_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id) {
        this();
        setId(id);
    }

    public PurchaseOrder(Long id, String orderNumber, Supplier supplier, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setOrderNumber(orderNumber);
        setSupplier(supplier);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
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
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public Supplier getSupplier() {
        return supplier;
    }

    @Override
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
