package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.AttachedPolicy;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;

public class AwsIamRolePolicyAttachmentClient {
    private static final Logger log = LoggerFactory.getLogger(AwsIamRolePolicyAttachmentClient.class);

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

            marker = result.isTruncated() ? result.getMarker() : null;

            List<AttachedPolicy> policies = result.getAttachedPolicies();
            allPolicies.addAll(policies);

        } while (marker != null);

        return allPolicies;
    }

    public boolean delete(String policyName, String roleName) {
        String policyArn = this.arnMapper.getPolicyArn(policyName);

        DetachRolePolicyRequest request = new DetachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(roleName);
        try {
            DetachRolePolicyResult result = this.awsClient.detachRolePolicy(request);
            return true;
        } catch(NoSuchEntityException e) {
            log.error("Failed to delete attachment with role: '{}' and policy: '{}'", roleName, policyArn);
            return false;
        }
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
