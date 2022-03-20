package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionResult;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsRequest;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.PolicyVersion;

public class AwsIamPolicyClient {
    private static final Logger log = LoggerFactory.getLogger(AwsIamPolicyClient.class);

    private AmazonIdentityManagement awsIamClient;
    private AwsArnMapper awsMapper;

    public AwsIamPolicyClient(AmazonIdentityManagement awsIamClient, AwsArnMapper awsMapper) {
        this.awsIamClient = awsIamClient;
        this.awsMapper = awsMapper;
    }

    public Policy get(String policyName) {
        Policy policy = null;

        String policyArn = awsMapper.getPolicyArn(policyName);
        GetPolicyRequest request = new GetPolicyRequest()
                                       .withPolicyArn(policyArn);
        try {
            GetPolicyResult result = this.awsIamClient.getPolicy(request);
            policy = result.getPolicy();
        } catch (NoSuchEntityException e) {
            log.error("No policy found with arn: {}", policyArn);
        }

        return policy;
    }

    public boolean delete(String policyName) {
        boolean success = false;

        String policyArn = awsMapper.getPolicyArn(policyName);
        DeletePolicyRequest request = new DeletePolicyRequest()
                                          .withPolicyArn(policyArn);

        try {
            DeletePolicyResult result = this.awsIamClient.deletePolicy(request);
            success = true;
        } catch (NoSuchEntityException e) { 
            log.error("Failed to policy with ARN: {}", policyArn);
        }

        return success;
    }

    public Policy add(String policyName, String description, String policyDocument) {
        CreatePolicyRequest request = new CreatePolicyRequest()
                .withPolicyName(policyName)
                .withDescription(description)
                .withPolicyDocument(policyDocument);

        CreatePolicyResult result = this.awsIamClient.createPolicy(request);

        return result.getPolicy();
    }

    public boolean exists(String policyName) {
        return get(policyName) != null;
    }

    public Policy update(String policyName, String policyDocument) {
        String policyArn = awsMapper.getPolicyArn(policyName);
        CreatePolicyVersionRequest request = new CreatePolicyVersionRequest()
                                                 .withPolicyArn(policyArn)
                                                 .withPolicyDocument(policyDocument)
                                                 .withSetAsDefault(true);

        CreatePolicyVersionResult result = awsIamClient.createPolicyVersion(request);
        // A policy can have max 5 versions. After that the existing versions need to be deleted.
        // This implementation removes all existing versions and retains only 1 at each time.
        deleteNonDefaultPolicyVersions(policyName);

        // TODO: Perform a test to make sure that when a new policy version is created, the get policy
        // returns the default document and not the original policy document.
        return get(policyName);
    }

    public Policy put(String policyName, String description, String policyDocument) {
        if (!exists(policyName)) {
            return add(policyName, description, policyDocument);
        } else {
            log.info("Updating the already existing policy. Description value will not be updated");
            return update(policyName, policyDocument);
        }
    }

    public List<PolicyVersion> getPolicyVersions(String policyName) {
        String policyArn = awsMapper.getPolicyArn(policyName);
        List<PolicyVersion> policyVersions = new ArrayList<>();

        String marker = null;
        do {
            ListPolicyVersionsRequest listRequest = new ListPolicyVersionsRequest()
                                                        .withPolicyArn(policyArn)
                                                        .withMarker(marker);

            ListPolicyVersionsResult listResult = this.awsIamClient.listPolicyVersions(listRequest);

            marker = listResult.getIsTruncated() ? listResult.getMarker() : null;

            List<PolicyVersion> policyVersionsSubset = listResult.getVersions();
            policyVersions.addAll(policyVersionsSubset);
        } while (marker != null);

        return policyVersions;
    }

    public void deleteNonDefaultPolicyVersions(String policyName) {
        List<PolicyVersion> versions = getPolicyVersions(policyName);

        String policyArn = awsMapper.getPolicyArn(policyName);
        versions.stream()
                .filter(version -> !version.isDefaultVersion())
                .forEach(version -> deletePolicyVersion(policyArn, version.getVersionId()));
    }

    public void deletePolicyVersion(String policyArn, String versionId) {
        DeletePolicyVersionRequest request = new DeletePolicyVersionRequest()
                                                 .withPolicyArn(policyArn)
                                                 .withVersionId(versionId);

        DeletePolicyVersionResult result = awsIamClient.deletePolicyVersion(request);
    }
}
