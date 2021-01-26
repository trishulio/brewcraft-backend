package io.company.brewcraft.model;

import javax.persistence.*;

@Entity(name = "purchase_order")
@Table
public class PurchaseOrderEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ORDER_NUMBER = "orderNumber";
    public static final String FIELD_SUPPLIER = "supplier";    

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_generator")
    @SequenceGenerator(name = "purchase_order_generator", sequenceName = "purchase_order_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    public PurchaseOrderEntity() {
    }

    public PurchaseOrderEntity(Long id) {
        setId(id);
    }

    public PurchaseOrderEntity(Long id, String orderNumber) {
        this(id);
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
