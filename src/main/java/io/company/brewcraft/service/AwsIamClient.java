package io.company.brewcraft.service;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.CreateRoleResult;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.Role;

public class AwsIamClient {
    private AmazonIdentityManagement awsIamClient;

    public AwsIamClient(AmazonIdentityManagement awsIamClient) {
        this.awsIamClient = awsIamClient;
    }

    public Policy createPolicy(String policyName, String description, String policyDoc) {
        CreatePolicyRequest request = new CreatePolicyRequest()
                                            .withPolicyName(policyName)
                                            .withDescription(description)
                                            .withPolicyDocument(policyDoc);

        CreatePolicyResult result = this.awsIamClient.createPolicy(request);

        return result.getPolicy();
    }

    public Role createRole(String roleName, String rolePolicyDocument) {
        CreateRoleRequest roleRequest = new CreateRoleRequest()
                .withRoleName(roleName)
                .withAssumeRolePolicyDocument(rolePolicyDocument);

        CreateRoleResult roleResult = awsIamClient.createRole(roleRequest);

        return roleResult.getRole();
    }

    public void attachRolePolicy(String roleName, String policyArn) {
        AttachRolePolicyRequest attachRequest = new AttachRolePolicyRequest()
                .withRoleName(roleName)
                .withPolicyArn(policyArn);

        awsIamClient.attachRolePolicy(attachRequest);        
    }
}
