package io.company.brewcraft.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;

import io.company.brewcraft.model.IaasPolicy;

public class AwsIamPolicyClientTest {
    private AwsIamPolicyClient client;

    private AmazonIdentityManagement mAwsIamClient;
    private AwsArnMapper mAwsMapper;
    private IaasEntityMapper<Policy, IaasPolicy> mapper;

    @BeforeEach
    public void init() {
        mAwsIamClient = mock(AmazonIdentityManagement.class);
        mAwsMapper = mock(AwsArnMapper.class);
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ARN").when(mAwsMapper).getPolicyArn(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class).replace("_ARN", "")).when(mAwsMapper).getName(anyString());

        client = new AwsIamPolicyClient(mAwsIamClient, mAwsMapper, AwsIaasPolicyMapper.INSTANCE);
    }

    @Test
    public void testGet_ReturnsPolicyFromAwsRequest() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(mAwsMapper.getName(req.getPolicyArn()));
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        IaasPolicy policy = client.get("POLICY");

        assertEquals(new IaasPolicy("POLICY"), policy);
        verify(mAwsIamClient, times(1)).getPolicy(any());
    }

    @Test
    public void testDelete_ReturnsTrue_WhenDeleteRequestSucceeds() {
        assertTrue(client.delete("POLICY"));

        verify(mAwsIamClient, times(1)).deletePolicy(new DeletePolicyRequest().withPolicyArn("POLICY_ARN"));
    }

    @Test
    public void testDelete_ReturnsFalse_WhenDeleteRequestThrowsNoEntityException() {
        doThrow(NoSuchEntityException.class).when(mAwsIamClient).deletePolicy(new DeletePolicyRequest().withPolicyArn("POLICY_ARN"));

        assertFalse(client.delete("POLICY"));
    }

    @Test
    public void testAdd_ReturnsAddedPolicy() {
        doAnswer(inv -> {
            CreatePolicyRequest req = inv.getArgument(0, CreatePolicyRequest.class);

            Policy policy = new Policy()
                            .withPolicyName(req.getPolicyName())
                            .withDescription(req.getDescription())
                            .withPolicyId(req.getPolicyName() + "_ID")
                            .withArn(req.getPolicyName() + "_ARN");

            return new CreatePolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).createPolicy(any());

        IaasPolicy policy = client.add(new IaasPolicy("POLICY_1", "DOCUMENT", "DESCRIPTION_1", "POLICY_1_ARN", "POLICY_1_ID", null, null));

        IaasPolicy expected = new IaasPolicy("POLICY_1", null, "DESCRIPTION_1", "POLICY_1_ARN", "POLICY_1_ID", null, null);

        assertEquals(expected, policy);
        verify(mAwsIamClient, times(1)).createPolicy(any());
    }

    @Test
    public void testUpdate_ReturnsUpdatePolicy() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(mAwsMapper.getName(req.getPolicyArn()));
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        IaasPolicy policy = client.update(new IaasPolicy("POLICY_1", null, "DESCRIPTION_1", "POLICY_1_ARN", "POLICY_1_ID", null, null));

        IaasPolicy expected = new IaasPolicy("POLICY_1");
        assertEquals(expected, policy);

        verify(mAwsIamClient).updatePolicy(new UpdatePolicyRequest().withPolicyName("POLICY_1").withDescription("DESCRIPTION_1"));
        verify(mAwsIamClient).updateAssumePolicyPolicy(new UpdateAssumePolicyPolicyRequest().withPolicyName("POLICY_1").withPolicyDocument("DOCUMENT_1"));
    }

    @Test
    public void testExists_ReturnsTrue_WhenGetReturnsObject() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(req.getPolicyName());
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        assertTrue(client.exists("POLICY"));
    }

    @Test
    public void testExists_ReturnsFalse_WhenGetReturnsNull() {
        doThrow(NoSuchEntityException.class).when(mAwsIamClient).getPolicy(any(GetPolicyRequest.class));

        assertFalse(client.exists("POLICY"));
    }

    @Test
    public void testPut_CallsAdd_WhenExistIsFalse() {
        client = spy(client);
        doReturn(false).when(client).exists("POLICY_1");

        doAnswer(inv -> inv.getArgument(0, IaasPolicy.class)).when(client).add(any());

        assertEquals(new IaasPolicy("POLICY_1"), client.put(new IaasPolicy("POLICY_1")));
    }

    @Test
    public void testPut_CallsUpdate_WhenExistIsTrue() {
        client = spy(client);
        doReturn(true).when(client).exists("POLICY_1");

        doAnswer(inv -> inv.getArgument(0, IaasPolicy.class)).when(client).update(any());

        assertEquals(new IaasPolicy("POLICY_1"), client.put(new IaasPolicy("POLICY_1")));
    }
}
