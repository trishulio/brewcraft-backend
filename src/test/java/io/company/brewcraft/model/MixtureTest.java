package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureTest {

    private Mixture mixture;

    @BeforeEach
    public void init() {
        mixture = new Mixture();
    }
    
    @Test
    public void testConstructor() {
		Long id = 1L;
		Mixture parentMixture = new Mixture(2L);
		List<Mixture> childMixtures = List.of(new Mixture(9L));
		Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
		Equipment equipment = new Equipment(3L);
		BrewStage brewStage = new BrewStage(4L);
		List<MaterialPortion> materialPortions = List.of(new MaterialPortion(5L));
		List<MixtureRecording> recordedMeasures = List.of(new MixtureRecording(6L));
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Mixture mixture = new Mixture(id, parentMixture, childMixtures, quantity, equipment, materialPortions, recordedMeasures, brewStage, created, lastUpdated, version);
        
        assertEquals(1L, mixture.getId());
        
        Mixture expectedParentMixture = new Mixture(2L);
        expectedParentMixture.addChildMixture(mixture);  
        assertEquals(expectedParentMixture, mixture.getParentMixture());
        
        Mixture expectedChildMixture = new Mixture(9L);
        expectedChildMixture.setParentMixture(mixture);  
        assertEquals(List.of(expectedChildMixture), mixture.getChildMixtures());
       
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), mixture.getQuantity());
        assertEquals(new Equipment(3L), mixture.getEquipment());
        assertEquals(new BrewStage(4L), mixture.getBrewStage());
        
        MaterialPortion expectedMaterialPortion = new MaterialPortion(5L);
        expectedMaterialPortion.setMixture(mixture);  
        assertEquals(List.of(expectedMaterialPortion), mixture.getMaterialPortions());
        
        MixtureRecording expectedMixtureRecording = new MixtureRecording(6L);
        expectedMixtureRecording.setMixture(mixture);  
        assertEquals(List.of(expectedMixtureRecording), mixture.getRecordedMeasures());
        
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixture.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixture.getLastUpdated());
        assertEquals(1, mixture.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        mixture.setId(1L);
        assertEquals(1L, mixture.getId());
    }

    @Test
    public void testGetSetParentMixture() {       
        mixture.setParentMixture(new Mixture(2L));
        
        Mixture expectedParentMixture = new Mixture(2L);
        expectedParentMixture.addChildMixture(mixture);  
        
        assertEquals(expectedParentMixture, mixture.getParentMixture());
    }
    
    @Test
    public void testGetSetChildMixtures() {
        mixture.setChildMixtures(List.of(new Mixture(9L)));
        
        Mixture expectedChildMixture = new Mixture(9L);
        expectedChildMixture.setParentMixture(mixture);  
        
        assertEquals(List.of(expectedChildMixture), mixture.getChildMixtures());
    }
    
    @Test
    public void testGetSetEquipment() {
        mixture.setEquipment(new Equipment(3L));
        assertEquals(new Equipment(3L), mixture.getEquipment());
    } 
    
    @Test
    public void testGetSetBrewStage() {
        mixture.setBrewStage(new BrewStage(4L));
        assertEquals(new BrewStage(4L), mixture.getBrewStage());
    }
    
    @Test
    public void testGetSetMaterialPortions() {
        mixture.setMaterialPortions(List.of(new MaterialPortion(5L)));
            
        MaterialPortion expectedMaterialPortion = new MaterialPortion(5L);
        expectedMaterialPortion.setMixture(mixture);  
        
        assertEquals(List.of(expectedMaterialPortion), mixture.getMaterialPortions());
    }
    
    @Test
    public void testGetSetRecordedMeasures() {
        mixture.setRecordedMeasures(List.of(new MixtureRecording(6L)));
    	
    	MixtureRecording expectedMixtureRecording = new MixtureRecording(6L);
        expectedMixtureRecording.setMixture(mixture);  
       
        assertEquals(List.of(expectedMixtureRecording), mixture.getRecordedMeasures());        
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        mixture.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixture.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        mixture.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixture.getLastUpdated());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixture.setVersion(version);
        assertEquals(version, mixture.getVersion());
    }
}
