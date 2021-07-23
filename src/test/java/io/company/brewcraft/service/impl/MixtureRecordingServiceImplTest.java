package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.ProductMeasure;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.MixtureRecordingServiceImpl;
import io.company.brewcraft.service.MixtureService;

public class MixtureRecordingServiceImplTest {
	
	private MixtureRecordingService mixtureRecordingService;

	private MixtureRecordingRepository mixtureRecordingRepositoryMock;
	
    private MixtureService mixtureServiceMock;

	@BeforeEach
	public void init() {
		mixtureRecordingRepositoryMock = mock(MixtureRecordingRepository.class);
		mixtureServiceMock = mock(MixtureService.class);

		doAnswer(i -> i.getArgument(0, MixtureRecording.class)).when(mixtureRecordingRepositoryMock).saveAndFlush(any(MixtureRecording.class));
		doAnswer(i -> {
			Collection<MixtureRecording> coll = i.getArgument(0, Collection.class);
			coll.forEach(s -> {
				s.setProductMeasure(new ProductMeasure(1L));
			});
			return null;
		}).when(mixtureRecordingRepositoryMock).refresh(anyCollection());

		mixtureRecordingService = new MixtureRecordingServiceImpl(mixtureRecordingRepositoryMock, mixtureServiceMock);
	}

	@Test
	public void testGetMixtureRecording_ReturnsMixtureRecording() {
		doReturn(Optional.of(new MixtureRecording(1L))).when(mixtureRecordingRepositoryMock).findById(1L);

		MixtureRecording mixtureRecording = mixtureRecordingService.getMixtureRecording(1L);

		assertEquals(new MixtureRecording(1L), mixtureRecording);
	}

	@Test
	public void testGetMixtureRecording_ReturnsNull_WhenMixtureRecordingDoesNotExist() {
		doReturn(Optional.empty()).when(mixtureRecordingRepositoryMock).findById(1L);
		MixtureRecording mixtureRecording = mixtureRecordingService.getMixtureRecording(1L);

		assertNull(mixtureRecording);
	}

	@Test
	@Disabled("TODO: Find a good strategy to test get method with long list of specifications")
	public void testGetMixtureRecording() {
		fail("Not tested");
	}

