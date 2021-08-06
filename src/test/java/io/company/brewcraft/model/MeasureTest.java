package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testToString_ReturnsJsonifiedString() {
        measure = new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);
        
        final String json = "{\"id\":1,\"name\":\"abv\",\"createdAt\":{\"nano\":0,\"year\":2018,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2019,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        assertEquals(json, measure.toString());
    }

}
