package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.BaseEquipment;
import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EquipmentServiceTest {
    private EquipmentService service;

    private UpdateService<Long, Equipment, BaseEquipment, UpdateEquipment> mUpdateService;
    private RepoService<Long, Equipment, EquipmentAccessor> mRepoService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.mRepoService = mock(RepoService.class);

        doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

        this.service = new EquipmentService(this.mUpdateService, this.mRepoService);
    }

    @Test
    public void testGetEquipment_ReturnsEntitiesFromRepoService_WithCustomSpec() {
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Specification<Equipment>> captor = ArgumentCaptor.forClass(Specification.class);
        final Page<Equipment> mPage = new PageImpl<>(List.of(new Equipment(1L)));
        doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

        final Page<Equipment> page = this.service.getEquipment(Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of(3L), // facilityIds
            Set.of(4L), // typeIds
            10, // page
            20, // size
            new TreeSet<>(List.of("id")), // sort
            true // orderAscending
        );

        final Page<Equipment> expected = new PageImpl<>(List.of(new Equipment(1L)));
        assertEquals(expected, page);

        // TODO: Pending testing for the specification
        captor.getValue();
    }

    @Test
    public void testGetEquipment_ReturnsEquipmentPojo_WhenRepoServiceReturnsOptionalWithEntity() {
        doReturn(new Equipment(1L)).when(this.mRepoService).get(1L);

        final Equipment equipment = this.service.get(1L);

        assertEquals(new Equipment(1L), equipment);
    }

    @Test
    public void testGetByIds_CallsRepoService() {
        ArgumentCaptor<List<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(List.class);

        doReturn(List.of(new Equipment(1L))).when(mRepoService).getByIds(captor.capture());

        assertEquals(List.of(new Equipment(1L)), service.getByIds(List.of(() -> 1L)));
        assertEquals(1L, captor.getValue().get(0).getId());
    }

    @Test
    public void testGetByAccessorIds_CallsRepoService() {
        ArgumentCaptor<Function<EquipmentAccessor, Equipment>> captor = ArgumentCaptor.forClass(Function.class);

        List<? extends EquipmentAccessor> accessors = List.of(new EquipmentAccessor() {
             @Override
             public void setEquipment(Equipment equipment) {}
             @Override
             public Equipment getEquipment() {
                 return new Equipment(1L);
             }
         });

        doReturn(List.of(new Equipment(1L))).when(mRepoService).getByAccessorIds(eq(accessors), captor.capture());

        assertEquals(List.of(new Equipment(1L)), service.getByAccessorIds(accessors));
        assertEquals(new Equipment(1L), captor.getValue().apply(accessors.get(0)));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testDelete_CallsRepoServiceDeleteBulk_WhenEquipmentExists() {
        doReturn(123L).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

        final long count = this.service.delete(Set.of(1L, 2L, 3L));
        assertEquals(123L, count);
    }

    @Test
    public void testDelete_CallsRepoServiceDelete_WhenEquipmentExists() {
        this.service.delete(1L);
        verify(this.mRepoService).delete(Set.of(1L));
    }

    @Test
    public void testAdd_AddsEquipmentAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

        final BaseEquipment equipment1 = new Equipment(1L);
        final BaseEquipment equipment2 = new Equipment();

        final List<Equipment> added = this.service.add(List.of(equipment1, equipment2));

        final List<Equipment> expected = List.of(new Equipment(1L), new Equipment());

        assertEquals(expected, added);
        verify(this.mRepoService, times(1)).saveAll(added);
    }

    @Test
    public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.add(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPut_UpdatesEquipmentAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

        final UpdateEquipment equipment1 = new Equipment(1L);
        final UpdateEquipment equipment2 = new Equipment(2L);

        doReturn(List.of(new Equipment(1L), new Equipment(2L))).when(this.mRepoService).getByIds(List.of(equipment1, equipment2));

        final List<Equipment> updated = this.service.put(List.of(equipment1, equipment2, new Equipment()));

        final List<Equipment> expected = List.of(new Equipment(1L), new Equipment(2L), new Equipment());

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.put(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesEquipmentAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

        final UpdateEquipment equipment1 = new Equipment(1L);
        final UpdateEquipment equipment2 = new Equipment(2L);

        doReturn(List.of(new Equipment(1L), new Equipment(2L))).when(this.mRepoService).getByIds(List.of(equipment1, equipment2));

        final List<Equipment> updated = this.service.patch(List.of(equipment1, equipment2));

        final List<Equipment> expected = List.of(new Equipment(1L), new Equipment(2L));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.service.patch(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllEquipmentDontExist() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

        final List<UpdateEquipment> updates = List.of(new Equipment(1L), new Equipment(2L), new Equipment(3L), new Equipment(4L));
        doReturn(List.of(new Equipment(1L), new Equipment(2L))).when(this.mRepoService).getByIds(updates);

        assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find equipment with Ids: [3, 4]");
    }
}
