package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
        Long id = 5L;
        Long mixtureId = 2L;
        Long measureId = 1L;
        BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(id, mixtureId, measureId, value, recordedAt, version);

        assertEquals(5L, updateMixtureRecordingDto.getId());
        assertEquals(2L, updateMixtureRecordingDto.getMixtureId());
        assertEquals(1L, updateMixtureRecordingDto.getMeasureId());
        assertEquals(new BigDecimal("100"), updateMixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), updateMixtureRecordingDto.getRecordedAt());
        assertEquals(1, updateMixtureRecordingDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        updateMixtureRecordingDto.setId(5L);

        assertEquals(5L, updateMixtureRecordingDto.getId());
    }

    @Test
    public void testGetSetMixtureId() {
        updateMixtureRecordingDto.setMixtureId(2L);

        assertEquals(2L, updateMixtureRecordingDto.getMixtureId());
    }

    @Test
    public void testGetSetMeasureId() {
        updateMixtureRecordingDto.setMeasureId(1L);

        assertEquals(1L, updateMixtureRecordingDto.getMeasureId());
    }

    @Test
    public void testGetSetValue() {
        updateMixtureRecordingDto.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), updateMixtureRecordingDto.getValue());
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
