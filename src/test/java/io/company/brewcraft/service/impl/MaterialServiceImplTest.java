package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.SupportedUnits;

public class MaterialServiceImplTest {
    private MaterialService materialService;

    private MaterialRepository materialRepositoryMock;

    private MaterialCategoryService materialCategoryServiceMock;

    private QuantityUnitService quantityUnitServiceMock;

    @BeforeEach
    public void init() {
        materialRepositoryMock = mock(MaterialRepository.class);
        materialCategoryServiceMock = mock(MaterialCategoryService.class);
        quantityUnitServiceMock = mock(QuantityUnitService.class);

        materialService = new MaterialServiceImpl(materialRepositoryMock, materialCategoryServiceMock, quantityUnitServiceMock);
    }

    @Test
    public void testGetMaterials_returnsMaterials() throws Exception {
        Material materialEntity = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        List<Material> materialEntitys = Arrays.asList(materialEntity);

        Page<Material> expectedMaterialEntities = new PageImpl<>(materialEntitys);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(materialRepositoryMock.findAll(ArgumentMatchers.<Specification<Material>>any(), pageableArgument.capture())).thenReturn(expectedMaterialEntities);

        Page<Material> actualMaterials = materialService.getMaterials(null, null, null, 0, 100, new TreeSet<>(List.of("id")), true);

        assertSame(0, pageableArgument.getValue().getPageNumber());
        assertSame(100, pageableArgument.getValue().getPageSize());
        assertSame(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertSame("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertSame(1, actualMaterials.getNumberOfElements());

        Material actualMaterial = actualMaterials.get().findFirst().get();

        assertSame(materialEntity.getId(), actualMaterial.getId());
        assertSame(materialEntity.getName(), actualMaterial.getName());
        assertSame(materialEntity.getDescription(), actualMaterial.getDescription());
        assertSame(materialEntity.getCategory().getId(), actualMaterial.getCategory().getId());
        assertSame(materialEntity.getUpc(), actualMaterial.getUpc());
        assertSame(materialEntity.getBaseQuantityUnit().getSymbol(), actualMaterial.getBaseQuantityUnit().getSymbol());
        assertSame(materialEntity.getImageSrc(), actualMaterial.getImageSrc());
        assertSame(materialEntity.getLastUpdated(), actualMaterial.getLastUpdated());
        assertSame(materialEntity.getCreatedAt(), actualMaterial.getCreatedAt());
        assertSame(materialEntity.getVersion(), actualMaterial.getVersion());
    }

    @Test
    public void testGetMaterial_returnsMaterial() throws Exception {
        Long id = 1L;
        Material materialEntity = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<Material> expectedMaterialEntity = Optional.ofNullable(materialEntity);

        when(materialRepositoryMock.findById(id)).thenReturn(expectedMaterialEntity);

        Material returnedMaterial = materialService.getMaterial(id);

        assertEquals(expectedMaterialEntity.get().getId(), returnedMaterial.getId());
        assertEquals(expectedMaterialEntity.get().getName(), returnedMaterial.getName());
        assertEquals(expectedMaterialEntity.get().getDescription(), returnedMaterial.getDescription());
        assertEquals(expectedMaterialEntity.get().getCategory().getId(), returnedMaterial.getCategory().getId());
        assertEquals(expectedMaterialEntity.get().getUpc(), returnedMaterial.getUpc());
        assertEquals(expectedMaterialEntity.get().getBaseQuantityUnit().getSymbol(), returnedMaterial.getBaseQuantityUnit().getSymbol());
        assertEquals(expectedMaterialEntity.get().getImageSrc(), returnedMaterial.getImageSrc());
        assertEquals(expectedMaterialEntity.get().getLastUpdated(), returnedMaterial.getLastUpdated());
        assertEquals(expectedMaterialEntity.get().getCreatedAt(), returnedMaterial.getCreatedAt());
        assertEquals(expectedMaterialEntity.get().getVersion(), returnedMaterial.getVersion());
    }

    @Test
    public void testAddMaterial_SavesMaterial() throws Exception {
        Material material = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), null, SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        Material newMaterial = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Material> persistedMaterialCaptor = ArgumentCaptor.forClass(Material.class);

        when(materialRepositoryMock.saveAndFlush(persistedMaterialCaptor.capture())).thenReturn(newMaterial);

        Material returnedMaterial = materialService.addMaterial(material);

        //Assert persisted entity
        assertEquals(material.getId(), persistedMaterialCaptor.getValue().getId());
        assertEquals(material.getName(), persistedMaterialCaptor.getValue().getName());
        assertEquals(material.getDescription(), persistedMaterialCaptor.getValue().getDescription());
        assertEquals(material.getCategory().getId(), persistedMaterialCaptor.getValue().getCategory().getId());
        assertEquals(material.getUpc(), persistedMaterialCaptor.getValue().getUpc());
        assertEquals(material.getBaseQuantityUnit().getSymbol(), persistedMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());
        assertEquals(material.getImageSrc(), persistedMaterialCaptor.getValue().getImageSrc());
        assertEquals(material.getLastUpdated(), persistedMaterialCaptor.getValue().getLastUpdated());
        assertEquals(material.getCreatedAt(), persistedMaterialCaptor.getValue().getCreatedAt());
        assertEquals(material.getVersion(), persistedMaterialCaptor.getValue().getVersion());

        //Assert returned POJO
        assertEquals(newMaterial.getId(), returnedMaterial.getId());
        assertEquals(newMaterial.getName(), returnedMaterial.getName());
        assertEquals(newMaterial.getDescription(), returnedMaterial.getDescription());
        assertEquals(newMaterial.getCategory().getId(), returnedMaterial.getCategory().getId());
        assertEquals(newMaterial.getUpc(), returnedMaterial.getUpc());
        assertEquals(newMaterial.getBaseQuantityUnit().getSymbol(), returnedMaterial.getBaseQuantityUnit().getSymbol());
        assertEquals(newMaterial.getImageSrc(), returnedMaterial.getImageSrc());
        assertEquals(newMaterial.getLastUpdated(), returnedMaterial.getLastUpdated());
        assertEquals(newMaterial.getCreatedAt(), returnedMaterial.getCreatedAt());
        assertEquals(newMaterial.getVersion(), returnedMaterial.getVersion());
    }

    @Test
    public void testPutMaterial_success() throws Exception {
        Long id = 1L;
        Material putMaterial = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), null, SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Material materialEntity = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Material> persistedMaterialCaptor = ArgumentCaptor.forClass(Material.class);

        when(materialRepositoryMock.saveAndFlush(persistedMaterialCaptor.capture())).thenReturn(materialEntity);

        Material returnedMaterial = materialService.putMaterial(id, putMaterial);

        //Assert persisted entity
        assertEquals(putMaterial.getId(), persistedMaterialCaptor.getValue().getId());
        assertEquals(putMaterial.getName(), persistedMaterialCaptor.getValue().getName());
        assertEquals(putMaterial.getDescription(), persistedMaterialCaptor.getValue().getDescription());
        assertEquals(putMaterial.getCategory().getId(), persistedMaterialCaptor.getValue().getCategory().getId());
        assertEquals(putMaterial.getUpc(), persistedMaterialCaptor.getValue().getUpc());
        assertEquals(putMaterial.getBaseQuantityUnit().getSymbol(), persistedMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());
        assertEquals(putMaterial.getImageSrc(), persistedMaterialCaptor.getValue().getImageSrc());
        //assertEquals(null, persistedMaterialCaptor.getValue().getLastUpdated());
        //assertEquals(putMaterial.getCreatedAt(), persistedMaterialCaptor.getValue().getCreatedAt());
        assertEquals(putMaterial.getVersion(), persistedMaterialCaptor.getValue().getVersion());

        //Assert returned POJO
        assertEquals(materialEntity.getId(), returnedMaterial.getId());
        assertEquals(materialEntity.getName(), returnedMaterial.getName());
        assertEquals(materialEntity.getDescription(), returnedMaterial.getDescription());
        assertEquals(materialEntity.getCategory().getId(), returnedMaterial.getCategory().getId());
        assertEquals(materialEntity.getUpc(), returnedMaterial.getUpc());
        assertEquals(materialEntity.getBaseQuantityUnit().getSymbol(), returnedMaterial.getBaseQuantityUnit().getSymbol());
        assertEquals(materialEntity.getImageSrc(), returnedMaterial.getImageSrc());
        assertEquals(materialEntity.getLastUpdated(), returnedMaterial.getLastUpdated());
        assertEquals(materialEntity.getCreatedAt(), returnedMaterial.getCreatedAt());
        assertEquals(materialEntity.getVersion(), returnedMaterial.getVersion());
    }

