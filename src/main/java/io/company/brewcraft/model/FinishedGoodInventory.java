package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity(name = "finished_good_inventory")
@Table
@Immutable
public class FinishedGoodInventory extends BaseEntity {
    public static final String FIELD_SKU = "sku";

    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    private Sku sku;

    private Long quantity;

    public FinishedGoodInventory() {
        super();
    }

    public FinishedGoodInventory(Long id, Sku sku, Long quantity) {
        this();
        this.id = id;
        this.sku = sku;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
