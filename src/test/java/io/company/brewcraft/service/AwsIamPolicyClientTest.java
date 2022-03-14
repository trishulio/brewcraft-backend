package io.company.brewcraft.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsRequest;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.PolicyVersion;

public class AwsIamPolicyClientTest {
    private AwsIamPolicyClient client;
    
    private AmazonIdentityManagement mAwsClient;
    private AwsArnMapper mArnMapper;
    
    @BeforeEach
    public void init() {
        mAwsClient = mock(AmazonIdentityManagement.class);
        mArnMapper = mock(AwsArnMapper.class);

        client = new AwsIamPolicyClient(mAwsClient, mArnMapper);
    }
    
    @Test
    public void testGet_ReturnsPolicy_WhenNameIsNotNull() {
        doReturn("POLICY_1_ARN").when(mArnMapper).getPolicyArn("POLICY_1");

        GetPolicyRequest request = new GetPolicyRequest()
                                    .withPolicyArn("POLICY_1_ARN");
        
        GetPolicyResult mResult = new GetPolicyResult().withPolicy(new Policy().withPolicyName("POLICY_1"));
        doReturn(mResult).when(mAwsClient).getPolicy(request);
        
        Policy policy = client.get("POLICY_1");
        
        assertEquals(new Policy().withPolicyName("POLICY_1"), policy);
    }
    
    @Test
    public void testDelete_CallsDelete_WhenNameIsNotNull() {
        doReturn("POLICY_1_ARN").when(mArnMapper).getPolicyArn("POLICY_1");

        DeletePolicyRequest request = new DeletePolicyRequest()
                                    .withPolicyArn("POLICY_1_ARN");

        client.delete("POLICY_1");
        verify(mAwsClient, times(1)).deletePolicy(request);
    }
    
    @Test
    public void testAdd_ReturnsPolicy() {
        CreatePolicyRequest request = new CreatePolicyRequest()
                                          .withPolicyName("POLICY_1")
                                          .withDescription("DESCRIPTION")
                                          .withPolicyDocument("DOCUMENT");
        
        CreatePolicyResult mResult = new CreatePolicyResult().withPolicy(new Policy().withPolicyName("POLICY_1"));
        doReturn(mResult).when(mAwsClient).createPolicy(request);
        
        Policy policy = client.add("POLICY_1", "DESCRIPTION", "DOCUMENT");
        
        assertEquals(new Policy().withPolicyName("POLICY_1"), policy);
    }
    
    @Test
    public void testExists_ReturnsTrue_WhenGetPolicyDontThrowException() {
        client = spy(client);
        doReturn(new Policy().withPolicyName("POLICY_1")).when(client).get("POLICY_1");
        
        boolean exists = client.exists("POLICY_1");
        assertTrue(exists);
    }
    
    @Test
    public void testExists_ReturnsFalse_WhenGetPolicyThrowException() {
        client = spy(client);
        doThrow(NoSuchEntityException.class).when(client).get("POLICY_1");
        
        boolean exists = client.exists("POLICY_1");
        assertFalse(exists);
    }

    @Test
    public void testUpdate_CreatesNewDefaultPolicyVersionAndDeletesExistingOnes() {
        client = spy(client);
        doNothing().when(client).deleteNonDefaultPolicyVersions("POLICY_1");        
        doReturn("POLICY_1_ARN").when(mArnMapper).getPolicyArn("POLICY_1");
        doReturn(new Policy().withPolicyName("POLICY_NAME")).when(client).get("POLICY_1");

        CreatePolicyVersionRequest request = new CreatePolicyVersionRequest()
                                                 .withPolicyArn("POLICY_1_ARN")
                                                 .withPolicyDocument("DOCUMENT")
                                                 .withSetAsDefault(true);
        
        Policy policy = client.update("POLICY_1", "DOCUMENT");
        
        assertEquals(new Policy().withPolicyName("POLICY_NAME"), policy);
        
        InOrder order = inOrder(client, mAwsClient);
        order.verify(mAwsClient, times(1)).createPolicyVersion(request);
        order.verify(client, times(1)).deleteNonDefaultPolicyVersions("POLICY_1");        
    }
    
