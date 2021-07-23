package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMixtureRecordingDtoTest {
	
    private AddMixtureRecordingDto addMixtureRecordingDto;

    @BeforeEach
    public void init() {
        addMixtureRecordingDto = new AddMixtureRecordingDto();
    }
    
    @Test
    public void testConstructor() {
		String name = "abv";
		String value = "100";
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);

        AddMixtureRecordingDto addMixtureRecordingDto = new AddMixtureRecordingDto(name, value, recordedAt);
        
        assertEquals("abv", addMixtureRecordingDto.getName());
        assertEquals("100", addMixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addMixtureRecordingDto.getRecordedAt());
    }
    
    @Test
    public void testGetSetName() {
        addMixtureRecordingDto.setName("abv");

        assertEquals("abv", addMixtureRecordingDto.getName());
    }
    
    @Test
    public void testGetSetValue() {
        addMixtureRecordingDto.setValue("100");
        assertEquals("100", addMixtureRecordingDto.getValue());
    } 
    
    @Test
    public void testGetSetRecordedAt() {
        addMixtureRecordingDto.setRecordedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addMixtureRecordingDto.getRecordedAt());
    }

}
