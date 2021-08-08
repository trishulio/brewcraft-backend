package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.service.BrewStageService;

public class BrewStageServiceImplTest {
	
	private BrewStageService brewStageService;

	private BrewStageRepository brewStageRepositoryMock;
	
	@BeforeEach
	public void init() {
		brewStageRepositoryMock = mock(BrewStageRepository.class);

		doAnswer(i -> i.getArgument(0, BrewStage.class)).when(brewStageRepositoryMock).saveAndFlush(any(BrewStage.class));
		doAnswer(i -> {
			Collection<BrewStage> coll = i.getArgument(0, Collection.class);
			coll.forEach(s -> {
				s.setStatus(new BrewStageStatus(1L));
				s.setTask(new BrewTask(2L));
				s.setBrew(new Brew(3L));
			});
			return null;
		}).when(brewStageRepositoryMock).refresh(anyCollection());

		brewStageService = new BrewStageServiceImpl(brewStageRepositoryMock);
	}

	@Test
	public void testGetBrewStage_ReturnsBrewStage() {
		doReturn(Optional.of(new BrewStage(1L))).when(brewStageRepositoryMock).findById(1L);

		BrewStage brewStage = brewStageService.getBrewStage(1L);

		assertEquals(new BrewStage(1L), brewStage);
	}

	@Test
	public void testGetBrewStage_ReturnsNull_WhenBrewStageDoesNotExist() {
		doReturn(Optional.empty()).when(brewStageRepositoryMock).findById(1L);
		BrewStage brewStage = brewStageService.getBrewStage(1L);

		assertNull(brewStage);
	}

	@Test
	@Disabled("TODO: Find a good strategy to test get method with long list of specifications")
	public void testGetBrews() {
		fail("Not tested");
	}

	@Test
	public void testAddBrewStage_AddsBrewStage() {
		BrewStage brewStage = new BrewStage(null, new Brew(3L), new BrewStageStatus(1L), new BrewTask(2L),
				null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4),
				LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

		BrewStage addedBrewStage = brewStageService.addBrewStage(brewStage);
		
		assertEquals(null, addedBrewStage.getId());
		assertEquals(new Brew(3L), addedBrewStage.getBrew());
		assertEquals(new BrewStageStatus(1L), addedBrewStage.getStatus());
		assertEquals(new BrewTask(2L), addedBrewStage.getTask());
		assertEquals(null, addedBrewStage.getMixtures());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addedBrewStage.getStartedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedBrewStage.getEndedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addedBrewStage.getCreatedAt());
		assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), addedBrewStage.getLastUpdated());
		assertEquals(1, addedBrewStage.getVersion());

		verify(brewStageRepositoryMock, times(1)).saveAndFlush(addedBrewStage);
		verify(brewStageRepositoryMock, times(1)).refresh(List.of(addedBrewStage));
	}

	@Test
	public void testPutBrewStage_OverridesWhenBrewStageExists() {
		BrewStage existing = new BrewStage(1L, new Brew(3L), new BrewStageStatus(1L), new BrewTask(2L),
				null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4),
				LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);
		
		BrewStage update = new BrewStage(null, new Brew(3L), new BrewStageStatus(1L), new BrewTask(2L),
				null, LocalDateTime.of(2010, 1, 2, 3, 4), LocalDateTime.of(2011, 1, 2, 3, 4),
				LocalDateTime.of(2012, 1, 2, 3, 4), LocalDateTime.of(2013, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(brewStageRepositoryMock).findById(1L);
		
		BrewStage brewStage = brewStageService.putBrewStage(1L, update);

		assertEquals(1L, brewStage.getId());
		assertEquals(new Brew(3L), brewStage.getBrew());
		assertEquals(new BrewStageStatus(1L), brewStage.getStatus());
		assertEquals(new BrewTask(2L), brewStage.getTask());
		assertEquals(null, brewStage.getMixtures());
		assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), brewStage.getStartedAt());
		assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), brewStage.getEndedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getCreatedAt());
		assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), brewStage.getLastUpdated());
		assertEquals(1, brewStage.getVersion());

		verify(brewStageRepositoryMock, times(1)).saveAndFlush(brewStage);
		verify(brewStageRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutBrewStage_AddsNewBrewStage_WhenNoBrewStageExists() {		
		BrewStage update = new BrewStage(1L, new Brew(), new BrewStageStatus(), new BrewTask(),
				null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4),
				LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        doReturn(Optional.empty()).when(brewStageRepositoryMock).findById(1L);
		
		BrewStage brewStage = brewStageService.putBrewStage(1L, update);

		assertEquals(1, brewStage.getId());
		assertEquals(new Brew(3L), brewStage.getBrew());
		assertEquals(new BrewStageStatus(1L), brewStage.getStatus());
		assertEquals(new BrewTask(2L), brewStage.getTask());
		assertEquals(null, brewStage.getMixtures());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), brewStage.getStartedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brewStage.getEndedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getCreatedAt());
		assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), brewStage.getLastUpdated());
		assertEquals(1, brewStage.getVersion());

		verify(brewStageRepositoryMock, times(1)).saveAndFlush(brewStage);
		verify(brewStageRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutBrewStage_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
		BrewStage existing = new BrewStage(1L);
		existing.setVersion(1);
		
		doReturn(Optional.of(existing)).when(brewStageRepositoryMock).findById(1L);

		BrewStage update = new BrewStage(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> brewStageService.putBrewStage(1L, update));
	}

	@Test
	public void testPatchBrewStage_PatchesExistingBrewStage() {
		BrewStage existing = new BrewStage(1L, new Brew(), new BrewStageStatus(), new BrewTask(),
				null, LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4),
				LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);
		
		BrewStage update = new BrewStage(null, new Brew(), new BrewStageStatus(), new BrewTask(),
				null, LocalDateTime.of(2010, 1, 2, 3, 4), LocalDateTime.of(2011, 1, 2, 3, 4),
				LocalDateTime.of(2012, 1, 2, 3, 4), LocalDateTime.of(2013, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(brewStageRepositoryMock).findById(1L);
		
		BrewStage brewStage = brewStageService.patchBrewStage(1L, update);

		assertEquals(1L, brewStage.getId());
		assertEquals(new Brew(3L), brewStage.getBrew());
		assertEquals(new BrewStageStatus(1L), brewStage.getStatus());
		assertEquals(new BrewTask(2L), brewStage.getTask());
		assertEquals(null, brewStage.getMixtures());
		assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), brewStage.getStartedAt());
		assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), brewStage.getEndedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewStage.getCreatedAt());
		assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), brewStage.getLastUpdated());
		assertEquals(1, brewStage.getVersion());

		verify(brewStageRepositoryMock, times(1)).saveAndFlush(brewStage);
		verify(brewStageRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
		BrewStage existing = new BrewStage(1L);
		existing.setVersion(1);
		
		doReturn(Optional.of(existing)).when(brewStageRepositoryMock).findById(1L);

		BrewStage update = new BrewStage(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> brewStageService.patchBrewStage(1L, update));
	}
	
	@Test
	public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
		doReturn(true).when(brewStageRepositoryMock).existsById(1L);

		boolean exists = brewStageService.brewStageExists(1L);

		assertTrue(exists);
	}

	@Test
	public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
		doReturn(false).when(brewStageRepositoryMock).existsById(1L);

		boolean exists = brewStageService.brewStageExists(1L);

		assertFalse(exists);
	}

	@Test
	public void testDelete_CallsDeleteByIdOnRepository() {
		brewStageService.deleteBrewStage(1L);

		verify(brewStageRepositoryMock, times(1)).deleteById(1L);
	}

}
