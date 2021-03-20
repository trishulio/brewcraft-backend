package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMeasuresTest {

    private ProductMeasures productMeasuresEntity;

    @BeforeEach
    public void init() {
        productMeasuresEntity = new ProductMeasures();
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

        ProductMeasures productMeasuresEntity = new ProductMeasures(id, abv, ibu, ph, mashTemperature, gravity, yield, brewhouseDuration, fermentationDays, conditioningDays);
        
        assertSame(id, productMeasuresEntity.getId());
        assertSame(abv, productMeasuresEntity.getAbv());
        assertSame(ibu, productMeasuresEntity.getIbu());
        assertSame(ph, productMeasuresEntity.getPh());
        assertSame(mashTemperature, productMeasuresEntity.getMashTemperature());
        assertSame(gravity, productMeasuresEntity.getGravity());
        assertSame(yield, productMeasuresEntity.getYield());
        assertSame(brewhouseDuration, productMeasuresEntity.getBrewhouseDuration());
        assertSame(fermentationDays, productMeasuresEntity.getFermentationDays());
        assertSame(conditioningDays, productMeasuresEntity.getConditioningDays());
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        productMeasuresEntity.setId(id);
        assertSame(id, productMeasuresEntity.getId());
    }

    @Test
    public void testGetSetAbv() {
        Double abv = 1.0;
        productMeasuresEntity.setAbv(abv);
        assertSame(abv, productMeasuresEntity.getAbv());
    }
    
    @Test
    public void testGetSetIbu() {
        Double ibu = 1.0;
        productMeasuresEntity.setIbu(ibu);
        assertSame(ibu, productMeasuresEntity.getIbu());
    }
    
    @Test
    public void testGetSetPh() {
        Double ph = 1.0;
        productMeasuresEntity.setPh(ph);
        assertSame(ph, productMeasuresEntity.getPh());
    }
    
    @Test
    public void testGetSetMashTemperature() {
        Double mashTemperature = 1.0;
        productMeasuresEntity.setMashTemperature(mashTemperature);
        assertSame(mashTemperature, productMeasuresEntity.getMashTemperature());
    }
    
    
    @Test
    public void testGetSetGravity() {
        Double gravity = 1.0;
        productMeasuresEntity.setGravity(gravity);
        assertSame(gravity, productMeasuresEntity.getGravity());
    }
    
    
    @Test
    public void testGetSetYield() {
        Double yield = 1.0;
        productMeasuresEntity.setYield(yield);
        assertSame(yield, productMeasuresEntity.getYield());
    }
    
    
    @Test
    public void testGetSetBrewhouseDuration() {
        Double brewhouseDuration = 1.0;
        productMeasuresEntity.setBrewhouseDuration(brewhouseDuration);
        assertSame(brewhouseDuration, productMeasuresEntity.getBrewhouseDuration());
    }
    
    
    @Test
    public void testGetSetFermentationDays() {
        Double fermentationDays = 1.0;
        productMeasuresEntity.setFermentationDays(fermentationDays);
        assertSame(fermentationDays, productMeasuresEntity.getFermentationDays());
    }
    
    
    @Test
    public void testGetSetConditioningDays() {
        Double conditioningDays = 1.0;
        productMeasuresEntity.setConditioningDays(conditioningDays);
        assertSame(conditioningDays, productMeasuresEntity.getConditioningDays());
    }

}
