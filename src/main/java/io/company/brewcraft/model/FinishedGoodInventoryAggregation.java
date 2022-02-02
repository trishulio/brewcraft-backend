package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.PathProvider;

@Entity(name = "finished_good_inventory_aggregation")
@Table(name = "finished_good_inventory")
@Immutable
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class FinishedGoodInventoryAggregation extends BaseFinishedGoodInventory {
    public enum AggregationField implements PathProvider {
        ID (FIELD_ID),
        SKU (FIELD_SKU),
        QUANTITY_UNIT (FIELD_QUANTITY, QuantityEntity.FIELD_UNIT),
        QUANTITY_VALUE (FIELD_QUANTITY, QuantityEntity.FIELD_VALUE),
        PACKAGED_ON (FIELD_PACKAGED_ON);

        private final String[] path;

        private AggregationField(String... path) {
            this.path = path;
        }

        @Override
        public String[] getPath() {
            return this.path;
        }
    }

    public FinishedGoodInventoryAggregation() {
        super();
    }

    public FinishedGoodInventoryAggregation(Long id) {
        super(id);
    }

    public FinishedGoodInventoryAggregation(Long id, Sku sku, LocalDateTime packagedOn, Quantity<?> quantity) {
        super(id, sku, quantity, packagedOn);
    }

    public FinishedGoodInventoryAggregation(Long id, Sku sku, LocalDateTime packagedOn, UnitEntity unit, BigDecimal value) {
        super(id, sku, unit, value, packagedOn);
    }

    public FinishedGoodInventoryAggregation(Sku sku) {
        super(sku);
    }

    public FinishedGoodInventoryAggregation(Sku sku, UnitEntity unit, BigDecimal value) {
        super(sku, unit, value);
    }
}
