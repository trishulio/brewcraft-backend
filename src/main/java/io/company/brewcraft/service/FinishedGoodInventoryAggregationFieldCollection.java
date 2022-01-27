package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodInventory.AggregationField;

public enum FinishedGoodInventoryAggregationFieldCollection {
    /**
     * The order of the values should match the FinishedGoodInventory constructors
     */
    ID(AggregationField.ID, AggregationField.SKU),

    SKU(AggregationField.SKU);

    private AggregationField[] fields;

    private FinishedGoodInventoryAggregationFieldCollection(AggregationField... fields) {
        this.fields = fields;
    }

    public AggregationField[] getFields() {
        return this.fields;
    }
}