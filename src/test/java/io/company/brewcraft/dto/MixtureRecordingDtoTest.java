package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MixtureRecordingDtoTest {
	
    private MixtureRecordingDto mixtureRecordingDto;

    @BeforeEach
    public void init() {
        mixtureRecordingDto = new MixtureRecordingDto();
    }
    
    @Test
    public void testConstructor() {
		Long id = 1L;
		String name = "abv";
		String value = "100";
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        MixtureRecordingDto mixtureRecordingDto = new MixtureRecordingDto(id, name, value, recordedAt, version);
        
        assertEquals(1L, mixtureRecordingDto.getId());       
        assertEquals("abv", mixtureRecordingDto.getName());
        assertEquals("100", mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        mixtureRecordingDto.setId(1L);
        assertEquals(1L, mixtureRecordingDto.getId());
    }
    
    @Test
    public void testGetSetName() {
        mixtureRecordingDto.setName("abv");

        assertEquals("abv", mixtureRecordingDto.getName());
    }
    
    @Test
    public void testGetSetValue() {
        mixtureRecordingDto.setValue("100");
        assertEquals("100", mixtureRecordingDto.getValue());
    } 
    
    @Test
    public void testGetSetRecordedAt() {
        mixtureRecordingDto.setRecordedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixtureRecordingDto.setVersion(version);
        assertEquals(version, mixtureRecordingDto.getVersion());
    }

}
