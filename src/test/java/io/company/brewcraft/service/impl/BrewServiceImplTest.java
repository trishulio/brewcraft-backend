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
import io.company.brewcraft.model.Product;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewService;

public class BrewServiceImplTest {

    private BrewService brewService;

    private BrewRepository brewRepositoryMock;

    private Refresher<Brew, BrewAccessor> brewRefresherMock;

    @BeforeEach
    public void init() {
        brewRepositoryMock = mock(BrewRepository.class);
        brewRefresherMock = mock(Refresher.class);
        doAnswer(i -> i.getArgument(0, Brew.class)).when(brewRepositoryMock).saveAndFlush(any(Brew.class));
        doAnswer(i -> {
            Collection<Brew> coll = i.getArgument(0, Collection.class);
            coll.forEach(s -> {
                s.setProduct(new Product(1L));
                s.setParentBrew(new Brew(2L));
            });
            return null;
        }).when(brewRefresherMock).refresh(anyCollection());

        brewService = new BrewServiceImpl(brewRepositoryMock, brewRefresherMock);
    }

    @Test
    public void testGetBrew_ReturnsBrew() {
        doReturn(Optional.of(new Brew(1L))).when(brewRepositoryMock).findById(1L);

        Brew brew = brewService.getBrew(1L);

        assertEquals(new Brew(1L), brew);
    }

    @Test
    public void testGetBrew_ReturnsNull_WhenBrewDoesNotExist() {
        doReturn(Optional.empty()).when(brewRepositoryMock).findById(1L);
        Brew brew = brewService.getBrew(1L);

        assertNull(brew);
    }

    @Test
    @Disabled("TODO: Find a good strategy to test get method with long list of specifications")
    public void testGetBrews() {
        fail("Not tested");
    }

