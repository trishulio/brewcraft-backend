package io.company.brewcraft.service;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.CreateRoleResult;
import com.amazonaws.services.identitymanagement.model.DeleteRoleRequest;
import com.amazonaws.services.identitymanagement.model.DeleteRoleResult;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.identitymanagement.model.GetRoleResult;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.UpdateRoleRequest;
import com.amazonaws.services.identitymanagement.model.UpdateRoleResult;

public class AwsIamRoleClient {
    private AmazonIdentityManagement awsIamClient;

    public AwsIamRoleClient(AmazonIdentityManagement awsIamClient) {
        this.awsIamClient = awsIamClient;
    }
    
    public Role get(String roleName) {
        GetRoleRequest request = new GetRoleRequest()
                                 .withRoleName(roleName);

        GetRoleResult result = this.awsIamClient.getRole(request);

        return result.getRole();
    }
    
    public void delete(String roleName) {
        DeleteRoleRequest request = new DeleteRoleRequest()
                                          .withRoleName(roleName);
        
        DeleteRoleResult result = this.awsIamClient.deleteRole(request);
    }
    
    public Role add(String roleName, String description, String assumePolicyDocument) {
        CreateRoleRequest request = new CreateRoleRequest()
                .withRoleName(roleName)
                .withAssumeRolePolicyDocument(assumePolicyDocument)
                .withDescription(description);

        CreateRoleResult result = this.awsIamClient.createRole(request);
        
        return result.getRole();   
    }
    
    public Role put(String roleName, String description) {
        UpdateRoleRequest request = new UpdateRoleRequest()
                                    .withRoleName(roleName)
                                    .withDescription(description);

        UpdateRoleResult result = this.awsIamClient.updateRole(request);

        return new Role()
            .withDescription(description)
            .withDescription(description);
    }
}