	@Test
	public void testAddMixtureRecording_AddsMixtureRecording() {
		MixtureRecording mixtureRecording = new MixtureRecording(null, null, new ProductMeasure(1L), "100", LocalDateTime.of(2019, 1, 2, 3, 4),
				LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        when(mixtureServiceMock.getMixture(1L)).thenReturn(new Mixture(1L));

        MixtureRecording addedMixtureRecording = mixtureRecordingService.addMixtureRecording(mixtureRecording, 1L);
		
		assertEquals(null, addedMixtureRecording.getId());
		assertEquals(new Mixture(1L), addedMixtureRecording.getMixture());
		assertEquals(new ProductMeasure(1L), addedMixtureRecording.getProductMeasure());
		assertEquals("100", addedMixtureRecording.getValue());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedMixtureRecording.getRecordedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addedMixtureRecording.getCreatedAt());
		assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), addedMixtureRecording.getLastUpdated());
		assertEquals(1, addedMixtureRecording.getVersion());

		verify(mixtureRecordingRepositoryMock, times(1)).saveAndFlush(addedMixtureRecording);
		verify(mixtureRecordingRepositoryMock, times(1)).refresh(List.of(addedMixtureRecording));
	}

	@Test
	public void testPutMixtureRecording_OverridesWhenMixtureRecordingExists() {
		MixtureRecording existing = new MixtureRecording(1L);
		existing.setCreatedAt(LocalDateTime.of(2020, 1, 2, 3, 4));
		existing.setVersion(1);
		
		MixtureRecording update = new MixtureRecording(null, new Mixture(3L), new ProductMeasure(1L), "150", LocalDateTime.of(2010, 1, 2, 3, 4),
				LocalDateTime.of(2011, 1, 2, 3, 4), LocalDateTime.of(2012, 1, 2, 3, 4), 1);

        when(mixtureServiceMock.getMixture(3L)).thenReturn(new Mixture(3L));
        doReturn(Optional.of(existing)).when(mixtureRecordingRepositoryMock).findById(1L);
		
        MixtureRecording mixtureRecording = mixtureRecordingService.putMixtureRecording(1L, update, 3L);

		assertEquals(1L, mixtureRecording.getId());
		assertEquals(new Mixture(3L), mixtureRecording.getMixture());
		assertEquals(new ProductMeasure(1L), mixtureRecording.getProductMeasure());
		assertEquals("150", mixtureRecording.getValue());
		assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), mixtureRecording.getRecordedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureRecording.getCreatedAt());
		assertEquals(null, mixtureRecording.getLastUpdated());
		assertEquals(1, mixtureRecording.getVersion());

		verify(mixtureRecordingRepositoryMock, times(1)).saveAndFlush(mixtureRecording);
		verify(mixtureRecordingRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutMixtureRecording_AddsNewMixtureRecording_WhenNoMixtureRecordingExists() {		
		MixtureRecording update = new MixtureRecording(null, new Mixture(3L), new ProductMeasure(1L), "150", LocalDateTime.of(2010, 1, 2, 3, 4),
				LocalDateTime.of(2011, 1, 2, 3, 4), LocalDateTime.of(2012, 1, 2, 3, 4), 1);

        when(mixtureServiceMock.getMixture(3L)).thenReturn(new Mixture(3L));
        doReturn(Optional.empty()).when(mixtureRecordingRepositoryMock).findById(1L);
		
        MixtureRecording mixtureRecording = mixtureRecordingService.putMixtureRecording(1L, update, 3L);

		assertEquals(1L, mixtureRecording.getId());
		assertEquals(new Mixture(3L), mixtureRecording.getMixture());
		assertEquals(new ProductMeasure(1L), mixtureRecording.getProductMeasure());
		assertEquals("150", mixtureRecording.getValue());
		assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), mixtureRecording.getRecordedAt());
		assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), mixtureRecording.getCreatedAt());
		assertEquals(LocalDateTime.of(2012, 1, 2, 3, 4), mixtureRecording.getLastUpdated());
		assertEquals(1, mixtureRecording.getVersion());

		verify(mixtureRecordingRepositoryMock, times(1)).saveAndFlush(mixtureRecording);
		verify(mixtureRecordingRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutMixtureRecording_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
		MixtureRecording existing = new MixtureRecording(1L);
		existing.setVersion(1);
		
        when(mixtureServiceMock.getMixture(2L)).thenReturn(new Mixture(2L));
		doReturn(Optional.of(existing)).when(mixtureRecordingRepositoryMock).findById(1L);

		MixtureRecording update = new MixtureRecording(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> mixtureRecordingService.putMixtureRecording(1L, update, 2L));
	}

	@Test
	public void testPatchMixtureRecording_PatchesExistingMixtureRecording() {
		MixtureRecording existing = new MixtureRecording(1L);
		existing.setCreatedAt(LocalDateTime.of(2020, 1, 2, 3, 4));
		existing.setVersion(1);
		
		MixtureRecording update = new MixtureRecording(null, new Mixture(3L), new ProductMeasure(1L), "150", LocalDateTime.of(2010, 1, 2, 3, 4),
				LocalDateTime.of(2011, 1, 2, 3, 4), LocalDateTime.of(2012, 1, 2, 3, 4), 1);


        when(mixtureServiceMock.getMixture(3L)).thenReturn(new Mixture(3L));
        doReturn(Optional.of(existing)).when(mixtureRecordingRepositoryMock).findById(1L);
		
        MixtureRecording mixtureRecording = mixtureRecordingService.patchMixtureRecording(1L, update);

		assertEquals(1L, mixtureRecording.getId());
		assertEquals(new Mixture(3L), mixtureRecording.getMixture());
		assertEquals(new ProductMeasure(1L), mixtureRecording.getProductMeasure());
		assertEquals("150", mixtureRecording.getValue());
		assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), mixtureRecording.getRecordedAt());
		assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureRecording.getCreatedAt());
		assertEquals(null, mixtureRecording.getLastUpdated());
		assertEquals(1, mixtureRecording.getVersion());

		verify(mixtureRecordingRepositoryMock, times(1)).saveAndFlush(mixtureRecording);
		verify(mixtureRecordingRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
		MixtureRecording existing = new MixtureRecording(1L);
		existing.setVersion(1);
		
        when(mixtureServiceMock.getMixture(2L)).thenReturn(new Mixture(2L));
		doReturn(Optional.of(existing)).when(mixtureRecordingRepositoryMock).findById(1L);

		MixtureRecording update = new MixtureRecording(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> mixtureRecordingService.patchMixtureRecording(1L, update));
	}
	
	@Test
	public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
		doReturn(true).when(mixtureRecordingRepositoryMock).existsById(1L);

		boolean exists = mixtureRecordingService.mixtureRecordingExists(1L);

		assertTrue(exists);
	}

	@Test
	public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
		doReturn(false).when(mixtureRecordingRepositoryMock).existsById(1L);

		boolean exists = mixtureRecordingService.mixtureRecordingExists(1L);

		assertFalse(exists);
	}

	@Test
	public void testDelete_CallsDeleteByIdOnRepository() {
		mixtureRecordingService.deleteMixtureRecording(1L);

		verify(mixtureRecordingRepositoryMock, times(1)).deleteById(1L);
	}

}
