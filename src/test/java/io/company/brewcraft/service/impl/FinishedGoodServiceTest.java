package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.model.BaseFinishedGood;
import io.company.brewcraft.model.BaseFinishedGoodMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodMixturePortion;
import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodAccessor;
import io.company.brewcraft.service.FinishedGoodMaterialPortionService;
import io.company.brewcraft.service.FinishedGoodMixturePortionService;
import io.company.brewcraft.service.FinishedGoodService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class FinishedGoodServiceTest {

   private FinishedGoodService service;

   private FinishedGoodMaterialPortionService mFgMaterialPortionService;

   private FinishedGoodMixturePortionService mFgMixturePortionService;

   private UpdateService<Long, FinishedGood, BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>, UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> mUpdateService;

   private RepoService<Long, FinishedGood, FinishedGoodAccessor> mRepoService;

   @BeforeEach
   public void init() {
       this.mFgMaterialPortionService = mock(FinishedGoodMaterialPortionService.class);
       this.mFgMixturePortionService = mock(FinishedGoodMixturePortionService.class);
       this.mUpdateService = mock(UpdateService.class);
       this.mRepoService = mock(RepoService.class);

       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mRepoService).saveAll(anyList());

       this.service = new FinishedGoodService(this.mUpdateService, this.mFgMixturePortionService, mFgMaterialPortionService, this.mRepoService);
   }

   @Test
   public void testGetFinishedGoods_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       @SuppressWarnings("unchecked")
       final ArgumentCaptor<Specification<FinishedGood>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<FinishedGood> mPage = new PageImpl<>(List.of(new FinishedGood(1L)));
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

       final Page<FinishedGood> page = this.service.getFinishedGoods(
           Set.of(1L),
           Set.of(2L),
           Set.of(3L),
           Set.of(4L),
           Set.of(5L),
           Set.of(6L),
           Set.of("a123"),
           Set.of(8L),
           new TreeSet<>(List.of("id")),
           true,
           10,
           20
       );

       final Page<FinishedGood> expected = new PageImpl<>(List.of(new FinishedGood(1L)));
       assertEquals(expected, page);

       // TODO: Pending testing for the specification
       captor.getValue();
   }

   @Test
   public void testGetFinishedGood_ReturnsFinishedGoodPojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new FinishedGood(1L)).when(this.mRepoService).get(1L);

       final FinishedGood finishedGood = this.service.get(1L);

       assertEquals(new FinishedGood(1L), finishedGood);
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
   public void testDelete_CallsRepoServiceDeleteBulk_WhenFinishedGoodExists() {
       doReturn(123).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

       final int count = this.service.delete(Set.of(1L, 2L, 3L));
       assertEquals(123, count);
   }

   @Test
   public void testDelete_CallsRepoServiceDelete_WhenFinishedGoodExists() {
       this.service.delete(1L);
       verify(this.mRepoService).delete(1L);
   }

   @Test
   public void testAdd_AddsFinishedGoodAndMaterialPortionsAndMixturePortionsAndSavesToRepo_WhenAdditionsAreNotNull() {
       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mFgMixturePortionService).getAddEntities(any());
       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mFgMaterialPortionService).getAddEntities(any());
       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

       final BaseFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood1 = new FinishedGood(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       final BaseFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood2 = new FinishedGood();
       finishedGood2.setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       final List<FinishedGood> added = this.service.add(List.of(finishedGood1, finishedGood2));

       final List<FinishedGood> expected = List.of(
           new FinishedGood(1L), new FinishedGood()
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       assertEquals(expected, added);
       verify(this.mRepoService, times(1)).saveAll(added);
   }

   @Test
   public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
       assertNull(this.service.add(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPut_UpdatesFinishedGoodAndMixturePortionsAndMaterialPortionsAndSavesToRepo_WhenUpdatesAreNotNull() {
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMixturePortionService).getPutEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMaterialPortionService).getPutEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

       final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood1 = new FinishedGood(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood2 = new FinishedGood(2L);
       finishedGood2.setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       doReturn(List.of(new FinishedGood(1L), new FinishedGood(2L))).when(this.mRepoService).getByIds(List.of(finishedGood1, finishedGood2));

       final List<FinishedGood> updated = this.service.put(List.of(finishedGood1, finishedGood2, new FinishedGood()));

       final List<FinishedGood> expected = List.of(
           new FinishedGood(1L), new FinishedGood(2L), new FinishedGood()
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
       assertNull(this.service.put(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_PatchesFinishedGoodAndMaterialPortionsAndMixturePortionsAndSavesToRepo_WhenPatchesAreNotNull() {
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMixturePortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMaterialPortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood1 = new FinishedGood(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       final UpdateFinishedGood<FinishedGoodMixturePortion, FinishedGoodMaterialPortion> finishedGood2 = new FinishedGood(2L);
       finishedGood2.setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       doReturn(List.of(new FinishedGood(1L), new FinishedGood(2L))).when(this.mRepoService).getByIds(List.of(finishedGood1, finishedGood2));

       final List<FinishedGood> updated = this.service.patch(List.of(finishedGood1, finishedGood2));

       final List<FinishedGood> expected = List.of(
           new FinishedGood(1L), new FinishedGood(2L)
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(30L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodMaterialPortion(40L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
       assertNull(this.service.patch(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_ThrowsNotFoundException_WhenAllFinishedGoodsDontExist() {
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMixturePortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgMaterialPortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final List<UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> updates = List.of(
           new FinishedGood(1L), new FinishedGood(2L), new FinishedGood(3L), new FinishedGood(4L)
       );
       doReturn(List.of(new FinishedGood(1L), new FinishedGood(2L))).when(this.mRepoService).getByIds(updates);

       assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find finished goods with Ids: [3, 4]");
   }
}