    @Test
    public void testPatchMaterial_success() throws Exception {
        Long id = 1L;
        Material patchedMaterial = new Material(1L, "updatedName", null, null, null, null, null, null, null, 1);
        Material existingMaterialEntity = new Material(1L, "materialName", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Material persistedMaterialEntity = new Material(1L, "updatedName", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, "http://www.test.com", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Material> persistedMaterialCaptor = ArgumentCaptor.forClass(Material.class);

        when(materialRepositoryMock.findById(id)).thenReturn(Optional.of(existingMaterialEntity));

        when(materialRepositoryMock.saveAndFlush(persistedMaterialCaptor.capture())).thenReturn(persistedMaterialEntity);

        Material returnedMaterial = materialService.patchMaterial(id, patchedMaterial);

        //Assert persisted entity
        assertEquals(existingMaterialEntity.getId(), persistedMaterialCaptor.getValue().getId());
        assertEquals(patchedMaterial.getName(), persistedMaterialCaptor.getValue().getName());
        assertEquals(existingMaterialEntity.getDescription(), persistedMaterialCaptor.getValue().getDescription());
        assertEquals(existingMaterialEntity.getCategory().getId(), persistedMaterialCaptor.getValue().getCategory().getId());
        assertEquals(existingMaterialEntity.getUpc(), persistedMaterialCaptor.getValue().getUpc());
        assertEquals(existingMaterialEntity.getBaseQuantityUnit().getSymbol(), persistedMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());
        assertEquals(existingMaterialEntity.getImageSrc(), persistedMaterialCaptor.getValue().getImageSrc());
        assertEquals(existingMaterialEntity.getLastUpdated(), persistedMaterialCaptor.getValue().getLastUpdated());
        assertEquals(existingMaterialEntity.getCreatedAt(), persistedMaterialCaptor.getValue().getCreatedAt());
        assertEquals(existingMaterialEntity.getVersion(), persistedMaterialCaptor.getValue().getVersion());

        //Assert returned POJO
        assertEquals(persistedMaterialEntity.getId(), returnedMaterial.getId());
        assertEquals(persistedMaterialEntity.getName(), returnedMaterial.getName());
        assertEquals(persistedMaterialEntity.getDescription(), returnedMaterial.getDescription());
        assertEquals(persistedMaterialEntity.getCategory().getId(), returnedMaterial.getCategory().getId());
        assertEquals(persistedMaterialEntity.getUpc(), returnedMaterial.getUpc());
        assertEquals(persistedMaterialEntity.getBaseQuantityUnit().getSymbol(), returnedMaterial.getBaseQuantityUnit().getSymbol());
        assertEquals(persistedMaterialEntity.getImageSrc(), returnedMaterial.getImageSrc());
        assertEquals(persistedMaterialEntity.getLastUpdated(), returnedMaterial.getLastUpdated());
        assertEquals(persistedMaterialEntity.getCreatedAt(), returnedMaterial.getCreatedAt());
        assertEquals(persistedMaterialEntity.getVersion(), returnedMaterial.getVersion());
    }

    @Test
    public void testPatchMaterial_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Material material = new Material();

        when(materialRepositoryMock.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            materialService.patchMaterial(id, material);
            verify(materialRepositoryMock, times(0)).saveAndFlush(Mockito.any(Material.class));
        });
    }

    @Test
    public void testDeleteMaterial_success() throws Exception {
        Long id = 1L;
        materialService.deleteMaterial(id);

        verify(materialRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    public void testMaterialExists_success() throws Exception {
        Long id = 1L;
        materialService.materialExists(id);

        verify(materialRepositoryMock, times(1)).existsById(id);
    }

    @Test
    public void testMaterialService_classIsTransactional() throws Exception {
        Transactional transactional = materialService.getClass().getAnnotation(Transactional.class);

        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }

    @Test
    public void testMaterialService_methodsAreNotTransactional() throws Exception {
        Method[] methods = materialService.getClass().getMethods();
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
