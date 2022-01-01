package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Lot.AggregationField;
import io.company.brewcraft.service.StockLotAggregationFieldCollection;

public class StockLotAggregationFieldCollectionTest {
    @Test
    public void testGetFields_ID_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.ID, AggregationField.LOT_NUMBER, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.INVOICE_ITEM, AggregationField.SHIPMENT, AggregationField.STORAGE, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.ID.getFields());
    }

    @Test
    public void testGetFields_MATERIAL_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.MATERIAL.getFields());
    }

    @Test
    public void testGetFields_LOT_NUMBER_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.LOT_NUMBER, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.LOT_NUMBER.getFields());
    }

    @Test
    public void testGetFields_INVOICE_ITEM_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.INVOICE_ITEM, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.INVOICE_ITEM.getFields());
    }

    @Test
    public void testGetFields_LOT_NUMBER_AND_INVOICE_ITEM_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.LOT_NUMBER, AggregationField.INVOICE_ITEM, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.LOT_NUMBER_AND_INVOICE_ITEM.getFields());
    }

    @Test
    public void testGetFields_SHIPMENT_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.SHIPMENT, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.SHIPMENT.getFields());
    }

    @Test
    public void testGetFields_STORAGE_ReturnsAllAggregationFieldsExceptQuantityValue() {
        AggregationField[] fields = { AggregationField.STORAGE, AggregationField.MATERIAL, AggregationField.MATERIAL_NAME, AggregationField.QUANTITY_UNIT };

        assertArrayEquals(fields, StockLotAggregationFieldCollection.STORAGE.getFields());
    }
}
