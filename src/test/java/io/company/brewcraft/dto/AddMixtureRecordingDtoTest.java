package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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
		Long mixtureId = 2L;
		Long measureId = 1L;
		BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);

        AddMixtureRecordingDto addMixtureRecordingDto = new AddMixtureRecordingDto(mixtureId, measureId, value, recordedAt);
        
        assertEquals(2L, addMixtureRecordingDto.getMixtureId());
        assertEquals(1L, addMixtureRecordingDto.getMeasureId());
        assertEquals(new BigDecimal("100"), addMixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addMixtureRecordingDto.getRecordedAt());
    }
    
    @Test
    public void testGetSetMixtureId() {
        addMixtureRecordingDto.setMixtureId(2L);

        assertEquals(2L, addMixtureRecordingDto.getMixtureId());
    }
    
    @Test
    public void testGetSetMeasureId() {
        addMixtureRecordingDto.setMeasureId(1L);

        assertEquals(1L, addMixtureRecordingDto.getMeasureId());
    }
    
    @Test
    public void testGetSetValue() {
        addMixtureRecordingDto.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), addMixtureRecordingDto.getValue());
    } 
    
    @Test
    public void testGetSetRecordedAt() {
        addMixtureRecordingDto.setRecordedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addMixtureRecordingDto.getRecordedAt());
    }

}
