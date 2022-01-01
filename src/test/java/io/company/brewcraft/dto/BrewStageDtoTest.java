package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrewStageDtoTest {

    private BrewStageDto brewStageDto;

    @BeforeEach
    public void init() {
        brewStageDto = new BrewStageDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        BrewDto brewId = new BrewDto(2L);
        BrewStageStatusDto status = new BrewStageStatusDto(3L);
        BrewTaskDto task = new BrewTaskDto(4L);
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        BrewStageDto brewStageDto = new BrewStageDto(id, brewId, status, task, startedAt, endedAt, version);

        assertEquals(1L, brewStageDto.getId());
        assertEquals(new BrewDto(2L), brewStageDto.getBrew());
        assertEquals(new BrewStageStatusDto(3L), brewStageDto.getStatus());
        assertEquals(new BrewTaskDto(4L), brewStageDto.getTask());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStageDto.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStageDto.getEndedAt());
        assertEquals(1, brewStageDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        brewStageDto.setId(id);
        assertEquals(1L, brewStageDto.getId());
    }

    @Test
    public void testGetSetBrew() {
        brewStageDto.setBrew(new BrewDto(2L));
        assertEquals(new BrewDto(2L), brewStageDto.getBrew());
    }

    @Test
    public void testGetSetStatus() {
        brewStageDto.setStatus(new BrewStageStatusDto(3L));
        assertEquals(new BrewStageStatusDto(3L), brewStageDto.getStatus());
    }

    @Test
    public void testGetSetTask() {
        brewStageDto.setTask(new BrewTaskDto(4L));
        assertEquals(new BrewTaskDto(4L), brewStageDto.getTask());
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStageDto.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStageDto.getStartedAt());
    }

    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewStageDto.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStageDto.getEndedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        brewStageDto.setVersion(version);
        assertEquals(version, brewStageDto.getVersion());
    }
}
