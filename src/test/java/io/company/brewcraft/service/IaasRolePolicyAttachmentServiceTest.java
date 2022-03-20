package io.company.brewcraft.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentAccessor;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;

public class IaasRolePolicyAttachmentServiceTest {
    private IaasRolePolicyAttachmentService service;

    private UpdateService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> mUpdateService;

    private IaasRolePolicyAttachmentIaasRepository mIaasRepo;

    @BeforeEach
    public void init() {
        mUpdateService = TestUtils.updateService(BaseIaasRolePolicyAttachment.class, UpdateIaasRolePolicyAttachment.class, IaasRolePolicyAttachment.class, Set.of("createdAt"));
        mIaasRepo = mock(IaasRolePolicyAttachmentIaasRepository.class);

        service = new IaasRolePolicyAttachmentService(mUpdateService, mIaasRepo);
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllAttachmentsExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), true)).when(mIaasRepo).exists(anySet());

        assertTrue(service.exists(Set.of(new IaasRolePolicyAttachmentId("POLICY", "ROLE"))));
    }

    @Test
    public void testExists_ReturnsFalse_WhenAllAttachmentsDoesNotExists() {
        doAnswer(inv -> Map.of(inv.getArgument(0, Set.class).iterator().next(), false)).when(mIaasRepo).exists(anySet());

        assertFalse(service.exists(Set.of(new IaasRolePolicyAttachmentId("POLICY", "ROLE"))));
    }


    @Test
    public void testExist_ReturnsTrue_WhenAllAttachmentsExists() {
        doReturn(true).when(mIaasRepo).exists(new IaasRolePolicyAttachmentId("POLICY", "ROLE"));

        assertTrue(service.exist(new IaasRolePolicyAttachmentId("POLICY", "ROLE")));
    }

    @Test
    public void testExist_ReturnsFalse_WhenAllAttachmentsDoesNotExist() {
        doReturn(false).when(mIaasRepo).exists(new IaasRolePolicyAttachmentId("POLICY", "ROLE"));

        assertFalse(service.exist(new IaasRolePolicyAttachmentId("POLICY", "ROLE")));
    }

    @Test
    public void testDelete_Set_CallsRepoDeleteWithIds() {
        doReturn(99).when(mIaasRepo).delete(Set.of(new IaasRolePolicyAttachmentId("POLICY_1", "ROLE_1"), new IaasRolePolicyAttachmentId("POLICY_2", "ROLE_2")));
        long deleteCount = service.delete(Set.of(new IaasRolePolicyAttachmentId("POLICY_1", "ROLE_1"), new IaasRolePolicyAttachmentId("POLICY_2", "ROLE_2")));

        assertEquals(99, deleteCount);
    }


    @Test
    public void testDelete_Id_CallsRepoDeleteWithIds() {
        doReturn(1).when(mIaasRepo).delete(Set.of(new IaasRolePolicyAttachmentId("POLICY", "ROLE")));
        long deleteCount = service.delete(new IaasRolePolicyAttachmentId("POLICY", "ROLE"));

        assertEquals(1, deleteCount);
    }

    @Test
    public void testGet_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> {
            IaasRolePolicyAttachmentId id = (IaasRolePolicyAttachmentId) inv.getArgument(0, Set.class).iterator().next();
            IaasRole role = new IaasRole(id.getRoleId());
            IaasPolicy policy = new IaasPolicy(id.getPolicyId());

            return List.of(new IaasRolePolicyAttachment(role, policy));
        }).when(mIaasRepo).get(anySet());

        IaasRolePolicyAttachment attachment = service.get(new IaasRolePolicyAttachmentId("POLICY", "ROLE"));

        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY")), attachment);
    }

    @Test
    public void testGetAll_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> {
            IaasRolePolicyAttachmentId id = (IaasRolePolicyAttachmentId) inv.getArgument(0, Set.class).iterator().next();
            IaasRole role = new IaasRole(id.getRoleId());
            IaasPolicy policy = new IaasPolicy(id.getPolicyId());

            return List.of(new IaasRolePolicyAttachment(role, policy));
        }).when(mIaasRepo).get(anySet());

        List<IaasRolePolicyAttachment> attachments = service.getAll(Set.of(new IaasRolePolicyAttachmentId("POLICY", "ROLE")));

        List<IaasRolePolicyAttachment> expected = List.of(new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY")));
        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByIds_ReturnAttachmentsFromRepo() {
        doAnswer(inv -> {
            IaasRolePolicyAttachmentId id = (IaasRolePolicyAttachmentId) inv.getArgument(0, Set.class).iterator().next();
            IaasRole role = new IaasRole(id.getRoleId());
            IaasPolicy policy = new IaasPolicy(id.getPolicyId());

            return List.of(new IaasRolePolicyAttachment(role, policy));
        }).when(mIaasRepo).get(anySet());

        List<IaasRolePolicyAttachment> attachments = service.getByIds(Set.of(() -> new IaasRolePolicyAttachmentId("POLICY", "ROLE")));

        List<IaasRolePolicyAttachment> expected = List.of(new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY")));

        assertEquals(expected, attachments);
    }

    @Test
    public void testGetByAccessorIds_ReturnsAttachmentFromRepo() {
        doAnswer(inv -> {
            IaasRolePolicyAttachmentId id = (IaasRolePolicyAttachmentId) inv.getArgument(0, Set.class).iterator().next();
            IaasRole role = new IaasRole(id.getRoleId());
            IaasPolicy policy = new IaasPolicy(id.getPolicyId());

            return List.of(new IaasRolePolicyAttachment(role, policy));
        }).when(mIaasRepo).get(anySet());

        IaasRolePolicyAttachmentAccessor accessor = new IaasRolePolicyAttachmentAccessor() {
            @Override
            public void setIaasRolePolicyAttachment(IaasRolePolicyAttachment attachment) {
            }
            @Override
            public IaasRolePolicyAttachment getIaasRolePolicyAttachment() {
                return new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY"));
            }
        };
        List<IaasRolePolicyAttachment> attachments = service.getByAccessorIds(Set.of(accessor));

        List<IaasRolePolicyAttachment> expected = List.of(new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY")));
        assertEquals(expected, attachments);
    }

    @Test
    public void testAdd_ReturnsAddedRepoEntities_AfterSavingAddEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).add(anyList());

        List<BaseIaasRolePolicyAttachment> additions = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0)),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"), LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0))
        );

        List<IaasRolePolicyAttachment> attachments = service.add(additions);

        List<IaasRolePolicyAttachment> expected = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).add(attachments);
        verify(mUpdateService).getAddEntities(additions);
    }

    @Test
    public void testPut_ReturnsPutRepoEntities_AfterSavingPutEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

        List<UpdateIaasRolePolicyAttachment> updates = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0)),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"), LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0))
        );

        List<IaasRolePolicyAttachment> attachments = service.put(updates);

        List<IaasRolePolicyAttachment> expected = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPutEntities(null, updates);
    }

    @Test
    public void testPatch_ReturnsPatchRepoEntities_AfterSavingPatchEntitiesFromUpdateService() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIaasRepo).put(anyList());

        doAnswer(inv -> {
            Iterator<IaasRolePolicyAttachmentId> it = inv.getArgument(0, Set.class).iterator();
            IaasRolePolicyAttachmentId id2 = it.next();
            IaasRolePolicyAttachmentId id1 = it.next();

            return List.of(
                new IaasRolePolicyAttachment(new IaasRole(id1.getRoleId()), new IaasPolicy(id1.getPolicyId()), LocalDateTime.of(1998, 1, 1, 0, 0), LocalDateTime.of(1999, 1, 1, 0, 0)),
                new IaasRolePolicyAttachment(new IaasRole(id2.getRoleId()), new IaasPolicy(id2.getPolicyId()), LocalDateTime.of(1998, 2, 1, 0, 0), LocalDateTime.of(1999, 2, 1, 0, 0))
            );
        }).when(mIaasRepo).get(anySet());

        List<UpdateIaasRolePolicyAttachment> updates = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0)),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"), LocalDateTime.of(2000, 2, 1, 0, 0), LocalDateTime.of(2001, 2, 1, 0, 0))
        );

        List<IaasRolePolicyAttachment> attachments = service.patch(updates);

        List<IaasRolePolicyAttachment> expected = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"), null, LocalDateTime.of(1999, 1, 1, 0, 0)),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"), null, LocalDateTime.of(1999, 2, 1, 0, 0))
        );

        assertEquals(expected, attachments);
        verify(mIaasRepo, times(1)).put(attachments);
        verify(mUpdateService).getPatchEntities(anyList(), eq(updates));
    }
}
