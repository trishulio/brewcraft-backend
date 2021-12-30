package io.company.brewcraft.service;

import io.company.brewcraft.model.Lot.AggregationField;

public enum ProcurementLotAggregationFieldCollection {
    /**
     * The order of the values should match the ProcurementLot constructors
     */

    ID(AggregationField.ID, AggregationField.LOT_NUMBER, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.INVOICE_ITEM, AggregationField.SHIPMENT, AggregationField.STORAGE, AggregationField.QUANTITY_UNIT),

    LOT_NUMBER(AggregationField.LOT_NUMBER, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT),

    INVOICE_ITEM(AggregationField.INVOICE_ITEM, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT),

    SHIPMENT(AggregationField.SHIPMENT, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT),

    STORAGE(AggregationField.STORAGE, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT);

    private AggregationField[] fields;

    private ProcurementLotAggregationFieldCollection(AggregationField... fields) {
        this.fields = fields;
    }

    public AggregationField[] getFields() {
        return this.fields;
    }
}