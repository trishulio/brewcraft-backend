package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class MeasureTest {
    Measure measure;

    @BeforeEach
    public void init() {
        measure = new Measure();
    }

    @Test
    public void testConstructor() {
        measure = new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        assertEquals(1L, measure.getId());
        assertEquals("abv", measure.getName());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), measure.getCreatedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), measure.getLastUpdated());
        assertEquals(1, measure.getVersion());
    }

    @Test
    public void testGetSetId() {
        measure.setId(1L);
        assertEquals(1L, measure.getId());
    }

    @Test
    public void testGetSetName() {
        measure.setName("abv");
        assertEquals("abv", measure.getName());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        measure = new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        final String json = "{\"id\":1,\"name\":\"abv\",\"createdAt\":\"2018-01-02T03:04:00\",\"lastUpdated\":\"2019-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, measure.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
