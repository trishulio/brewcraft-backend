package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseDummyCrudEntity;
import io.company.brewcraft.model.DummyCrudEntity;
import io.company.brewcraft.model.UpdateDummyCrudEntity;
import io.company.brewcraft.service.SimpleUpdateService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;

public class SimpleUpdateServiceTest {

    private UpdateService<Long, DummyCrudEntity, BaseDummyCrudEntity, UpdateDummyCrudEntity> service;

    private UtilityProvider mUtilProvider;

    @BeforeEach
    public void init() {
        this.mUtilProvider = mock(UtilityProvider.class);
        doReturn(new Validator()).when(this.mUtilProvider).getValidator();

        this.service = new SimpleUpdateService<>(this.mUtilProvider, BaseDummyCrudEntity.class, UpdateDummyCrudEntity.class, DummyCrudEntity.class, Set.of(BaseDummyCrudEntity.ATTR_EXCLUDED_VALUE));
    }

    @Test
    public void testGetAddEntities_ReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.getAddEntities(null));
    }

    @Test
    public void testGetAddEntities_ReturnsListOfEntitiesWithBasePropertiesOnly_WhenAdditionsAreNotNull() {

        final List<BaseDummyCrudEntity> additions = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getAddEntities(additions);

        final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(null, "VALUE", null, null));
        assertEquals(expected, entities);
    }

    @Test
    public void testGetPutEntities_ReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.getPutEntities(null, null));
        assertNull(this.service.getPutEntities(List.of(), null));
        assertNull(this.service.getPutEntities(List.of(new DummyCrudEntity(1L)), null));
    }

    @Test
    public void testGetPutEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenUpdatesAreNotNull() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L, null, null, 1));
        final List<UpdateDummyCrudEntity> updates = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1), new DummyCrudEntity(null, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getPutEntities(existing, updates);

        final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L, "VALUE", null, 1), new DummyCrudEntity(null, "VALUE", null, null));
        assertEquals(expected, entities);
    }

    @Test
    public void testGetPutEntities_ThrowsEntityNotFoundException_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> updates = List.of(new DummyCrudEntity(2L), new DummyCrudEntity(3L));

        assertThrows(ValidationException.class, () -> this.service.getPutEntities(existing, updates), "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
        assertThrows(ValidationException.class, () -> this.service.getPutEntities(null, updates), "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
    }

    @Test
    public void testGetPutEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> updates = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1), new DummyCrudEntity());

        assertThrows(OptimisticLockException.class, () -> this.service.getPutEntities(existing, updates), "Cannot update entity of version with: null with update payload of version: 1");
    }

    @Test
    public void testGetPatchEntities_ReturnsExistingEntities_WhenUpdatesAreNull() {
        assertNull(this.service.getPatchEntities(null, null));
        assertEquals(List.of(), this.service.getPatchEntities(List.of(), null));
        assertEquals(List.of(new DummyCrudEntity(1L)), this.service.getPatchEntities(List.of(new DummyCrudEntity(1L)), null));
    }

    @Test
    public void testGetPatchEntities_ReturnsListOfEntitiesWithUpdateProperties_WhenPatchesAreNotNull() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L, "OLD_VALUE", "OLD_EXCLUDED_VALUE", 1));
        final List<UpdateDummyCrudEntity> patches = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1));

        final List<DummyCrudEntity> entities = this.service.getPatchEntities(existing, patches);

        final List<DummyCrudEntity> expected = List.of(new DummyCrudEntity(1L, "VALUE", null, 1));
        assertEquals(expected, entities);
    }

    @Test
    public void testGetPatchEntities_ThrowsEntityNotFoundException_WhenUpdateEntityIdDoesNotExistInExistingEntities() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> patches = List.of(new DummyCrudEntity(2L), new DummyCrudEntity(3L));

        assertThrows(ValidationException.class, () -> this.service.getPatchEntities(existing, patches), "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
        assertThrows(ValidationException.class, () -> this.service.getPatchEntities(null, patches), "1. No existing DummyCrudEntity found with Id: 2.\n2. No existing DummyCrudEntity found with Id: 3.");
    }

    @Test
    public void testGetPatchEntities_ThrowsOptimisticLockException_WhenExistingEntityVersionIsDifferentFromUpdateVersion() {
        final List<DummyCrudEntity> existing = List.of(new DummyCrudEntity(1L));
        final List<UpdateDummyCrudEntity> patches = List.of(new DummyCrudEntity(1L, "VALUE", "EXCLUDED_VALUE", 1), new DummyCrudEntity());

        assertThrows(OptimisticLockException.class, () -> this.service.getPatchEntities(existing, patches), "Cannot update entity of version with: null with update payload of version: 1");
    }
}
