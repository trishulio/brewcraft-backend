package io.company.brewcraft.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;

import io.company.brewcraft.model.IaasBucketCrossOriginConfiguration;
import io.company.brewcraft.model.IaasBucketCrossOriginConfigurationAccessor;

public class IaasBucketCrossOriginConfigServiceTest {
    private IaasBucketCrossOriginConfigService service;

    private UpdateService<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> mUpdateService;

    private IaasRepository<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> mIaasRepo;

    @BeforeEach
    public void init() {
        mUpdateService = TestBeans.updateService(IaasBucketCrossOriginConfiguration.class, IaasBucketCrossOriginConfiguration.class, IaasBucketCrossOriginConfiguration.class, Set.of("createdAt"));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasBucketCrossOriginConfigService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of("BUCKET_1")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of("BUCKET_1")));
    }

    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exist("BUCKET_1"));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exist("BUCKET_1"));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(99L).when(mIaasRepo).delete(Set.of("BUCKET_1", "BUCKET_2"));
        long deleteCount = service.delete(Set.of("BUCKET_1", "BUCKET_2"));

        assertEquals(99L, deleteCount);
    }

    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1L).when(mIaasRepo).delete(Set.of("BUCKET_1", "BUCKET_2"));
        long deleteCount = service.delete(Set.of("BUCKET_1", "BUCKET_2"));

        assertEquals(1L, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        IaasBucketCrossOriginConfiguration expected = new IaasBucketCrossOriginConfiguration();
        doAnswer(inv -> {
            return List.of(expected);
        }).when(mIaasRepo).get(anySet());

        IaasBucketCrossOriginConfiguration actual = service.get("BUCKET_1");

        assertEquals(expected, actual);
    }

    @Test
    public void testGet_ReturnsNull_WhenNoAttachmentIsFound() {
        doReturn(new ArrayList<>()).when(mIaasRepo).get(anySet());

        IaasBucketCrossOriginConfiguration actual = service.get("BUCKET_1");

        assertNull(actual);
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(new IaasBucketCrossOriginConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasBucketCrossOriginConfiguration> actual = service.getAll(Set.of("BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(new IaasBucketCrossOriginConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasBucketCrossOriginConfiguration> actual = service.getByIds(Set.of(() -> "BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(new IaasBucketCrossOriginConfiguration());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        IaasBucketCrossOriginConfigurationAccessor accessor = new IaasBucketCrossOriginConfigurationAccessor() {
            @Override
            public void setIaasBucketCrossOriginConfiguration(IaasBucketCrossOriginConfiguration attachment) {
            }
            @Override
            public IaasBucketCrossOriginConfiguration getIaasBucketCrossOriginConfiguration() {
                return new IaasBucketCrossOriginConfiguration();
            }
        };

        List<IaasBucketCrossOriginConfiguration> actual = service.getByAccessorIds(Set.of(accessor));

        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).add(anyList());

        List<IaasBucketCrossOriginConfiguration> additions = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasBucketCrossOriginConfiguration> configs = service.add(additions);

        assertEquals(expected, configs);
        verify(mIaasRepo, times(1)).add(additions);
        verify(mUpdateService).getAddEntities(additions);
    }

    @Test
    public void testAdd_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.add(null));
    }

    @Test
    public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromUpdateService() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        List<IaasBucketCrossOriginConfiguration> updates = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasBucketCrossOriginConfiguration> configs = service.put(updates);

        assertEquals(expected, configs);
        verify(mIaasRepo, times(1)).put(updates);
        verify(mUpdateService).getPutEntities(null, updates);
    }

    @Test
    public void testPut_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.put(null));
    }

    @Test
    public void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromUpdateService() {
        List<IaasBucketCrossOriginConfiguration> expected = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasBucketCrossOriginConfiguration> updates = List.of(
            new IaasBucketCrossOriginConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()),
            new IaasBucketCrossOriginConfiguration("BUCKET_2", new BucketCrossOriginConfiguration())
        );

        List<IaasBucketCrossOriginConfiguration> attachments = service.patch(updates);

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(updates);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
