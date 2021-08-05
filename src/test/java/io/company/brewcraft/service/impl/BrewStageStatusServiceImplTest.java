package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.BrewStageStatusServiceImpl;

public class BrewStageStatusServiceImplTest {
    private BrewStageStatusService brewStageStatusService;

    private BrewStageStatusRepository brewStageStatusRepositoryMock;

    @BeforeEach
    public void init() {
        brewStageStatusRepositoryMock = mock(BrewStageStatusRepository.class);
        brewStageStatusService = new BrewStageStatusServiceImpl(brewStageStatusRepositoryMock);
    }

    @Test
    public void testGetBrewStageStatuses_ReturnsListOfBrewStageStatuses() {
        Page<BrewStageStatus> expectedBrewStageStatusPage = new PageImpl<>(List.of(new BrewStageStatus(1L, "IN-PROGRESS"), new BrewStageStatus(2L, "COMPLETE")));

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(brewStageStatusRepositoryMock.findAll(ArgumentMatchers.<Specification<BrewStageStatus>>any(), pageableArgument.capture())).thenReturn(expectedBrewStageStatusPage);

        Page<BrewStageStatus> actualBrewStageStatusPage = brewStageStatusService.getStatuses(null, 0, 100, new TreeSet<>(List.of("id")), true);

        assertEquals(List.of(new BrewStageStatus(1L, "IN-PROGRESS"), new BrewStageStatus(2L, "COMPLETE")), actualBrewStageStatusPage.getContent());
    }

    @Test
    public void testGetBrewStageStatus_ReturnsStatus_WhenEntityExists() {
        doReturn(Set.of(new BrewStageStatus(1L, "IN-PROGRESS"))).when(brewStageStatusRepositoryMock).findByNames(Set.of("MASH"));

        BrewStageStatus status = brewStageStatusService.getStatus("MASH");
        assertEquals(new BrewStageStatus(1L, "IN-PROGRESS"), status);
    }

    @Test
    public void testGetBrewStageStatus_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(new ArrayList<>()).when(brewStageStatusRepositoryMock).findByNames(Set.of("COMPLETE"));

        BrewStageStatus status = brewStageStatusService.getStatus("COMPLETE");
        assertNull(status);
    }
}
