package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseSku;
import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.UpdateSku;
import io.company.brewcraft.model.UpdateSkuMaterial;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.SkuMaterialService;
import io.company.brewcraft.service.SkuService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class SkuServiceTest {
   private SkuService skuService;

   private SkuMaterialService skuMaterialService;
   private UpdateService<Long, Sku, BaseSku<? extends BaseSkuMaterial<?>>, UpdateSku<? extends UpdateSkuMaterial<?>>> mUpdateService;
   private RepoService<Long, Sku, SkuAccessor> mRepoService;

   @BeforeEach
   public void init() {
       this.skuMaterialService = mock(SkuMaterialService.class);
       this.mUpdateService = mock(UpdateService.class);
       this.mRepoService = mock(RepoService.class);

       doAnswer(sku -> sku.getArgument(0)).when(this.mRepoService).saveAll(anyList());

       this.skuService = new SkuServiceImpl(this.mRepoService, this.mUpdateService, this.skuMaterialService);
   }

   @Test
   public void testGetSkus_ReturnsEntitiesFromRepoService_WithCustomSpec() {
       final ArgumentCaptor<Specification<Sku>> captor = ArgumentCaptor.forClass(Specification.class);
       final Page<Sku> mPage = new PageImpl<>(List.of(new Sku(1L)));
       doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

       final Page<Sku> page = this.skuService.getSkus(
           Set.of(1L),
           Set.of(2L),
           10,
           20,
           new TreeSet<>(List.of("id")),
           true
       );

       final Page<Sku> expected = new PageImpl<>(List.of(new Sku(1L)));
       assertEquals(expected, page);

       captor.getValue();
   }

   @Test
   public void testGetSku_ReturnsSkuPojo_WhenRepoServiceReturnsOptionalWithEntity() {
       doReturn(new Sku(1L)).when(this.mRepoService).get(1L);

       final Sku sku = this.skuService.getSku(1L);

       assertEquals(new Sku(1L), sku);
   }

   @Test
   public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
       doReturn(true).when(this.mRepoService).exists(1L);

       assertTrue(this.skuService.skuExists(1L));
   }

   @Test
   public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
       doReturn(true).when(this.mRepoService).exists(1L);

       assertTrue(this.skuService.skuExists(1L));
   }

   @Test
   public void testDelete_CallsRepoServiceDelete_WhenSkuExists() {
       this.skuService.deleteSku(1L);
       verify(this.mRepoService).delete(1L);
   }

   @Test
   public void testAdd_AddsSkuAndSkuMaterialsAndSavesToRepo_WhenAdditionsAreNotNull() {
       doAnswer(sku -> sku.getArgument(0)).when(this.skuMaterialService).getAddEntities(any());
       doAnswer(sku -> sku.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

       final BaseSku<SkuMaterial> sku1 = new Sku(1L);
       sku1.setMaterials(List.of(new SkuMaterial(10L)));
       final BaseSku<SkuMaterial> sku2 = new Sku();
       sku2.setMaterials(List.of(new SkuMaterial(20L)));

       final List<Sku> added = this.skuService.addSkus(List.of(sku1, sku2));

       final List<Sku> expected = List.of(
           new Sku(1L), new Sku()
       );
       expected.get(0).setMaterials(List.of(new SkuMaterial(10L)));
       expected.get(1).setMaterials(List.of(new SkuMaterial(20L)));

       assertEquals(expected, added);
       verify(this.mRepoService, times(1)).saveAll(added);
   }

   @Test
   public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
       assertNull(this.skuService.addSkus(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPut_UpdatesSkuAndSkuMaterialsAndSavesToRepo_WhenUpdatesAreNotNull() {
       doAnswer(sku -> sku.getArgument(1)).when(this.skuMaterialService).getPutEntities(any(), any());
       doAnswer(sku -> sku.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

       final UpdateSku<SkuMaterial> sku1 = new Sku(1L);
       sku1.setMaterials(List.of(new SkuMaterial(10L)));
       final UpdateSku<SkuMaterial> sku2 = new Sku(2L);
       sku2.setMaterials(List.of(new SkuMaterial(20L)));

       doReturn(List.of(new Sku(1L), new Sku(2L))).when(this.mRepoService).getByIds(List.of(sku1, sku2));

       final List<Sku> updated = this.skuService.putSkus(List.of(sku1, sku2, new Sku()));

       final List<Sku> expected = List.of(
           new Sku(1L), new Sku(2L), new Sku()
       );
       expected.get(0).setMaterials(List.of(new SkuMaterial(10L)));
       expected.get(1).setMaterials(List.of(new SkuMaterial(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
       assertNull(this.skuService.putSkus(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_PatchesSkuAndSkuMaterialsAndSavesToRepo_WhenPatchesAreNotNull() {
       doAnswer(inv -> inv.getArgument(1)).when(this.skuMaterialService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final UpdateSku<SkuMaterial> sku1 = new Sku(1L);
       sku1.setMaterials(List.of(new SkuMaterial(10L)));
       final UpdateSku<SkuMaterial> sku2 = new Sku(2L);
       sku2.setMaterials(List.of(new SkuMaterial(20L)));

       doReturn(List.of(new Sku(1L), new Sku(2L))).when(this.mRepoService).getByIds(List.of(sku1, sku2));

       final List<Sku> updated = this.skuService.patchSkus(List.of(sku1, sku2));

       final List<Sku> expected = List.of(
           new Sku(1L), new Sku(2L)
       );
       expected.get(0).setMaterials(List.of(new SkuMaterial(10L)));
       expected.get(1).setMaterials(List.of(new SkuMaterial(20L)));

       assertEquals(expected, updated);
       verify(this.mRepoService, times(1)).saveAll(updated);
   }

   @Test
   public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
       assertNull(this.skuService.patchSkus(null));
       verify(this.mRepoService, times(0)).saveAll(any());
   }

   @Test
   public void testPatch_ThrowsNotFoundException_WhenAllSkusDontExist() {
       doAnswer(inv -> inv.getArgument(1)).when(this.skuMaterialService).getPatchEntities(any(), any());
       doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

       final List<UpdateSku<? extends UpdateSkuMaterial<?>>> updates = List.of(
           new Sku(1L), new Sku(2L), new Sku(3L), new Sku(4L)
       );
       doReturn(List.of(new Sku(1L), new Sku(2L))).when(this.mRepoService).getByIds(updates);

       assertThrows(EntityNotFoundException.class, () -> this.skuService.patchSkus(updates), "Cannot find skus with Ids: [3, 4]");
   }

   @Test
   public void testSkuService_classIsTransactional() throws Exception {
       Transactional transactional = skuService.getClass().getAnnotation(Transactional.class);

       assertNotNull(transactional);
       assertEquals(transactional.isolation(), Isolation.DEFAULT);
       assertEquals(transactional.propagation(), Propagation.REQUIRED);
   }

   @Test
   public void testSkuService_methodsAreNotTransactional() throws Exception {
       Method[] methods = skuService.getClass().getMethods();
       for(Method method : methods) {
           assertFalse(method.isAnnotationPresent(Transactional.class));
       }
   }
}
