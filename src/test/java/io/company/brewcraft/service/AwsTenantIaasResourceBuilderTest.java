package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;

import io.company.brewcraft.model.AwsDocumentTemplates;
import io.company.brewcraft.model.IaasBucketCorsConfiguration;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;

public class AwsTenantIaasResourceBuilderTest {
    private TenantIaasResourceBuilder builder;
    private AwsDocumentTemplates mTemplates;

    @BeforeEach
    public void init() {
        mTemplates = mock(AwsDocumentTemplates.class);
        this.builder = new AwsTenantIaasResourceBuilder(mTemplates);
    }

    @Test
    public void testGetRoleName_ReturnsRoleNameFromTemplateWithTenantName() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());

        String roleName = builder.getRoleName(new IaasIdpTenant("T1"));

        assertEquals("T1_ROLE_NAME", roleName);
    }

    @Test
    public void testBuildRole_ReturnsRoleObjectFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_DESCRIPTION").when(mTemplates).getTenantIaasRoleDescription(anyString());
        doReturn("ROLE_DOC").when(mTemplates).getCognitoIdAssumeRolePolicyDoc();

        IaasRole role = builder.buildRole(new IaasIdpTenant("T1"));

        IaasRole expected = new IaasRole("T1_ROLE_NAME", "T1_ROLE_DESCRIPTION", "ROLE_DOC", null, null, null, null, null);

        assertEquals(expected, role);
    }

    @Test
    public void testGetVfsPolicyName_ReturnsVfsPolicyNameFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());

        String policyName = builder.getVfsPolicyName(new IaasIdpTenant("T1"));

        assertEquals("T1_POLICY_NAME", policyName);
    }

    @Test
    public void testBuildVfsPolicy_ReturnsVfsPolicyObjectFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DESCRIPTION").when(mTemplates).getTenantVfsPolicyDescription(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_DOC").when(mTemplates).getTenantBucketPolicyDoc(anyString());

        IaasPolicy policy = builder.buildVfsPolicy(new IaasIdpTenant("T1"));

        IaasPolicy expected = new IaasPolicy("T1_POLICY_NAME", "T1_POLICY_DOC", "T1_POLICY_DESCRIPTION", null, null, null, null);

        assertEquals(expected, policy);
    }

    @Test
    public void testGetObjectStoreName_ReturnsObjectStoreNameFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

        String objectStoreName = builder.getObjectStoreName(new IaasIdpTenant("T1"));

        assertEquals("T1_OBJECT_STORE_NAME", objectStoreName);
    }

    @Test
    public void testBuildObjectStore_ReturnsObjectStoreObjectFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

        IaasObjectStore objectStore = builder.buildObjectStore(new IaasIdpTenant("T1"));

        IaasObjectStore expected = new IaasObjectStore("T1_OBJECT_STORE_NAME");

        assertEquals(expected, objectStore);
    }

    @Test
    public void testBuildVfsAttachmentId_ReturnsAttachmentIdFromTenant() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME").when(mTemplates).getTenantIaasRoleName(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class) + "_POLICY_NAME").when(mTemplates).getTenantVfsPolicyName(anyString());

        IaasRolePolicyAttachmentId id = builder.buildVfsAttachmentId(new IaasIdpTenant("T1"));

        IaasRolePolicyAttachmentId expected = new IaasRolePolicyAttachmentId("T1_ROLE_NAME", "T1_POLICY_NAME");
        assertEquals(expected, id);
    }

    @Test
    public void testBuildAttachment_ReturnsAttachmentFromRoleAndPolicy() {
        IaasRolePolicyAttachment attachment = builder.buildAttachment(new IaasRole("T1_ROLE"), new IaasPolicy("T1_POLICY"));

        IaasRolePolicyAttachment expected = new IaasRolePolicyAttachment(new IaasRole("T1_ROLE"), new IaasPolicy("T1_POLICY"));

        assertEquals(expected, attachment);
    }

    @Test
    public void testBuildBucketCrossOriginConfiguration_ReturnsCrossOriginConfig() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_OBJECT_STORE_NAME").when(mTemplates).getTenantVfsBucketName(anyString());

        IaasBucketCorsConfiguration actual = builder.buildBucketCrossOriginConfiguration(new IaasIdpTenant("T1"));

        assertEquals("T1_OBJECT_STORE_NAME", actual.getId());
        assertEquals("T1_OBJECT_STORE_NAME", actual.getBucketName());

        assertEquals(2, actual.getBucketCrossOriginConfiguration().getRules().size());
        assertEquals(List.of("*"), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedHeaders());
        assertEquals(List.of(AllowedMethods.PUT, AllowedMethods.POST, AllowedMethods.DELETE), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedMethods());
        assertEquals(List.of("http://wwww.localhost:3000"), actual.getBucketCrossOriginConfiguration().getRules().get(0).getAllowedOrigins());
        assertEquals(List.of("*"), actual.getBucketCrossOriginConfiguration().getRules().get(1).getAllowedHeaders());
        assertEquals(List.of(AllowedMethods.PUT, AllowedMethods.POST, AllowedMethods.DELETE), actual.getBucketCrossOriginConfiguration().getRules().get(1).getAllowedMethods());
        assertEquals(List.of("https://apollo.brewcraft.io"), actual.getBucketCrossOriginConfiguration().getRules().get(1).getAllowedOrigins());
    }
}
