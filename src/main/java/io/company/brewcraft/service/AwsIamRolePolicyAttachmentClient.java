package io.company.brewcraft.service;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.Role;

public class AwsIamRolePolicyAttachmentClient {
    private AmazonIdentityManagement awsClient;

    public AwsIamRolePolicyAttachmentClient(AmazonIdentityManagement awsIamClient) {
        this.awsClient = awsIamClient;
    }
    
    public void get(String policyArn, String roleName) {
        throw new UnsupportedOperationException("AWS SDK doesn't support an get operation"); 
    }
    
    public void delete(String policyArn, String roleName) {
        DetachRolePolicyRequest request = new DetachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(roleName);

        DetachRolePolicyResult result = this.awsClient.detachRolePolicy(request);
    }
    
    public void add(String policyArn, String roleName) {
        AttachRolePolicyRequest request = new AttachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(roleName);
        
        AttachRolePolicyResult result = this.awsClient.attachRolePolicy(request);
    }
    
    public Role put(String roleName, String policyName) {
        throw new UnsupportedOperationException("AWS SDK doesn't support an update operation");
    }
}
