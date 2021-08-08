package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class BrewStageTest {

    private BrewStage brewStage;

    @BeforeEach
    public void init() {
        brewStage = new BrewStage();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Brew brew = new Brew(1L);
        BrewStageStatus status = new BrewStageStatus(1L);
        BrewTask task = new BrewTask(1L);
        List<Mixture> mixtures = List.of(new Mixture());
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        BrewStage brewStage = new BrewStage(id,brew, status, task, mixtures, startedAt, endedAt, created, lastUpdated, version);
        
        assertEquals(1L, brewStage.getId());
        assertEquals(new Brew(1L), brewStage.getBrew());
        assertEquals(new BrewStageStatus(1L), brewStage.getStatus());
        assertEquals(new BrewTask(1L), brewStage.getTask());
        
        assertEquals(1, brewStage.getMixtures().size());
        Mixture mixture = new Mixture();
        mixture.setBrewStage(brewStage);
        assertEquals(mixture, brewStage.getMixtures().iterator().next());        
    
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getEndedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getLastUpdated());
        assertEquals(1, brewStage.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        brewStage.setId(id);
        assertEquals(1L, brewStage.getId());
    }

    @Test
    public void testGetSetBrew() {
        Brew brew = new Brew(1L);
        brewStage.setBrew(brew);
        assertEquals(new Brew(1L), brewStage.getBrew());
    }
    
    @Test
    public void testGetSetStatus() {
        BrewStageStatus status = new BrewStageStatus(1L);
        brewStage.setStatus(status);
        assertEquals(new BrewStageStatus(1L), brewStage.getStatus());
    }
    
    @Test
    public void testGetSetTask() {
        BrewTask task = new BrewTask(1L);
        brewStage.setTask(task);
        assertEquals(new BrewTask(1L), brewStage.getTask());
    } 
    
    @Test
    public void testGetSetMixtures() {
        List<Mixture> mixtures = List.of(new Mixture());
        brewStage.setMixtures(mixtures);
        
        assertEquals(1, brewStage.getMixtures().size());
        Mixture mixture = new Mixture();
        mixture.setBrewStage(brewStage);
        assertEquals(mixture, brewStage.getMixtures().iterator().next());        
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStage.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getStartedAt());
    }
    
    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStage.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getEndedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStage.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStage.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getLastUpdated());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        brewStage.setVersion(version);
        assertEquals(version, brewStage.getVersion());
    }
    
    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Brew brew = new Brew(1L);
        BrewStageStatus status = new BrewStageStatus(1L);
        BrewTask task = new BrewTask(1L);
        List<Mixture> mixtures = List.of(new Mixture());
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        BrewStage brewStage = new BrewStage(id,brew, status, task, mixtures, startedAt, endedAt, created, lastUpdated, version);
        
        final String json = "{\"id\":1,\"brew\":{\"id\":1,\"name\":null,\"description\":null,\"batchId\":null,\"product\":null,\"parentBrew\":null,\"startedAt\":null,\"endedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"status\":{\"id\":1,\"name\":null},\"task\":{\"id\":1,\"name\":null},\"startedAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"endedAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"createdAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, brewStage.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
