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

import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionService;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionService;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionService;
import io.company.brewcraft.service.FinishedGoodLotService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class FinishedGoodLotServiceTest {

   private FinishedGoodLotService service;

   private FinishedGoodLotMaterialPortionService mFgMaterialPortionService;

   private FinishedGoodLotMixturePortionService mFgMixturePortionService;

   private FinishedGoodLotFinishedGoodLotPortionService mFgLotPortionService;

   private UpdateService<Long, FinishedGoodLot, BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>, UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> mUpdateService;

   private RepoService<Long, FinishedGoodLot, FinishedGoodLotAccessor> mRepoService;

   @BeforeEach
   public void init() {
       this.mFgMaterialPortionService = mock(FinishedGoodLotMaterialPortionService.class);
       this.mFgMixturePortionService = mock(FinishedGoodLotMixturePortionService.class);
       this.mFgLotPortionService = mock(FinishedGoodLotFinishedGoodLotPortionService.class);
       this.mUpdateService = mock(UpdateService.class);
       this.mRepoService = mock(RepoService.class);

       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mRepoService).saveAll(anyList());

       this.service = new FinishedGoodLotService(this.mUpdateService, this.mFgMixturePortionService, this.mFgMaterialPortionService, this.mFgLotPortionService, this.mRepoService);
   }

   @Test
   public void testGetFinishedGoods_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       @SuppressWarnings("unchecked")
       final ArgumentCaptor<Specification<FinishedGoodLot>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<FinishedGoodLot> mPage = new PageImpl<>(List.of(new FinishedGoodLot(1L)));
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

       final Page<FinishedGoodLot> page = this.service.getFinishedGoodLots(
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

       final Page<FinishedGoodLot> expected = new PageImpl<>(List.of(new FinishedGoodLot(1L)));
       assertEquals(expected, page);

       // TODO: Pending testing for the specification
       captor.getValue();
   }

   @Test
   public void testGetFinishedGood_ReturnsFinishedGoodPojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new FinishedGoodLot(1L)).when(this.mRepoService).get(1L);

       final FinishedGoodLot finishedGood = this.service.get(1L);

       assertEquals(new FinishedGoodLot(1L), finishedGood);
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

       final long count = this.service.delete(Set.of(1L, 2L, 3L));
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
       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mFgLotPortionService).getAddEntities(any());
       doAnswer(finishedGood -> finishedGood.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

       final BaseFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood1 = new FinishedGoodLot(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       finishedGood1.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       final BaseFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood2 = new FinishedGoodLot();
       finishedGood2.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       finishedGood2.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

       final List<FinishedGoodLot> added = this.service.add(List.of(finishedGood1, finishedGood2));

       final List<FinishedGoodLot> expected = List.of(
           new FinishedGoodLot(1L), new FinishedGoodLot()
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       expected.get(0).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       expected.get(1).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

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
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgLotPortionService).getPutEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

       final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood1 = new FinishedGoodLot(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       finishedGood1.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood2 = new FinishedGoodLot(2L);
       finishedGood2.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       finishedGood2.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

       doReturn(List.of(new FinishedGoodLot(1L), new FinishedGoodLot(2L))).when(this.mRepoService).getByIds(List.of(finishedGood1, finishedGood2));

       final List<FinishedGoodLot> updated = this.service.put(List.of(finishedGood1, finishedGood2, new FinishedGoodLot()));

       final List<FinishedGoodLot> expected = List.of(
           new FinishedGoodLot(1L), new FinishedGoodLot(2L), new FinishedGoodLot()
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       expected.get(0).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       expected.get(1).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

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
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgLotPortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood1 = new FinishedGoodLot(1L);
       finishedGood1.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       finishedGood1.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       finishedGood1.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       final UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion> finishedGood2 = new FinishedGoodLot(2L);
       finishedGood2.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       finishedGood2.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       finishedGood2.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

       doReturn(List.of(new FinishedGoodLot(1L), new FinishedGoodLot(2L))).when(this.mRepoService).getByIds(List.of(finishedGood1, finishedGood2));

       final List<FinishedGoodLot> updated = this.service.patch(List.of(finishedGood1, finishedGood2));

       final List<FinishedGoodLot> expected = List.of(
           new FinishedGoodLot(1L), new FinishedGoodLot(2L)
       );

       expected.get(0).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(10L)));
       expected.get(0).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(30L)));
       expected.get(0).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(50L)));

       expected.get(1).setMixturePortions(List.of(new FinishedGoodLotMixturePortion(20L)));
       expected.get(1).setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(40L)));
       expected.get(1).setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(60L)));

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
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mFgLotPortionService).getPatchEntities(any(), any());
       doAnswer(finishedGood -> finishedGood.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final List<UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> updates = List.of(
           new FinishedGoodLot(1L), new FinishedGoodLot(2L), new FinishedGoodLot(3L), new FinishedGoodLot(4L)
       );
       doReturn(List.of(new FinishedGoodLot(1L), new FinishedGoodLot(2L))).when(this.mRepoService).getByIds(updates);

       assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find finished goods with Ids: [3, 4]");
   }
}
