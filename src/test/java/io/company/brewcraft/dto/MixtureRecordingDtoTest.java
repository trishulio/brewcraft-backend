package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
        MixtureDto mixtureDto = new MixtureDto(2L);
        MeasureDto measure = new MeasureDto(1L, "abv", 1);
        BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        MixtureRecordingDto mixtureRecordingDto = new MixtureRecordingDto(id, mixtureDto, measure, value, recordedAt, version);

        assertEquals(1L, mixtureRecordingDto.getId());
        assertEquals(new MixtureDto(2L), mixtureRecordingDto.getMixture());
        assertEquals(new MeasureDto(1L, "abv", 1), mixtureRecordingDto.getMeasure());
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureRecordingDto.getRecordedAt());
        assertEquals(1, mixtureRecordingDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixtureRecordingDto.setId(1L);
        assertEquals(1L, mixtureRecordingDto.getId());
    }

    @Test
    public void testGetSetMixtureId() {
        mixtureRecordingDto.setMixture(new MixtureDto(2L));
        assertEquals(new MixtureDto(2L), mixtureRecordingDto.getMixture());
    }

    @Test
    public void testGetSetMeasure() {
        mixtureRecordingDto.setMeasure(new MeasureDto(1L, "abv", 1));

        assertEquals(new MeasureDto(1L, "abv", 1), mixtureRecordingDto.getMeasure());
    }

    @Test
    public void testGetSetValue() {
        mixtureRecordingDto.setValue(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), mixtureRecordingDto.getValue());
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
