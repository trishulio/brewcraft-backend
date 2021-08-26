package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class BrewTest {

    private Brew brew;

    @BeforeEach
    public void init() {
        brew = new Brew();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        String batchId = "2";
        Product product = new Product();
        Brew parentBrew = new Brew();
        List<Brew> childBrews = List.of(new Brew());
        List<BrewStage> brewStages = List.of(new BrewStage());
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Brew brew = new Brew(id, name, description, batchId, product, parentBrew, childBrews, brewStages, startedAt, endedAt, created, lastUpdated, version);

        assertEquals(1L, brew.getId());
        assertEquals("testName", brew.getName());
        assertEquals("testDesc", brew.getDescription());
        assertEquals("2", brew.getBatchId());
        assertEquals(new Product(), brew.getProduct());
        assertEquals(parentBrew, brew.getParentBrew());

        assertEquals(1, brew.getChildBrews().size());
        Brew childBrew = new Brew();
        childBrew.setParentBrew(brew);
        assertEquals(childBrew, brew.getChildBrews().iterator().next());

        assertEquals(1, brew.getBrewStages().size());
        BrewStage stage = new BrewStage();
        stage.setBrew(brew);
        assertEquals(stage, brew.getBrewStages().iterator().next());

        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getLastUpdated());
        assertEquals(1, brew.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        brew.setId(id);
        assertEquals(1L, brew.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        brew.setName(name);
        assertEquals("testName", brew.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        brew.setDescription(description);
        assertEquals("testDesc", brew.getDescription());
    }

    @Test
    public void testGetSetBatchId() {
        brew.setBatchId("2");
        assertEquals("2", brew.getBatchId());
    }

    @Test
    public void testGetSetProduct() {
        Product product = new Product();
        brew.setProduct(product);
        assertEquals(new Product(), brew.getProduct());
    }

    @Test
    public void testGetSetParentBrew() {
        Brew parentBrew = new Brew();
        brew.setParentBrew(parentBrew);

        assertEquals(parentBrew, brew.getParentBrew());
    }

    @Test
    public void testGetSetChildBrews() {
        List<Brew> childBrews = List.of(new Brew());
        brew.setChildBrews(childBrews);

        assertEquals(1, brew.getChildBrews().size());
        Brew childBrew = new Brew();
        childBrew.setParentBrew(brew);
        assertEquals(childBrew, brew.getChildBrews().iterator().next());
    }

    @Test
    public void testGetSetBrewStages() {
        List<BrewStage> brewStages = List.of(new BrewStage());
        brew.setBrewStages(brewStages);

        assertEquals(1, brew.getBrewStages().size());
        BrewStage stage = new BrewStage();
        stage.setBrew(brew);
        assertEquals(stage, brew.getBrewStages().iterator().next());
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brew.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getStartedAt());
    }

    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brew.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getEndedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        brew.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        brew.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        brew.setVersion(version);
        assertEquals(version, brew.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        String batchId = "2";
        Product product = new Product();
        Brew parentBrew = new Brew();
        List<Brew> childBrews = List.of(new Brew());
        List<BrewStage> brewStages = List.of(new BrewStage());
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Brew brew = new Brew(id, name, description, batchId, product, parentBrew, childBrews, brewStages, startedAt, endedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"name\":\"testName\",\"description\":\"testDesc\",\"batchId\":\"2\",\"product\":{\"id\":null,\"name\":null,\"description\":null,\"category\":null,\"targetMeasures\":null,\"createdAt\":null,\"lastUpdated\":null,\"deletedAt\":null,\"version\":null},\"parentBrew\":{\"id\":null,\"name\":null,\"description\":null,\"batchId\":null,\"product\":null,\"parentBrew\":null,\"startedAt\":null,\"endedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"startedAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"endedAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"createdAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, brew.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
