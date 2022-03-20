package io.company.brewcraft.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.identitymanagement.model.AttachedPolicy;

import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public class IaasRolePolicyAttachmentIaasRepositoryTest {
    private IaasRolePolicyAttachmentIaasRepository repo;

    private AwsIamRolePolicyAttachmentClient mAttachmentClient;
    private BlockingAsyncExecutor mExecutor;

    @BeforeEach
    public void init() {
        mExecutor = TestUtils.mockExecutor();
        mAttachmentClient = mock(AwsIamRolePolicyAttachmentClient.class);
        repo = new IaasRolePolicyAttachmentIaasRepository(mAttachmentClient, mExecutor);
    }

    @Test
    public void testAdd_ReturnsAttachmentsFromClient_WhenAttachmentsAreNotNull() {
        List<IaasRolePolicyAttachment> mAttachments = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );

        List<IaasRolePolicyAttachment> attachments = repo.add(mAttachments);

        List<IaasRolePolicyAttachment> expected = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );
        assertEquals(expected, attachments);

        verify(mAttachmentClient).add("POLICY_1", "ROLE_1");
        verify(mAttachmentClient).add("POLICY_2", "ROLE_2");
    }

    @Test
    public void testPut_ReturnsAttachmentsFromClient_WhenAttachmentsAreNotNull() {
        List<IaasRolePolicyAttachment> mAttachments = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );

        List<IaasRolePolicyAttachment> attachments = repo.put(mAttachments);

        List<IaasRolePolicyAttachment> expected = List.of(
            new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")),
            new IaasRolePolicyAttachment(new IaasRole("ROLE_2"), new IaasPolicy("POLICY_2"))
        );
        assertEquals(expected, attachments);

        verify(mAttachmentClient).put("POLICY_1", "ROLE_1");
        verify(mAttachmentClient).put("POLICY_2", "ROLE_2");
    }

    @Test
    public void testDelete_CallsDeleteOnClient_WhenAttachmentsAreNotNull() {
        Set<IaasRolePolicyAttachmentId> ids = Set.of(
            new IaasRolePolicyAttachmentId("POLICY_1", "ROLE_1"),
            new IaasRolePolicyAttachmentId("POLICY_2", "ROLE_2")
        );

        repo.delete(ids);

        verify(mAttachmentClient).delete("POLICY_1", "ROLE_1");
        verify(mAttachmentClient).delete("POLICY_2", "ROLE_2");
    }

    @Test
    public void testExists_ReturnsBooleanMap_WhenIdsAreNotNull() {
        List<AttachedPolicy> mRoleAAttachedPolicies = List.of(new AttachedPolicy().withPolicyName("POLICY_1A"));
        List<AttachedPolicy> mRoleBAttachedPolicies = List.of(new AttachedPolicy().withPolicyName("POLICY_1B"));
        doReturn(mRoleAAttachedPolicies).when(mAttachmentClient).get("ROLE_A");
        doReturn(mRoleBAttachedPolicies).when(mAttachmentClient).get("ROLE_B");

        Set<IaasRolePolicyAttachmentId> ids = Set.of(
            new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"),
            new IaasRolePolicyAttachmentId("POLICY_2A", "ROLE_A"),
            new IaasRolePolicyAttachmentId("POLICY_1B", "ROLE_B"),
            new IaasRolePolicyAttachmentId("POLICY_2B", "ROLE_B")
        );

        Map<IaasRolePolicyAttachmentId, Boolean> exists = repo.exists(ids);

        Map<IaasRolePolicyAttachmentId, Boolean> expected = Map.of(
            new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"), true,
            new IaasRolePolicyAttachmentId("POLICY_2A", "ROLE_A"), false,
            new IaasRolePolicyAttachmentId("POLICY_1B", "ROLE_B"), true,
            new IaasRolePolicyAttachmentId("POLICY_2B", "ROLE_B"), false
        );
        assertEquals(expected, exists);
    }

    @Test
    public void testExists_Id_ReturnsTrue_WhenExistsMapReturnsTrueForKey() {
        repo = spy(repo);
        Map<IaasRolePolicyAttachmentId, Boolean> mExists = Map.of(
            new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"), true
        );
        doReturn(mExists).when(repo).exists(Set.of(new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A")));

        boolean exists = repo.exists(new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"));

        assertTrue(exists);
    }

    @Test
    public void testExists_Id_ReturnsFalse_WhenExistsMapReturnsFalseForKey() {
        repo = spy(repo);
        Map<IaasRolePolicyAttachmentId, Boolean> mExists = Map.of(
            new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"), false
        );
        doReturn(mExists).when(repo).exists(Set.of(new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A")));

        boolean exists = repo.exists(new IaasRolePolicyAttachmentId("POLICY_1A", "ROLE_A"));

        assertFalse(exists);
    }
}
