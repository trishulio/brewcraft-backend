package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodInventoryAggregation.AggregationField;

public enum FinishedGoodInventoryAggregationFieldCollection {
    /**
     * The order of the values should match the FinishedGoodInventory constructors
     */
    ID(AggregationField.ID, AggregationField.SKU, AggregationField.PACKAGED_ON, AggregationField.QUANTITY_UNIT),

    SKU(AggregationField.SKU, AggregationField.QUANTITY_UNIT);

    private AggregationField[] fields;

    private FinishedGoodInventoryAggregationFieldCollection(AggregationField... fields) {
        this.fields = fields;
    }

    public AggregationField[] getFields() {
        return this.fields;
    }
}