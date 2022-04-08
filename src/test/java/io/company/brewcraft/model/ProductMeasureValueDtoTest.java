package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;

public class ProductMeasureValueDtoTest {
    ProductMeasureValueDto productMeasureValueDto;

    @BeforeEach
    public void init() {
        productMeasureValueDto = new ProductMeasureValueDto();
    }

    @Test
    public void testConstructor() {
        productMeasureValueDto = new ProductMeasureValueDto(1L, new MeasureDto(2L, "abv", 1), new BigDecimal("100"));

        assertEquals(1L, productMeasureValueDto.getId());
        assertEquals(new MeasureDto(2L, "abv", 1), productMeasureValueDto.getMeasure());
        assertEquals(new BigDecimal("100"), productMeasureValueDto.getValue());
    }

    @Test
    public void testGetSetId() {
        productMeasureValueDto.setId(1L);
        assertEquals(1L, productMeasureValueDto.getId());
    }

    @Test
    public void testGetSetMeasure() {
        productMeasureValueDto.setMeasure(new MeasureDto(2L, "abv", 1));
        assertEquals(new MeasureDto(2L, "abv", 1), productMeasureValueDto.getMeasure());
    }

    @Test
    public void testGetSetValue() {
        productMeasureValueDto.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), productMeasureValueDto.getValue());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        productMeasureValueDto = new ProductMeasureValueDto(1L, new MeasureDto(2L, "abv", 1), new BigDecimal("100"));

        final String json = "{\"id\":1,\"measure\":{\"id\":2,\"name\":\"abv\",\"version\":1},\"value\":100}";
        JSONAssert.assertEquals(json, productMeasureValueDto.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
