package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		doReturn(List.of(new BrewStageStatus(1L, "IN-PROGRESS"), new BrewStageStatus(2L, "COMPLETE"))).when(brewStageStatusRepositoryMock).findAll();

		List<BrewStageStatus> statuses = brewStageStatusService.getStatuses();
		
		assertEquals(List.of(new BrewStageStatus(1L, "IN-PROGRESS"), new BrewStageStatus(2L, "COMPLETE")), statuses);
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
