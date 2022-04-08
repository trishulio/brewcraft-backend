package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddBrewStageDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.dto.UpdateBrewStageDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.Mixture;

public class BrewStageMapperTest {
    private BrewStageMapper brewStageMapper;

    @BeforeEach
    public void init() {
        brewStageMapper = BrewStageMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity() {
        BrewStageDto dto = new BrewStageDto(1L, new BrewDto(2L), new BrewStageStatusDto(3L), new BrewTaskDto(4L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        BrewStage brewStage = brewStageMapper.fromDto(dto);

        BrewStage expectedBrewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L), new BrewTask(4L), null, LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedBrewStage, brewStage);
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddBrewStageDto dto = new AddBrewStageDto(2L, 3L, 4L, LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4));

        BrewStage brewStage = brewStageMapper.fromDto(dto);

        BrewStage expectedBrewStage = new BrewStage(null, new Brew(2L), new BrewStageStatus(3L), new BrewTask(4L), null, LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), null, null, null);

        assertEquals(expectedBrewStage, brewStage);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateBrewStageDto dto = new UpdateBrewStageDto(2L, 3L, 4L, LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        BrewStage brewStage = brewStageMapper.fromDto(dto);

        BrewStage expectedBrewStage = new BrewStage(null, new Brew(2L), new BrewStageStatus(3L), new BrewTask(4L), null, LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedBrewStage, brewStage);
    }

    @Test
    public void testToDto_ReturnsDto() {
        BrewStage brewStage = new BrewStage(1L, new Brew(2L), new BrewStageStatus(3L), new BrewTask(4L), List.of(new Mixture()), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        BrewStageDto dto = brewStageMapper.toDto(brewStage);

        assertEquals(new BrewStageDto(1L, new BrewDto(2L), new BrewStageStatusDto(3L), new BrewTaskDto(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), dto);
    }
}
