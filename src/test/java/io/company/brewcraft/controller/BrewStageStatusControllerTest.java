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
import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class BrewStageStatusControllerTest {

    private BrewStageStatusController brewStageStatusController;

    private BrewStageStatusService brewStageStatusService;

    @BeforeEach
    public void init() {
        brewStageStatusService = mock(BrewStageStatusService.class);

        brewStageStatusController = new BrewStageStatusController(brewStageStatusService, new AttributeFilter());
    }

    @Test
    public void testGetBrewStageStatuses() {
        Page<BrewStageStatus> mPage = new PageImpl<>(List.of(new BrewStageStatus(1L, "IN-PROGRESS"), new BrewStageStatus(2L, "COMPLETE")));

        doReturn(mPage).when(brewStageStatusService).getStatuses(null, null, 1, 10, new TreeSet<>(List.of("id")), true);

        PageDto<BrewStageStatusDto> dto = brewStageStatusController.getStatuses(null, null, new TreeSet<>(List.of("id")), true, 1, 10);

        assertEquals(1, dto.getTotalPages());
        assertEquals(List.of(new BrewStageStatusDto(1L, "IN-PROGRESS"), new BrewStageStatusDto(2L, "COMPLETE")), dto.getContent());
    }

}