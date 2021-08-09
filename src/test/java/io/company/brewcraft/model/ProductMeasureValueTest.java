package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ProductMeasureValueTest {

    ProductMeasureValue productMeasureValue;

    @BeforeEach
    public void init() {
        productMeasureValue = new ProductMeasureValue();
    }

    @Test
    public void testConstructor() {
        productMeasureValue = new ProductMeasureValue(1L,new Measure(), new BigDecimal("100"), new Product());

        assertEquals(1L, productMeasureValue.getId());
        assertEquals(new Measure(), productMeasureValue.getMeasure());
        assertEquals(new BigDecimal("100"), productMeasureValue.getValue());
        assertEquals(new Product(), productMeasureValue.getProduct());
    }

    @Test
    public void testGetSetId() {
        productMeasureValue.setId(1L);
        assertEquals(1L, productMeasureValue.getId());
    }

    @Test
    public void testGetSetProductMeasure() {
        productMeasureValue.setMeasure(new Measure());
        assertEquals(new Measure(), productMeasureValue.getMeasure());
    }

    @Test
    public void testGetSetValue() {
        productMeasureValue.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), productMeasureValue.getValue());
    }

    @Test
    public void testGetSetProduct() {
        productMeasureValue.setProduct(new Product());
        assertEquals(new Product(), productMeasureValue.getProduct());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        productMeasureValue = new ProductMeasureValue(1L,new Measure(), new BigDecimal("100"), new Product());

        final String json = "{\"id\":1,\"measure\":{\"id\":null,\"name\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"value\":100}";
        JSONAssert.assertEquals(json, productMeasureValue.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
