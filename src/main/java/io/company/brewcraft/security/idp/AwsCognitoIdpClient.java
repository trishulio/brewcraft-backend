package io.company.brewcraft.security.idp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.CreateGroupRequest;
import com.amazonaws.services.cognitoidp.model.DeleteGroupRequest;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.GetGroupRequest;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;

public class AwsCognitoIdpClient implements IdentityProviderClient {
    private static final Logger logger = LoggerFactory.getLogger(AwsCognitoIdpClient.class);

    private final AWSCognitoIdentityProvider awsIdpProvider;

    private final String userPoolId;

    public AwsCognitoIdpClient(final AWSCognitoIdentityProvider awsIdpProvider, final String userPoolId) {
        this.awsIdpProvider = awsIdpProvider;
        this.userPoolId = userPoolId;
    }

    @Override
    public void createUser(final String userName, final Map<String, String> userAttr) {
        final List<AttributeType> attributeTypes = userAttr.entrySet().stream().map(attr -> getAttribute(attr.getKey(), attr.getValue())).collect(Collectors.toList());
        final AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(userName).withDesiredDeliveryMediums(DeliveryMediumType.EMAIL).withUserAttributes(attributeTypes);
        logger.debug("Attempting to save user {} in cognito user pool {} ", userName, userPoolId);
        try {
            final AdminCreateUserResult adminCreateUserResult = this.awsIdpProvider.adminCreateUser(adminCreateUserRequest);
            logger.debug("Successfully Saved user {} in cognito user pool {} with status : {}", userName, userPoolId, adminCreateUserResult.getUser().getUserStatus());
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void updateUser(final String userName, final Map<String, String> userAttr) {
        final List<AttributeType> attributeTypes = userAttr.entrySet().stream().map(attr -> getAttribute(attr.getKey(), attr.getValue())).collect(Collectors.toList());
        final AdminUpdateUserAttributesRequest adminUpdateUserRequest = new AdminUpdateUserAttributesRequest().withUserPoolId(userPoolId).withUsername(userName).withUserAttributes(attributeTypes);
        logger.debug("Attempting to update user {} in cognito user pool {} ", userName, userPoolId);
        this.awsIdpProvider.adminUpdateUserAttributes(adminUpdateUserRequest);
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
    public void createUserGroup(final String group) {
        final CreateGroupRequest createGroupRequest = new CreateGroupRequest().withUserPoolId(userPoolId).withGroupName(group);
        logger.debug("Attempting to create group {} in cognito user pool {}", group, userPoolId);
        try {
            this.awsIdpProvider.createGroup(createGroupRequest);
            logger.debug("Successfully created group {} in cognito user pool", group, userPoolId);
        } catch (AWSCognitoIdentityProviderException e) {
            String msg = String.format("ErrorCode: %s; StatusCode: %s; Message: %s", e.getErrorCode(), e.getStatusCode(), e.getMessage());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public void deleteUserGroup(final String group) {
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
    public boolean userGroupExists(final String group) {
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
