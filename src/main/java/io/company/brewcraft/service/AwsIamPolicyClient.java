package io.company.brewcraft.service;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyResult;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.Policy;

public class AwsIamPolicyClient {
    private AmazonIdentityManagement awsIamClient;

    public AwsIamPolicyClient(AmazonIdentityManagement awsIamClient) {
        this.awsIamClient = awsIamClient;
    }
    
    public Policy get(String policyArn) {
        GetPolicyRequest request = new GetPolicyRequest()
                                        .withPolicyArn(policyArn);

        GetPolicyResult result = this.awsIamClient.getPolicy(request);

        return result.getPolicy();
    }
    
    public void delete(String policyArn) {
        DeletePolicyRequest request = new DeletePolicyRequest()
                                          .withPolicyArn(policyArn);
        
        DeletePolicyResult result = this.awsIamClient.deletePolicy(request);
    }
    
    public Policy add(String policyName, String description, String policyDocument) {
        CreatePolicyRequest request = new CreatePolicyRequest()
                .withPolicyName(policyName)
                .withDescription(description)
                .withPolicyDocument(policyDocument);

        CreatePolicyResult result = this.awsIamClient.createPolicy(request);
        
        return result.getPolicy();   
    }
    
    public Policy put() {
        throw new UnsupportedOperationException("Update operations for the AWS policy has not been implemented because AWS SDK doesn't have a straight-forward approach to perform updates on policies. It requires using versions.");
    }
}
