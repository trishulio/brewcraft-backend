package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.service.MixtureRecordingAccessor;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.MixtureRecordingServiceImpl;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class MixtureRecordingServiceImplTest {

    private MixtureRecordingService mixtureRecordingService;

    private RepoService<Long, MixtureRecording, MixtureRecordingAccessor> repoService;

    private UpdateService<Long, MixtureRecording, BaseMixtureRecording, UpdateMixtureRecording> updateService;

    @BeforeEach
    public void init() {
        this.updateService = mock(UpdateService.class);
        this.repoService = mock(RepoService.class);

        doAnswer(mixtureRecording -> mixtureRecording.getArgument(0)).when(this.repoService).saveAll(anyList());

        this.mixtureRecordingService = new MixtureRecordingServiceImpl(this.repoService, this.updateService);
    }

    @Test
    public void testGetMixtureRecording_ReturnsMixtureRecording() {
        doReturn(new MixtureRecording(1L)).when(this.repoService).get(1L);

        MixtureRecording mixtureRecording = mixtureRecordingService.getMixtureRecording(1L);

        assertEquals(new MixtureRecording(1L), mixtureRecording);
    }

    @Test
    public void testGetMixtureRecording_ReturnsNull_WhenMixtureRecordingDoesNotExist() {
        doReturn(null).when(this.repoService).get(1L);
        MixtureRecording mixtureRecording = mixtureRecordingService.getMixtureRecording(1L);

        assertNull(mixtureRecording);
    }

    @Test
    @Disabled("TODO: Find a good strategy to test get method with long list of specifications")
    public void testGetMixtureRecording() {
        fail("Not tested");
    }

    @Test
    public void testAddMixtureRecordings_AddsMixtureRecordings() {
        doAnswer(mixtureRecording -> mixtureRecording.getArgument(0)).when(this.updateService).getAddEntities(any());

        final BaseMixtureRecording mixtureRecording1 = new MixtureRecording(1L);
        final BaseMixtureRecording mixtureRecording2 = new MixtureRecording();

        final List<MixtureRecording> added = this.mixtureRecordingService.addMixtureRecordings(List.of(mixtureRecording1, mixtureRecording2));

        final List<MixtureRecording> expected = List.of(
            new MixtureRecording(1L), new MixtureRecording()
        );

        assertEquals(expected, added);
        verify(this.repoService, times(1)).saveAll(added);
    }

    @Test
    public void testPut_UpdatesMixtureRecordingAndSavesToRepo_WhenUpdatesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPutEntities(any(), any());

        final UpdateMixtureRecording recording1 = new MixtureRecording(1L);
        final UpdateMixtureRecording recording2 = new MixtureRecording(2L);

        doReturn(List.of(new MixtureRecording(1L), new MixtureRecording(2L))).when(this.repoService).getByIds(List.of(recording1, recording2));

        final List<MixtureRecording> updated = this.mixtureRecordingService.putMixtureRecordings(List.of(recording1, recording2, new MixtureRecording()));

        final List<MixtureRecording> expected = List.of(
            new MixtureRecording(1L), new MixtureRecording(2L), new MixtureRecording()
        );

        assertEquals(expected, updated);
        verify(this.repoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.mixtureRecordingService.putMixtureRecordings(null));
        verify(this.repoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesMixtureRecordingsAndSavesToRepo_WhenPatchesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPatchEntities(any(), any());

        final UpdateMixtureRecording recording1 = new MixtureRecording(1L);
        final UpdateMixtureRecording recording2 = new MixtureRecording(2L);

        doReturn(List.of(new MixtureRecording(1L), new MixtureRecording(2L))).when(this.repoService).getByIds(List.of(recording1, recording2));

        final List<MixtureRecording> updated = this.mixtureRecordingService.patchMixtureRecordings(List.of(recording1, recording2));

        final List<MixtureRecording> expected = List.of(
            new MixtureRecording(1L), new MixtureRecording(2L)
        );

        assertEquals(expected, updated);
        verify(this.repoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.mixtureRecordingService.patchMixtureRecordings(null));
        verify(this.repoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllMixtureRecordingsDontExist() {
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPatchEntities(any(), any());

        final List<UpdateMixtureRecording> updates = List.of(
            new MixtureRecording(1L), new MixtureRecording(2L), new MixtureRecording(3L), new MixtureRecording(4L)
        );
        doReturn(List.of(new MixtureRecording(1L), new MixtureRecording(2L))).when(this.repoService).getByIds(updates);

        assertThrows(EntityNotFoundException.class, () -> this.mixtureRecordingService.patchMixtureRecordings(updates), "Cannot find mixture recordings with Ids: [3, 4]");
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(repoService).exists(1L);

        boolean exists = mixtureRecordingService.mixtureRecordingExists(1L);

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(repoService).exists(1L);

        boolean exists = mixtureRecordingService.mixtureRecordingExists(1L);

        assertFalse(exists);
    }

    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        mixtureRecordingService.deleteMixtureRecordings(Set.of(1L, 2L));

        verify(repoService, times(1)).delete(Set.of(1L, 2L));
    }
}
