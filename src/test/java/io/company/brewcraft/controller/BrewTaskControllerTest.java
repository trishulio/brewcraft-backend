package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class BrewTaskControllerTest {

    private BrewTaskController brewTaskController;

    private BrewTaskService brewTaskService;

    @BeforeEach
    public void init() {
        brewTaskService = mock(BrewTaskService.class);

        brewTaskController = new BrewTaskController(brewTaskService, new AttributeFilter());
    }

    @Test
    public void testGetBrewTasks() {
        Page<BrewTask> mPage = new PageImpl<>(List.of(new BrewTask(1L, "MASH"), new BrewTask(2L, "BOIL")));

        doReturn(mPage).when(brewTaskService).getTasks(null, null, 1, 10, new TreeSet<>(List.of("id")), true);

        PageDto<BrewTaskDto> dto = brewTaskController.getBrewTasks(null, null, new TreeSet<>(List.of("id")), true, 1, 10);

        assertEquals(1, dto.getTotalPages());
        assertEquals(List.of(new BrewTaskDto(1L, "MASH"), new BrewTaskDto(2L, "BOIL")), dto.getContent());
    }

}