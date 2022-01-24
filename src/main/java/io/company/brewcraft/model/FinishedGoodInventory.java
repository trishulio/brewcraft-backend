package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.PathProvider;

@Entity(name = "finished_good_inventory")
@Table
@Immutable
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class FinishedGoodInventory extends BaseEntity {
    public enum AggregationField implements PathProvider {
        ID (FIELD_ID),
        SKU (FIELD_SKU),
        QUANTITY (FIELD_QUANTITY);

        private final String[] path;

        private AggregationField(String... path) {
            this.path = path;
        }

        @Override
        public String[] getPath() {
            return this.path;
        }
    }

    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU = "sku";
    public static final String FIELD_QUANTITY = "quantity";

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    private Sku sku;

    private Long quantity;

    public FinishedGoodInventory() {
        super();
    }

    public FinishedGoodInventory(Long id) {
        this();
        this.id = id;
    }

    public FinishedGoodInventory(Long id, Sku sku, Long quantity) {
        this(id);
        this.sku = sku;
        this.quantity = quantity;
    }

    public FinishedGoodInventory(Sku sku) {
        this.sku = sku;
    }

    public FinishedGoodInventory(Sku sku, Long quantity) {
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