    @Test
    public void testAddBrew_AddsBrew() {
        Brew brew = new Brew(1L, "testName", "testDesc", "2", new Product(), new Brew(), null, null,
                LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4),
                LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2021, 1, 2, 3, 4), 1);

        Brew addedBrew = brewService.addBrew(brew);

        assertEquals(1L, addedBrew.getId());
        assertEquals("testName", addedBrew.getName());
        assertEquals("testDesc", addedBrew.getDescription());
        assertEquals("2", addedBrew.getBatchId());
        assertEquals(new Product(1L), addedBrew.getProduct());

        Brew parentBrew = new Brew(2L);
        parentBrew.addChildBrew(brew);
        assertEquals(parentBrew, addedBrew.getParentBrew());

        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addedBrew.getStartedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedBrew.getEndedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addedBrew.getCreatedAt());
        assertEquals(LocalDateTime.of(2021, 1, 2, 3, 4), addedBrew.getLastUpdated());
        assertEquals(1, addedBrew.getVersion());

        verify(brewRepositoryMock, times(1)).saveAndFlush(addedBrew);
        verify(brewRefresherMock, times(1)).refresh(List.of(addedBrew));
    }

    @Test
    public void testPutBrew_OverridesWhenBrewExists() {
        Brew existing = new Brew(1L, "testName", "testDesc", "2", new Product(2L), new Brew(3L), null, null,
                LocalDateTime.of(2017, 1, 2, 3, 4), LocalDateTime.of(2018, 1, 2, 3, 4),
                LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        Brew update = new Brew(null, "testNameUpdate", "testDescUpdate", "3", new Product(), new Brew(), null, null,
                LocalDateTime.of(2010, 1, 2, 3, 4), LocalDateTime.of(2011, 1, 2, 3, 4),
                LocalDateTime.of(2012, 1, 2, 3, 4), LocalDateTime.of(2013, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(brewRepositoryMock).findById(1L);

        Brew brew = brewService.putBrew(1L, update);

        assertEquals(1L, brew.getId());
        assertEquals("testNameUpdate", brew.getName());
        assertEquals("testDescUpdate", brew.getDescription());
        assertEquals("3", brew.getBatchId());
        assertEquals(new Product(1L), brew.getProduct());

        Brew parentBrew = new Brew(2L);
        parentBrew.addChildBrew(brew);
        assertEquals(parentBrew, brew.getParentBrew());

        assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), brew.getStartedAt());
        assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), brew.getEndedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brew.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getLastUpdated());
        assertEquals(1, brew.getVersion());

        verify(brewRepositoryMock, times(1)).saveAndFlush(brew);
        verify(brewRefresherMock, times(1)).refresh(anyList());
    }

    @Test
    public void testPutBrew_AddsNrewBrew_WhenNoBrewExists() {
        Brew update = new Brew(null, "testNameUpdate", "testDescUpdate", "3", new Product(), new Brew(), null, null,
                LocalDateTime.of(2010, 1, 2, 3, 4), LocalDateTime.of(2011, 1, 2, 3, 4),
                LocalDateTime.of(2012, 1, 2, 3, 4), LocalDateTime.of(2013, 1, 2, 3, 4), 1);

        doReturn(Optional.empty()).when(brewRepositoryMock).findById(1L);

        Brew brew = brewService.putBrew(1L, update);

        assertEquals(1L, brew.getId());
        assertEquals("testNameUpdate", brew.getName());
        assertEquals("testDescUpdate", brew.getDescription());
        assertEquals("3", brew.getBatchId());
        assertEquals(new Product(1L), brew.getProduct());

        Brew parentBrew = new Brew(2L);
        parentBrew.addChildBrew(brew);
        assertEquals(parentBrew, brew.getParentBrew());

        assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), brew.getStartedAt());
        assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), brew.getEndedAt());
        assertEquals(LocalDateTime.of(2012, 1, 2, 3, 4), brew.getCreatedAt());
        assertEquals(LocalDateTime.of(2013, 1, 2, 3, 4), brew.getLastUpdated());
        assertEquals(1, brew.getVersion());

        verify(brewRepositoryMock, times(1)).saveAndFlush(brew);
        verify(brewRefresherMock, times(1)).refresh(anyList());
    }

    @Test
    public void testPutBrew_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
        Brew existing = new Brew(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(brewRepositoryMock).findById(1L);

        Brew update = new Brew(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> brewService.putBrew(1L, update));
    }

    @Test
    public void testPatchBrew_PatchesExistingBrew() {
        Brew existing = new Brew(1L, "testName", "testDesc", "2", new Product(2L), new Brew(3L), null, null,
                LocalDateTime.of(2017, 1, 2, 3, 4), LocalDateTime.of(2018, 1, 2, 3, 4),
                LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        Brew update = new Brew(null, "testNameUpdate", "testDescUpdate", "3", new Product(), new Brew(), null, null,
                LocalDateTime.of(2010, 1, 2, 3, 4), LocalDateTime.of(2011, 1, 2, 3, 4),
                LocalDateTime.of(2012, 1, 2, 3, 4), LocalDateTime.of(2013, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(brewRepositoryMock).findById(1L);

        Brew brew = brewService.patchBrew(1L, update);

        assertEquals(1L, brew.getId());
        assertEquals("testNameUpdate", brew.getName());
        assertEquals("testDescUpdate", brew.getDescription());
        assertEquals("3", brew.getBatchId());
        assertEquals(new Product(1L), brew.getProduct());

        Brew parentBrew = new Brew(2L);
        parentBrew.addChildBrew(brew);
        assertEquals(parentBrew, brew.getParentBrew());

        assertEquals(LocalDateTime.of(2010, 1, 2, 3, 4), brew.getStartedAt());
        assertEquals(LocalDateTime.of(2011, 1, 2, 3, 4), brew.getEndedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), brew.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brew.getLastUpdated());
        assertEquals(1, brew.getVersion());

        verify(brewRepositoryMock, times(1)).saveAndFlush(brew);
        verify(brewRefresherMock, times(1)).refresh(anyList());
    }

    @Test
    public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
        Brew existing = new Brew(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(brewRepositoryMock).findById(1L);

        Brew update = new Brew(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> brewService.patchBrew(1L, update));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(brewRepositoryMock).existsById(1L);

        boolean exists = brewService.brewExists(1L);

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(brewRepositoryMock).existsById(1L);

        boolean exists = brewService.brewExists(1L);

        assertFalse(exists);
    }

    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        brewService.deleteBrew(1L);

        verify(brewRepositoryMock, times(1)).deleteById(1L);
    }

}
