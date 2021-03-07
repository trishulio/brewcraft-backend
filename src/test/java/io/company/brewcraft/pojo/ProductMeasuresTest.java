package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMeasuresTest {

    private ProductMeasures productMeasures;

    @BeforeEach
    public void init() {
        productMeasures = new ProductMeasures();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Double abv = 1.0;
        Double ibu = 1.0;
        Double ph = 1.0;
        Double mashTemperature = 1.0;
        Double gravity = 1.0;
        Double yield = 1.0;
        Double brewhouseDuration = 1.0;
        Double fermentationDays = 1.0;
        Double conditioningDays = 1.0;

        ProductMeasures productMeasures = new ProductMeasures(id, abv, ibu, ph, mashTemperature, gravity, yield, brewhouseDuration, fermentationDays, conditioningDays);
        
        assertSame(id, productMeasures.getId());
        assertSame(abv, productMeasures.getAbv());
        assertSame(ibu, productMeasures.getIbu());
        assertSame(ph, productMeasures.getPh());
        assertSame(mashTemperature, productMeasures.getMashTemperature());
        assertSame(gravity, productMeasures.getGravity());
        assertSame(yield, productMeasures.getYield());
        assertSame(brewhouseDuration, productMeasures.getBrewhouseDuration());
        assertSame(fermentationDays, productMeasures.getFermentationDays());
        assertSame(conditioningDays, productMeasures.getConditioningDays());
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        productMeasures.setId(id);
        assertSame(id, productMeasures.getId());
    }

    @Test
    public void testGetSetAbv() {
        Double abv = 1.0;
        productMeasures.setAbv(abv);
        assertSame(abv, productMeasures.getAbv());
    }
    
    @Test
    public void testGetSetIbu() {
        Double ibu = 1.0;
        productMeasures.setIbu(ibu);
        assertSame(ibu, productMeasures.getIbu());
    }
    
    @Test
    public void testGetSetPh() {
        Double ph = 1.0;
        productMeasures.setPh(ph);
        assertSame(ph, productMeasures.getPh());
    }
    
    @Test
    public void testGetSetMashTemperature() {
        Double mashTemperature = 1.0;
        productMeasures.setMashTemperature(mashTemperature);
        assertSame(mashTemperature, productMeasures.getMashTemperature());
    }
    
    
    @Test
    public void testGetSetGravity() {
        Double gravity = 1.0;
        productMeasures.setGravity(gravity);
        assertSame(gravity, productMeasures.getGravity());
    }
    
    
    @Test
    public void testGetSetYield() {
        Double yield = 1.0;
        productMeasures.setYield(yield);
        assertSame(yield, productMeasures.getYield());
    }
    
    
    @Test
    public void testGetSetBrewhouseDuration() {
        Double brewhouseDuration = 1.0;
        productMeasures.setBrewhouseDuration(brewhouseDuration);
        assertSame(brewhouseDuration, productMeasures.getBrewhouseDuration());
    }
    
    
    @Test
    public void testGetSetFermentationDays() {
        Double fermentationDays = 1.0;
        productMeasures.setFermentationDays(fermentationDays);
        assertSame(fermentationDays, productMeasures.getFermentationDays());
    }
    
    
    @Test
    public void testGetSetConditioningDays() {
        Double conditioningDays = 1.0;
        productMeasures.setConditioningDays(conditioningDays);
        assertSame(conditioningDays, productMeasures.getConditioningDays());
    }

}
