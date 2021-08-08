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

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.service.MixtureService;
import io.company.brewcraft.service.MixtureServiceImpl;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureServiceImplTest {
	
	private MixtureService mixtureService;

	private MixtureRepository mixtureRepositoryMock;

	@BeforeEach
	public void init() {
		mixtureRepositoryMock = mock(MixtureRepository.class);
		doAnswer(i -> i.getArgument(0, Mixture.class)).when(mixtureRepositoryMock).saveAndFlush(any(Mixture.class));
		doAnswer(i -> {
			Collection<Mixture> coll = i.getArgument(0, Collection.class);
			coll.forEach(s -> {
				s.setBrewStage(new BrewStage(4L));
				s.setParentMixture(new Mixture(2L));
				s.setEquipment(new Equipment(3L));
				
			});
			return null;
		}).when(mixtureRepositoryMock).refresh(anyCollection());

		mixtureService = new MixtureServiceImpl(mixtureRepositoryMock);
	}

	@Test
	public void testGetMixture_ReturnsMixture() {
		doReturn(Optional.of(new Mixture(1L))).when(mixtureRepositoryMock).findById(1L);

		Mixture mixture = mixtureService.getMixture(1L);

		assertEquals(new Mixture(1L), mixture);
	}

	@Test
	public void testGetMixture_ReturnsNull_WhenMixtureDoesNotExist() {
		doReturn(Optional.empty()).when(mixtureRepositoryMock).findById(1L);
		Mixture mixture = mixtureService.getMixture(1L);

		assertNull(mixture);
	}

	@Test
	@Disabled("TODO: Find a good strategy to test get method with long list of specifications")
	public void testGetMixtures() {
		fail("Not tested");
	}

	@Test
	public void testAddMixture_AddsMixture() {
		Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

		Mixture addedMixture = mixtureService.addMixture(mixture);

		assertEquals(1L, addedMixture.getId());
		
		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(mixture);
		assertEquals(parentMixture, addedMixture.getParentMixture());
		
		assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), addedMixture.getQuantity());
		assertEquals(new Equipment(3L), addedMixture.getEquipment());
		assertEquals(1, addedMixture.getMaterialPortions().size());
		assertEquals(6L, addedMixture.getMaterialPortions().get(0).getId());
		assertEquals(1, addedMixture.getRecordedMeasures().size());		
		assertEquals(7L, addedMixture.getRecordedMeasures().get(0).getId());		
		assertEquals(new BrewStage(4L), addedMixture.getBrewStage());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addedMixture.getCreatedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedMixture.getLastUpdated());
		assertEquals(1, addedMixture.getVersion());

		verify(mixtureRepositoryMock, times(1)).saveAndFlush(addedMixture);
		verify(mixtureRepositoryMock, times(1)).refresh(List.of(addedMixture));
	}

	@Test
	public void testPutMixture_OverridesWhenMixtureExists() {
		Mixture existing = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

		Mixture update = new Mixture(null, new Mixture(2L), null, Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(mixtureRepositoryMock).findById(1L);
		
		Mixture putMixture = mixtureService.putMixture(1L, update);

		assertEquals(1L, putMixture.getId());
		
		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(putMixture);
		assertEquals(parentMixture, putMixture.getParentMixture());
		
		assertEquals(Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), putMixture.getQuantity());
		assertEquals(new Equipment(3L), putMixture.getEquipment());
		assertEquals(1, putMixture.getMaterialPortions().size());
		assertEquals(6L, putMixture.getMaterialPortions().get(0).getId());
		assertEquals(1, putMixture.getRecordedMeasures().size());		
		assertEquals(7L, putMixture.getRecordedMeasures().get(0).getId());		
		assertEquals(new BrewStage(4L), putMixture.getBrewStage());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putMixture.getCreatedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), putMixture.getLastUpdated());
		assertEquals(1, putMixture.getVersion());

		verify(mixtureRepositoryMock, times(1)).saveAndFlush(putMixture);
		verify(mixtureRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutMixture_AddsNewMixture_WhenNoMixtureExists() {		
		Mixture update = new Mixture(null, new Mixture(2L), null, Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        doReturn(Optional.empty()).when(mixtureRepositoryMock).findById(1L);
		
        Mixture putMixture = mixtureService.putMixture(1L, update);

		assertEquals(1L, putMixture.getId());
		
		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(putMixture);
		assertEquals(parentMixture, putMixture.getParentMixture());
		
		assertEquals(Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), putMixture.getQuantity());
		assertEquals(new Equipment(3L), putMixture.getEquipment());
		assertEquals(1, putMixture.getMaterialPortions().size());
		assertEquals(6L, putMixture.getMaterialPortions().get(0).getId());
		assertEquals(1, putMixture.getRecordedMeasures().size());		
		assertEquals(7L, putMixture.getRecordedMeasures().get(0).getId());		
		assertEquals(new BrewStage(4L), putMixture.getBrewStage());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putMixture.getCreatedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), putMixture.getLastUpdated());
		assertEquals(1, putMixture.getVersion());

		verify(mixtureRepositoryMock, times(1)).saveAndFlush(putMixture);
		verify(mixtureRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPutMixture_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
		Mixture existing = new Mixture(1L);
		existing.setVersion(1);
		doReturn(Optional.of(existing)).when(mixtureRepositoryMock).findById(1L);

		Mixture update = new Mixture(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> mixtureService.putMixture(1L, update));
	}

	@Test
	public void testPatchMixture_PatchesExistingMixture() {
		Mixture existing = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

		Mixture update = new Mixture(null, new Mixture(12L), null, Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(mixtureRepositoryMock).findById(1L);
		
        Mixture patchMixture = mixtureService.patchMixture(1L, update);

		assertEquals(1L, patchMixture.getId());
		
		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(patchMixture);
		assertEquals(parentMixture, patchMixture.getParentMixture());
		
		assertEquals(Quantities.getQuantity(150.0, SupportedUnits.HECTOLITRE), patchMixture.getQuantity());
		assertEquals(new Equipment(3L), patchMixture.getEquipment());
		assertEquals(1, patchMixture.getMaterialPortions().size());
		assertEquals(6L, patchMixture.getMaterialPortions().get(0).getId());
		assertEquals(1, patchMixture.getRecordedMeasures().size());		
		assertEquals(7L, patchMixture.getRecordedMeasures().get(0).getId());		
		assertEquals(new BrewStage(4L), patchMixture.getBrewStage());
		assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), patchMixture.getCreatedAt());
		assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), patchMixture.getLastUpdated());
		assertEquals(1, patchMixture.getVersion());

		verify(mixtureRepositoryMock, times(1)).saveAndFlush(patchMixture);
		verify(mixtureRepositoryMock, times(1)).refresh(anyList());
	}

	@Test
	public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
		Mixture existing = new Mixture(1L);
		existing.setVersion(1);
		doReturn(Optional.of(existing)).when(mixtureRepositoryMock).findById(1L);

		Mixture update = new Mixture(1L);
		existing.setVersion(2);

		assertThrows(OptimisticLockException.class, () -> mixtureService.patchMixture(1L, update));
	}
	
	@Test
	public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
		doReturn(true).when(mixtureRepositoryMock).existsById(1L);

		boolean exists = mixtureService.mixtureExists(1L);

		assertTrue(exists);
	}

	@Test
	public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
		doReturn(false).when(mixtureRepositoryMock).existsById(1L);

		boolean exists = mixtureService.mixtureExists(1L);

		assertFalse(exists);
	}

	@Test
	public void testDelete_CallsDeleteByIdOnRepository() {
		mixtureService.deleteMixture(1L);

		verify(mixtureRepositoryMock, times(1)).deleteById(1L);
	}

}
