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

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

import io.company.brewcraft.model.IaasPublicAccessBlock;
import io.company.brewcraft.model.IaasPublicAccessBlockAccessor;

public class IaasPublicAccessBlockServiceTest {
    private IaasPublicAccessBlockService service;

    private UpdateService<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> mUpdateService;

    private IaasRepository<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> mIaasRepo;

    @BeforeEach
    public void init() {
        mUpdateService = TestBeans.updateService(IaasPublicAccessBlock.class, IaasPublicAccessBlock.class, IaasPublicAccessBlock.class, Set.of("createdAt"));
        mIaasRepo = mock(IaasRepository.class);

        service = new IaasPublicAccessBlockService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllObjectStoreIdsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of("BUCKET_1")));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllObjectStoreIdsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of("BUCKET_1")));
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
    public void testGet_ReturnsPublicAccessBlockFromRepo() {
        IaasPublicAccessBlock expected = new IaasPublicAccessBlock();
        doAnswer(inv -> {
            return List.of(expected);
        }).when(mIaasRepo).get(anySet());

        IaasPublicAccessBlock actual = service.get("BUCKET_1");

        assertEquals(expected, actual);
    }

    @Test
    public void testGet_ReturnsNull_WhenNoPublicAccessBlockIsFound() {
        doReturn(new ArrayList<>()).when(mIaasRepo).get(anySet());

        IaasPublicAccessBlock actual = service.get("BUCKET_1");

        assertNull(actual);
    }

    @Test
    public void testGetAll_ReturnsPublicAccessBlockFromRepo() {
        List<IaasPublicAccessBlock> expected = List.of(new IaasPublicAccessBlock());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasPublicAccessBlock> actual = service.getAll(Set.of("BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByIds_ReturnPublicAccessBlocksFromRepo() {
        List<IaasPublicAccessBlock> expected = List.of(new IaasPublicAccessBlock());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasPublicAccessBlock> actual = service.getByIds(Set.of(() -> "BUCKET_1"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByAccessorIds_ReturnsPublicAccessBlockFromRepo() {
        List<IaasPublicAccessBlock> expected = List.of(new IaasPublicAccessBlock());
        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        IaasPublicAccessBlockAccessor accessor = new IaasPublicAccessBlockAccessor() {
            @Override
            public void setIaasPublicAccessBlock(IaasPublicAccessBlock attachment) {
            }
            @Override
            public IaasPublicAccessBlock getIaasPublicAccessBlock() {
                return new IaasPublicAccessBlock();
            }
        };

        List<IaasPublicAccessBlock> actual = service.getByAccessorIds(Set.of(accessor));

        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        List<IaasPublicAccessBlock> expected = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).add(anyList());

        List<IaasPublicAccessBlock> additions = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        List<IaasPublicAccessBlock> configs = service.add(additions);

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
        List<IaasPublicAccessBlock> expected = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        List<IaasPublicAccessBlock> updates = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        List<IaasPublicAccessBlock> configs = service.put(updates);

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
        List<IaasPublicAccessBlock> expected = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        doAnswer(inv -> expected).when(mIaasRepo).put(anyList());

        doAnswer(inv -> {
            return expected;
        }).when(mIaasRepo).get(anySet());

        List<IaasPublicAccessBlock> updates = List.of(
            new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()),
            new IaasPublicAccessBlock("BUCKET_2", new PublicAccessBlockConfiguration())
        );

        List<IaasPublicAccessBlock> attachments = service.patch(updates);

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(updates);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }

    @Test
    public void testPatch_DoesNothingReturnsNull_WhenArgIsNull() {
        assertNull(service.patch(null));
    }
}
