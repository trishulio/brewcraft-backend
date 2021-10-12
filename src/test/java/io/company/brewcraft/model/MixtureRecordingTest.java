package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class MixtureRecordingTest {

    private MixtureRecording mixtureRecording;

    @BeforeEach
    public void init() {
        mixtureRecording = new MixtureRecording();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Mixture mixture = new Mixture(2L);
        Measure measure = new Measure(3L);
        BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MixtureRecording mixtureRecording = new MixtureRecording(id, mixture, measure, value, recordedAt, created, lastUpdated, version);

        assertEquals(1L, mixtureRecording.getId());
        assertEquals(new Mixture(2L), mixtureRecording.getMixture());
        assertEquals(new Measure(3L), mixtureRecording.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecording.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecording.getRecordedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureRecording.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureRecording.getLastUpdated());
        assertEquals(1, mixtureRecording.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixtureRecording.setId(1L);
        assertEquals(1L, mixtureRecording.getId());
    }

    @Test
    public void testGetSetMixture() {
        mixtureRecording.setMixture(new Mixture(2L));
        assertEquals(new Mixture(2L), mixtureRecording.getMixture());
    }

    @Test
    public void testGetSetMeasure() {
        mixtureRecording.setMeasure(new Measure(3L));

        assertEquals(new Measure(3L), mixtureRecording.getMeasure());
    }

    @Test
    public void testGetSetValue() {
        mixtureRecording.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), mixtureRecording.getValue());
    }

    @Test
    public void testGetSetRecordedAt() {
        mixtureRecording.setRecordedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureRecording.getRecordedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        mixtureRecording.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureRecording.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        mixtureRecording.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureRecording.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixtureRecording.setVersion(version);
        assertEquals(version, mixtureRecording.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Mixture mixture = new Mixture(2L);
        Measure measure = new Measure(3L);
        BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MixtureRecording mixtureRecording = new MixtureRecording(id, mixture, measure, value, recordedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"mixture\":{\"id\":2,\"parentMixture\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"measure\":{\"id\":3,\"name\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"value\":100,\"recordedAt\":\"2018-01-02T03:04:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, mixtureRecording.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
