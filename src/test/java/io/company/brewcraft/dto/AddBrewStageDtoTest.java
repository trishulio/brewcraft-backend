package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddBrewStageDtoTest {
	
	private AddBrewStageDto addBrewStageDto;

	@BeforeEach
	public void init() {
		addBrewStageDto = new AddBrewStageDto();
	}

	@Test
	public void testConstructor() {
		Long statusId = 3L;
		Long taskId = 4L;
		LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);

		AddBrewStageDto addBrewStageDto = new AddBrewStageDto(statusId, taskId, startedAt, endedAt);

		assertEquals(3L, addBrewStageDto.getStatusId());
		assertEquals(4L, addBrewStageDto.getTaskId());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewStageDto.getStartedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewStageDto.getEndedAt());
	}

	@Test
	public void testGetSetStatusId() {
		addBrewStageDto.setStatusId(3L);
		assertEquals(3L, addBrewStageDto.getStatusId());
	}

	@Test
	public void testGetSetTaskId() {
		addBrewStageDto.setTaskId(4L);
		assertEquals(4L, addBrewStageDto.getTaskId());
	}

	@Test
	public void testGetSetStartedAt() {
		LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		addBrewStageDto.setStartedAt(startedAt);
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewStageDto.getStartedAt());
	}

	@Test
	public void testGetSetEndedAt() {
		LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		addBrewStageDto.setEndedAt(endedAt);
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewStageDto.getEndedAt());
	}

}
