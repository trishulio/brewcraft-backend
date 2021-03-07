package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMeasuresDtoTest {

    private ProductMeasuresDto productMeasuresDto;

    @BeforeEach
    public void init() {
        productMeasuresDto = new ProductMeasuresDto();
    }
    
    @Test
    public void testConstructor() {
        Double abv = 1.0;
        Double ibu = 1.0;
        Double ph = 1.0;
        Double mashTemperature = 1.0;
        Double gravity = 1.0;
        Double yield = 1.0;
        Double brewhouseDuration = 1.0;
        Double fermentationDays = 1.0;
        Double conditioningDays = 1.0;

        ProductMeasuresDto productMeasuresDto = new ProductMeasuresDto(abv, ibu, ph, mashTemperature, gravity, yield, brewhouseDuration, fermentationDays, conditioningDays);
        
        assertSame(abv, productMeasuresDto.getAbv());
        assertSame(ibu, productMeasuresDto.getIbu());
        assertSame(ph, productMeasuresDto.getPh());
        assertSame(mashTemperature, productMeasuresDto.getMashTemperature());
        assertSame(gravity, productMeasuresDto.getGravity());
        assertSame(yield, productMeasuresDto.getYield());
        assertSame(brewhouseDuration, productMeasuresDto.getBrewhouseDuration());
        assertSame(fermentationDays, productMeasuresDto.getFermentationDays());
        assertSame(conditioningDays, productMeasuresDto.getConditioningDays());       
    }

    @Test
    public void testGetSetAbv() {
        Double abv = 1.0;
        productMeasuresDto.setAbv(abv);
        assertSame(abv, productMeasuresDto.getAbv());
    }
    
    @Test
    public void testGetSetIbu() {
        Double ibu = 1.0;
        productMeasuresDto.setIbu(ibu);
        assertSame(ibu, productMeasuresDto.getIbu());
    }
    
    @Test
    public void testGetSetPh() {
        Double ph = 1.0;
        productMeasuresDto.setPh(ph);
        assertSame(ph, productMeasuresDto.getPh());
    }
    
    @Test
    public void testGetSetMashTemperature() {
        Double mashTemperature = 1.0;
        productMeasuresDto.setMashTemperature(mashTemperature);
        assertSame(mashTemperature, productMeasuresDto.getMashTemperature());
    }
    
    
    @Test
    public void testGetSetGravity() {
        Double gravity = 1.0;
        productMeasuresDto.setGravity(gravity);
        assertSame(gravity, productMeasuresDto.getGravity());
    }
    
    
    @Test
    public void testGetSetYield() {
        Double yield = 1.0;
        productMeasuresDto.setYield(yield);
        assertSame(yield, productMeasuresDto.getYield());
    }
    
    
    @Test
    public void testGetSetBrewhouseDuration() {
        Double brewhouseDuration = 1.0;
        productMeasuresDto.setBrewhouseDuration(brewhouseDuration);
        assertSame(brewhouseDuration, productMeasuresDto.getBrewhouseDuration());
    }
    
    
    @Test
    public void testGetSetFermentationDays() {
        Double fermentationDays = 1.0;
        productMeasuresDto.setFermentationDays(fermentationDays);
        assertSame(fermentationDays, productMeasuresDto.getFermentationDays());
    }
    
    
    @Test
    public void testGetSetConditioningDays() {
        Double conditioningDays = 1.0;
        productMeasuresDto.setConditioningDays(conditioningDays);
        assertSame(conditioningDays, productMeasuresDto.getConditioningDays());
    }
}
