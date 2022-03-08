package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.AttachedPolicy;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesResult;

public class AwsIamRolePolicyAttachmentClient {
    private AmazonIdentityManagement awsClient;
    private AwsArnMapper arnMapper;

    public AwsIamRolePolicyAttachmentClient(AmazonIdentityManagement awsIamClient, AwsArnMapper arnMapper) {
        this.awsClient = awsIamClient;
        this.arnMapper = arnMapper;
    }
    
    public List<AttachedPolicy> get(String roleName) {
        List<AttachedPolicy> allPolicies = new ArrayList<>();

        String marker = null;
        do {
            ListAttachedRolePoliciesRequest request = new ListAttachedRolePoliciesRequest()
                                                      .withRoleName(roleName)
                                                      .withMarker(marker);
            ListAttachedRolePoliciesResult result = this.awsClient.listAttachedRolePolicies(request);
            
            if (result.isTruncated()) {
                marker = result.getMarker();
            }

            List<AttachedPolicy> policies = result.getAttachedPolicies();
            allPolicies.addAll(policies); 
            
        } while (marker != null);
        
        return allPolicies;
    }

    public void delete(String policyName, String roleName) {
        String policyArn = this.arnMapper.getPolicyArn(policyName);

        DetachRolePolicyRequest request = new DetachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(roleName);

        DetachRolePolicyResult result = this.awsClient.detachRolePolicy(request);
    }

    public void add(String policyName, String roleName) {
        String policyArn = this.arnMapper.getPolicyArn(policyName);

        AttachRolePolicyRequest request = new AttachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(roleName);

        AttachRolePolicyResult result = this.awsClient.attachRolePolicy(request);
    }

    public void put(String policyName, String roleName) {
        // TODO: Test if the call to add for this entity is idempotent.
        // If not, then this implementation won't work.
        add(policyName, roleName);
    }
}
