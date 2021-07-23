package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMixtureRecordingDtoTest {
    
	private UpdateMixtureRecordingDto updateMixtureRecordingDto;

    @BeforeEach
    public void init() {
        updateMixtureRecordingDto = new UpdateMixtureRecordingDto();
    }
    
    @Test
    public void testConstructor() {
		String name = "abv";
		String value = "100";
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(name, value, recordedAt, version);
        
        assertEquals("abv", updateMixtureRecordingDto.getName());
        assertEquals("100", updateMixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), updateMixtureRecordingDto.getRecordedAt());
        assertEquals(1, updateMixtureRecordingDto.getVersion());        
    }
  
    @Test
    public void testGetSetName() {
        updateMixtureRecordingDto.setName("abv");

        assertEquals("abv", updateMixtureRecordingDto.getName());
    }
    
    @Test
    public void testGetSetValue() {
        updateMixtureRecordingDto.setValue("100");
        assertEquals("100", updateMixtureRecordingDto.getValue());
    } 
    
    @Test
    public void testGetSetRecordedAt() {
        updateMixtureRecordingDto.setRecordedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), updateMixtureRecordingDto.getRecordedAt());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateMixtureRecordingDto.setVersion(version);
        assertEquals(version, updateMixtureRecordingDto.getVersion());
    }
}