    @Test
    public void testPut_CallsAdd_WhenExistsReturnsFalse() {
        client = spy(client);
        doReturn(false).when(client).exists("POLICY_1");
        doReturn(new Policy().withPolicyId("POLICY_1")).when(client).add("POLICY_1", "DESCRIPTION", "DOCUMENT");
        
        Policy policy = client.put("POLICY_1", "DESCRIPTION", "DOCUMENT");
        
        assertEquals(new Policy().withPolicyId("POLICY_1"), policy);
    }
    
    @Test
    public void testPut_CallsUpdate_WhenExistsReturnsTrue() {
        client = spy(client);
        doReturn(true).when(client).exists("POLICY_1");
        doReturn(new Policy().withPolicyId("POLICY_1")).when(client).update("POLICY_1", "DOCUMENT");
        
        Policy policy = client.put("POLICY_1", "DESCRIPTION", "DOCUMENT");
        
        assertEquals(new Policy().withPolicyId("POLICY_1"), policy);
    }
    
    @Test
    public void testGetPolicyVersions_ReturnsAllPolicyVersions() {
        doReturn("POLICY_1_ARN").when(mArnMapper).getPolicyArn("POLICY_1");

        ListPolicyVersionsRequest req1 = new ListPolicyVersionsRequest()
                                        .withPolicyArn("POLICY_1_ARN");
        
        ListPolicyVersionsResult res1 = new ListPolicyVersionsResult()
                                            .withIsTruncated(true)
                                            .withMarker("MARKER")
                                            .withVersions(new PolicyVersion().withVersionId("VERSION_1"));
        
        ListPolicyVersionsRequest req2 = new ListPolicyVersionsRequest()
                                        .withPolicyArn("POLICY_1_ARN")
                                        .withMarker("MARKER");

        ListPolicyVersionsResult res2 = new ListPolicyVersionsResult()
                                        .withIsTruncated(false)
                                        .withVersions(new PolicyVersion().withVersionId("VERSION_2"));
        
        doReturn(res1).when(mAwsClient).listPolicyVersions(req1);
        doReturn(res2).when(mAwsClient).listPolicyVersions(req2);
        
        List<PolicyVersion> versions = client.getPolicyVersions("POLICY_1");
        
        List<PolicyVersion> expected = List.of(
            new PolicyVersion().withVersionId("VERSION_1"),
            new PolicyVersion().withVersionId("VERSION_2")
        );
        assertEquals(expected, versions);
    }
    
    @Test
    public void testDeleteNonDefaultPolicyVersions_DeletesPolicyVersionWithNonDefaultAsFalse() {
        doReturn("POLICY_1_ARN").when(mArnMapper).getPolicyArn("POLICY_1");

        client = spy(client);
        List<PolicyVersion> versions = List.of(
            new PolicyVersion().withVersionId("VERSION_1").withIsDefaultVersion(false),
            new PolicyVersion().withVersionId("VERSION_2").withIsDefaultVersion(true),
            new PolicyVersion().withVersionId("VERSION_3").withIsDefaultVersion(false)
        );
        doReturn(versions).when(client).getPolicyVersions("POLICY_1");
        
        client.deleteNonDefaultPolicyVersions("POLICY_1");
        
        verify(client, times(1)).deletePolicyVersion("POLICY_1_ARN", "VERSION_1");
        verify(client, times(1)).deletePolicyVersion("POLICY_1_ARN", "VERSION_3");
        
        verify(client, times(0)).deletePolicyVersion("POLICY_1_ARN", "VERSION_2");
    }
    
    @Test
    public void testDeletePolicyVersion_SendsDeletePolicyVersionRequest() {
        DeletePolicyVersionRequest request = new DeletePolicyVersionRequest()
                                                .withPolicyArn("POLICY_1_ARN")
                                                .withVersionId("VERSION_1");

        client.deletePolicyVersion("POLICY_1_ARN", "VERSION_1");

        verify(mAwsClient, times(1)).deletePolicyVersion(request);
    }
}
