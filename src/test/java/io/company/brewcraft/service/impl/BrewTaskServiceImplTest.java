package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.BrewTaskServiceImpl;

public class BrewTaskServiceImplTest {
	private BrewTaskService brewTaskService;

	private BrewTaskRepository brewTaskRepositoryMock;

	@BeforeEach
	public void init() {
		brewTaskRepositoryMock = mock(BrewTaskRepository.class);
		brewTaskService = new BrewTaskServiceImpl(brewTaskRepositoryMock);
	}
	
	@Test
	public void testGetBrewTasks_ReturnsListOfBrewTasks() {
		doReturn(List.of(new BrewTask(1L, "MASH"), new BrewTask(2L, "BOIL"))).when(brewTaskRepositoryMock).findAll();

		List<BrewTask> brewTasks = brewTaskService.getTasks();
		
		assertEquals(List.of(new BrewTask(1L, "MASH"), new BrewTask(2L, "BOIL")), brewTasks);
	}

	@Test
	public void testGetBrewTask_ReturnsPojo_WhenEntityExists() {
		doReturn(Set.of(new BrewTask(1L, "MASH"))).when(brewTaskRepositoryMock).findByNames(Set.of("MASH"));

		BrewTask brewTask = brewTaskService.getTask("MASH");
		assertEquals(new BrewTask(1L, "MASH"), brewTask);
	}

	@Test
	public void testGetBrewTask_ReturnsNull_WhenEntityDoesNotExists() {
		doReturn(new ArrayList<>()).when(brewTaskRepositoryMock).findByNames(Set.of("MASH"));

		BrewTask brewTask = brewTaskService.getTask("MASH");
		assertNull(brewTask);
	}
}
