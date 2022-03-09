package io.company.brewcraft.security.idp;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.CreateGroupRequest;
import com.amazonaws.services.cognitoidp.model.CreateGroupResult;
import com.amazonaws.services.cognitoidp.model.DeleteGroupRequest;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.GetGroupRequest;
import com.amazonaws.services.cognitoidp.model.GetGroupResult;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.cognitoidp.model.UpdateGroupRequest;
import com.amazonaws.services.cognitoidp.model.UpdateGroupResult;
import com.amazonaws.services.cognitoidp.model.UserType;

public class AwsCognitoIdpClient implements IdentityProviderClient {
    private static final Logger logger = LoggerFactory.getLogger(AwsCognitoIdpClient.class);

    private final AWSCognitoIdentityProvider awsIdpProvider;

    private final String userPoolId;

    public AwsCognitoIdpClient(final AWSCognitoIdentityProvider awsIdpProvider, final String userPoolId) {
        this.awsIdpProvider = awsIdpProvider;
        this.userPoolId = userPoolId;
    }

    @Override
    public UserType createUser(final String userName, final Map<String, String> userAttr) {
        final List<AttributeType> attributeTypes = userAttr.entrySet().stream().map(attr -> getAttribute(attr.getKey(), attr.getValue())).toList();
        final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(userName).withDesiredDeliveryMediums(DeliveryMediumType.EMAIL).withUserAttributes(attributeTypes);
        logger.debug("Attempting to save user {} in cognito user pool {} ", userName, userPoolId);
        try {
            final AdminCreateUserResult adminCreateUserResult = this.awsIdpProvider.adminCreateUser(adminCreateUserRequest);
            logger.debug("Successfully Saved user {} in cognito user pool {} with status : {}", userName, userPoolId, adminCreateUserResult.getUser().getUserStatus());

            return adminCreateUserResult.getUser();
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void updateUser(final String userName, final Map<String, String> userAttr) {
        final List<AttributeType> attributeTypes = userAttr.entrySet().stream().map(attr -> getAttribute(attr.getKey(), attr.getValue())).toList();
        final AdminUpdateUserAttributesRequest adminUpdateUserRequest = new AdminUpdateUserAttributesRequest().withUserPoolId(userPoolId).withUsername(userName).withUserAttributes(attributeTypes);
        logger.debug("Attempting to update user {} in cognito user pool {} ", userName, userPoolId);
        AdminUpdateUserAttributesResult result = this.awsIdpProvider.adminUpdateUserAttributes(adminUpdateUserRequest);
        logger.debug("Successfully Updated user {} in cognito user pool {}", userName, userPoolId);
    }

    @Override
    public void deleteUser(final String userName) {
        final AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest().withUserPoolId(userPoolId).withUsername(userName);
        logger.debug("Attempting to delete user {} in cognito user pool {} ", userName, userPoolId);
        this.awsIdpProvider.adminDeleteUser(adminDeleteUserRequest);
        logger.debug("Successfully deleted user {} from cognito user pool {}", userName, userPoolId);
    }

    @Override
    public void addUserToGroup(final String userName, final String group) {
        final AdminAddUserToGroupRequest adminAddUserToGroupRequest = new AdminAddUserToGroupRequest().withUserPoolId(userPoolId).withUsername(userName).withGroupName(group);
        logger.debug("Attempting to add user {} in cognito user pool {} to group {}", userName, userPoolId, group);
        try {
            this.awsIdpProvider.adminAddUserToGroup(adminAddUserToGroupRequest);
            logger.debug("Successfully saved user {} in cognito user pool {} to group {}", userName, userPoolId, group);
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void removeUserFromGroup(final String userName, final String group) {
        final AdminRemoveUserFromGroupRequest adminRemoveUserFromGroupRequest = new AdminRemoveUserFromGroupRequest().withUserPoolId(userPoolId).withUsername(userName).withGroupName(group);
        logger.debug("Attempting to remove user {} in cognito user pool {} from group {}", userName, userPoolId, group);
        try {
            this.awsIdpProvider.adminRemoveUserFromGroup(adminRemoveUserFromGroupRequest);
            logger.debug("Successfully removed user {} in cognito user pool {} from group {}", userName, userPoolId, group);
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public GroupType getGroup(String group) {
        GetGroupRequest request = new GetGroupRequest()
                                    .withGroupName(group)
                                    .withUserPoolId(userPoolId);

        logger.debug("Attempting to get group {} in cognito user pool {}", group, userPoolId);
        try {
            GetGroupResult result = this.awsIdpProvider.getGroup(request);
            logger.debug("Successfully retrieved group {} in cognito user pool", group, userPoolId);
            return result.getGroup();
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public GroupType createGroup(final String group, final String roleArn) {
        final CreateGroupRequest createGroupRequest = new CreateGroupRequest()
                                                      .withUserPoolId(userPoolId)
                                                      .withGroupName(group)
                                                      .withRoleArn(roleArn);
        logger.debug("Attempting to create group {} in cognito user pool {}", group, userPoolId);
        try {
            CreateGroupResult result = this.awsIdpProvider.createGroup(createGroupRequest);
            logger.debug("Successfully created group {} in cognito user pool", group, userPoolId);

            return result.getGroup();
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public GroupType putGroup(String group, String roleArn) {
        if (groupExists(group)) {
            return updateGroup(group, roleArn);
        } else {
            return createGroup(group, roleArn);
        }
    }

    @Override
    public GroupType updateGroup(String group, String roleArn) {
        UpdateGroupRequest request = new UpdateGroupRequest()
                                     .withUserPoolId(userPoolId)
                                     .withGroupName(group)
                                     .withRoleArn(roleArn);

        logger.debug("Attempting to update group {} in cognito user pool {}", group, userPoolId);
        try {
            UpdateGroupResult result = this.awsIdpProvider.updateGroup(request);
            logger.debug("Successfully update group {} in cognito user pool", group, userPoolId);

            return result.getGroup();
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void deleteGroup(final String group) {
        final DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest().withUserPoolId(userPoolId).withGroupName(group);
        logger.debug("Attempting to delete group {} in cognito user pool {}", group, userPoolId);
        try {
            this.awsIdpProvider.deleteGroup(deleteGroupRequest);
            logger.debug("Successfully deleted group {} in cognito user pool", group, userPoolId);
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public boolean groupExists(final String group) {
        final GetGroupRequest getGroupRequest = new GetGroupRequest().withUserPoolId(userPoolId).withGroupName(group);
        logger.debug("Attempting to get group {} in cognito user pool {}", group, userPoolId);
        try {
            this.awsIdpProvider.getGroup(getGroupRequest);
            logger.debug("Successfully found group {} in cognito user pool {}", group, userPoolId);
            return true;
        } catch (ResourceNotFoundException e) {
            logger.debug("group {} in cognito user pool {} does not exist", group, userPoolId);
            return false;
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    private AttributeType getAttribute(final String name, final String value) {
        return new AttributeType().withName(name).withValue(value);
    }
}
