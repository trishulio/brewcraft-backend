package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateBrewStageDtoTest {
	
	private UpdateBrewStageDto updateBrewStageDto;

	@BeforeEach
	public void init() {
		updateBrewStageDto = new UpdateBrewStageDto();
	}

	@Test
	public void testConstructor() {
		Long statusId = 3L;
		Long taskId = 4L;
		LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		int version = 1;

		UpdateBrewStageDto updateBrewStageDto = new UpdateBrewStageDto(statusId, taskId, startedAt, endedAt, version);

		assertEquals(3L, updateBrewStageDto.getStatusId());
		assertEquals(4L, updateBrewStageDto.getTaskId());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewStageDto.getStartedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewStageDto.getEndedAt());
		assertEquals(1, updateBrewStageDto.getVersion());
	}

	@Test
	public void testGetSetStatusId() {
		updateBrewStageDto.setStatusId(3L);
		assertEquals(3L, updateBrewStageDto.getStatusId());
	}

	@Test
	public void testGetSetTaskId() {
		updateBrewStageDto.setTaskId(4L);
		assertEquals(4L, updateBrewStageDto.getTaskId());
	}
	
	@Test
	public void testGetSetStartedAt() {
		LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		updateBrewStageDto.setStartedAt(startedAt);
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewStageDto.getStartedAt());
	}

	@Test
	public void testGetSetEndedAt() {
		LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
		updateBrewStageDto.setEndedAt(endedAt);
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewStageDto.getEndedAt());
	}

	@Test
	public void testGetSetVersion() {
		Integer version = 1;
		updateBrewStageDto.setVersion(version);
		assertEquals(version, updateBrewStageDto.getVersion());
	}

}
