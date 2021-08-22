package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddMixtureRecordingDto;
import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.UpdateMixtureRecordingDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;

public class MixtureRecordingMapperTest {

    private MixtureRecordingMapper mixtureRecordingMapper;

    @BeforeEach
    public void init() {
        mixtureRecordingMapper = MixtureRecordingMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddMixtureRecordingDto addMixtureRecordingDto = new AddMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4));

        MixtureRecording mixtureRecording = mixtureRecordingMapper.fromDto(addMixtureRecordingDto);

        MixtureRecording expectedMixtureRecording = new MixtureRecording(null, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, null);

        assertEquals(expectedMixtureRecording, mixtureRecording);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateMixtureRecordingDto updateMixtureRecordingDto = new UpdateMixtureRecordingDto(2L, 1L, new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureRecording mixtureRecording = mixtureRecordingMapper.fromDto(updateMixtureRecordingDto);

        MixtureRecording expectedMixtureRecording = new MixtureRecording(null, new Mixture(2L), new Measure(1L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedMixtureRecording, mixtureRecording);
    }

    @Test
    public void testToDto_ReturnsDto() {
        MixtureRecording mixtureRecording = new MixtureRecording(1L, new Mixture(2L), new Measure(3L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        MixtureRecordingDto dto = mixtureRecordingMapper.toDto(mixtureRecording);

        MixtureRecordingDto expectedMixtureRecordingDto = new MixtureRecordingDto(1L, 2L, new MeasureDto(3L), new BigDecimal("100"), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        assertEquals(expectedMixtureRecordingDto, dto);
    }

}
