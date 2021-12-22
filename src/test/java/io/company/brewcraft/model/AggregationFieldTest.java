package io.company.brewcraft.model;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Lot.AggregationField;

public class AggregationFieldTest {

    @Test
    public void testLotNumber_GetPath_ReturnsLotNumberField() {
        String[] path = AggregationField.LOT_NUMBER.getPath();

        assertArrayEquals(new String[] { Lot.FIELD_LOT_NUMBER }, path);
    }

    @Test
    public void testMaterial_GetPath_ReturnsMaterialField() {
        String[] path = AggregationField.MATERIAL.getPath();

        assertArrayEquals(new String[] { Lot.FIELD_MATERIAL }, path);
    }

    @Test
    public void testMaterialName_GetPath_ReturnsMaterialNameField() {
       String[] path = AggregationField.MATERIAL_NAME.getPath();

       assertArrayEquals(new String[] { Lot.FIELD_MATERIAL, Material.FIELD_NAME }, path);
    }

    @Test
    public void testInvoiceItem_GetPath_ReturnsInvoiceItemField() {
        String[] path = AggregationField.INVOICE_ITEM.getPath();

        assertArrayEquals(new String[] { Lot.FIELD_INVOICE_ITEM }, path);
    }

    @Test
    public void testShipment_GetPath_ReturnsShipmentField() {
        String[] path = AggregationField.SHIPMENT.getPath();

        assertArrayEquals(new String[] { Lot.FIELD_SHIPMENT }, path);
    }

    @Test
    public void testStorage_GetPath_ReturnsStorageField() {
        String[] path = AggregationField.STORAGE.getPath();

        assertArrayEquals(new String[] { Lot.FIELD_STORAGE }, path);
    }
}
